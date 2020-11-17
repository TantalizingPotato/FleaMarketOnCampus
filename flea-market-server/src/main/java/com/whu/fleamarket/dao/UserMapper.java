package com.whu.fleamarket.dao;

import com.whu.fleamarket.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {

    public List<User> findAll();

    public Integer add(User user);

    public Integer update(User user);

    public User queryById(Integer id);

    public User queryByUsername(String username);

    public Integer deleteById(Integer id);
}
