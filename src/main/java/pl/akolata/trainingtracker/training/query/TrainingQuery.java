package pl.akolata.trainingtracker.training.query;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import pl.akolata.trainingtracker.common.query.BaseQuery;
import pl.akolata.trainingtracker.training.entity.Training;

import java.time.LocalDate;

@Getter
@EqualsAndHashCode(callSuper = true)
public class TrainingQuery extends BaseQuery {
    private final Long id;
    private final String name;
    private final String additionalInfo;
    private Long userId;
    private LocalDate date;

    public TrainingQuery(Long id, String name, String additionalInfo, Pageable pageable) {
        super(pageable);
        this.id = id;
        this.name = name;
        this.additionalInfo = additionalInfo;
    }

    public TrainingQuery(Long id, String name, String additionalInfo, Long userId, LocalDate date, Pageable pageable) {
        super(pageable);
        this.id = id;
        this.name = name;
        this.additionalInfo = additionalInfo;
        this.userId = userId;
        this.date = date;
    }

    public Specification<Training> toSpecification() {
        return new TrainingSpecification(this);
    }
}
