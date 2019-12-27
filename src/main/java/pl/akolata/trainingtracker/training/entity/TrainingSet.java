package pl.akolata.trainingtracker.training.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.akolata.trainingtracker.core.entity.BaseEntity;
import pl.akolata.trainingtracker.exercises.entity.Exercise;

import javax.persistence.*;

@Entity
@Table(name = "TRAINING_SET")
@Getter
@Setter
@ToString
public class TrainingSet extends BaseEntity {

    private static final String TRAINING_SET_SEQ_GENERATOR = "TRAINING_SET_SEQ_GENERATOR";

    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = TRAINING_SET_SEQ_GENERATOR)
    @SequenceGenerator(name = TRAINING_SET_SEQ_GENERATOR, sequenceName = "TRAINING_SET_SEQ")
    @Access(AccessType.PROPERTY)
    private Long id;

    @ManyToOne
    @JoinColumn(
            name = "EXERCISE_ID",
            foreignKey = @ForeignKey(name = "FK_TRAINING_SET_EXERCISE_ID")
    )
    private Exercise exercise;

    @ManyToOne
    @JoinColumn(
            name = "TRAINING_ID",
            foreignKey = @ForeignKey(name = "FK_TRAINING_SET_TRAINING_ID")
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
