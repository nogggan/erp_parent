package com.entor.erp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.entor.erp.entity.Dep;
import com.entor.erp.service.IDepService;

@RestController
@RequestMapping("/dep")
public class DepController {
	
	@Autowired
	private IDepService depService;
	
	@GetMapping("/list")
	public List<Dep>findList(){
		return depService.findByList();
	}
	
	@GetMapping("/add")
	public Dep dept(Dep dep) {
		if(depService.addDep(dep))
			return dep;
		return null;
	}
	
	@GetMapping("/update")
	public String update(Dep dep) {
		if(depService.updateDep(dep))
			return "修改成功";
		return "修改失败";
	}

}
