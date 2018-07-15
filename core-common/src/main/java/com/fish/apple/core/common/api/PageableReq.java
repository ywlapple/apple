package com.fish.apple.core.common.api;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;

import com.fish.apple.core.common.domain.Domain;
import com.fish.apple.core.web.env.Environment;

public class PageableReq<T> {

	private T data ;
	private PageInfo pageInfo = new PageInfo();
	

    public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
	public int getPage() {
		return pageInfo.page;
	}

	public void setPage(int page) {
		this.pageInfo.page = page;
	}
	
	public int getSize() {
		return pageInfo.size;
	}
	public void setSize(int size ) {
		this.pageInfo.setSize(size);
	}
	public void setPageInfo(PageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}
	public PageInfo getPageInfo() {
		return this.pageInfo;
	}
	
	public PageRequest getPageRequest() {
		return PageRequest.of(this.pageInfo.getPage(), this.pageInfo.getSize());
	}
	public Example<T> getExample(){
		if(null != data) {
			if(data instanceof Domain){
				Domain domain = (Domain)data ;
				domain.setTenantNo(Environment.currentTenantNo());
			}
			return Example.of(data);
		}
		return null;
	}

	@Override
    public String toString() {
        return ReflectionToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	public static class PageInfo{
		private int page = 1 ;
		private int size = 20 ;
		public int getPage() {
			return page;
		}
		public void setPage(int page) {
			this.page = page;
		}
		public int getSize() {
			return size;
		}
		public void setSize(int size) {
			this.size = size;
		}
		
	}
}
