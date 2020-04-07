package pl.akolata.trainingtracker.training.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.akolata.trainingtracker.core.entity.BaseEntity;
import pl.akolata.trainingtracker.exercise.entity.Exercise;

import javax.persistence.*;

@Entity
@Table(
        name = "training_set",
        indexes = {
                @Index(
                        name = "training_set_training_id_idx",
                        columnList = TrainingSet.COLUMN_TRAINING_ID
                ),
                @Index(
                        name = "training_set_exercise_id_idx",
                        columnList = TrainingSet.COLUMN_EXERCISE_ID
                )
        },
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "training_set_uuid_uk",
                        columnNames = BaseEntity.COLUMN_UUID
                )
        }
)
@Getter
@Setter
public class TrainingSet extends BaseEntity {
    static final String COLUMN_EXERCISE_ID = "exercise_id";
    static final String COLUMN_TRAINING_ID = "training_id";
    private static final String TRAINING_SET_SEQ_GENERATOR = "TRAINING_SET_SEQ_GENERATOR";

    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = TRAINING_SET_SEQ_GENERATOR)
    @SequenceGenerator(name = TRAINING_SET_SEQ_GENERATOR, sequenceName = "training_set_seq", allocationSize = 1)
    @Access(AccessType.PROPERTY)
    private Long id;

    @ManyToOne
    @JoinColumn(
            name = COLUMN_EXERCISE_ID,
            foreignKey = @ForeignKey(name = "training_set_exercise_fk")
    )
    private Exercise exercise;

    @ManyToOne
    @JoinColumn(
            name = COLUMN_TRAINING_ID,
            foreignKey = @ForeignKey(name = "training_set_training_fk")
    )
    @ToString.Exclude
    private Training training;

    @Column
    private Integer reps;

    @Column
    private Integer weight;

    @Column
    private Integer calories;

    @Column
    private Integer durationInMinutes;

    @Column(scale = 2)
    private Double distanceInKm;

    @Column
    private String additionalInfo;
}
