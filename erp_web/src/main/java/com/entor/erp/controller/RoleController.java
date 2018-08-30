package com.entor.erp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.plugins.Page;
import com.entor.erp.entity.Role;
import com.entor.erp.entity.RoleMenu;
import com.entor.erp.result.Result;
import com.entor.erp.result.ResultType;
import com.entor.erp.service.IMenuService;
import com.entor.erp.service.IRoleMenuService;
import com.entor.erp.service.IRoleService;

@RestController
@RequestMapping("/role")
public class RoleController {
	
	@Autowired
	private IRoleService roleService;
	
	@Autowired
	private IRoleMenuService roleMenuService;
	
	@PostMapping("/page")
	public ResponseEntity<Map<String, Object>> getPage(Role role,@RequestParam(value="page",defaultValue="1")String pageNow,
			@RequestParam(value="rows",defaultValue="3")String pageSize){
		Integer realPageNow = 1;
		Integer realPageSize = 3;
		try {
			realPageNow = Integer.parseInt(pageNow);
		} catch (Exception e) {}
		try {
			realPageSize = Integer.parseInt(pageSize);
		} catch (Exception e) {}
		Page<Role> page = new Page<>(realPageNow, realPageSize);
		page = roleService.getPage(page, role);
		Map<String, Object> body = new HashMap<>();
		body.put("total", page.getTotal());
		body.put("rows",page.getRecords());
		return new ResponseEntity<Map<String,Object>>(body,HttpStatus.OK);
	}
	
	@PostMapping("/updateRoleAuth")
	public Result<String> updateRoleAuth(@RequestParam(value="ids",required=false)
						@Validated @NotBlank(message="菜单编号不能为空")String ids,
						@RequestParam(value="roleid",required=false) 
						@Validated @NotNull(message="角色编号不能为空")Long roleid){
		String[] menuIds = ids.split(",");
		if(roleMenuService.updateRoleMenu(roleid, menuIds))
			return Result.success("权限修改成功");
		else
			return Result.error(ResultType.ERROR, "修改权限失败，请重新尝试");
	}
	
}
