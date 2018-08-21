package com.entor.erp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.plugins.Page;
import com.entor.erp.entity.Dep;
import com.entor.erp.result.Result;
import com.entor.erp.result.ResultType;
import com.entor.erp.service.IDepService;

@RestController
@RequestMapping("/dep")
public class DepController {
	
	@Autowired
	private IDepService depService;
	
	@PostMapping("/list")
	public ResponseEntity<Map<String, Object>> findList(){
		Map<String, Object> map = new HashMap<>();
		List<Dep> list = depService.findByList();
		map.put("total",list.size());
		map.put("rows",list);
		HttpStatus status = HttpStatus.OK;
		return new ResponseEntity<>(map, status);
	}
	
	/**
	 * 添加部门
	 * @param dep 
	 * @return {@link Result}
	 */
	@PostMapping("/add")
	public Result<String> add(Dep dep) {
		if(depService.addDep(dep))
			return Result.success("添加成功");
		return Result.error(ResultType.PARA_ERROR, "添加失败");
	}
	
	@DeleteMapping("/{id}")
	public Result<String> delete(@PathVariable("id")Long id){
		if(depService.delete(id))
			return Result.success("删除成功");
		return Result.error(ResultType.PARA_ERROR, "删除失败");
	}
	
	@GetMapping("/update")
	public String update(Dep dep) {
		if(depService.updateDep(dep))
			return "修改成功";
		return "修改失败";
	}
	
	@GetMapping("/get/{id}")
	public Dep get(@PathVariable("id")Long id) {
		return depService.getById(id);
	}
	
	/**
	 * 分页查询
	 * @param dep 查询实体
	 * @param pageNow 当前第几页
	 * @param pageSize 一页显示多少条
	 * @return
	 */
	@PostMapping("/page")
	public ResponseEntity<Map<String, Object>> getPage(Dep dep,@RequestParam(value="page",defaultValue="1")String pageNow,
			@RequestParam(value="rows",defaultValue="3")String pageSize){
		Integer realPageNow = 1;
		Integer realPageSize = 3;
		try {
			realPageNow = Integer.parseInt(pageNow);
		} catch (Exception e) {}
		try {
			realPageSize = Integer.parseInt(pageSize);
		} catch (Exception e) {}
		Page<Dep> page = new Page<>(realPageNow, realPageSize);
		depService.getPage(page,dep);
		Map<String, Object> map = new HashMap<>();
		map.put("total", page.getTotal());
		map.put("rows", page.getRecords());
		return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
	}

}
