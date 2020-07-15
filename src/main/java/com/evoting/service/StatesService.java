package com.evoting.service;

import com.evoting.dao.StatesDao;
import com.evoting.dao.StatesDaoImpl;
import com.evoting.model.States;
import java.util.List;

public class StatesService {
private StatesDaoImpl statesDao;

  public StatesService() {
    statesDao = new StatesDaoImpl();
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

  public List<States> findAll() {
    statesDao.openCurrentSession();
    List<States> statess = statesDao.findAll();
    statesDao.closeCurrentSession();
    return statess;
  }

  public States findById(int id){
    statesDao.openCurrentSessionWithTransaction();
    States states = statesDao.findById(id);
    statesDao.closeCurrentSessionWithTransaction();
    return states;
  }

  public StatesDao getStatesDao(){
    return this.statesDao;
  }

  public void update(States states){
    statesDao.openCurrentSessionWithTransaction();
    statesDao.update(states);
    statesDao.closeCurrentSessionWithTransaction();
  }


  public States registerStates(String name){
    States states = new States();
    states.setName(name);
    add(states);
    return states;
  }
}