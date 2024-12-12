package com.plb.vinylmgt.service;

import com.plb.vinylmgt.configuration.security.SecurityUtils;
import com.plb.vinylmgt.entity.User;
import com.plb.vinylmgt.error.UserNotFoundException;
import com.plb.vinylmgt.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * @return
     */
    @Transactional(readOnly = true)
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Transactional
    public User save(User newUser) {
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        newUser.setAuthorities(SecurityUtils.USER);
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

    @Transactional(readOnly = true)
    public Optional<User> getConnectedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findOneByEmailEqualsIgnoreCase(authentication.getName());
    }
}
