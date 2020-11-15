package ru.school21.matcha.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.school21.matcha.utils.validation.email.ValidEmail;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "t_user")
public class User implements UserDetails {

    private static final long serialVersionUID = 1259641534805949841L;

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "User name can't be empty")
    @Size(min = 2, max = 32, message = "User name must be between 2 and 32 characters")
    @Column(nullable = false, length = 32)
    private String username;

    @NotBlank(message = "First name can't be empty")
    @Size(min = 2, max = 64, message = "First name must be between 2 and 64 characters")
    @Column(name = "first_name", nullable = false, length = 64)
    private String firstName;

    @NotBlank(message = "Last name can't be empty")
    @Size(min = 2, max = 64, message = "Last name must be between 2 and 64 characters")
    @Column(name = "last_name", nullable = false, length = 64)
    private String lastName;

    @NotBlank(message = "Password can't be empty")
    @Size(min = 6, max = 60, message = "Password must be between 6 and 60 characters")
    @Column(nullable = false, length = 60)
    private String password;

    @Transient
    private String matchingPassword;

    @NotBlank(message = "Email can't be empty")
    @Size(max = 64, message = "Email must be less then 64 characters")
    @ValidEmail
    @Column(nullable = false, length = 64)
    private String email;

    @Column(nullable = false)
    private boolean enabled;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    private Set<Role> roles;

    public User() {

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    public void setAuthorities(Set<Role> roles) {
        this.roles = roles;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
