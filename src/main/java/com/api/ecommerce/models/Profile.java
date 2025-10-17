package com.api.ecommerce.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name="profiles")
public class Profile {
    @Id
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String bio;
    private LocalDate dateOfBirth;
    private Integer loyaltyPoints;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id")
    @MapsId
    private User user;

}
