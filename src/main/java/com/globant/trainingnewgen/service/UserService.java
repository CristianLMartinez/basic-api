package com.globant.trainingnewgen.service;

import com.globant.trainingnewgen.model.User;
import com.globant.trainingnewgen.model.UserDto;

import java.util.List;

public interface UserService {

    User getUserById(long id);

    void create(UserDto userDto);

    UserDto update(long id, UserDto userDto);

    void delete(long id);

    UserDto getUserByName(String name);

    List<UserDto> getAll();

}
