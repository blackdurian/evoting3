package com.evoting.service;

import com.evoting.dao.UserDao;
import com.evoting.model.Party;
import com.evoting.model.Role;
import com.evoting.model.States;
import com.evoting.model.User;
import spark.utils.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

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

  public void addVoter(User user, InputStream inputFile) {
    try {
      byte[] fileBytes = IOUtils.toByteArray(inputFile);
      user.setPhoto(fileBytes);
      userDao.openCurrentSessionWithTransaction();
      userDao.save(user);
      userDao.closeCurrentSessionWithTransaction();
      inputFile.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void delete(int id){
    userDao.openCurrentSessionWithTransaction();
    User user = userDao.findById(id);
    userDao.delete(user);
    userDao.closeCurrentSessionWithTransaction();
  }

  public void deleteAll(){
    userDao.openCurrentSessionWithTransaction();
    userDao.deleteAll();
    userDao.closeCurrentSessionWithTransaction();

  }

  public List<User> findAll() {
    userDao.openCurrentSession();
    List<User> user = userDao.findAll();
    userDao.closeCurrentSession();
    return user;
  }

  public User findById(int id){
    userDao.openCurrentSessionWithTransaction();
    User user = userDao.findById(id);
    userDao.closeCurrentSessionWithTransaction();
    return user;
  }

  public void updateUserById(User user, InputStream inputFile){//TODO UPDATE ADMIN
    try {
      byte[] fileBytes = IOUtils.toByteArray(inputFile);
      user.setPhoto(fileBytes);
      userDao.openCurrentSessionWithTransaction();
      userDao.update(user);
      userDao.closeCurrentSessionWithTransaction();
      inputFile.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
