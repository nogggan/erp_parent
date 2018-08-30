package com.entor.erp.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.entor.erp.dao.RoleMenuMapper;
import com.entor.erp.entity.RoleMenu;
import com.entor.erp.service.IRoleMenuService;

@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu>
						implements IRoleMenuService{

}
