package penyaka.petproject.spring_rest_web_mvc.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class PagingUtil {
    private final static int DEFAULT_PAGE = 0;
    private final static int DEFAULT_PAGE_SIZE = 25;
    public static PageRequest buildPageRequest (Integer pageNumber, Integer pageSize, String sortBy) {
        int queryPageNumber;
        int queryPageSize;

        if (pageNumber != null && pageNumber > 0)
            queryPageNumber = pageNumber-1;
        else
            queryPageNumber = DEFAULT_PAGE;


        if (pageSize == null) {
            queryPageSize = DEFAULT_PAGE_SIZE;
        } else {
            if (pageSize > 1000) {
                queryPageSize = 1000;
            } else {
                queryPageSize = pageSize;
            }
        }

        Sort sort = Sort.by(Sort.Order.asc(sortBy));

        return PageRequest.of(queryPageNumber, queryPageSize, sort);
    }
}
