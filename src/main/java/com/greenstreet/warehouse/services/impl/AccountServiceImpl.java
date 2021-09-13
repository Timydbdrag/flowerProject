package com.greenstreet.warehouse.services.impl;

import com.greenstreet.warehouse.exception.ApiRequestException;
import com.greenstreet.warehouse.model.request.PasswordChangeDTO;
import com.greenstreet.warehouse.model.response.ResponseUserDTO;
import com.greenstreet.warehouse.repository.UserRepository;
import com.greenstreet.warehouse.security.SecurityUtils;
import com.greenstreet.warehouse.services.AccountService;
import com.greenstreet.warehouse.util.Utilities;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.greenstreet.warehouse.config.Constants.PASSWORD_CHANGED;
import static com.greenstreet.warehouse.exception.ExceptionConstant.INCORRECT_PASSWORD;
import static com.greenstreet.warehouse.exception.ExceptionConstant.USER_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public ResponseUserDTO getUserWithAuthorities() {
        return SecurityUtils.getCurrentUserLogin()
                .flatMap(userRepository::findOneByLogin)
                .map(ResponseUserDTO::new)
                .orElseThrow(() -> new ApiRequestException(USER_NOT_FOUND));
    }

    @Override
    public String changePassword(PasswordChangeDTO passwordChangeDTO) {
        if ((Utilities.isPasswordLengthInvalid(passwordChangeDTO.getNewPassword()))) {
            throw new ApiRequestException(INCORRECT_PASSWORD);
        }

        SecurityUtils.getCurrentUserLogin()
                .flatMap(userRepository::findOneByLogin)
                .ifPresent(
                        user -> {
                            if (!passwordEncoder.matches(passwordChangeDTO.getCurrentPassword(), user.getPassword())) {
                                throw new ApiRequestException(INCORRECT_PASSWORD);
                            }
                            String encryptedPassword = passwordEncoder.encode(passwordChangeDTO.getNewPassword());
                            user.setPassword(encryptedPassword);

                            log.debug("Changed password for User: {}", user);
                            userRepository.save(user);
                        });
        return PASSWORD_CHANGED;
    }


}
