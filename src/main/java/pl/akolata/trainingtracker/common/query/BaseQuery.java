package pl.akolata.trainingtracker.common.query;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@AllArgsConstructor
public class BaseQuery {

    private static final int DEFAULT_PAGE_NUMBER = 0;
    private static final int DEFAULT_PAGE_SIZE = 20;
    private static final Sort.Direction DEFAULT_SORT_DIRECTION = Sort.Direction.ASC;
    private static final String DEFAULT_SORT_ORDER = "id";

    protected final Pageable pageable;

    public Pageable getPageable() {
        if (pageable != null) {
            return pageable;
        }

        return PageRequest.of(
                DEFAULT_PAGE_NUMBER,
                DEFAULT_PAGE_SIZE,
                Sort.by(DEFAULT_SORT_DIRECTION, DEFAULT_SORT_ORDER));
    }
}
