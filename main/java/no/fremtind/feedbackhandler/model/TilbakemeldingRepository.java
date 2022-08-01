package no.fremtind.feedbackhandler.model;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.sql.Date;
import java.time.OffsetDateTime;
import java.util.List;

public interface TilbakemeldingRepository extends CrudRepository<Tilbakemelding, Long>, JpaSpecificationExecutor<Tilbakemelding>, PagingAndSortingRepository<Tilbakemelding, Long> {
    List<Tilbakemelding> findTilbakemeldingByDato(String dato);
    List<Tilbakemelding> findTilbakemeldingByDistributoer(String distributoer);
    Tilbakemelding findTilbakemeldingById(long id);
    List<Tilbakemelding> findTilbakemeldingByKilde(String kilde);
    List<Tilbakemelding> findTilbakemeldingByKommentar(String kommentar);
    List<Tilbakemelding> findTilbakemeldingByKontekstNummer(int kontekstNummer);
    List<Tilbakemelding> findTilbakemeldingByScore(int score);
    List<Tilbakemelding> findTilbakemeldingByTags(Tag tag);
    List<Tilbakemelding> findTilbakemeldingByTilbakemeldingstekst(String tilbakemeldingstekst);

}
