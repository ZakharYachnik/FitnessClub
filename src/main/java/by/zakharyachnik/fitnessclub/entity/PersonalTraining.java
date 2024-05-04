package by.zakharyachnik.fitnessclub.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

@Entity
@Table(name = "personal_trainings")
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode
public class PersonalTraining {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User customer;

    @ManyToOne
    private User trainer;

    @ManyToOne
    private TrainingProgram trainingProgram;

    @Column
    private Boolean active;
}
