package com.evoting.dao;

import com.evoting.model.Party;
import java.util.List;

public class PartyDao extends HibernateSession implements DataAccessObject<Party,Integer>{

  public PartyDao() {
  }

  @Override
  public void save(Party entity) {
    getCurrentSession().save(entity);
  }

  @Override
  public void update(Party entity) {
    getCurrentSession().update(entity);
  }

  @Override
  public Party findById(Integer id) {
    return getCurrentSession().get(Party.class, id);
  }

  @Override
  public void delete(Party entity) {
    getCurrentSession().delete(entity);
  }

  @Override
  @SuppressWarnings("unchecked")
  public List<Party> findAll() {
    return (List<Party>) getCurrentSession().createQuery("from model.Party").list();
  }

  @Override
  public void deleteAll() {
    List<Party> entityList = findAll();
    for (Party entity : entityList) {
      delete(entity);
    }
  }

}
