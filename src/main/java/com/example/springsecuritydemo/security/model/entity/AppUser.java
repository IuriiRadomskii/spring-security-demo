package com.example.springsecuritydemo.security.model.entity;

import com.example.springsecuritydemo.model.entity.UserProfile;
import com.example.springsecuritydemo.security.enums.UserAuthorities;
import com.example.springsecuritydemo.security.enums.UserRoles;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "APP_USER")
@Table(name = "app_user")
public class AppUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(unique = true)
    private String login;
    @Column
    private String pass;
    @Enumerated(EnumType.STRING)
    private List<UserRoles> roles;
    @Enumerated(EnumType.STRING)
    private List<UserAuthorities> authorities;
    @CreationTimestamp
    private Instant createdAt;
    @UpdateTimestamp
    private Instant updatedAt;
    @OneToOne(mappedBy = "appUser", cascade = CascadeType.ALL)
    private UserProfile userProfile;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
         var roleList =  Stream.of(roles)
                .flatMap(Collection::stream)
                .map(UserRoles::name)
                .map(SimpleGrantedAuthority::new)
                .toList();
         var authorityList = Stream.of(authorities)
                .flatMap(Collection::stream)
                .map(UserAuthorities::name)
                .map(SimpleGrantedAuthority::new)
                .toList();
        Set<SimpleGrantedAuthority> set = new HashSet<>(roleList);
        set.addAll(authorityList);
        return set;
    }

    @Override
    public String getPassword() {
        return pass;
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
        return true;
    }
}
