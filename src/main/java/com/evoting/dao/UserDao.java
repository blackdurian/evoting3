package com.evoting.dao;

import com.evoting.model.User;
import java.util.List;

public class UserDao extends HibernateSession implements DataAccessObject<User,Integer> {

  public UserDao() {
  }

  @Override
  public void save(User entity) {
    getCurrentSession().save(entity);
  }

  @Override
  public void update(User entity) {
    getCurrentSession().update(entity);
  }

  @Override
  public User findById(Integer id) {
    return getCurrentSession().get(User.class, id);
  }

  @Override
  public void delete(User entity) {
    getCurrentSession().delete(entity);
  }

  @Override
  @SuppressWarnings("unchecked")
  public List<User> findAll() {
    return (List<User>) getCurrentSession().createQuery("from com.evoting.model.User").list();
  }

  @Override
  public void deleteAll() {
    List<User> entityList = findAll();
    for (User entity : entityList) {
      delete(entity);
    }
  }

}
