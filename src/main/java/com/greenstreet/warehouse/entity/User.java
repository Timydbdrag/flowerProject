package com.greenstreet.warehouse.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.greenstreet.warehouse.model.UserStatus;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Table(schema = "warehouse",name = "users")
public class User extends BaseAuditingEntity{

    @Id
    private UUID id;

    @Column(name = "firstname", length = 30)
    private java.lang.String firstName;

    @Column(name = "lastname", length = 30)
    private java.lang.String lastName;

    @Email
    @Size(min = 5, max = 254)
    @Column(length = 254, unique = true)
    private java.lang.String email;

    @Column(name = "login", length = 30, unique = true)
    private java.lang.String login;

    @Column(name = "password")
    @JsonIgnore
    private java.lang.String password;

    @Column(name = "status")
    @Enumerated(EnumType.ORDINAL)
    private UserStatus userStatus;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(schema = "warehouse", name = "users_roles",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "role_id") }
    )
    private Set<Role> roles = new HashSet<>();

}
