package com.fish.apple.core.common.api;

public class PageResponse<T> extends Response<T> {
    
    public PageInfo getPage() {
		return page;
	}

	public void setPage(PageInfo page) {
		this.page = page;
	}

	private PageInfo page;


    public PageResponse() {
        success(null);
    }
    
    public PageResponse<T> success(T data , PageInfo page) {
    	super.success(data);
        this.page = page;
        return this;
    }
}
