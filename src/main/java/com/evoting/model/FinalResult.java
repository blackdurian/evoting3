package com.evoting.model;



import java.sql.Date;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.UpdateTimestamp;

@Entity(name = "final_result")
public class FinalResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="statesId")
    @NotNull
    private States states;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="candidateId")
    @NotNull
    private Candidate candidate;

    @UpdateTimestamp
    @NotNull
    private Date timestamp;

    private boolean isVictory = false;

    public FinalResult(){

    }

    public FinalResult(States states, Candidate candidate, Date timestamp, boolean isVictory) {
        this.states = states;
        this.candidate = candidate;
        this.timestamp = timestamp;
        this.isVictory = isVictory;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public States getStates() {
        return states;
    }

    public void setStates(States states) {
        this.states = states;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isVictory() {
        return isVictory;
    }

    public void setVictory(boolean victory) {
        isVictory = victory;
    }
}
