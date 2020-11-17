package com.whu.fleamarket.service;

import com.whu.fleamarket.entity.User;

import java.util.List;

public interface UserService {

    public List<User> findAll();

    public Boolean add(User user);

    public Boolean update(User user);

    public User queryById(Integer id);

    public User queryByUsername(String username);

    public Boolean deleteById(Integer id);
}
