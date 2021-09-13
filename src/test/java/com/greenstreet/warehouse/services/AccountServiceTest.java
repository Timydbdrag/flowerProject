package com.greenstreet.warehouse.services;

import com.greenstreet.warehouse.entity.User;
import com.greenstreet.warehouse.exception.ApiRequestException;
import com.greenstreet.warehouse.model.request.PasswordChangeDTO;
import com.greenstreet.warehouse.model.response.ResponseUserDTO;
import com.greenstreet.warehouse.repository.UserRepository;
import com.greenstreet.warehouse.security.SecurityUtils;
import com.greenstreet.warehouse.services.impl.AccountServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static com.greenstreet.warehouse.config.Constants.PASSWORD_CHANGED;
import static com.greenstreet.warehouse.exception.ExceptionConstant.INCORRECT_PASSWORD;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    private final static String TEST_LOGIN = "tester";

    @Mock
    UserRepository userRepository;

    @InjectMocks
    AccountServiceImpl accountService;

    @Mock
    PasswordEncoder passwordEncoder;

    @Test
    void getUserWithAuthorities() {
        try (MockedStatic<SecurityUtils> theMock = Mockito.mockStatic(SecurityUtils.class)) {
            theMock.when(SecurityUtils::getCurrentUserLogin)
                    .thenReturn(Optional.of(TEST_LOGIN));

            assertEquals(Optional.of(TEST_LOGIN), SecurityUtils.getCurrentUserLogin());

            User user = getUser();
            given(userRepository.findOneByLogin(TEST_LOGIN)).willReturn(Optional.of(user));

            ResponseUserDTO expected = new ResponseUserDTO(user);
            ResponseUserDTO actual = accountService.getUserWithAuthorities();
            assertThat(actual.getLogin()).isEqualTo(expected.getLogin());
            assertThat(actual.getId()).isEqualTo(expected.getId());
        }
    }

    @Test
    void shouldChangedPassword() {
        PasswordChangeDTO passwordDTO = new PasswordChangeDTO("oldPassword", "newPassword");

        try (MockedStatic<SecurityUtils> securityUtilsMockedStatic = Mockito.mockStatic(SecurityUtils.class)) {
            securityUtilsMockedStatic.when(SecurityUtils::getCurrentUserLogin)
                    .thenReturn(Optional.of(TEST_LOGIN));

            User user = getUser();
            given(userRepository.findOneByLogin(TEST_LOGIN)).willReturn(Optional.of(user));
            given(passwordEncoder.matches(any(), any())).willReturn(true);

            assertThat(accountService.changePassword(passwordDTO)).isEqualTo(PASSWORD_CHANGED);
        }
    }

    @Test
    void shouldReturnExceptionWhenChangingPassword() {
        PasswordChangeDTO passwordDTO = new PasswordChangeDTO("oldPassword", "newPassword");

        try (MockedStatic<SecurityUtils> securityUtilsMockedStatic = Mockito.mockStatic(SecurityUtils.class)) {
            securityUtilsMockedStatic.when(SecurityUtils::getCurrentUserLogin)
                    .thenReturn(Optional.of(TEST_LOGIN));

            User user = getUser();
            given(userRepository.findOneByLogin(TEST_LOGIN)).willReturn(Optional.of(user));
            given(passwordEncoder.matches(any(), any())).willReturn(false);

            Exception exception = assertThrows(ApiRequestException.class, () -> accountService.changePassword(passwordDTO));

            assertThat(exception.getMessage()).isEqualTo(INCORRECT_PASSWORD);
        }
    }

    private User getUser() {
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setLogin(TEST_LOGIN);
        user.setPassword("oldPassword");
        return user;
    }
}