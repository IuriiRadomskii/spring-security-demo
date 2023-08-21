package com.example.reactdemoback.model.entity;

import com.example.reactdemoback.security.model.entity.AppUser;
import jakarta.persistence.*;
import lombok.*;


@Builder
@Entity(name = "USER_PROFILE")
@Table(name = "user_profile")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column
    private String name;
    @Column
    private String surname;
    @Column
    private String birthTown;
    @Column
    private String birthDate;
    @Column
    private String about;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "app_user_id", referencedColumnName = "id")
    private AppUser appUser;

    @Override
    public String toString() {
        return "UserProfile{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", birthTown='" + birthTown + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", about='" + about + '\'' +
                ", appUser=" + appUser.getId() +
                '}';
    }
}
