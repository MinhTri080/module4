package com.cg.service.user;

import com.cg.model.User;
import com.cg.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import repository.UserRepository;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Object getById(Long id) {
        return userRepository.getById(id);
    }

    @Override
    public Object save(Object o) {
        return null;
    }

    @Override
    public void remove(Long id) {
        userRepository.deleteById(id);
    }
}
