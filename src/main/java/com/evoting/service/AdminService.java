package com.evoting.service;

import com.evoting.dao.UserDao;
import com.evoting.model.User;

public class AdminService {
  private UserDao userDao;

  public AdminService() {
    userDao = new UserDao();
  }
  public void add(User user){
    userDao.openCurrentSessionWithTransaction();
    userDao.save(user);
    userDao.closeCurrentSessionWithTransaction();
  }

}
