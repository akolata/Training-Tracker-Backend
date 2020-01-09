package pl.akolata.trainingtracker.exercise.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pl.akolata.trainingtracker.core.entity.BaseEntity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(
        name = "EXERCISE",
        uniqueConstraints = {
                @UniqueConstraint(name = "EXERCISE_NAME_TYPE_UK", columnNames = {Exercise.COLUMN_NAME, Exercise.COLUMN_TYPE})
        }
)
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Exercise extends BaseEntity implements Serializable {

    static final String COLUMN_NAME = "NAME";
    static final String COLUMN_TYPE = "TYPE";

    private static final String EXERCISE_SEQ_GENERATOR = "EXERCISE_SEQ_GENERATOR";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = EXERCISE_SEQ_GENERATOR)
    @SequenceGenerator(name = EXERCISE_SEQ_GENERATOR, sequenceName = "EXERCISE_SEQ")
    @Access(AccessType.PROPERTY)
    private Long id;

    @Column(nullable = false, name = COLUMN_NAME)
    private String name;

    @Column(nullable = false, name = COLUMN_TYPE)
    @Enumerated(value = EnumType.STRING)
    private ExerciseType type;

    public enum ExerciseType {
        CARDIO,
        WEIGHT_LIFTING,
        GROUP_WORKOUT,
        ;
    }
}
