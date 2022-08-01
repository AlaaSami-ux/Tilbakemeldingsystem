package no.fremtind.feedbackhandler.model;


import no.fremtind.feedbackhandler.util.EAttributtType;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class QueryInterpeter {

    //Interpreter som "Oversetter" valgte filtreringsparametere til SQL-søkestrenger

    /**
     * Funksjon som konstruerer en JPA data Specification knyttet til et intervall mellom to datoer.
     * @param datoStart Første tilatte dato i spørringen
     * @param datoSlutt Siste tilatte dato i spørringen
     * @return Specification tilsvarende BETWEEN datoStart AND datoSlutt
     */
    public static Specification<Tilbakemelding> getSpecificationInterval(long datoStart, long datoSlutt){
        //Cursed konvertering, jeg beklager ingenting
        java.util.Date utilDate1 = new java.util.Date();
        utilDate1.setTime(datoStart);
        java.sql.Date sqlDate1 = new java.sql.Date(utilDate1.getTime());
        java.util.Date utilDate2 = new java.util.Date();
        utilDate2.setTime(datoSlutt);
        java.sql.Date sqlDate2 = new java.sql.Date(utilDate2.getTime());

        return (root, query, builder) -> builder.between(root.get("dato"), sqlDate1, sqlDate2);
    }

    public static Specification<Tilbakemelding> getSpecificationLike(String key, String value){
        return (root, query, builder) ->builder
                .like(builder.upper(root.get(key)), "%"+value.toUpperCase()+"%");
    }

    /**
     * Funksjon som konstruerer en JPA data Specification der alle attributter lagt inn blir OR-et med hverandre.
     * @param attributter Attributt-objekter som beskriver hvilke filtere som skal OR-es med hverandre
     * @return Specification tilsvarende alle attributt objekter OR-et med hverandre
     */
    public static Specification<Tilbakemelding> getSpecificationAtri(List<Attributt> attributter)  {

        switch (attributter.stream().findAny().get().type){
            case INT:
                return (root, query, builder) -> builder
                        .or((attributter.stream().map(atri -> builder.equal(root.get(atri.key), atri.value)))
                                .toArray(Predicate[]::new)
                        );
            case DATE:
                return getSpecificationInterval(
                        attributter.stream()
                                .map(atri -> Long.valueOf(atri.value))
                                .mapToLong(v -> v)
                                .min()
                                .getAsLong()
                        ,
                        attributter.stream()
                                .map(atri -> Long.valueOf(atri.value))
                                .mapToLong(v -> v)
                                .max()
                                .getAsLong()
                );
            case STRING:
            default:
                return attributter.stream()
                        .map(atri -> getSpecificationLike(atri.key, atri.value))
                        .reduce((atri, specification) -> atri.or(specification))
                        .get();

        }
    }

}
