<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<#assign base=request.contextPath>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title></title>
<link href="${base}/omui/css/elegant/om-all.css" rel="stylesheet" type="text/css" />
<link href="${base}/css/admin.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="${base}/js/jquery-1.7.1.js"></script>
<script type="text/javascript" src="${base}/js/HForm.js"></script>
<script type="text/javascript" src="${base}/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${base}/omui/js/operamasks-ui.min.js"></script>
<script type="text/javascript" src="${base}/omui/js/common.js"></script>
<script type="text/javascript" src="${base}/bui/js/common.js"></script>
<script type="text/javascript" src="${base}/js/export.js"></script>

<script type="text/javascript">


	$().ready(function(){

		//初始化控件
		$("#search-panel").omPanel({
			title : "条件搜索",collapsible:false
		});

		//搜索
		$('#searchButton').bind('click', function(e) {
		     var data = $("#listForm").HForm('form2json');
		     $('#listGrid').omGrid({extraData:data});
		});

		$("#start-time").omCalendar();
		$("#time-end").omCalendar();

		 $('#searchButton').omButton({
		 	icons : {left : '${base}/static/omui/images/search.png'},width : 70
		 });

        $(".input-select").change(function(){
            $('#searchButton').click();
        });

		$('#buttonbar').omButtonbar({
			btns : [{label:"导出Excel",
						id:"addbutton" ,
						icons : {left : '${base}/static/omui/images/export.png'},
						onClick:function()
							{
								exportUtil({
									title : "列表导出",
									exportUrl : "${base}/admin/goods/searchList",
									extraParam : $("#listForm").HForm('form2json')
								});
							}
						}
					]
		});


		//初始化列表
		var  height=$(document).height() -$('#search-panel').outerHeight()-$('#buttonbar').outerHeight()-40;
		$('#listGrid').omGrid({
			height:height,
			limit:20,
			method:'post',
			singleSelect:false,
			extraData:$("#listForm").HForm('form2json'),
			dataSource : '${base}/admin/goods/searchList',
			colModel : [
			{header : 'id', name : 'id',width : 90, align : 'left',sort:'serverSide'},


			{header : '商品编码', name : 'goodsSn',width : 90, align : 'left',sort:'serverSide'},


			{header : '商品名称', name : 'name',width : 90, align : 'left',sort:'serverSide'},


			{header : '标题', name : 'title',width : 90, align : 'left',sort:'serverSide'},


			{header : '售价', name : 'price',width : 90, align : 'left',sort:'serverSide'},


			{header : '商品状态', name : 'status',width : 90, align : 'left',sort:'serverSide'},


			{header : '销量', name : 'saleCount',width : 90, align : 'left',sort:'serverSide'},


				{header : '创建时间', name : 'createDate', width : 150, align : 'left',sort:'serverSide',renderer :dataFormat1},
				{header : '修改时间', name : 'modifyDate', width : 150, align : 'left',sort:'serverSide',renderer :dataFormat1},

			],
			rowDetailsProvider:function(rowData){
			}
		});

		//初始化控件  end
		function getIds(datas) {
			var str = "";
			for (var i = 0; i < datas.length; i++) {
				str += datas[i].id + ",";
			}
			//去掉最后一个逗号(如果不需要去掉，就不用写)
			if (str.length > 0) {
				str = str.substr(0, str.length - 1);
			}
			return str;
		}

		$('#searchButton').click();
	});



</script>
</head>
<body >

<div id="search-panel">
    <form id="listForm">
	<div>
		<span class="label">状态:</span>
		<select class="js-example-basic-single input-select"  name="filter_status">
			<option value="0" selected>出售中</option>
			<option value="1">已下架</option>
			<option value="">全部</option>
		</select>
		<span class="label">商品编码：</span>
		<input type="text" class="input-text" name="filter_goodsSn" />
		<span class="label">商品名称：</span>
		<input type="text" class="input-text" name="filter_name" />
		<span class="label">创建时间：</span>
		<input id="start-time" style="width: 118px" name="filter_beginDate"/>
		-
		<input id="time-end"  style="width: 118px" name="filter_endDate"/>
		<span id="searchButton">查询</span>
	</div>
	</form>
</div>

<div id="buttonbar"></div><!-- 工具栏位置 -->
<table id="listGrid"></table> <!-- 主列表位置 -->

</body>
</html>