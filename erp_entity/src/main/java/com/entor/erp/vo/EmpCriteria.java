package com.entor.erp.vo;

import java.util.Date;

import com.entor.erp.serializer.DateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;

@Data
public class EmpCriteria {
	
	private Long uuid;
	
    private String username;
    
    private String name;
    
    private Integer gender;
    
    private String email;
    
    private String tele;
    
    private String address;
    
    @JsonSerialize(using=DateSerializer.class)
    private Date birthday;
    
    private Integer depuuid;

}
