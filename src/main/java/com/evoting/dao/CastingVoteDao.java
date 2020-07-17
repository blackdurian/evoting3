package com.evoting.dao;

import com.evoting.model.CastingVote;
import java.util.List;

public class CastingVoteDao extends HibernateSession implements DataAccessObject<CastingVote,Integer>{

  public CastingVoteDao() {
  }

  @Override
  public void save(CastingVote entity) {
    getCurrentSession().save(entity);
  }

  @Override
  public void update(CastingVote entity) {
    getCurrentSession().update(entity);
  }

  @Override
  public CastingVote findById(Integer id) {
    return getCurrentSession().get(CastingVote.class, id);
  }

  @Override
  public void delete(CastingVote entity) {
    getCurrentSession().delete(entity);
  }

  @Override
  @SuppressWarnings("unchecked")
  public List<CastingVote> findAll() {
    return (List<CastingVote>) getCurrentSession().createQuery("from model.CastingVote").list();
  }

  @Override
  public void deleteAll() {
    List<CastingVote> entityList = findAll();
    for (CastingVote entity : entityList) {
      delete(entity);
    }
  }
  
}
