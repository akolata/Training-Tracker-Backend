package pl.akolata.trainingtracker.core.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAccountDetails implements Serializable {

    @Column(nullable = false)
    private Boolean accountExpired;

    @Column(nullable = false)
    private Boolean accountLocked;

    @Column(nullable = false)
    private Boolean credentialsExpired;

    @Column(nullable = false)
    private Boolean enabled;
}
