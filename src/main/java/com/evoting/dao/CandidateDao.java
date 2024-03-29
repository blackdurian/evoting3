package com.evoting.dao;

import com.evoting.model.Candidate;
import java.util.List;

public class CandidateDao extends HibernateSession implements DataAccessObject<Candidate, Integer>{

  public CandidateDao() {
  }

  @Override
  public void save(Candidate entity) {
    getCurrentSession().save(entity);
  }

  @Override
  public void update(Candidate entity) {
    getCurrentSession().update(entity);
  }

  @Override
  public Candidate findById(Integer id) {
    return getCurrentSession().get(Candidate.class, id);
  }

  @Override
  public void delete(Candidate entity) {
    getCurrentSession().delete(entity);
  }

  @Override
  @SuppressWarnings("unchecked")
  public List<Candidate> findAll() {
    return (List<Candidate>) getCurrentSession().createQuery("from com.evoting.model.Candidate").list();
  }

  @Override
  public void deleteAll() {
    List<Candidate> entityList = findAll();
    for (Candidate entity : entityList) {
      delete(entity);
    }
  }

}
