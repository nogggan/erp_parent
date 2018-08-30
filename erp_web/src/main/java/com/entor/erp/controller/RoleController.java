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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.plugins.Page;
import com.entor.erp.entity.Role;
import com.entor.erp.entity.Tree;
import com.entor.erp.result.Result;
import com.entor.erp.result.ResultType;
import com.entor.erp.service.IRoleMenuService;
import com.entor.erp.service.IRoleService;

@RestController
@RequestMapping("/role")
@Validated
public class RoleController {
	
	@Autowired
	private IRoleService roleService;
	
	@Autowired
	private IRoleMenuService roleMenuService;
	
	/**
	 * 角色分页
	 * @param role  条件查询的载体
	 * @param pageNow	当前第几页
	 * @param pageSize		一页显示多少条数据
	 * @return
	 */
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
	
	/**
	 * 修改角色的权限
	 * @param ids 权限点的id
	 * @param roleid 角色id
	 * @return
	 */
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
	
	/**
	 * 查询指定员工拥有的角色
	 * @param empid		员工id
	 * @return
	 */
	@PostMapping("/roleTree")
	public List<Tree> getRoleTree(@RequestParam(value="empid",required=false)
						@Validated @NotNull(message="员工编号不能为空")Long empid){
		return roleService.getRoleTree(empid);
	}
	
}
