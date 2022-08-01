package no.fremtind.feedbackhandler.controller;

import no.fremtind.feedbackhandler.model.*;
import no.fremtind.feedbackhandler.util.EAttributt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class HenteManager{

    //Spørringsvariabler (Hvilke verdier inkluderes i spørring?)
    private Map<String, List<String>> attributtMap = new HashMap<>();


    //Komponenter
    @Autowired
    public TilbakemeldingRepository tilbakemeldingRepository;

    @Autowired
    public TagRepository tagRepository;


    /**
     * Setter søkeparameterene til hentemenager som sjekkes ved bruk av getTilbakemelding()
     * @param attributter er liste med attributt objekter. Disse består av en Key-verdi som tilsvarer en kolonne i tabellen, og en value som er verdien vi søker etter i kolonnen med.
     * Hver eneste attributt vil bli OR-et som default.
     */
    public void setAttributter(HashMap<String, List<String>> attributter){
        attributtMap = attributter;
    }


    /**
     * Hent tilbakemeldinger basert på aktive søkevariabler som er satt i hente-manager med setAttributter()
     * @return Liste med tilbakemeldinger som finnes innenfor søkeparameterene
     */
    public List<Tilbakemelding> getTilbakemeldinger(){
        List<Specification> specifications = new ArrayList<>();

        //Finner filtreringsverdien i kolonnenavnene man finner i AttributtMap-et
        for (String kolonne : attributtMap.keySet())
        {
            if (attributtMap.get(kolonne).size() > 0 ) {
                specifications.add(
                        QueryInterpeter.getSpecificationAtri(
                                attributtMap.get(kolonne).stream()
                                        .map(atri -> new Attributt(kolonne, atri))
                                        .collect(Collectors.toList())));
            }
        }
        if (specifications.size() == 0){
            //Frontend la ikke inn dato av en eller annen grunn, kjør backup:
            Date sqlDate1 = Date.valueOf("2021-07-01");
            java.util.Date utilDate1 = new java.util.Date(sqlDate1.getTime());
            Date sqlDate2 = Date.valueOf("2023-01-01");
            java.util.Date utilDate2 = new java.util.Date(sqlDate2.getTime());
            specifications.add(QueryInterpeter.getSpecificationInterval(utilDate1.getTime(), utilDate2.getTime()));
        }

        //Hent med definerte filtere (Specification)
        return tilbakemeldingRepository.findAll(specifications.stream()
                .reduce((specificationRoot, specification) -> specificationRoot.and(specification))
                .get());

    }

    /**
     * Hent alle eksisterende tags i systemet.
     * @return Alle tags i database
     */
    public List<Tag> getTags(){
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(tagRepository.findAll().iterator(), Spliterator.SORTED), false).collect(Collectors.toList());
    }

    /**
     * Setter en kommentar på 1 tilbakemelding og oppdaterer databasen.
     * @param tilbakemeldingId Tilbakemelding som skal kommenteres
     * @param kommentar Kommentar som skal festes på tilbakemeldingen
     */
    public void setTilbakemeldingKommentar(long tilbakemeldingId, String kommentar){
        Tilbakemelding tb = tilbakemeldingRepository.findTilbakemeldingById(tilbakemeldingId);
        tb.setKommentar(kommentar);
        tilbakemeldingRepository.save(tb);
    }

    /**
     * Legger til tag på alle valgte tilbakemeldinger i hente-manager
     * @param tag tag som skal legges inn på tilbakemeldingene
     */
    public void addTilbakemeldingerTag(Tag tag){
        for (Tilbakemelding tb : getTilbakemeldinger()){
            tb.addTag(tag);
            tilbakemeldingRepository.save(tb);
        }
    }

    /**
     * Fjerner en tag fra en tilbakemelding om taggen er festet på den. Hvis den ikke er festet på returneres tilbakemeldingen uforandret.
     * @param idTag ID-en til tagen som skal fjernes
     * @param idTilbakemelding ID-en til tilbakemeldingen taggen skal fjernes fra
     * @return Tilbakemeldingen etter det er oppdatert
     */
    public Tilbakemelding removeTagFromTilbakemelding(long idTag, long idTilbakemelding){
        Tag tag = tagRepository.findById(idTag);
        Tilbakemelding tilbakemelding = tilbakemeldingRepository.findTilbakemeldingById(idTilbakemelding);
        if (tilbakemelding.getTags().contains(tag)){
            tilbakemelding.getTags().remove(tag);
            tilbakemeldingRepository.save(tilbakemelding);
        }
        return tilbakemelding;
    }

    /**
     * Finner tag og fester taggen til tilbakemeldingene med riktig ID så lenge tilbakemelding ikke allerede inneholder taggen.
     * @param idTag ID-en til tagen man vil feste på tilbakemeldingene
     * @param idTilbakemeldinger List med ID-ene til tilbakameldingene som skal bli festet på
     * @return Liste med tilbakemeldingene som er forandret.
     */
    public void setTagToTilbakemeldinger(String idTag, List<Long> idTilbakemeldinger){
        Tag tag = tagRepository.findByNavn(idTag);
        if (tag == null){
            //Ingen tag med dette navnet finnes, så vi lager en!
            tagRepository.save(new Tag(null, idTag));
            tag = tagRepository.findByNavn(idTag);
        }

        //Markerer for Lambda-funksjonen at tag er final
        Tag finalTag = tag;

        idTilbakemeldinger.stream()
                .filter(tbID -> tbID != null)
                .map(tbID ->
                {
                    Tilbakemelding tb = tilbakemeldingRepository.findTilbakemeldingById(tbID.longValue());
                        return tb;
                })
                .filter(tb -> !(tb.getTags().contains(finalTag)))
                .forEach(tb ->
                {
                    tb.addTag(finalTag);
                    tilbakemeldingRepository.save(tb);
                });
        tagRepository.save(finalTag);
    }

    /**
     * Testefunksjon for å sette hentemanageren i en tilstand som gir testbare resultater
     * Kommenter inn ved behov, men HUSK Å SETTE PÅ setTest() I FeedBackHandlerApplication sin Logger-funksjon!
     */
    //TODO: Implementer som JUnit tester istedenfor
    /*
    public void setTest(){
        //Testing/placeholder begin

        List<String> atribDistrib = new ArrayList<>();
        atribDistrib.add("Fremtind");
        atribDistrib.add("DNB");
        atribDistrib.add("SB1");
        List<String> atribScore = new ArrayList<>();
        atribScore.add("5");
        List<String> atribDato = new ArrayList<>();

        Date sqlDate1 = Date.valueOf("2021-07-01");
        java.util.Date utilDate1 = new java.util.Date(sqlDate1.getTime());
        Date sqlDate2 = Date.valueOf("2023-01-01");
        java.util.Date utilDate2 = new java.util.Date(sqlDate2.getTime());
        atribDato.add(Long.toString(utilDate1.getTime()));
        atribDato.add(Long.toString(utilDate2.getTime()));
        attributtMap.put(new Attributt(EAttributt.DATO, "test").key, atribDato);
        attributtMap.put(new Attributt(EAttributt.DISTRIBUTOER, "test").key, atribDistrib);
        attributtMap.put(new Attributt(EAttributt.SCORE, "test").key, atribScore);
    }

     */

}
