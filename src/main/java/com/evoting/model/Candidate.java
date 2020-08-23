package com.evoting.model;



import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity(name = "candidate")
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String name;

    @Lob
    @NotNull
    private byte[] profileImg;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="partyId")
    @NotNull
    private Party party;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="statesId")
    @NotNull
    private States states;

    public Candidate() {
    }

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(byte[] profileImg) {
        this.profileImg = profileImg;
    }

    public Party getParty() {
        return party;
    }

    public void setParty(Party party) {
        this.party = party;
    }

    public States getStates() {
        return states;
    }

    public void setStates(States states) {
        this.states = states;
    }
}
