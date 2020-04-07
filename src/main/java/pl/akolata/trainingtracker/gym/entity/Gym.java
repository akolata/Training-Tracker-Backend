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
        name = "gym",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "gym_uuid_uk",
                        columnNames = BaseEntity.COLUMN_UUID
                ),
                @UniqueConstraint(
                        name = "gym_name_uk",
                        columnNames = Gym.COLUMN_NAME
                )
        }
)
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
public class Gym extends BaseEntity implements Serializable {

    static final String COLUMN_NAME = "name";
    private static final String GYM_SEQ_GENERATOR = "GYM_SEQ_GENERATOR";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = GYM_SEQ_GENERATOR)
    @SequenceGenerator(name = GYM_SEQ_GENERATOR, sequenceName = "gym_seq", allocationSize = 1)
    @Access(AccessType.PROPERTY)
    private Long id;

    @Column(nullable = false, name = COLUMN_NAME)
    private String name;
}
