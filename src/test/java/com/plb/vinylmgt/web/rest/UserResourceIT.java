package com.plb.vinylmgt.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.plb.vinylmgt.DBCleaner;
import com.plb.vinylmgt.entity.User;
import com.plb.vinylmgt.repository.UserRepository;
import com.plb.vinylmgt.service.UserServiceTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserResourceIT {

    @Autowired
    private UserRepository userRepository;

    User user;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DBCleaner dbCleaner;

    private final ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void init() {
        user = UserServiceTest.createEntity();
        dbCleaner.clearAllTables();
    }

    @Test
    @WithMockUser(username = "toto")
    public void getAll() throws Exception {
        User user1 = userRepository.save(user);
        User user2 = userRepository.save(UserServiceTest.createEntity());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[*].id")
                        .value(hasItems(user1.getId().toString(), user2.getId().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].email")
                        .value(hasItems(user1.getEmail(), user2.getEmail())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].password")
                        .value(hasItems(user1.getPassword(), user2.getPassword())));;
    }

    @Test
    @WithMockUser
    public void save() throws Exception {
        int databaseSizeCreate = userRepository.findAll().size();

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(user)))
                .andExpect(status().isOk());

        List<User> all = userRepository.findAll();
        assertThat(all.size()).isEqualTo(databaseSizeCreate + 1);
        User testUser = all.get(all.size() - 1);
        assertThat(testUser.getEmail()).isEqualTo(UserServiceTest.DEFAULT_EMAIL);
        assertThat(testUser.getFirstname()).isEqualTo(UserServiceTest.DEFAULT_FIRSTNAME);
        assertThat(testUser.getLastname()).isEqualTo(UserServiceTest.DEFAULT_LASTNAME);
    }

    @Test
    @WithMockUser
    public void getByIdReturnUserSuccessfuly() throws Exception {
        // Initialize the database
        user.setAuthorities("USER");
        userRepository.save(user);

        // Get the user
        mockMvc.perform(get("/api/users/get-by-id")
                        .param("id", user.getId().toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id").value(user.getId().toString()))
                .andExpect(jsonPath("$.email").value(user.getEmail()))
                .andExpect(jsonPath("$.firstname").value(UserServiceTest.DEFAULT_FIRSTNAME))
                .andExpect(jsonPath("$.lastname").value(UserServiceTest.DEFAULT_LASTNAME))
                .andExpect(jsonPath("$.authorities").value("USER"));
    }

    @Test
    @WithMockUser
    public void deleteUserSuccessfuly() throws Exception {
        // Initialize the database
        userRepository.save(user);
        int databaseSizeBeforeDelete = userRepository.findAll().size();

        // Delete the user
        mockMvc.perform(delete("/api/users")
                        .param("id", user.getId().toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<User> all = userRepository.findAll();
        Assertions.assertThat(all).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @WithMockUser
    public void deleteUserNotFound() throws Exception {
        mockMvc.perform(delete("/api/users")
                        .param("id", UUID.randomUUID().toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
