package pl.akolata.trainingtracker.gym.query;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import pl.akolata.trainingtracker.common.query.BaseQuery;
import pl.akolata.trainingtracker.gym.entity.Gym;

@Getter
@EqualsAndHashCode(callSuper = true)
public class GymQuery extends BaseQuery {
    private String name;

    public GymQuery(String name, Pageable pageable) {
        super(pageable);
        this.name = name;
    }

    public Specification<Gym> toSpecification() {
        return new GymSpecification(this);
    }
}
