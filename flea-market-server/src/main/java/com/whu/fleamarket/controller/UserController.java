package com.whu.fleamarket.controller;

import com.whu.fleamarket.service.UserService;
import com.whu.fleamarket.entity.User;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/user", produces = "application/json") //配置返回值 application/json
@Api(description = "用户管理")
public class UserController {

    @Autowired
    private UserService userService;
    ArrayList<User> users = new ArrayList<>();


    @ApiOperation(value = "获取用户列表", notes = "获取所有用户信息")
    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public List<User> findAllUser() {

        List<User> users =userService.findAll();

        return users;
    }

    @ApiOperation(value = "创建用户", notes = "根据User对象创建新用户")
//    @ApiImplicitParam(name = "user", value = "包含用户信息的User对象", required = true, dataType = "User")
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String createUser(User user) {

        userService.add(user);

        return "success" ;
    }

    @ApiOperation(value="查找用户", notes="根据用户id获取用户详细信息")
    @ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "Integer", paramType = "path")
    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public User getUser(@PathVariable String id) {

        return userService.queryById(Integer.parseInt(id));
    }

    @ApiOperation(value="使用用户名查找用户", notes="根据用户名获取用户详细信息")
    @RequestMapping(value="/ofUsername/{username}", method=RequestMethod.GET)
    public User getUserByUsername(@ApiParam(value = "用户名", required = true) @PathVariable String username) {

        return userService.queryByUsername(username);
    }

    @ApiOperation(value="更新用户", notes="根据url指定的id和传来的user对象，更新相应的用户信息")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Integer", paramType = "path")
    @RequestMapping(value="/{id}", method=RequestMethod.PUT)
    public String putUser(@PathVariable String id, @RequestBody User user) {
        user.setId(Integer.parseInt(id));
        userService.update(user);
        return "success";
    }

    @ApiOperation(value="删除用户", notes="根据url中给定的id，删除相应用户信息")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Integer", paramType = "path")
    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public String deleteUser(@PathVariable String id) {
        userService.deleteById(Integer.parseInt(id));
        return "success";
    }

}

