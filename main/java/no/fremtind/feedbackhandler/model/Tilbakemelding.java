package no.fremtind.feedbackhandler.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="Tilbakemelding")
@Getter
@Setter
@AllArgsConstructor
public class Tilbakemelding implements Serializable {

    //Obligatorisk

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    //Hvilket team kom denne tilbakemeldingen fra? BM, SeOpp, motor etc.
    private String kilde;
    //Når ble tilbakemeldingen postet?
    private Date dato;
    //Scoren bruker ga i tilbakemelding
    private int score;

    //Valgfrie parametere

    //Nummer for kontekst. Typisk org. nummer, skadenummer,etc.
    private int kontekstNummer;
    //Teksten en bruker eventuelt skrev med i tilbakemeldingen sin
    @Lob
    private String tilbakemeldingstekst;
    //Hvem var distributør for kunde? DNB, SB1, Fremtind?
    private String distributoer;
    //Hvor i løsningen kom denne tilbakemeldingen fra? Syntaks burde være feks.
    @Lob
    private String sted;

    //Tileggsløsning (Egenskaper som blir satt på tilbakmeldinger via. frontend visnings-applikasjonen)

    //Tags festet på denne tilbakemeldingen
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Tag> tags;
    //Notat på tilbakemelding
    @Lob
    private String kommentar;


    public void addTag(Tag tag){
        tags.add(tag);
    }

    public void removeTag(Tag tag){
        if (tags.contains(tag)){
            tags.remove(tag);
        }
    }


    //Constructor for JPA
    protected Tilbakemelding(){}

    public Tilbakemelding(Long id, String kilde, Date dato, int score){
        this.id = id;
        this.kilde = kilde;
        this.dato = dato;
        this.score = score;
        distributoer = "Fremtind";
    }

    public Tilbakemelding(String kilde, String tilbakemeldingstekst, int score, String distributoer){
        id = null;
        this.kilde = kilde;
        dato = Date.valueOf(LocalDate.now());
        this.tilbakemeldingstekst = tilbakemeldingstekst;
        this.score = score;
        this.distributoer = distributoer;
    }

    public Tilbakemelding(Long id, String kilde, Date dato, int score, String distributør){
        this.id = id;
        this.kilde = kilde;
        this.dato = dato;
        this.score = score;
        this.distributoer = distributør;
    }


    @Override
    public String toString(){
        return " Tilbakemelding: ID: "+id.toString()+" Kilde: "+kilde+ " Dato: "+dato+" Tekst: "+tilbakemeldingstekst+" Score: "+score+ " Tags: "+ tags;
    }

}
