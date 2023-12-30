package com.chernyshev.messenger;

import com.chernyshev.messenger.api.dtos.AuthenticationDto;
import com.chernyshev.messenger.api.dtos.InfoDto;
import com.chernyshev.messenger.api.dtos.PasswordDto;
import com.chernyshev.messenger.store.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Objects;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringJUnitConfig
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-user-before.sql"},executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/clear.sql"},executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@WithUserDetails(value = "test1234")
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;
    @Test
    public void getInfoTest() throws  Exception {
        mockMvc.perform(get("/api/v1/user"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lastname").value("test1"))
                .andExpect(jsonPath("$.firstname").value("test1"))
                .andExpect(jsonPath("$.username").value("test1234"))
                .andExpect(jsonPath("$.email").value("test1@gmail.com"))
                .andDo(print());

    }
    @Test
    public void putEditTest() throws Exception {
        InfoDto infoRequest = InfoDto.builder()
                .firstname("NewFirstName")
                .lastname("NewLastName")
                .bio("NewBio")
                .status("NewStatus")
                .avatarUrl("NewAvatarUrl")
                .build();
        mockMvc.perform(put("/api/v1/user/edit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(infoRequest)));
        mockMvc.perform(get("/api/v1/user"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lastname").value("NewLastName"))
                .andExpect(jsonPath("$.firstname").value("NewFirstName"))
                .andExpect(jsonPath("$.bio").value("NewBio"))
                .andExpect(jsonPath("$.status").value("NewStatus"))
                .andExpect(jsonPath("$.avatarUrl").value("NewAvatarUrl"))
                .andDo(print());

    }
    @Test
    public void postUsernameTest() throws Exception {
        UsernameRequest usernameRequest = UsernameRequest.builder()
                .username("test1111")
                .build();
        mockMvc.perform(post("/api/v1/user/change-username")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(usernameRequest)));

        AuthenticationDto authenticationDTO = AuthenticationDto.builder()
                .username("test1111")
                .password("test1234").build();
        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(authenticationDTO)))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    public void postEmailTest() throws Exception {
        EmailRequest emailRequest = EmailRequest.builder()
                .email("test111@mail.ru")
                .build();
        mockMvc.perform(post("/api/v1/user/change-email")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(emailRequest)))
                .andDo(print()).andExpect(status().isOk());
    }
    @Test
    public void postPasswordTest() throws Exception {
        PasswordDto passwordDto = PasswordDto.builder()
                .oldPassword("test1234")
                .newPassword("test1111")
                .build();
        mockMvc.perform(post("/api/v1/user/change-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(passwordDto)))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    public void deleteTest() throws Exception{
        mockMvc.perform(delete("/api/v1/user/delete"))
                .andDo(print())
                .andExpect(status().isOk());
        System.out.println("\n\n\nАктивность пользователя: " + Objects.requireNonNull(userRepository.findByUsername("test1234").orElse(null)).isActive()+"\n\n\n");
    }
    @Test
    public void postRecoverTest() throws Exception {
        System.out.println("\n\n\nАктивность пользователя: " + Objects.requireNonNull(userRepository.findByUsername("test1234").orElse(null)).isActive()+"\n\n\n");
        mockMvc.perform(post("/api/v1/user/recover"))
                .andDo(print())
                .andExpect(status().isOk());
        System.out.println("\n\n\nАктивность пользователя: " + Objects.requireNonNull(userRepository.findByUsername("test1234").orElse(null)).isActive()+"\n\n\n");
    }
    @Test
    public void putProfilePrivacyTest() throws Exception{
        System.out.println("\n\n\nПриватность профиля пользователя: " + Objects.requireNonNull(userRepository.findByUsername("test1234").orElse(null)).isPrivateProfile()+"\n\n\n");
        mockMvc.perform(put("/api/v1/user/change-profile-privacy"))
                .andDo(print())
                .andExpect(status().isOk());
        System.out.println("\n\n\nПриватность профиля пользователя: " + Objects.requireNonNull(userRepository.findByUsername("test1234").orElse(null)).isPrivateProfile()+"\n\n\n");
    }
}
