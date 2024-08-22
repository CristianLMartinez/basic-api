package com.globant.trainingnewgen.service;

import com.globant.trainingnewgen.exception.ResourceNotFoundException;
import com.globant.trainingnewgen.mapper.UserMapper;
import com.globant.trainingnewgen.model.User;
import com.globant.trainingnewgen.model.UserDto;
import com.globant.trainingnewgen.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final String EXCEPTION_MESSAGE = "User with id %s not found";

    @Override
    public List<UserDto> getAll() {
        var users = userRepository.findAll();
        return users.stream()
                .map(UserMapper::entityToDto)
                .toList();
    }

    @Override
    public User getUserById(long id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(EXCEPTION_MESSAGE, id)));
    }

    @Override
    public void create(UserDto userdto) {
        var user = UserMapper.dtoToEntity(userdto);
        userRepository.save(user);
    }

    @Override
    public UserDto update(long id, UserDto userDto) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(EXCEPTION_MESSAGE, id)));

        if (!userDto.name().equals(user.getName()) &&
                user.getName().isBlank()) {
            user.setName(userDto.name());
        }

        var userSaved = userRepository.save(user);


        return UserMapper.entityToDto(userSaved);

    }

    @Override
    public void delete(long id) {
        var user = userRepository.findById(id)
                .orElseThrow(() ->  new ResourceNotFoundException(String.format(EXCEPTION_MESSAGE, id)));

        userRepository.delete(user);
    }

    @Override
    public UserDto getUserByName(String name) {
        var user = userRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Can't find user with name: %s", name)));
        return UserMapper.entityToDto(user);
    }




}
