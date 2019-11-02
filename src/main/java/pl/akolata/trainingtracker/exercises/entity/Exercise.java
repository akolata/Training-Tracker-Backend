package pl.akolata.trainingtracker.exercises.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.akolata.trainingtracker.core.entity.BaseEntity;

import javax.persistence.*;

@Entity
@Table(
        name = "EXERCISE",
        uniqueConstraints = {
                @UniqueConstraint(name = "UK_EXERCISE_NAME", columnNames = Exercise.COLUMN_NAME)
        }
)
@Getter
@Setter
@ToString
public class Exercise extends BaseEntity {

    static final String COLUMN_NAME = "NAME";

    private static final String EXERCISE_SEQ_GENERATOR = "EXERCISE_SEQ_GENERATOR";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = EXERCISE_SEQ_GENERATOR)
    @SequenceGenerator(name = EXERCISE_SEQ_GENERATOR, sequenceName = "EXERCISE_SEQ")
    @Access(AccessType.PROPERTY)
    private Long id;

    @Column(nullable = false, name = COLUMN_NAME)
    private String name;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private ExerciseType type;

    public enum ExerciseType {
        CARDIO,
        WEIGHT_LIFTING,
        GROUP_WORKOUT,
        ;
    }
}
