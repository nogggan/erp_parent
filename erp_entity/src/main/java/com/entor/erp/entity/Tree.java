package com.entor.erp.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Tree implements Serializable{

	private static final long serialVersionUID = 7390657592140462812L;

	private String id;
	
	private String text;
	
	private boolean checked;
	
	private List<Tree> children = new ArrayList<>();
	
}
