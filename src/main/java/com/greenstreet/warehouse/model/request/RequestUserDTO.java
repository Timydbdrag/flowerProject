package com.greenstreet.warehouse.model.request;

import com.greenstreet.warehouse.entity.Role;
import com.greenstreet.warehouse.entity.User;

import javax.validation.constraints.*;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.greenstreet.warehouse.config.Constants.LOGIN_REGEX;

public class RequestUserDTO {

    private UUID id;

    @Size(max = 30)
    private String firstName;

    @Size(max = 30)
    private String lastName;

    @Email
    @Size(min = 5, max = 254)
    private String email;

    @NotBlank
    @Pattern(regexp = LOGIN_REGEX)
    @Size(min = 1, max = 30)
    private String login;

    @Size(min = 5)
    @NotNull(message = "Пароль не может быть null")
    private String password;

    private Set<String> roles;

    private String userStatus;

    public RequestUserDTO(){}

    public RequestUserDTO(User user) {
        this.id = user.getId();
        this.login = user.getLogin();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.roles = user.getRoles().stream().map(Role::getName).collect(Collectors.toSet());
        this.userStatus = user.getUserStatus().toString();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }
}
