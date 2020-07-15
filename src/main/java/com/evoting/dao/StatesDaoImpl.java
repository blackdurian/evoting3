package com.evoting.dao;

import com.evoting.model.States;
import java.util.List;

public class StatesDaoImpl extends DaoSession implements StatesDao{

  public StatesDaoImpl() {
  }

  @Override
  public void save(States entity) {
    getCurrentSession().save(entity);
  }

  @Override
  public void update(States entity) {
    getCurrentSession().update(entity);
  }

  @Override
  public States findById(Integer id) {
    return getCurrentSession().get(States.class, id);
  }

  @Override
  public void delete(States entity) {
    getCurrentSession().delete(entity);
  }

  @Override
  @SuppressWarnings("unchecked")
  public List<States> findAll() {
    return (List<States>) getCurrentSession().createQuery("from model.States").list();
  }

  @Override
  public void deleteAll() {
    List<States> entityList = findAll();
    for (States entity : entityList) {
      delete(entity);
    }
  }

}
