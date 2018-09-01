package com.entor.erp.controller;

import java.awt.Font;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.baomidou.mybatisplus.plugins.Page;
import com.entor.erp.aspect.RequiredPermission;
import com.entor.erp.exception.GlobalException;
import com.entor.erp.result.Result;
import com.entor.erp.result.ResultType;
import com.entor.erp.service.IGoodsService;
import com.entor.erp.util.ExcelModel;
import com.entor.erp.util.ExcelUtils;
import com.entor.erp.vo.GoodsCountVo;

@Controller
@RequestMapping("/goodscount")
public class GoodsCountController {
	
	@Autowired
	private IGoodsService goodsService;
	
	/**
	 * 销售统计表
	 * @param pageNow
	 * @param pageSize
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@PostMapping("/page")
	@RequiredPermission("销售报表")
	public ResponseEntity<Map<String, Object>> getPage(@RequestParam(value="page",defaultValue="1")String pageNow,
			@RequestParam(value="rows",defaultValue="3")String pageSize,
			Date startDate,Date endDate){
		System.out.println(startDate);
		System.out.println(endDate);
		Integer realPageNow = 1;
		Integer realPageSize = 3;
		try {
			realPageNow = Integer.parseInt(pageNow);
		} catch (Exception e) {}
		try {
			realPageSize = Integer.parseInt(pageSize);
		} catch (Exception e) {}
		Page<GoodsCountVo> page = new Page<>(realPageNow,realPageSize);
		page = goodsService.getGoodsCountInfo(page,startDate,endDate);
		Map<String, Object> body = new HashMap<>();
		body.put("total", page.getTotal());
		body.put("rows",page.getRecords());
		return new ResponseEntity<Map<String,Object>>(body,HttpStatus.OK);
	}
	
	/**
	 * 销售报表统计图
	 * @param response
	 * @param startDate
	 * @param endDate
	 */
	@GetMapping("/chart")
	@RequiredPermission("销售报表")
	public void chart(HttpServletResponse response,Date startDate,Date endDate) {
		List<GoodsCountVo> goodsCountInfo = goodsService.getGoodsCountInfo(startDate, endDate);
		DefaultPieDataset defaultPieDataset = new DefaultPieDataset();
		if(goodsCountInfo != null && !goodsCountInfo.isEmpty()) {
			goodsCountInfo.forEach(goodscount->{
				defaultPieDataset.setValue(goodscount.getName(), goodscount.getMoney());
			});
		}
		JFreeChart jFreeChart = ChartFactory.createPieChart3D("销售统计图", defaultPieDataset);
		jFreeChart.setTitle(new TextTitle("销售统计图", new Font("微软雅黑", Font.BOLD, 18)));
		PiePlot3D piePlot3D = (PiePlot3D) jFreeChart.getPlot();
		piePlot3D.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}[{1}元]({2})",new DecimalFormat("0.0"),new DecimalFormat("0.0%")));
		piePlot3D.setLabelFont(new Font("微软雅黑", Font.PLAIN,14));
		jFreeChart.getLegend().setItemFont(new Font("微软雅黑", Font.PLAIN,14));
		try {
			ChartUtils.writeChartAsPNG(response.getOutputStream(), jFreeChart, 450,330);
		} catch (IOException e) {
			throw new GlobalException(Result.error(ResultType.ERROR, "生成统计图失败，请重新尝试"));
		}
	}
	
	/**
	 * 导出销售Excel报表
	 * @param response
	 * @param startDate
	 * @param endDate
	 * @throws UnsupportedEncodingException
	 */
	@GetMapping("/exportExcel")
	@RequiredPermission("销售报表")
	public void excel(HttpServletResponse response,Date startDate,Date endDate) throws UnsupportedEncodingException {
		String fileName = "销售报表统计";
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment;filename="+URLEncoder.encode(fileName+".xls", "utf-8"));
		ExcelModel<GoodsCountVo> excelModel = new ExcelModel<>();
		List<GoodsCountVo> goodsCountInfo = goodsService.getGoodsCountInfo(startDate, endDate);
		excelModel.setColumns(new String[] {"商品类型名称","销售总额"});
		excelModel.setFieldNames(new String[] {"getName","getMoney"});
		excelModel.setContents(goodsCountInfo);
		excelModel.setTitle(fileName);
		HSSFWorkbook workBook = ExcelUtils.getWorkBook(excelModel);
		try {
			workBook.write(response.getOutputStream());
		} catch (IOException e) {
			throw new GlobalException(Result.error(ResultType.ERROR, "下载销售报表Excel失败，请重新尝试"));
		}
	}
	
}
