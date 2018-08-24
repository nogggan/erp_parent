package com.entor.erp.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.entor.erp.dao.SupplierMapper;
import com.entor.erp.entity.Supplier;
import com.entor.erp.service.ISupplierService;

@Service
public class SupplierServiceImpl extends ServiceImpl<SupplierMapper, Supplier>
							implements ISupplierService{

}
