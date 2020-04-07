package pl.akolata.trainingtracker.core.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
        name = "tt_user",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "tt_user_uuid_uk",
                        columnNames = BaseEntity.COLUMN_UUID
                ),
                @UniqueConstraint(
                        name = "tt_user_username_uk",
                        columnNames = User.COLUMN_USERNAME
                ),
                @UniqueConstraint(
                        name = "tt_user_email_uk",
                        columnNames = User.COLUMN_EMAIL
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
public class User extends BaseEntity implements Serializable {

    static final String COLUMN_USERNAME = "username";
    static final String COLUMN_EMAIL = "email";
    private static final String USER_SEQ_GENERATOR = "USER_SEQ_GENERATOR";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = USER_SEQ_GENERATOR)
    @SequenceGenerator(name = USER_SEQ_GENERATOR, sequenceName = "tt_user_seq", allocationSize = 1)
    @Access(AccessType.PROPERTY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, name = COLUMN_USERNAME)
    private String username;

    @Column(nullable = false, name = COLUMN_EMAIL)
    private String email;

    @Column(nullable = false)
    private String password;

    @Embedded
    private UserAccountDetails userAccountDetails;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "USER_ROLE",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "ROLE_ID"),
            foreignKey = @ForeignKey(name = "FK_USER_ROLE"),
            inverseForeignKey = @ForeignKey(name = "FK_ROLE_USER"))
    @Getter
    private Set<Role> roles = new HashSet<>();

    public static User createNewActiveUser(String firstName, String lastName, String username, String email, String password) {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setUserAccountDetails(new UserAccountDetails(false, false, false, true));
        return user;
    }
}
