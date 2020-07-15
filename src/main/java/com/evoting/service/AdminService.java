package com.evoting.service;

import com.evoting.dao.UserDao;
import com.evoting.dao.UserDaoImpl;
import com.evoting.model.User;
import java.util.Date;

public class AdminService {
  private  UserDaoImpl userDao;

  public AdminService() {
    userDao = new UserDaoImpl();
  }
  public void add(User user){
    userDao.openCurrentSessionWithTransaction();
    userDao.save(user);
    userDao.closeCurrentSessionWithTransaction();
  }

}
