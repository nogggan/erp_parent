package com.entor.erp.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.entor.erp.serializer.DateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 员工表
 * @author Gan
 *
 */
@Data
public class Emp implements Serializable{

	private static final long serialVersionUID = 8921050265653862045L;

	@TableId
	private Long uuid;
	
    private String username;
    
    private String password;
    
    private String name;
    
    private Integer gender;
    
    private String email;
    
    private String tele;
    
    private String address;
    
    @JsonSerialize(using=DateSerializer.class)
    private Date birthday;
    
    private Integer depuuid;

}
