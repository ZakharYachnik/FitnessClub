package by.zakharyachnik.fitnessclub.entity;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Entity
@Table(name = "users_memberships")
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode
public class UserMembership {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Membership membership;

    @Column(nullable = false)
    private boolean active;

    @Column
    private LocalDate purchaseDate;
}
