package no.fremtind.feedbackhandler.model;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TagRepository extends CrudRepository<Tag, Long> {
    Tag findById(long id);
    Tag findByNavn(String navn);
}
