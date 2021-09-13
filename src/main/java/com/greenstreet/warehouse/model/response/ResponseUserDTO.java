package com.greenstreet.warehouse.model.response;

import com.greenstreet.warehouse.entity.Role;
import com.greenstreet.warehouse.entity.User;
import com.greenstreet.warehouse.model.UserStatus;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class ResponseUserDTO {

    private UUID id;
    private String login;
    private String firstName;
    private String lastName;
    private String email;
    private String createdBy;
    private Instant createdDate;
    private String updatedBy;
    private Instant updatedDate;
    private Set<String> roles;
    private UserStatus userStatus;

    public ResponseUserDTO() {}

    public ResponseUserDTO(User user) {
        this.id = user.getId();
        this.login = user.getLogin();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.createdBy = user.getCreatedBy();
        this.createdDate = user.getCreatedDate();
        this.updatedBy = user.getUpdateBy();
        this.updatedDate = user.getUpdateDate();
        this.roles = user.getRoles().stream().map(Role::getName).collect(Collectors.toSet());
        this.userStatus = user.getUserStatus();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }
}
