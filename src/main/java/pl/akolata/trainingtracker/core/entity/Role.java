package pl.akolata.trainingtracker.core.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(
        name = "TT_ROLE",
        uniqueConstraints = {@UniqueConstraint(name = "UK_ROLE_NAME", columnNames = Role.COLUMN_NAME)}
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
    @SequenceGenerator(name = ROLE_SEQ_GENERATOR, sequenceName = "ROLE_SEQ", allocationSize = 1)
    @Access(AccessType.PROPERTY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = COLUMN_NAME)
    private RoleName name;

    Role(RoleName name) {
        this.name = name;
    }
}