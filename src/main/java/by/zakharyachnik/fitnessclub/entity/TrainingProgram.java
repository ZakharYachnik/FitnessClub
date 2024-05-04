package by.zakharyachnik.fitnessclub.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

@Entity
@Table(name = "training_programs")
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode
public class TrainingProgram {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String description;
}
