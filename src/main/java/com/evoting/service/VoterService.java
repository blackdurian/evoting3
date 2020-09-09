package com.evoting.service;

import com.evoting.dao.CastingVoteDao;
import com.evoting.dao.VotingResultDao;
import com.evoting.model.*;
import com.evoting.util.DateUtil;

import java.util.Date;

public class VoterService {
    private VotingResultDao votingResultDao;
    private CastingVoteDao castingVoteDao;
    private CastingVoteService castingVoteService;

    public VoterService() {
        this.votingResultDao = new VotingResultDao();
        this.castingVoteDao = new CastingVoteDao();
        this.castingVoteService = new CastingVoteService();
    }

    public void add(VotingResult votingResult){
        votingResultDao.openCurrentSessionWithTransaction();
        votingResultDao.save(votingResult);
        votingResultDao.closeCurrentSessionWithTransaction();
    }

    public VotingResult addSelectedCandidate(int voteCount, Candidate candidate, States states){

        VotingResult votingResult = new VotingResult(voteCount, candidate, states, DateUtil.getNow());
        add(votingResult);
        return votingResult;
    }

    public void voterChoice(User user, Candidate candidate){
        CastingVote castingVote = castingVoteService.findByUser(user);//todo validation
        castingVote.setCandidate(candidate);
        castingVote.setVoted(true);
        castingVoteService.update(castingVote);
    }

    public VoteStatus checkStatus(User user){
        if (user==null){
            throw new NullPointerException("Null User");
        }
        if (user.getRole().equals(Role.Voter)){
            CastingVote castingVote = castingVoteService.findByUser(user);
            if (castingVote == null){
                return VoteStatus.NOT_REGISTER;
            }
            if (castingVote.isVoted()){
                return VoteStatus.VOTED;
            }else{
                return VoteStatus.NOT_VOTED;
            }
        }else{
            //todo: validation Voter only
        }
        return null;
    }
}
