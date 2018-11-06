package com.apap.tutorial6.service;

import com.apap.tutorial6.model.UserRoleModel;

import java.util.Optional;

public interface UserRoleService {
    UserRoleModel addUser(UserRoleModel user);
    public String encrypt(String password);
    UserRoleModel getUserRoleModelByUserName(String username);

}
