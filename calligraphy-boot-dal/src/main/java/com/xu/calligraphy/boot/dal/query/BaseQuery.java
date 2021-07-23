package com.xu.calligraphy.boot.dal.query;

/**
 * @author xu
 * @date 2020/1/1 12:40
 */
public class BaseQuery {
    private Long currentPage = 1L;
    private Long pageSize = 10L;
    private Long limit;

    public Long getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Long currentPage) {
        this.currentPage = currentPage;
    }

    public Long getPageSize() {
        return pageSize;
    }

    public void setPageSize(Long pageSize) {
        this.pageSize = pageSize;
    }

    public Long getLimit() {
        return (currentPage - 1) * pageSize;
    }

    public void setLimit(Long limit) {
        this.limit = limit;
    }
}
