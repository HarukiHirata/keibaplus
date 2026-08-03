package com.keibaplus.webap.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.keibaplus.webap.dto.UsersRegisterDto;
import com.keibaplus.webap.service.UsersService;

@ExtendWith(MockitoExtension.class)
class UsersControllerTest {

    @Mock
    private UsersService usersService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        InternalResourceViewResolver viewResolver =
                new InternalResourceViewResolver("/WEB-INF/views/", ".html");
        mockMvc = MockMvcBuilders.standaloneSetup(new UsersController(usersService))
                .setViewResolvers(viewResolver)
                .build();
    }

    @Test
    void registerPageDisplaysEmptyRegistrationForm() throws Exception {
        mockMvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(model().attributeExists("form"))
                .andExpect(model().attribute("form", org.hamcrest.Matchers.instanceOf(UsersRegisterDto.class)));
    }

    @Test
    void registerCreatesUserAndRedirectsWhenInputIsValid() throws Exception {
        when(usersService.existsByUserId("sample01")).thenReturn(false);
        when(usersService.existsByMailAddress("sample@example.com")).thenReturn(false);

        mockMvc.perform(post("/register")
                        .param("userId", "sample01")
                        .param("mailAddress", "sample@example.com")
                        .param("password", "password01")
                        .param("passwordConfirm", "password01"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?registered"));

        verify(usersService).createUser(any(UsersRegisterDto.class));
    }

    @Test
    void registerReturnsFormAndDoesNotCreateUserWhenUserIdAlreadyExists() throws Exception {
        when(usersService.existsByUserId("sample01")).thenReturn(true);
        when(usersService.existsByMailAddress("sample@example.com")).thenReturn(false);

        mockMvc.perform(post("/register")
                        .param("userId", "sample01")
                        .param("mailAddress", "sample@example.com")
                        .param("password", "password01")
                        .param("passwordConfirm", "password01"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(model().attributeHasFieldErrors("form", "userId"));

        verify(usersService, never()).createUser(any(UsersRegisterDto.class));
    }
}
