package com.company.books;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.FormLoginRequestBuilder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class LoginItTest {

    @Autowired
    private MockMvc mockMvc;

    // Test when username is valid
    @Test
    void loginWithValidUser() throws Exception {
       FormLoginRequestBuilder login = formLogin()
                .user("admin")
                .password("pass22");

        mockMvc.perform(login)
                .andExpect(authenticated().withUsername("admin"));
    }

    // Test when username is invalid
    @Test
    void loginWithInvalidUser() throws Exception {
        FormLoginRequestBuilder login = formLogin()
                .user("someUser")
                .password("password");

        mockMvc.perform(login)
                .andExpect(unauthenticated());
    }

    // Test when try to access unsecured resources
    @Test
    void accessUnsecuredResource() throws Exception {
        mockMvc.perform(get("/css/style.css"))
                .andExpect(status().isOk());
    }

    // Test when try to access not authenticated
    @Test
    void accessSecuredResourceUnauthenticated() throws Exception {
        mockMvc.perform(get("/mainPage"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }

    // Test when try to access secured resources and authenticated
    @Test
    @WithMockUser
    void accessSecuredResourceAuthenticated() throws Exception {
        mockMvc.perform(get("/mainPage"))
                .andExpect(status().isOk());
    }

    // Test when try to access with login admin
    @Test
    @WithMockUser(roles = "ADMIN")
    void loginWithRoleAdmin() throws Exception {
        mockMvc.perform(get("/mainPage"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Admin, Welcome")))
                .andExpect(content().string(doesNotContainString()));
    }

    private Matcher<String> doesNotContainString() {
        return CoreMatchers.not(containsString("User, Welcome"));
    }
}
