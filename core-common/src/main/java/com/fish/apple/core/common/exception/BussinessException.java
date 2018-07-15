package com.fish.apple.core.common.exception;

import java.util.ArrayList;

import com.fish.apple.core.common.domain.EnumBase;


/**
 * 最外层的异常，当该异常需要暴露到接口中时，使用该类型，选择自己的错误编码和参数，
 * 如果异常仅仅是需要打日志，则可以使用自己的异常，在响应中如果捕获到该异常，框架会封装成通用异常暴露出去
 * @author ysh9832
 *
 */
public class BussinessException extends RuntimeException {
	private static final long serialVersionUID = -8832697903697007504L;
	
	private EnumBase kind;
	private ReadableList<Object> args = new ReadableList<>();

    public BussinessException() {
    	super() ;
    }
    public BussinessException(Throwable e ) {
    	super(e);
    }    
    public BussinessException(String msg ) {
    	super(msg) ;
    }
    public BussinessException(String msg , Throwable e ) {
    	super(msg , e);
    }
    
    public static BussinessException create() {
    	return new BussinessException();
    }
    public static BussinessException create(String msg) {
    	return new BussinessException(msg);
    }
    public static BussinessException create(Exception e) {
    	return new BussinessException(e);
    }
    public static BussinessException create(String msg ,Exception e) {
    	return new BussinessException(msg,e);
    }
   
    public BussinessException kind(ExceptionKind kind) {
    	this.kind = kind;
    	return this;
    }
    public BussinessException msg(Object arg) {
    	this.args.addInternal(arg);
    	return this;
    }
    public EnumBase getKind() {
    	return kind;
    }
    public Object[] getArgs(){
    	return args.toArray();
    }
    
    private class ReadableList<E> extends ArrayList<E> {
		private static final long serialVersionUID = -5893830681285112745L;

		public boolean addInternal(E e) {
    		return super.add(e);
    	}
    	
    	@Override
        public boolean add(E e) {
            throw new RuntimeException("Read only list , not allowed to use method(add)");
        }
    }
}
