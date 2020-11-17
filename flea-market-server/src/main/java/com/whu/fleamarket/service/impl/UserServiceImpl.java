package com.whu.fleamarket.service.impl;

import com.whu.fleamarket.dao.UserMapper;
import com.whu.fleamarket.entity.User;
import com.whu.fleamarket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> findAll() {
        return userMapper.findAll();
    }

    @Override
    public Boolean add(User user) {
        return userMapper.add(user) > 0;
    }

    @Override
    public Boolean update(User user) {
        return userMapper.update(user) > 0;
    }

    @Override
    public User queryById(Integer id) {
        return userMapper.queryById(id);
    }

    @Override
    public User queryByUsername(String username) {
        return userMapper.queryByUsername(username);
    }

    @Override
    public Boolean deleteById(Integer id) {
        return userMapper.deleteById(id) > 0;
    }
}
