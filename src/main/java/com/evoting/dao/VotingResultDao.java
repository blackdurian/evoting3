package com.evoting.dao;

import com.evoting.model.VotingResult;
import java.util.List;

public class VotingResultDao extends HibernateSession implements DataAccessObject<VotingResult, Integer>{

  public VotingResultDao() {
  }

  @Override
  public void save(VotingResult entity) {
    getCurrentSession().save(entity);
  }

  @Override
  public void update(VotingResult entity) {
    getCurrentSession().update(entity);
  }

  @Override
  public VotingResult findById(Integer id) {
    return getCurrentSession().get(VotingResult.class, id);
  }

  @Override
  public void delete(VotingResult entity) {
    getCurrentSession().delete(entity);
  }

  @Override
  @SuppressWarnings("unchecked")
  public List<VotingResult> findAll() {
    return (List<VotingResult>) getCurrentSession().createQuery("from com.evoting.model.VotingResult").list();
  }

  @Override
  public void deleteAll() {
    List<VotingResult> entityList = findAll();
    for (VotingResult entity : entityList) {
      delete(entity);
    }
  }

}
