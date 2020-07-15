package com.evoting.dao;

import java.io.Serializable;
import java.util.List;

public interface DataAccessObject<T, Id extends Serializable> {
  void save(T entity);

  void update(T entity);

  T findById(Id id);

  void delete(T entity);

  Iterable<T> findAll();

  void deleteAll();
}
