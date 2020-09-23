package com.evoting.service;

import com.evoting.dao.*;
import com.evoting.model.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class DashBoardService {
    private PartyService partyService;
    private UserService userService;
    private ElectionCommissionService electionCommissionService;
    private CastingVoteDao castingVoteDao;
    private FinalResultDao finalResultDao;
    private VotingResultDao votingResultDao;

    public DashBoardService() {
        partyService = new PartyService();
        userService = new UserService();
        electionCommissionService = new ElectionCommissionService();
        castingVoteDao = new CastingVoteDao();
        finalResultDao = new FinalResultDao();
        votingResultDao = new VotingResultDao();
    }

    public List<FinalResult> findAllFinalResult() {
        finalResultDao.openCurrentSession();
        List<FinalResult> finalResults = finalResultDao.findAll();
        finalResultDao.closeCurrentSession();
        return finalResults;
    }

    public synchronized List<VotingResult> findAllVotingResult(){
        votingResultDao.openCurrentSession();
        List<VotingResult> votingResults = votingResultDao.findAll();
        votingResultDao.closeCurrentSession();
        return votingResults;
    }

    public int totalVoter() {
        // todo: count candidate where isvoted group by candidate
        return (int) userService.findAll().stream()
                .filter(e -> e.getRole().equals(Role.Voter)).count();
    }

    public int totalParty() {
        return partyService.findAllParty().size();
    }

    public int totalCandidate() {
        return electionCommissionService.findAllCandidate().size();
    }

    public Map<States, Party> partyWinByState() {
        return findAllFinalResult().stream()
                .filter(FinalResult::isVictory)
                .collect(Collectors.toMap(FinalResult::getStates, finalResult -> finalResult.getCandidate().getParty(), (a, b) -> b));
    }

    public Map<States, Long> countVotedByState() {

        return findAllVotingResult().stream()
                .collect(Collectors.groupingBy(VotingResult::getStates
                        ,Collectors.summingLong(c-> (long) c.getVoteCount())));
    }

    public Map<Party, Long> countVotedByParty() {
        return findAllVotingResult().stream()
                .collect(Collectors.groupingBy(c->c.getCandidate().getParty()
                        ,Collectors.summingLong(c-> (long) c.getVoteCount())));
    }

}

