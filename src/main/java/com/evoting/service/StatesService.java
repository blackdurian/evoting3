package com.evoting.service;

import com.evoting.dao.StatesDao;
import com.evoting.model.Role;
import com.evoting.model.States;
import com.evoting.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class StatesService {
private StatesDao statesDao;

  public StatesService() {
    statesDao = new StatesDao();
  }
  public void add(States states){
    statesDao.openCurrentSessionWithTransaction();
    statesDao.save(states);
    statesDao.closeCurrentSessionWithTransaction();
  }

  public void delete(int id){
    statesDao.openCurrentSessionWithTransaction();
    States states = statesDao.findById(id);
    statesDao.delete(states);
    statesDao.closeCurrentSessionWithTransaction();
  }

  public void deleteAll(){
    statesDao.openCurrentSessionWithTransaction();
    statesDao.deleteAll();
    statesDao.closeCurrentSessionWithTransaction();

  }

  public List<States> findAllStates() {
    statesDao.openCurrentSession();
    List<States> states = statesDao.findAll();
    statesDao.closeCurrentSession();
    return states;
  }

  public States findById(int id){
    statesDao.openCurrentSessionWithTransaction();
    States states = statesDao.findById(id);
    statesDao.closeCurrentSessionWithTransaction();
    return states;
  }


  public void update(States states){
    statesDao.openCurrentSessionWithTransaction();
    statesDao.update(states);
    statesDao.closeCurrentSessionWithTransaction();
  }

  public States findByName(String name){
      return findAllStates()
              .stream().filter(e -> e.getName().equals(name))
              .findFirst()
              .orElse(null);
  }
}
