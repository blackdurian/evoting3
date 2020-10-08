package com.evoting.model;


import java.sql.Date;
import java.util.Objects;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.UpdateTimestamp;

@Entity(name = "casting_vote")
public class CastingVote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="userId")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="candidateId")
    private Candidate candidate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="statesId")
    private States states;

    private boolean isVoted = false;

    @UpdateTimestamp
    private Date timestamp;

    public CastingVote(){

    }

    public CastingVote(User user, Candidate candidate, States states, boolean isVoted, Date timestamp) {
        this.user = user;
        this.candidate = candidate;
        this.states = states;
        this.isVoted = isVoted;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    public States getStates() {
        return states;
    }

    public void setStates(States states) {
        this.states = states;
    }

    public boolean isVoted() {
        return isVoted;
    }

    public void setVoted(boolean voted) {
        isVoted = voted;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

}
