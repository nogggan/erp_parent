$(function(){
	var orderDetailUrl = basePath+"/orderdetail/";
	var stats = new Array();
	var ordersType = Request['type'];
	var orderType = new Array();
	orderType[1] = '采购订单';
	orderType[2] = '销售订单';
	var orderDetailState = new Array();
	var endTimeName; // 如果type=1显示入库日期，type=2显示出库日期
	var supplierName; // 如果type=1显示供应商，type=2显示客户
	if(ordersType==1){
		stats[0] = '未审核';
		stats[1] = '已审核';
		stats[2] = '已确认';
		stats[3] = '已入库';
		orderDetailState[0] = "未入库";
		orderDetailState[1] = "已入库";
		endTimeName = "入库日期";
		supplierName="供应商";
	}else if(ordersType==2){
		stats[0] = '未出库';
		stats[1] = '已出库';
		orderDetailState[0] = "未揽收";
		orderDetailState[1] = "已揽收";
		endTimeName = "出库日期";
		supplierName = "客户";
	}
	$("#grid").datagrid({
		url:pageUrl,
		title:"订单列表",
		fitColumns:"true",
		columns: [[
			{field: 'uuid', title: '订单编号', width: 80,align:'center'},
			{field: 'createtime', title: '生成日期', align:'center',width: 150},
			{field: 'checktime', title: '检查日期', width: 150, align:'center'},
			{field: 'starttime', title: '开始日期', width: 150, align:'center'},
			{field: 'endtime', title: endTimeName, width: 150, align:'center'},
			{field: 'type', title: '订单类型', width: 80, align:'center',formatter:function(value,row,index){
				return orderType[value];
			}},
			{field: 'creater', title: '下单员', width: 100, align:'center',formatter: function(value,row,index) {
				if(value == null)
					return "";
				else
					return row.creater.name;}},
			{field: 'checker', title: '审查员', width: 100, align:'center',formatter: function(value,row,index) {
				if(value == null)
					return "";
				else
					return row.checker.name}},
			{field: 'starter', title: '采购员', width: 100, align:'center',formatter: function(value,row,index) {
				if(value == null)
					return "";
				else
					return row.starter.name}},
			{field: 'ender', title: '库管员', width: 100, align:'center',formatter: function(value,row,index) {
				if(value == null)
					return "";
				else
					return row.ender.name}},
			{field: 'supplier', title: supplierName, width: 200, align:'center',formatter:function(value,row,index){
				if(value == null)
					return "";
				else
					return value.name+" ("+value.address+") ";
			}},
			{field: 'totalmoney', title: '总金额', width: 80, align:'center',formatter(value,row,index){
				return OSREC.CurrencyFormatter.format(value,{currency:'CNY'});
			}},
			{field: 'state', title: '订单状态', align:'center',width:80,formatter:function(value,row,index){
				return stats[value];
			}},
			operator
		]],
		view: detailview,
		height:400,
		detailFormatter: function(index, row) {
			return "<div id='ddv_" + index + "'></div>";
		},
		onExpandRow: function(index, row) {
			$("#ddv_"+index).datagrid({
				url:orderDetailUrl+row.uuid,
				method:"GET",
				columns:[[
					{field: 'uuid', title: '编号', width: 100,align:'center'},
					{field: 'goodsuuid', title: '商品编号', width: 100,align:'center'},
					{field: 'goodsname', title: '商品名称', width: 100,align:'center'},
					{field: 'price', title: '价格', width: 100,align:'center',formatter:function(value,row,index){
						return OSREC.CurrencyFormatter.format(value,{currency:'CNY'});
					}},
					{field: 'num', title: '数量', width: 100,align:'center'},
					{field: 'money', title: '金额', width: 100,align:'center',formatter:function(value,row,index){
						return OSREC.CurrencyFormatter.format(value,{currency:'CNY'});
					}},
					{field: 'endtime', title: endTimeName, width: 150,align:'center'},
					{field: 'ender', title: '仓管员', width: 100,align:'center',formatter:function(value,row,index){
						if(value != null)
							return value.name;
						else
							return "";
					}},
					{field: 'store', title: '仓库', width: 100,align:'center',formatter:function(value,row,index){
						if(value != null)
							return value.name;
						else
							return "";
					}},
					{field: 'state', title: '状态', width: 100,align:'center',formatter:function(value,row,index){
						return orderDetailState[value];
					}}
				]],
				onDblClickRow:function(rowIndex,rowData){
					if(isDouble){
						$("#orderWindow").window('open');
						$("#goodsuuid").html(rowData.goodsuuid);
						$("#goodsname").html(rowData.goodsname);
						$("#num").html(rowData.num);
						$("#id").val(rowData.uuid);
						dblSelectIndex = rowIndex;
						expandRowIndex = index;
					}
				},
				singleSelect:"true",
				loadFilter:function(data){
					var value = {total:0,rows:[]};
					for(var i=0;i<data.rows.length;i++){
						if((isFilter&&data.rows[i].state=="0")||!isFilter){
							value.rows.push(data.rows[i]);
							value.total++;
						}
					}
					return value;
				}
			})
		},
		singleSelect: "true",
		pagination: true, 
		pageSize: '5',
		pageList: [5, 10, 15, 20]
	});
	if (Request['type'] == 2) {
		$('#grid').datagrid('hideColumn', 'checktime');
		$('#grid').datagrid('hideColumn', 'starttime');
		$('#grid').datagrid('hideColumn', 'checker');
		$('#grid').datagrid('hideColumn', 'starter');
	}
	$("#searchBtn").bind('click', function() {
		var data = getFormData("searchForm");
		$("#grid").datagrid('reload',data );
	});
})

//修改订单的审核状态为已审核
function doCheck(id){
	var flag = $.messager.confirm("提示","确定要修改此订单为已审核吗?",function(data){
		if(data){
			$.post(checkUrl,{uuid:id},function(data){
				if(data.code==200){
					alert(data.data);
					$("#grid").datagrid("reload");
				}else{
					alert(data.msg);
				}
			});
		}
	});
}

//修改订单的审核状态为已确认
function doConfirm(id){
	var flag = $.messager.confirm("提示","确定要修改此订单为已确认吗?",function(data){
		if(data){
			$.post(confirmUrl,{uuid:id},function(data){
				if(data.code==200){
					alert(data.data);
					$("#grid").datagrid("reload");
				}else{
					alert(data.msg);
				}
			});
		}
	});
}

//订单入库
function doInStore(){
	var formData = getFormData("orderForm");
	var flag = $.messager.confirm("提示","确定要入库吗?",function(data){
		if(data){
			$.post(instoreUrl,formData,function(result){
				if(result.code==200){
					$("#orderWindow").window("close");
					$("#ddv_"+expandRowIndex).datagrid("deleteRow",dblSelectIndex);
					if($("#ddv_"+expandRowIndex).datagrid("getRows").length==0){
						$("#grid").datagrid("deleteRow",expandRowIndex);
					}
					alert(result.data);
				}else{
					alert(result.msg);
				}
			});
		}
	});
}

//订单出库
function doOutStore(){
	var formData = getFormData("orderForm");
	var flag = $.messager.confirm("提示","确定要出库吗?",function(data){
		if(data){
			$.post(outstoreUrl,formData,function(result){
				if(result.code==200){
					$("#orderWindow").window("close");
					$("#ddv_"+expandRowIndex).datagrid("deleteRow",dblSelectIndex);
					if($("#ddv_"+expandRowIndex).datagrid("getRows").length==0){
						$("#grid").datagrid("deleteRow",expandRowIndex);
					}
					alert(result.data);
				}else{
					alert(result.msg);
				}
			});
		}
	});
}