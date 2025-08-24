package com.sandwich.app.domain.dto.pagination;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(staticName = "byDefault")
@AllArgsConstructor(staticName = "of")
public class Pagination {

    /* номер страницы */
    private Integer pageNo = 0;

    /* размер страницы */
    private Integer pageSize = 50;

    @JsonIgnore
    public Integer getLimit() {
        return pageSize;
    }

    /**
     * Получить смещение.
     *
     * @return смещение.
     */
    @JsonIgnore
    public Long getOffset() {
        if (pageNo == null || pageSize == null) {
            return null;
        }
        return ((long) pageNo) * pageSize;
    }
}