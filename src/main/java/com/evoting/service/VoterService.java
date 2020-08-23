package com.evoting.service;

import com.evoting.dao.VotingResultDao;
import com.evoting.model.Candidate;
import com.evoting.model.States;
import com.evoting.model.VotingResult;
import com.evoting.util.DateUtil;

import java.util.Date;

public class VoterService {
    private VotingResultDao votingResultDao;

    public VoterService() {
        this.votingResultDao = new VotingResultDao();
    }

    public void add(VotingResult votingResult){
        votingResultDao.openCurrentSessionWithTransaction();
        votingResultDao.save(votingResult);
        votingResultDao.closeCurrentSessionWithTransaction();
    }

    public VotingResult addSelectedCandidate(int voteCount, Candidate candidate, States states){

        VotingResult votingResult = new VotingResult(voteCount, candidate, states, new DateUtil().getNow());
        add(votingResult);
        return votingResult;
    }
}
