package com.fish.apple.core.common.api;

import com.fish.apple.core.common.exception.Result;

public class Response<T> {
	private boolean success = true;
    private String code ;
    private String message;
    private String remark;
    //业务数据
    private T data;
    
    public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public boolean isSuccess() {
        return success;
    }

    public Response() {
        success(null);
    }

    
    public Response<T> success(T data ) {
        this.success = true;
        this.code = Result.Success.getCode() ;
        this.message = Result.Success.getMessage();
        this.data = data;
        return this;
    }
    
    public Response<T> failed(String code ) {
    	return failed(code , null , null);
    }
    public Response<T> failed(String code , String message ) {
    	return failed(code , message , null);
    }    
    public Response<T> failed(String code ,  String message ,String remark) {
        this.success = false;
        this.code = code ;
        this.message = message;
        this.remark = remark ;
        return this;
    } 
}
