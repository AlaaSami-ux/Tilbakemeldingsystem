package no.fremtind.feedbackhandler.model;

import no.fremtind.feedbackhandler.util.EAttributt;
import no.fremtind.feedbackhandler.util.EAttributtType;


public class Attributt{
    public String key;
    public String value;
    public EAttributtType type;

    //Egendefinerte søkeord og datatyper for filtere. Hvis det ikke er noen egendefinerte nøkkelord defaulter den til enum-navnet og datatype String
    public Attributt(EAttributt key, String value){
        //Default
        this.value = value;
        this.type = EAttributtType.STRING;

        switch (key){
            case DATO:
                this.key = "dato";
                this.type = EAttributtType.DATE;
                break;
            case KILDE:
                this.key = "kilde";
                break;
            case TEKST:
                this.key = "tilbakemeldingstekst";
                break;
            case SCORE:
                this.key = "score";
                this.type = EAttributtType.INT;
                break;
            case NUMMER:
                this.key = "kontekstNummer";
                this.type = EAttributtType.INT;
                break;
            case DISTRIBUTOER:
                this.key = "distributoer";
                break;
            case TAGS:
                this.key = "tags";
                break;
            case STED:
                this.key = "sted";
            default:
                this.key = key.name();
        }
    }

    public Attributt(String key, String value){
        this.value = value;
        this.type = EAttributtType.STRING;
        switch (key.toUpperCase()){
            case "DATO":
                this.key = "dato";
                this.type = EAttributtType.DATE;
                break;
            case "KILDE":
                this.key = "kilde";
                break;
            case "TILBAKEMELDINGSTEKST":
                this.key = "tilbakemeldingstekst";
                break;
            case "SCORE":
                this.key = "score";
                this.type = EAttributtType.INT;
                break;
            case "KONTEKSTNUMMER":
                this.key = "kontekstNummer";
                this.type = EAttributtType.INT;
                break;
            case "DISTRIBUTOER":
                this.key = "distributoer";
                break;
            case "TAGS":
                this.key = "tags";
                break;
            case "STED":
                this.key = "sted";
            default:
                this.key = key;
        }
    }

}
