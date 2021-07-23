package com.xu.calligraphy.boot.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xu
 * @date 2020/1/1 13:11
 */
@Data
public class Page<T> implements Serializable {
    private Long currentPage;
    private Long total;
    private T content;

    public Page(Long currentPage, Long total, T content) {
        this.currentPage = currentPage;
        this.total = total;
        this.content = content;
    }
}
