package no.fremtind.feedbackhandler.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Tag")
@Getter
@Setter
@AllArgsConstructor
public class Tag implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String navn;

    @ManyToMany
    private List<Tilbakemelding> tilbakemeldinger;

    //Constructor for JPA
    protected Tag(){}

    public Tag(Long id, String navn){
        this.id = id;
        this.navn = navn;
        this.tilbakemeldinger = new ArrayList<>();
    }

    @Override
    public String toString(){
        return "Tag: ID: "+id.toString()+" Navn: "+navn;
    }

}
