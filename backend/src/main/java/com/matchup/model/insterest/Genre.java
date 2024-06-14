package com.matchup.model.insterest;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.matchup.model.Interest;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "genre", schema = "matchup")
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany(mappedBy = "genres")
    @JsonIgnore
    private List<Interest> interests;

    // <editor-fold desc="Constructors">

    public Genre() {

    }

    public Genre(String name) {
        this.name = name;
    }

    public Genre(long id, String name) {
        this.id = id;
        this.name = name;
    }
    // </editor-fold>

    // <editor-fold desc="Encapsulation">

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Interest> getInterests() {
        return interests;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setInterests(List<Interest> interests) {
        this.interests = interests;
    }

    // </editor-fold>

    public void addInterest(Interest interest){
        if(this.interests == null){
            this.interests = new ArrayList<>();
        }
        this.interests.add(interest);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
