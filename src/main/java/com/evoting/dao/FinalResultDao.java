package com.evoting.dao;

import com.evoting.model.FinalResult;
import java.util.List;

public class FinalResultDao extends HibernateSession implements DataAccessObject<FinalResult,Integer>{

  public FinalResultDao() {
  }

  @Override
  public void save(FinalResult entity) {
    getCurrentSession().save(entity);
  }

  @Override
  public void update(FinalResult entity) {
    getCurrentSession().update(entity);
  }

  @Override
  public FinalResult findById(Integer id) {
    return getCurrentSession().get(FinalResult.class, id);
  }

  @Override
  public void delete(FinalResult entity) {
    getCurrentSession().delete(entity);
  }

  @Override
  @SuppressWarnings("unchecked")
  public List<FinalResult> findAll() {
    return (List<FinalResult>) getCurrentSession().createQuery("from com.evoting.model.FinalResult").list();
  }

  @Override
  public void deleteAll() {
    List<FinalResult> entityList = findAll();
    for (FinalResult entity : entityList) {
      delete(entity);
    }
  }

}
