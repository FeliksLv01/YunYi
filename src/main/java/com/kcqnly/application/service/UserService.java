package com.kcqnly.application.service;

import com.kcqnly.application.dao.UserDao;
import com.kcqnly.application.entity.Role;
import com.kcqnly.application.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userDao.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        return user;
    }
    public User findById(int id)
    {
        return userDao.findById(id).get();
    }

    public User findByUsername(String username)
    {
        return userDao.findByUsername(username);
    }
    public Role getUserRole(String username)
    {
       User user= userDao.findByUsername(username);
       return user.getRole();
    }

    public List<User> findAll(int page,int limit)
    {

        Pageable pageable = PageRequest.of(page,limit);
        return userDao.findAll(pageable).getContent();
    }

    public long getTotal()
    {
       return userDao.count();
    }

    public void save(User user)
    {
        userDao.save(user);
    }

    public User updateState(int id ,boolean state)
    {
        userDao.updateState(id,state);
        User user =userDao.findById(id).get();
        return user;
    }

    public User updateInformation(int id ,String mobile, String email)
    {
        userDao.updateInformation(id, mobile, email);
        User user =userDao.findById(id).get();
        return user;
    }

    public User updateRole(int id,int rid)
    {
        userDao.updateRole(id,rid);
        return userDao.findById(id).get();
    }
    public void deleteById(int id)
    {
        userDao.deleteById(id);
    }
}