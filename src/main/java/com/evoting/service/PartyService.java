package com.evoting.service;

import com.evoting.dao.PartyDao;
import com.evoting.dao.UserDao;
import com.evoting.model.Party;
import com.evoting.model.States;
import com.evoting.model.User;

import java.util.List;

public class PartyService {


    private PartyDao partyDao;

    public PartyService() {
        partyDao = new PartyDao();
    }

    public synchronized List<Party> findAllParty() {
        partyDao.openCurrentSession();
        List<Party> parties = partyDao.findAll();
        partyDao.closeCurrentSession();
        return parties;
    }

    public Party findByName(String name){
        return findAllParty()
                .stream().filter(e -> e.getName().equals(name))
                .findFirst()
                .orElse(null);
    }
}
