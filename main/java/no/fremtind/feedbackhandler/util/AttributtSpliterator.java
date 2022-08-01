package no.fremtind.feedbackhandler.util;

import no.fremtind.feedbackhandler.model.Attributt;

import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * Dette er en klasse som ennå ikke er i bruk av hentemanager/queryinterpeter.
 * Den kan brukes for å behandle både dato-intervall spørringer og vanlige spørringer.
 * Dette gjøres ved at man kan ta inn en stream av attributter, sortere basert på enum-type (Kolonne-navn), og så bruke denne spliteratoren til å dele opp streamen til en stream per ENUM som kan hver for seg bli en Specification.
 * Dette ble litt overkill å implementere, så jeg lar denne klassen stå i tilfelle man vil gjøre det senere.
 */
public class AttributtSpliterator implements Spliterator<Attributt> {

    private List<Attributt> attributter;
    private int current;
    private int last;

    public AttributtSpliterator(List<Attributt> attributter){
        this.attributter = attributter;
        last = attributter.size();
    }

    public AttributtSpliterator(List<Attributt> attributter, int splitPosition, int last) {
        this.attributter = attributter;
        this.current = splitPosition;
        this.last = last;
    }

    @Override
    public boolean tryAdvance(Consumer<? super Attributt> action) {
        if (current <= last){
            action.accept(attributter.get(current));
            current++;
            return true;
        }
        return false;
    }

    @Override
    public Spliterator<Attributt> trySplit() {
        int splitPosition = current + (last - current) / 2;
        // Attributtene er av samme type, så vi kan ikke splitte her
        String attributtBeforeSplit = attributter.get(splitPosition-1).key;
        String attributtAfterSplit = attributter.get(splitPosition).key;

        // Fortsett til vi finner et sted man kan splitte
        while (attributtBeforeSplit.equals(attributtAfterSplit)) {
            splitPosition++;
            attributtBeforeSplit = attributtAfterSplit;
            attributtAfterSplit = attributter.get(splitPosition).key;
        }

        AttributtSpliterator secondHalf = new AttributtSpliterator(attributter,splitPosition,last);
        // Reset index
        last = splitPosition - 1;

        return secondHalf;
    }

    @Override
    public long estimateSize() {
        return last-current;
    }

    @Override
    public int characteristics() {
        return ORDERED;
    }
}
