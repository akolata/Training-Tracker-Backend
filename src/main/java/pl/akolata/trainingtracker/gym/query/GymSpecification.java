package pl.akolata.trainingtracker.gym.query;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import pl.akolata.trainingtracker.gym.entity.Gym;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.LinkedList;
import java.util.List;

@RequiredArgsConstructor
class GymSpecification implements Specification<Gym> {
    private final GymQuery query;

    @Override
    public Predicate toPredicate(Root<Gym> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
        List<Predicate> predicates = new LinkedList<>();

        if (query.getName() != null) {
            predicates.add(cb.like(cb.lower(root.get("name")), "%" + query.getName().toLowerCase() + "%"));
        }

        return cq
                .where(cb.and(predicates.toArray(new Predicate[0])))
                .distinct(true)
                .getRestriction();

    }
}
