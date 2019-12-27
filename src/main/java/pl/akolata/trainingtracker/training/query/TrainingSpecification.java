package pl.akolata.trainingtracker.training.query;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import pl.akolata.trainingtracker.training.entity.Training;

import javax.persistence.criteria.*;
import java.util.LinkedList;
import java.util.List;

@AllArgsConstructor
class TrainingSpecification implements Specification<Training> {

    private final TrainingQuery trainingQuery;

    @Override
    public Predicate toPredicate(Root<Training> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
        List<Predicate> predicates = new LinkedList<>();

        if (trainingQuery.getId() != null) {
            predicates.add(cb.equal(root.get("id"), trainingQuery.getId()));
        }

        if (trainingQuery.getName() != null) {
            predicates.add(cb.like(cb.lower(root.get("name")), "%" + trainingQuery.getName().toLowerCase() + "%"));
        }

        if (trainingQuery.getAdditionalInfo() != null) {
            predicates.add(cb.like(cb.lower(root.get("additionalInfo")), "%" + trainingQuery.getAdditionalInfo().toLowerCase() + "%"));
        }

        if (currentQueryIsCountRecords(criteriaQuery)) {
            root.join("gym", JoinType.LEFT);
            Join<Object, Object> sets = root.join("sets", JoinType.LEFT);
            sets.join("exercise", JoinType.LEFT);
        } else {
            root.fetch("gym", JoinType.LEFT);
            Fetch<Object, Object> sets = root.fetch("sets", JoinType.LEFT);
            sets.fetch("exercise", JoinType.LEFT);
        }

        return criteriaQuery
                .where(cb.and(predicates.toArray(new Predicate[0])))
                .distinct(true)
                .getRestriction();
    }

    private boolean currentQueryIsCountRecords(CriteriaQuery<?> criteriaQuery) {
        return criteriaQuery.getResultType() == Long.class || criteriaQuery.getResultType() == long.class;
    }
}
