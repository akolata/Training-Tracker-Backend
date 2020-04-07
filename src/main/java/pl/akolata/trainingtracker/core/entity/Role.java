package pl.akolata.trainingtracker.core.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(
        name = "tt_role",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "tt_role_name_uk",
                        columnNames = Role.COLUMN_NAME
                ),
                @UniqueConstraint(
                        name = "tt_role_uuid_uk",
                        columnNames = BaseEntity.COLUMN_UUID
                )
        }
)
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
public class Role extends BaseEntity {

    static final String COLUMN_NAME = "NAME";
    private static final String ROLE_SEQ_GENERATOR = "ROLE_SEQ_GENERATOR";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = ROLE_SEQ_GENERATOR)
    @SequenceGenerator(name = ROLE_SEQ_GENERATOR, sequenceName = "tt_role_seq", allocationSize = 1)
    @Access(AccessType.PROPERTY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = COLUMN_NAME, length = 32)
    private RoleName name;
}