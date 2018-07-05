package com.fish.apple.core.common.domain;

import java.io.Serializable;


public interface BIdable extends Serializable {

	String getBIdName();
	String getBId();
	void setBId(String bId );
    
}
