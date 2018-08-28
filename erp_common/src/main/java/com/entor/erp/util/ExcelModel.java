package com.entor.erp.util;

import java.util.Arrays;
import java.util.List;

public class ExcelModel<T> {
	
	private String[] columns;
	
	private String[] fieldNames;
	
	private List<T> contents;
	
	private String title;
	
	

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String[] getColumns() {
		return columns;
	}

	public void setColumns(String[] columns) {
		this.columns = columns;
	}

	public String[] getFieldNames() {
		return fieldNames;
	}

	public void setFieldNames(String[] fieldNames) {
		this.fieldNames = fieldNames;
	}

	public List<T> getContents() {
		return contents;
	}

	public void setContents(List<T> contents) {
		this.contents = contents;
	}

	@Override
	public String toString() {
		return "ExcelModel [columns=" + Arrays.toString(columns) + ", fieldNames=" + Arrays.toString(fieldNames)
				+ ", contents=" + contents + ", title=" + title + "]";
	}

	
	
}
