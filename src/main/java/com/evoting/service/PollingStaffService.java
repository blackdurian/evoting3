package com.evoting.service;

import com.evoting.dao.CastingVoteDao;
import com.evoting.dao.UserDao;
import com.evoting.model.CastingVote;
import com.evoting.model.Role;
import com.evoting.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class PollingStaffService {
    private UserDao userDao;
    private CastingVoteDao castingVoteDao;

    public PollingStaffService() {
        userDao = new UserDao();
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
        castingVoteDao.openCurrentSessionWithTransaction();
        castingVoteDao.save(castingVote);
        castingVoteDao.closeCurrentSessionWithTransaction();
    }
}
