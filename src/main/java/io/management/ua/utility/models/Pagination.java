package io.management.ua.utility.models;

import lombok.*;
import org.springframework.data.domain.Sort;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pagination {
    private int pageNumber;
    private int pageSize;
    private String sortBy;
    private Sort.Direction direction;
}
