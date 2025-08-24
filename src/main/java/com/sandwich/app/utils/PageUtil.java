package com.sandwich.app.utils;

import com.sandwich.app.domain.dto.pagination.PageData;
import com.sandwich.app.domain.dto.pagination.PageMetaData;
import com.sandwich.app.domain.dto.pagination.Pagination;
import com.sandwich.app.domain.dto.pagination.Sorting;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;


@UtilityClass
public class PageUtil {

    /**
     * Создание страницы с результатами поиска.
     *
     * @param totalPages    общее количество страниц
     * @param totalElements общее количество найденных элементов
     * @param results       список с искомыми данными
     * @param pagination    информация о пейджинге
     * @param <R>           тип результата
     * @return страница с результатами поиска
     */
    public static <R> PageData<R> createPage(int totalPages, Long totalElements, List<R> results, Pagination pagination) {
        var pageData = new PageData<R>();
        PageMetaData metaData = new PageMetaData();
        pageData.setMetaData(metaData);
        metaData.setNumber(pagination.getPageNo());

        metaData.setTotalPages(totalPages);
        metaData.setTotalElements(totalElements);
        metaData.setNumber(pagination.getPageNo());
        metaData.setSize(pagination.getPageSize());

        pageData.setContent(results);

        return pageData;
    }

    /**
     * Сбор параметров сортировки.
     *
     * @param sorting параметры сортировки.
     * @return описание сортировки
     */
    public static Sort createSort(List<Sorting> sorting) {
        Sort sort = Sort.unsorted();
        if (!sorting.isEmpty()) {
            for (Sorting item : sorting) {
                sort = sort.and(Sort.by(
                    Sort.Direction.fromString(item.getSortType().toString()),
                    item.getParameter()));

            }
        } else {
            sort = Sort.by(Sort.Direction.ASC, "id");
        }
        return sort;
    }

    /**
     * Перевод пагинации из запроса в пагинацию QDSL.
     *
     * @param pagination - параметра пагинации
     * @param sort       - тип сортировки
     * @return запрос страницы
     */
    public static Pageable createPageable(Pagination pagination, Sort sort) {
        return PageRequest
            .of(pagination.getPageNo(), pagination.getPageSize(), sort);
    }

    /**
     * Подсчёт общего количества страниц.
     *
     * @param totalElements - общее количество элементов в БД
     * @param content       - набор элементов за один запрос
     * @return общее количество страниц
     */
    public static int countTotalPages(long totalElements, List<?> content) {
        return totalElements == 0 ? 1 : (int) Math.ceil((double) totalElements / (double) content.size());
    }
}
