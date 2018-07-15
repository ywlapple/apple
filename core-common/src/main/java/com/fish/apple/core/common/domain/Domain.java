package com.fish.apple.core.common.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fish.apple.core.web.repository.BIdListener;

import lombok.Data;

@MappedSuperclass
@EntityListeners({BIdListener.class})
@Data
public class Domain implements Serializable {

	private static final long serialVersionUID = -1975423804162673584L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
    private String tenantNo;
    @JsonIgnore
    private Date createTime;
    @JsonIgnore
    private Date updateTime;
    @JsonIgnore
    private String createUser;
    @JsonIgnore
    private String updateUser;
}
