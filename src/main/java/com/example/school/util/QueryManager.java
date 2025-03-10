package com.example.school.util;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;

import java.util.List;

public class QueryManager<T> {

    private static final int DEFAULT_PAGE_SIZE = 10;
    
    public PagedResult<T> getPagedResult(PanacheQuery<T> query, int page, Integer size) {
        int pageSize = size != null ? size : DEFAULT_PAGE_SIZE;
        
        query.page(Page.of(page, pageSize));
        List<T> list = query.list();
        long count = query.count();
        
        return new PagedResult<>(list, page, pageSize, count);
    }
    
    public static class PagedResult<T> {
        private final List<T> data;
        private final int page;
        private final int pageSize;
        private final long totalItems;
        private final long totalPages;
        
        public PagedResult(List<T> data, int page, int pageSize, long totalItems) {
            this.data = data;
            this.page = page;
            this.pageSize = pageSize;
            this.totalItems = totalItems;
            this.totalPages = pageSize > 0 ? (totalItems + pageSize - 1) / pageSize : 0;
        }
        
        public List<T> getData() {
            return data;
        }
        
        public int getPage() {
            return page;
        }
        
        public int getPageSize() {
            return pageSize;
        }
        
        public long getTotalItems() {
            return totalItems;
        }
        
        public long getTotalPages() {
            return totalPages;
        }
    }
}
