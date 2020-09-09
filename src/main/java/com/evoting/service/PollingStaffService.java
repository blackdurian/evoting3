package com.evoting.service;

import com.evoting.dao.CandidateDao;
import com.evoting.dao.CastingVoteDao;
import com.evoting.dao.PartyDao;
import com.evoting.dao.UserDao;
import com.evoting.model.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PollingStaffService {
    private UserDao userDao;
    private CastingVoteService castingVoteService;
    private CandidateDao candidateDao;
    private PartyDao partyDao;

    public PollingStaffService() {
        userDao = new UserDao();
        castingVoteService = new CastingVoteService();
        candidateDao = new CandidateDao();
        partyDao = new PartyDao();
    }

    public List<User> findAll() {
        userDao.openCurrentSession();
        List<User> user = userDao.findAll();
        userDao.closeCurrentSession();
        return user;
    }

    public List<User> findAllVoters(){
        List<User> voter = findAll().stream()
                .filter(e -> e.getRole().equals(Role.Voter)).collect(Collectors.toList());
        return voter;
    }

    public User findVoterById(int id){
        userDao.openCurrentSessionWithTransaction();
        User user = userDao.findById(id);
        userDao.closeCurrentSessionWithTransaction();
        return user;
    }

    public void prepareVote(User user){
        CastingVote castingVote = new CastingVote();
        castingVote.setUser(user);
        castingVote.setStates(user.getStates());
        castingVoteService.add(castingVote); // todo validation unique user
    }

    public List<Candidate> getCandidateOptionsByUser(User user){
        candidateDao.openCurrentSession();
        List<Candidate> candidates = candidateDao.findAll()
                .stream()
                .filter(e -> Objects.equals(e.getStates(), user.getStates()))
                .collect(Collectors.toList());
        candidateDao.closeCurrentSession();
        return candidates;
    }

    synchronized public Candidate findCandidateById(int id){
        candidateDao.openCurrentSessionWithTransaction();
        Candidate candidate = candidateDao.findById(id);
        candidateDao.closeCurrentSessionWithTransaction();
        return candidate;
    }

    public Party findPartyById(int id){
        partyDao.openCurrentSessionWithTransaction();
        Party party = partyDao.findById(id);
        partyDao.closeCurrentSessionWithTransaction();
        return party;
    }
}
