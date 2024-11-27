package de.telran.restapitestwork.model.entity.user;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor (access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
@Table(name = "user_")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "jobTitle")
    private String jobTitle;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;

    @Column(name = "interests")
    private String[] interests;

    @Column(name = "links")
    private String links;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "profile")
    @Enumerated(EnumType.STRING)
    private de.telran.restapitestwork.model.entity.user.profile profile;

}
