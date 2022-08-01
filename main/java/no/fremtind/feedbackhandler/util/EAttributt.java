package no.fremtind.feedbackhandler.util;
//Søkbare kolonner i databasen.
//Søkefunksjonaliteten oppdateres dynamisk ved å legge inn en ny enum med *nøyaktig samme navn* som kolonnen man søker på.
//Ved behov for adskilte navn mellom Enum og kolonnenavn kan det defineres i Attributter-klassen.
public enum EAttributt {
    DATO,
    KILDE,
    TEKST,
    SCORE,
    NUMMER,
    DISTRIBUTOER,
    TAGS,
    STED
}
