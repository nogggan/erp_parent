package com.entor.erp.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;

import lombok.Data;

@Data
public class Emp {

	@TableId
	private Long uuid;
	
    private String username;
    
    private String password;
    
    private String name;
    
    private Integer gender;
    
    private String email;
    
    private String tele;
    
    private String address;
    
    private Date birthday;
    
    private Integer depuuid;

	
}
