package com.epam.rd.java.basic.model;

import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "Please enter login")
    @Length(min = 3, message = "Min length = 3")
    @Column(name = "login", nullable = false)
    private String login;

    @NotBlank(message = "Please enter password")
    @Length(min = 3, message = "Min length = 3")
    @Column(name = "password", nullable = false)
    private String password;

    @NotBlank(message = "Please enter first name")
    @Length(min = 3, message = "Min length = 3")
    @Column(name = "first_name")
    private String firsName;

    @NotBlank(message = "Please enter last name")
    @Length(min = 3, message = "Min length = 3")
    @Column(name = "last_name")
    private String lastName;

    @Email(message = "Your email not correct")
    @Column(name = "email")
    private String email;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    private StatusUser statusUser;

    @OneToMany (mappedBy = "user", fetch = FetchType.EAGER)
    private Set<Order> orders = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return statusUser.equals(StatusUser.ACTIVE);
    }
}



