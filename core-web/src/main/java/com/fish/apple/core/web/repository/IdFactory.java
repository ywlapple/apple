package com.fish.apple.core.web.repository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IdFactory {
	@Autowired
	private SequenceRepository repository;
	private List<IdInfo> idInfoList;
	private static IdFactory factory;
	private IdInfo defaultIdInfo;
	
	@PostConstruct
	public void init(){
		factory = this;
	}
	
	
	public static String getCommonId(){
		if(null == factory){
			throw new IllegalStateException("Id工厂未正确初始化");
		}
		return factory.parseId(factory.getDefaultIdInfo());
		
	}
	public static String getId(String idName){
		if(null == factory){
			throw new IllegalStateException("Id工厂未正确初始化");
		}
		for(IdInfo idInfo : factory.getIdInfoList()){
			if(idName.equals(idInfo.getName())){
				return factory.parseId(idInfo);
			}
		}
		return factory.parseId(null);
	}
	
	public static String getUUID(){
		return UUID.randomUUID().toString();
	}
	
	private String parseId(IdInfo idInfo){
		if(null == idInfo){
			idInfo = defaultIdInfo ;
		}
		if(null == idInfo ) {
			return getUUID();
		}
		String pre = null == idInfo.getPrefix()? "":idInfo.getPrefix();
		String middle = idInfo.getPattern();
		String afterfix = null == idInfo.getSuffix() ? "" : idInfo.getSuffix() ;
		
		long sequence = repository.next(idInfo.getSequenceName());
		
		if(org.apache.commons.lang3.StringUtils.isBlank(middle) ){
			middle = Long.valueOf(sequence).toString();
		}else{
			middle = parseString(middle,sequence);
		}
		StringBuffer sb = new StringBuffer(pre.length() + middle.length() + afterfix.length());
		sb.append(pre).append(middle).append(afterfix);
		return sb.toString();
	}
	
	public String parseString(String pattern , long sequence ){
		String datePattern = pattern.replace("0", "");
		
		SimpleDateFormat format = new SimpleDateFormat(datePattern);
		String date = format.format(new Date());
		List<Integer> list = new ArrayList<Integer>(pattern.length());
		for(int i = 0 ;i < pattern.length() ;i++ ){
			char a = pattern.charAt(i);
			if('0' == a){
				list.add(i);
			}
		}
		int zeroLength = list.size() ;
		if(zeroLength > 0){
			String number = Long.valueOf(sequence).toString();
			if(number.length() > zeroLength){
				number = number.substring(number.length()-zeroLength);
				
			}else if(number.length() < zeroLength){
				int zeroPreLength = zeroLength -number.length();
				char[] zeroPreChar = new char[zeroPreLength];
				for(int j=0 ; j< zeroPreLength ;j++){
					zeroPreChar[j] = '0';
				}
				number = new String(zeroPreChar) + number;
			}
			char[] result = new char[pattern.length()];
			int l = 0;
			
			for(int k=0 ; k < list.size() ;k++ ){
				int index = list.get(k);
				for( ; l < index-k; l++){
					result[l+k] = date.charAt(l);
				}
				result[index] = number.charAt(k);
			}
			
			for(;l <date.length() ;l++  ){
				result[l+list.size()] = date.charAt(l);
			}
			return new String(result);	
		}else{
			return date;
		}
		
	}
	

	public SequenceRepository getRepository() {
		return repository;
	}

	public void setRepository(SequenceRepository repository) {
		this.repository = repository;
	}

	public List<IdInfo> getIdInfoList() {
		return idInfoList;
	}

	public void setIdInfoList(List<IdInfo> idInfoList) {
		this.idInfoList = idInfoList;
	}


	public IdInfo getDefaultIdInfo() {
		return defaultIdInfo;
	}


	public void setDefaultIdInfo(IdInfo defaultIdInfo) {
		this.defaultIdInfo = defaultIdInfo;
	}
	
	public static class IdInfo{
		
		private String name;
		private String sequenceName;
		private String prefix;
		private String pattern;
		private String suffix;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getSequenceName() {
			return sequenceName;
		}
		public void setSequenceName(String sequenceName) {
			this.sequenceName = sequenceName;
		}
		public String getPrefix() {
			return prefix;
		}
		public void setPrefix(String prefix) {
			this.prefix = prefix;
		}
		public String getPattern() {
			return pattern;
		}
		public void setPattern(String pattern) {
			this.pattern = pattern;
		}
		public String getSuffix() {
			return suffix;
		}
		public void setSuffix(String suffix) {
			this.suffix = suffix;
		}
		
	}
}
