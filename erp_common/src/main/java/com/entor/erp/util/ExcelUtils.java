package com.entor.erp.util;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;


public class ExcelUtils {
	
	public static final <T> HSSFWorkbook getWorkBook(ExcelModel<T> model) {
		String[] columns = model.getColumns();
		String[] fieldNames = model.getFieldNames();
		List<T> contents = model.getContents();
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFFont titleFont = workbook.createFont();
		titleFont.setFontName("微软雅黑");
		titleFont.setFontHeightInPoints((short)(14));
		titleFont.setBold(true);
		HSSFCellStyle titleCellStyle = workbook.createCellStyle();
		titleCellStyle.setAlignment(HorizontalAlignment.CENTER);
		titleCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		titleCellStyle.setFont(titleFont);
		HSSFCellStyle contentCellStyle = workbook.createCellStyle();
		contentCellStyle.setAlignment(HorizontalAlignment.CENTER);
		contentCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		HSSFSheet sheet = workbook.createSheet(model.getTitle());
		HSSFRow row01 = sheet.createRow(0);
		row01.setHeightInPoints(20);
		if(columns != null) {
			for(int i=0;i<columns.length;i++) {
				sheet.setColumnWidth(i,20*256);
				HSSFCell cell = row01.createCell(i);
				cell.setCellValue(columns[i]);
				cell.setCellStyle(titleCellStyle);
			}
		}
		if(contents != null) {
			for(int x=0;x<contents.size();x++) {
				HSSFRow row02 = sheet.createRow(x+1);
				row02.setHeightInPoints(20);
				for(int j=0;j<fieldNames.length;j++) {
					String fieldName = fieldNames[j];
					T t = contents.get(x);
					HSSFCell contentCell = row02.createCell(j);
					contentCell.setCellStyle(contentCellStyle);
					Method method = null;
					String result = null;
					if(fieldName.contains(".")) {
						try {
							method = t.getClass().getMethod(fieldName.substring(0, fieldName.indexOf(".")));
						} catch (Exception e) {
						}
						if(method != null) {
							Object cascadeObject = null;
							try {
								cascadeObject = method.invoke(t);
								method = cascadeObject.getClass().getMethod(fieldName.substring(fieldName.indexOf(".")+1));
							} catch (Exception e) {
							}
							if(method != null && cascadeObject!=null) {
								try {
									result = method.invoke(cascadeObject).toString();
								} catch (Exception e) {
								}
							}
						}
					}else {
						try {
							method = t.getClass().getMethod(fieldName);
						} catch (Exception e) {
						}
						if(method != null) {
							try {
								result = method.invoke(t).toString();
							} catch (Exception e) {
							}
						}
					}
					contentCell.setCellValue(result);
				}
			}
		}
		try {
			workbook.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return workbook;
	}

}
