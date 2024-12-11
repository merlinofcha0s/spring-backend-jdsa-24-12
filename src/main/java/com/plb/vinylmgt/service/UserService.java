package com.plb.vinylmgt.service;

import com.plb.vinylmgt.entity.User;
import com.plb.vinylmgt.error.UserNotFoundException;
import com.plb.vinylmgt.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Transactional
    public User save(User newUser) {
        return userRepository.save(newUser);
    }

    @Transactional(readOnly = true)
    public Optional<User> getById(UUID id) {
        return userRepository.findById(id);
    }

    @Transactional
    public void delete(UUID id) {
        long nbOfDeletedUsers = userRepository.deleteByIdWithCount(id);
        if (nbOfDeletedUsers == 0) {
            throw new UserNotFoundException("User not found");
        }
    }
}
