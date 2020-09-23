package com.evoting.service;

import com.evoting.dao.*;
import com.evoting.model.*;
import com.evoting.util.DateUtil;
import spark.utils.IOUtils;

import javax.servlet.MultipartConfigElement;
import java.io.IOException;
import java.io.InputStream;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ElectionCommissionService {
    private final CandidateDao candidateDao;
    private final PartyDao partyDao;
    private final UserDao userDao;
    private final CastingVoteService castingVoteService;
    private final VotingResultDao votingResultDao;
    private final StatesService statesService;
    private final FinalResultDao finalResultDao;

    public ElectionCommissionService() {
        this.candidateDao = new CandidateDao();
        this.partyDao = new PartyDao();
        this.userDao = new UserDao();
        this.votingResultDao = new VotingResultDao();
        this.castingVoteService = new CastingVoteService();
        this.statesService = new StatesService();
        this.finalResultDao = new FinalResultDao();
    }

    public void addCandidate(Candidate candidate, InputStream inputFile) {

        try {
            byte[] fileBytes = IOUtils.toByteArray(inputFile);
            candidate.setProfileImg(fileBytes);

            candidateDao.openCurrentSessionWithTransaction();
            candidateDao.save(candidate);
            candidateDao.closeCurrentSessionWithTransaction();
            inputFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addVotingResult(VotingResult votingResult) {
        votingResultDao.openCurrentSessionWithTransaction();
        votingResultDao.save(votingResult);
        votingResultDao.closeCurrentSessionWithTransaction();
    }

    public void addFinalResult(FinalResult finalResult) {
        finalResultDao.openCurrentSessionWithTransaction();
        finalResultDao.save(finalResult);
        finalResultDao.closeCurrentSessionWithTransaction();
    }

    public synchronized List<VotingResult> findAllVotingResult() {
        votingResultDao.openCurrentSession();
        List<VotingResult> votingResults = votingResultDao.findAll();
        votingResultDao.closeCurrentSession();
        return votingResults;
    }

    public void addPollingStaff(User user, InputStream inputFile) {
        try {
            byte[] fileBytes = IOUtils.toByteArray(inputFile);
            user.setPhoto(fileBytes);
            userDao.openCurrentSessionWithTransaction();
            userDao.save(user);
            userDao.closeCurrentSessionWithTransaction();
            inputFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void deleteCandidate(int id) {
        candidateDao.openCurrentSessionWithTransaction();
        Candidate candidate = candidateDao.findById(id);
        candidateDao.delete(candidate);
        candidateDao.closeCurrentSessionWithTransaction();
    }

    public void deleteAllCandidate() {
        candidateDao.openCurrentSessionWithTransaction();
        candidateDao.deleteAll();
        candidateDao.closeCurrentSessionWithTransaction();

    }

    public synchronized List<Candidate> findAllCandidate() {
        candidateDao.openCurrentSession();
        List<Candidate> candidates = candidateDao.findAll();
        candidateDao.closeCurrentSession();
        return candidates;
    }

    public synchronized Candidate findCandidateById(int id) {
        candidateDao.openCurrentSessionWithTransaction();
        Candidate candidate = candidateDao.findById(id);
        candidateDao.closeCurrentSessionWithTransaction();
        return candidate;
    }

    public void updateCandidate(Candidate candidate) {
        candidateDao.openCurrentSessionWithTransaction();
        candidateDao.update(candidate);
        candidateDao.closeCurrentSessionWithTransaction();
    }

    public void addParty(Party party, InputStream inputFile) {

        try {
            byte[] fileBytes = IOUtils.toByteArray(inputFile);
            party.setLogo(fileBytes);

            partyDao.openCurrentSessionWithTransaction();
            partyDao.save(party);
            partyDao.closeCurrentSessionWithTransaction();
            inputFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized List<Party> findAllParty() {
        partyDao.openCurrentSession();
        List<Party> parties = partyDao.findAll();
        partyDao.closeCurrentSession();
        return parties;
    }

    public synchronized Party findPartyById(int id) {
        partyDao.openCurrentSessionWithTransaction();
        Party party = partyDao.findById(id);
        partyDao.closeCurrentSessionWithTransaction();
        return party;
    }

    public synchronized User findPollingStaffById(int id) {
        userDao.openCurrentSessionWithTransaction();
        User user = userDao.findById(id);
        userDao.closeCurrentSessionWithTransaction();
        return user;
    }

    public synchronized List<User> findAll() {
        userDao.openCurrentSession();
        List<User> user = userDao.findAll();
        userDao.closeCurrentSession();
        return user;
    }

    public synchronized List<User> findAllPollingStaff() {
        return findAll().stream()
                .filter(e -> e.getRole().equals(Role.Polling_Staff))
                .collect(Collectors.toList());
    }

    public void updateVotingResult() {

//todo update to table
        Map<Candidate, Long> counts = countVoted();

        counts.forEach((k, v) -> {
            VotingResult votingResult = new VotingResult();
            votingResult.setCandidate(k);
            votingResult.setStates(k.getStates());
            votingResult.setVoteCount(Math.toIntExact(v));
            addVotingResult(votingResult);
            System.out.println(votingResult.getCandidate().getId() +" "+votingResult.getCandidate().getName() +" "+votingResult.getVoteCount());
        });

    }

    public void updateFinalResult() {
        List<States> allStates = statesService.findAllStates();
        allStates.forEach(k-> System.out.println(k.getName()));
        List<VotingResult> allVotingResult = findAllVotingResult();
        allVotingResult.forEach(k-> System.out.println(k.getCandidate().getName()+" "+k.getStates().getName()));
        for (States s : allStates) {
            List<VotingResult> resultByStates = allVotingResult.stream()
                    .filter(votingResult -> votingResult.getStates().equals(s))
                    .sorted(Comparator.comparing(VotingResult::getVoteCount, Integer::compareTo))
                    .collect(Collectors.toList());

            resultByStates.forEach(k -> System.out.println(k.getCandidate().getName()));
            // compare candidate vote count in each states, if largest then set victory
            if (resultByStates.size() > 0) {
                for (int i = 0; i < resultByStates.size(); i++) {
                    FinalResult finalResult = new FinalResult();
                    finalResult.setCandidate(resultByStates.get(i).getCandidate());
                    finalResult.setStates(s);
                    finalResult.setVictory(i == (resultByStates.size()-1));
                    System.out.println(finalResult.getCandidate().getId() +" " +finalResult.isVictory());
                    // update final result table
                    addFinalResult(finalResult);
                }
            }
        }
    }

    public Map<Candidate, Long> countVoted() {
        // todo: count candidate where isvoted group by candidate
        Map<Candidate, Long> result = castingVoteService.findAll().stream()
                .filter(CastingVote::isVoted)
                .collect(Collectors.groupingBy(CastingVote::getCandidate,
                        Collectors.counting()));
/*        result.entrySet().stream()
                .sorted(Map.Entry.<Candidate, Long>comparingByValue().reversed());*/
        return result;
    }

    public Map<Candidate, Long> countNotVoted() {
        // todo: count candidate where isvoted group by candidate
        return castingVoteService.findAll().stream()
                .filter(castingVote -> !castingVote.isVoted())
                .collect(Collectors.groupingBy(CastingVote::getCandidate,
                        Collectors.counting()));
    }


}
