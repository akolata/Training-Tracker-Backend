package pl.akolata.trainingtracker.training.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.akolata.trainingtracker.core.entity.BaseEntity;
import pl.akolata.trainingtracker.core.entity.User;
import pl.akolata.trainingtracker.gym.entity.Gym;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
        name = "training",
        indexes = {
                @Index(
                        name = "training_gym_id_idx",
                        columnList = Training.COLUMN_GYM_ID
                ),
                @Index(
                        name = "training_user_id_idx",
                        columnList = Training.COLUMN_USER_ID
                )
        },
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "training_uid_uk",
                        columnNames = BaseEntity.COLUMN_UUID
                )
        }
)
@Setter
public class Training extends BaseEntity {
    static final String COLUMN_USER_ID = "user_id";
    static final String COLUMN_GYM_ID = "gym_id";

    private static final String TRAINING_SEQ_GENERATOR = "TRAINING_SEQ_GENERATOR";

    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = TRAINING_SEQ_GENERATOR)
    @SequenceGenerator(name = TRAINING_SEQ_GENERATOR, sequenceName = "training_seq", allocationSize = 1)
    @Access(AccessType.PROPERTY)
    private Long id;

    @Column(nullable = false)
    @Getter
    private LocalDate date;

    @Column
    @Getter
    private String additionalInfo;

    @Column
    @Getter
    private String name;

    @ManyToOne
    @JoinColumn(
            name = COLUMN_GYM_ID,
            foreignKey = @ForeignKey(name = "training_gym_fk")
    )
    @Getter
    private Gym gym;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = COLUMN_USER_ID,
            foreignKey = @ForeignKey(name = "training_user_fk")
    )
    @Getter
    @ToString.Exclude
    private User user;

    @OneToMany(
            mappedBy = "training",
            cascade = {CascadeType.ALL},
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private Set<TrainingSet> sets = new HashSet<>();

    public void addTrainingSet(TrainingSet trainingSet) {
        trainingSet.setTraining(this);
        sets.add(trainingSet);
    }

    public Set<TrainingSet> getSets() {
        return Collections.unmodifiableSet(sets);
    }
}
