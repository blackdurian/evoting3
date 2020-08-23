package com.evoting.service;

import com.evoting.dao.UserDao;
import com.evoting.model.Role;
import com.evoting.model.States;
import com.evoting.model.User;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class UserService {
    private UserDao userDao;

    public UserService() {
        this.userDao = new UserDao();
    }
    public void add(User user){
        userDao.openCurrentSessionWithTransaction();
        userDao.save(user);
        userDao.closeCurrentSessionWithTransaction();
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

    public void update(User user){
        userDao.openCurrentSessionWithTransaction();
        userDao.update(user);
        userDao.closeCurrentSessionWithTransaction();
    }

    public User findByUsername(String username){
    return  findAll()
            .stream()
            .filter(e -> e.getUsername().equals(username))
            .findFirst()
            .orElse(null);
    }

    public List<User> findAllVoter(){
       List<User> voter = findAll().stream()
                .filter(e -> e.getRole().equals(Role.Voter)).collect(Collectors.toList());
        return voter;
    }

    public User authenticate(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            return null;
        }
        User user = findByUsername(username);
        if (user == null) {
            return null;
        }

        if(user.getPassword().equals(password)){
            return user;
        }
        return null;
    }
}
