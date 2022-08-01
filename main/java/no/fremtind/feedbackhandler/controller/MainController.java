package no.fremtind.feedbackhandler.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import no.fremtind.feedbackhandler.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@CrossOrigin(origins="http://localhost:3000")
@RestController
public class MainController {
    @Autowired
    private HenteManager henteManager;

    //Alle tilbakemeldinger vil bli sendt til frontend når man oppdaterer en eller flere tilbakemeldinger
    //Tilbakemeldinger vil alltid bli sendt til frontend med alle tilhørende attributter vedlagt.


    @GetMapping("/")
    public String index(){
        return ("Spring boot OK");
    }


    /**
     * Bruker ID-er til å legge inn en eksisterende tag på så mange eksisterende tilbakemeldinger man ønsker.
     * @param objectNode Tilsvarer en JSON body bestående av "tag" og "idTilbakemeldinger"
     *                   "tag" er en String med navnet på taggem som skal legges på.
     *                   "idTilbakemeldinger" er en liste med strings av ID-ene til tilbakemeldingne som taggen skal festes på.
     * @return Alle tilbakemeldinger som har blitt markert med taggen og oppfyller kravene til eksisterende filtere
     */
    @RequestMapping ("/addTagtoTilbakemeldinger")
    public ResponseEntity<List<Tilbakemelding>> postTilbakemelding(@RequestBody ObjectNode objectNode){
        //Finner alle idTilbakemeldinger String-s i JSON-en
        Iterator<JsonNode> iterator = objectNode.get("idTilbakemeldinger").elements();
        Iterable<JsonNode> iterable = () -> iterator;
        //Lager en liste av alle ID-ene
        List<Long> results = StreamSupport
                .stream(iterable.spliterator(), false)
                .map(id -> {
                    long tId = Long.valueOf(id.asText());
                    return tId;
                })
                .collect(Collectors.toList());
        /*
        if (results.size() == 0){
            //Ingen tilbakemeldinger med valgte ID-er funnet
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

         */
        henteManager.setTagToTilbakemeldinger(objectNode.get("tag").asText(), results);

        return new ResponseEntity(henteManager.getTilbakemeldinger(), HttpStatus.ACCEPTED);
    }

    /**
     * Setter individuelle filtere som skal brukes for å finne relevante tilbakemeldinger
     * @param attributter Attributt-objekter med en nøkkel tilsvarende kolonnenavn, og en value tilsvarende søkeordet.
     * @return Alle tilbakemeldinger som oppfyller attributtspørringen.
     */
    @RequestMapping("/setAttributter")
    public ResponseEntity<List<Tilbakemelding>> setAttributtter(@RequestBody HashMap<String, List<String>> attributter){
        henteManager.setAttributter(attributter);
        return new ResponseEntity(henteManager.getTilbakemeldinger(), HttpStatus.ACCEPTED);
    }

    /**
     * Henter alle tags som er lagret i hele system, typisk brukt for autocompletion.
     * @return Alle tags som eksisterer i systemet.
     */
    @GetMapping("/getAllTags")
    public ResponseEntity<List<Tag>> getAllTags(){
        return ResponseEntity.ok(henteManager.getTags());
    }

    /**
     * Henter alle tilbakemeldinger som oppfyller spørringen som ble spurt sist.
     * @return Oppdatert liste med alle tilbakemeldinger.
     */
    @GetMapping("/getAllTilbakemeldinger")
    public ResponseEntity<List<Tilbakemelding>> getAllTilbakemeldinger(){
        return new ResponseEntity(henteManager.getTilbakemeldinger(), HttpStatus.ACCEPTED);
    }

    /**
     * Bruker ID-er til å legger til en kommentar på en tilbakemelding.
     * @param objectNode Tilsvarer en JSON body bestående av "tilbakemeldingId" og "kommentar"
     *                   "tilbakemeldingId" er en String av ID-en til tilbakemeldingen kommentaren skal legges på.
     *                   "kommentar" er en String av selve kommentaren som skal festes på tilbakemeldingen.
     * @return Oppdatert iste med alle tilbakemeldinger.
     */
    @RequestMapping(path="addKommentarToTilbakemelding",method = RequestMethod.POST,consumes ="application/json")
    public ResponseEntity<List<Tilbakemelding>> addKommentar(@RequestBody ObjectNode objectNode){
        henteManager.setTilbakemeldingKommentar(objectNode.get("tilbakemeldingId").asLong(),objectNode.get("kommentar").asText());
        return new ResponseEntity(henteManager.getTilbakemeldinger(), HttpStatus.ACCEPTED);
    }

    /**
     * Oppretter en ny tag i systemet.
     * @param nyTag String med navnet til den nye taggen.
     * @return Navnet på taggen som ble lagt til.
     */
    @PostMapping(path="/addTag", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Tilbakemelding>> addTag(@RequestBody String nyTag){
        henteManager.tagRepository.save(new Tag(null, nyTag));
        return new ResponseEntity(henteManager.getTilbakemeldinger(), HttpStatus.CREATED);

    }

}
