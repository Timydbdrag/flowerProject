package com.greenstreet.warehouse.services;

import com.greenstreet.warehouse.model.request.PasswordChangeDTO;
import com.greenstreet.warehouse.model.response.ResponseUserDTO;

public interface AccountService {
    ResponseUserDTO getUserWithAuthorities();
    String changePassword(PasswordChangeDTO passwordChangeDTO);
}
