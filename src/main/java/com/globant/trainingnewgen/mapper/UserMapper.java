package com.globant.trainingnewgen.mapper;

import com.globant.trainingnewgen.model.User;
import com.globant.trainingnewgen.model.UserDto;

public final class UserMapper {

    private UserMapper() {
    }


    public static User dtoToEntity(UserDto dto) {
        var user = new User();
        user.setName(dto.name());
        return user;
    }

    public static UserDto entityToDto(User user) {
        return new UserDto(
                user.getName()
        );
    }

}
