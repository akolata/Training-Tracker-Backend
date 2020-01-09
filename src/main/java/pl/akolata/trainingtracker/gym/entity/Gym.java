package pl.akolata.trainingtracker.gym.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pl.akolata.trainingtracker.core.entity.BaseEntity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(
        name = "GYM",
        uniqueConstraints = {
                @UniqueConstraint(name = "GYM_NAME_UK", columnNames = Gym.COLUMN_NAME)
        }
)
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
public class Gym extends BaseEntity implements Serializable {

    static final String COLUMN_NAME = "NAME";
    private static final String GYM_SEQ_GENERATOR = "GYM_SEQ_GENERATOR";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = GYM_SEQ_GENERATOR)
    @SequenceGenerator(name = GYM_SEQ_GENERATOR, sequenceName = "GYM_SEQ")
    @Access(AccessType.PROPERTY)
    private Long id;

    @Column(nullable = false, name = COLUMN_NAME)
    private String name;
}
