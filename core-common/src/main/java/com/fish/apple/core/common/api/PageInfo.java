package com.fish.apple.core.common.api;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class PageInfo {
    private static final Integer DEFAULT_PAGE_SIZE = 10;
    private static final Integer DEFAULT_PAGE = 1;
    private Integer              total;
    private Integer              size;
    private Integer              page;

    public PageInfo(Integer page , Integer size) {
    	this.page = page;
    	this.size = size;
    }
    public Integer getPage() {
        return page == null ? DEFAULT_PAGE : page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size == null ? DEFAULT_PAGE_SIZE : size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getTotalPage() {
        Integer total = getTotal();
        if (total != null) {
            int left = total % getSize();
            return total / getSize() + (left > 0 ? 1 : 0);
        }
        return null;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
