package pl.akolata.trainingtracker.common.query;

import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;

@UtilityClass
public class PaginationHeadersUtils {

    private static final String HEADER_PAGE_NUMBER = "Page-Number";
    private static final String HEADER_PAGE_SIZE = "Page-Size";
    private static final String HEADER_PAGE_TOTAL_PAGES = "Page-Total-Pages";
    private static final String HEADER_PAGE_TOTAL_ELEMENTS = "Page-Total-Elements";

    public HttpHeaders buildPaginationHeaders(Page<?> page) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HEADER_PAGE_NUMBER, "" + (page.getNumber() + 1));
        headers.add(HEADER_PAGE_SIZE, "" + page.getSize());
        headers.add(HEADER_PAGE_TOTAL_PAGES, "" + page.getTotalPages());
        headers.add(HEADER_PAGE_TOTAL_ELEMENTS, "" + page.getTotalElements());
        return headers;
    }
}
