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
@Table(name = "TRAINING")
@Setter
@ToString
public class Training extends BaseEntity {

    private static final String TRAINING_SEQ_GENERATOR = "TRAINING_SEQ_GENERATOR";

    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = TRAINING_SEQ_GENERATOR)
    @SequenceGenerator(name = TRAINING_SEQ_GENERATOR, sequenceName = "TRAINING_SEQ")
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
            name = "GYM_ID",
            foreignKey = @ForeignKey(name = "FK_TRAINING_GYM_ID")
    )
    @Getter
    private Gym gym;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "USER_ID",
            foreignKey = @ForeignKey(name = "FK_TRAINING_USER_ID")
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
