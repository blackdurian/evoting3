package com.evoting.service;

import com.evoting.dao.CastingVoteDao;
import com.evoting.model.CastingVote;
import com.evoting.model.User;

import java.util.List;

public class CastingVoteService {
    private CastingVoteDao castingVoteDao;

    public CastingVoteService() {
        castingVoteDao = new CastingVoteDao();
    }
    public void add(CastingVote castingVote){
        castingVoteDao.openCurrentSessionWithTransaction();
        castingVoteDao.save(castingVote);
        castingVoteDao.closeCurrentSessionWithTransaction();
    }

    public void delete(int id){
        castingVoteDao.openCurrentSessionWithTransaction();
        CastingVote castingVote = castingVoteDao.findById(id);
        castingVoteDao.delete(castingVote);
        castingVoteDao.closeCurrentSessionWithTransaction();
    }

    public void deleteAll(){
        castingVoteDao.openCurrentSessionWithTransaction();
        castingVoteDao.deleteAll();
        castingVoteDao.closeCurrentSessionWithTransaction();

    }

    public synchronized List<CastingVote> findAll(){
        castingVoteDao.openCurrentSession();
        List<CastingVote> castingVote = castingVoteDao.findAll();
        castingVoteDao.closeCurrentSession();
        return castingVote;
    }

    public synchronized CastingVote findById(int id){
        castingVoteDao.openCurrentSessionWithTransaction();
        CastingVote castingVote = castingVoteDao.findById(id);
        castingVoteDao.closeCurrentSessionWithTransaction();
        return castingVote;
    }


    public void update(CastingVote castingVote){
        castingVoteDao.openCurrentSessionWithTransaction();
        castingVoteDao.update(castingVote);
        castingVoteDao.closeCurrentSessionWithTransaction();
    }

    public CastingVote findByUser(User cUser){
        for (CastingVote e : findAll()) {
            if (e.getUser().getId() == cUser.getId()) {
                return e;
            }
        }
        return null;
    }
}
