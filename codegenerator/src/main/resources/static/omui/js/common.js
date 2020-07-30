woyouhuo = {
	baseUrl: "/fm95338",
	currencySign: "￥",
	currencyUnit: "元",
	priceScale: "2",
	priceRoundType: "roundHalfUp"
};

//货币格式化 88.88
function priceFormat(price) {
	price = setScale(price, woyouhuo.priceScale, woyouhuo.priceRoundType);
	return price;
}
//货币格式化 88.88
function priceFormat2(price) {
	if(price.length == 0 ){
		price = "";
	}else{
		price = setScale(price, woyouhuo.priceScale, woyouhuo.priceRoundType);
	}
	console.info("price:"+price);
	return price;
}

//货币格式  8,888.88
function formatCurrency(num) {
    num = num.toString().replace(/\$|\,/g,'');
    if(isNaN(num))
        num = "0";
    var sign = (num == (num = Math.abs(num)));
    num = Math.floor(num*100+0.50000000001);
    var cents = num%100;
    num = Math.floor(num/100).toString();
    if(cents<10)
        cents = "0" + cents;
    for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
        num = num.substring(0,num.length-(4*i+3))+','+
            num.substring(num.length-(4*i+3));
    return (((sign)?'':'-') + num + '.' + cents);
}

//浮点数加法运算
function floatAdd(arg1, arg2) {
	var r1, r2, m;
	try{
		r1 = arg1.toString().split(".")[1].length;
	} catch(e) {
		r1 = 0;
	}
	try {
		r2 = arg2.toString().split(".")[1].length;
	} catch(e) {
		r2 = 0;
	}
	m = Math.pow(10, Math.max(r1, r2));
	return (arg1 * m + arg2 * m) / m;
}

// 浮点数减法运算
function floatSub(arg1, arg2) {
	var r1, r2, m, n;
	try {
		r1 = arg1.toString().split(".")[1].length;
	} catch(e) {
		r1 = 0
	}
	try {
		r2 = arg2.toString().split(".")[1].length;
	} catch(e) {
		r2 = 0
	}
	m = Math.pow(10, Math.max(r1, r2));
	n = (r1 >= r2) ? r1 : r2;
	return ((arg1 * m - arg2 * m) / m).toFixed(n);
}

// 浮点数乘法运算
function floatMul(arg1, arg2) {
	var m = 0, s1 = arg1.toString(), s2 = arg2.toString();
	try {
		m += s1.split(".")[1].length;
	} catch(e) {}
	try {
		m += s2.split(".")[1].length;
	} catch(e) {}
	return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m);
}

// 浮点数除法运算
function floatDiv(arg1, arg2) {
	var t1 = 0, t2 = 0, r1, r2;    
	try {
		t1 = arg1.toString().split(".")[1].length;
	} catch(e) {}
	try {
		t2 = arg2.toString().split(".")[1].length;
	} catch(e) {}
	with(Math) {
		r1 = Number(arg1.toString().replace(".", ""));
		r2 = Number(arg2.toString().replace(".", ""));
		return (r1 / r2) * pow(10, t2 - t1);
	}
}

// 设置数值精度
function setScale(value, scale, roundingMode) {
	if (roundingMode.toLowerCase() == "roundhalfup") {
		return (Math.round(value * Math.pow(10, scale)) / Math.pow(10, scale)).toFixed(scale);
	} else if (roundingMode.toLowerCase() == "roundup") {
		return (Math.ceil(value * Math.pow(10, scale)) / Math.pow(10, scale)).toFixed(scale);
	} else {
		return (Math.floor(value * Math.pow(10, scale)) / Math.pow(10, scale)).toFixed(scale);
	}
}

function percentFormat(percent) {
	if (percent == null || percent == '' || percent == undefined){
        percent = 0;
	}
	return (Math.round(percent * 10000)/100).toFixed(2) + '%';
}

/**
 * 删除单行数据
 * @param {} url
 * @param {} gridId omui grid ID
 * @param {} index 当然选中行
 */
function deleteByOne(url,gridId,index){
	var $grid = $("#"+gridId);
	$.omMessageBox.confirm({
	           title:'确认删除',
	           content:'删除该项数据后，它所有相关的数据将同时删除（不可恢复），你确定要这样做吗？',
	           onClose:function(v){
		           if(v){
						var rowData = $grid.omGrid("getData").rows[index];
						$.post(url,{ids:rowData.id},function(data){
						  if(data){
							  $grid.omGrid('reload');
							  $.omMessageTip.show({type:'success',title: "提示", content: "删除成功", timeout: 1500});
						   }else{
						      $.omMessageTip.show({type:'error',title: "提示", content: "删除失败", timeout: 1500});
						   }
						});
		           }
	           }
	});
}

/**
 * 撤回单行数据
 * @param {} url
 * @param {} gridId omui grid ID
 * @param {} index 当然选中行
 */
function retract(url,gridId,index){
	var $grid = $("#"+gridId);
	$.omMessageBox.confirm({
	           title:'确认撤回',
	           content:'确定要撤回该项数据吗？',
	           onClose:function(v){
		           if(v){
						var rowData = $grid.omGrid("getData").rows[index];
						$.post(url,{id:rowData.id},function(data){
						  if(data){
							  $grid.omGrid('reload');
							  $.omMessageTip.show({type:'success',title: "提示", content: "删除成功", timeout: 1500});
						   }else{
						      $.omMessageTip.show({type:'error',title: "提示", content: "删除失败", timeout: 1500});
						   }
						});
		           }
	           }
	});
}

/**
 * 复制商品
 */
function suppliergoodsCopy(id){
    $.omMessageBox.confirm({
        title:'确认复制',
        content:'确定需要复制商品数据？',
        onClose:function(v){
            if(v){
                //window.location.href = "${base}/admin/goods/gooodscopy/"+id +"?isnew=true";
                var url = base+"/supplier/goods/gooodscopy/"+id +"?isnew=true";
                $.ajax({
                    url: url,
                    type: "GET",
                    dataType:'json',
                    success: function(data) {
                        if(data != null){
                            //alert(data);
                            var eUrl = base+"/supplier/goods/update/"+data+"?isnew=true";
                            openTab("editGoods",eUrl,"编辑商品");
                        }
                    }

                })
            }
        }
    });
}



function deleteById(url,gridId,index){
	var $grid = $("#"+gridId);
	$.omMessageBox.confirm({
	           title:'确认删除',
	           content:'删除该项数据后，它所有相关的数据将同时删除（不可恢复），你确定要这样做吗？',
	           onClose:function(v){
		           if(v){
						var rowData = $grid.omGrid("getData").rows[index];
						$.ajax({
							url: url,
							type: "POST",
							data: {id:rowData.id},
							dataType:'json',
							success: function(data) {
								 if(data.status=="success"){
									  $grid.omGrid('reload');
									  $.omMessageTip.show({type:'success',title: "提示", content:"删除成功", timeout: 1500});
								   }else{
									  
								      $.omMessageTip.show({type:'error',title: "提示", content:data.message, timeout: 1500});
								   }
	                        }
		            	 });
		           }
	           }
	});
}




/**
 * 日期格式转换 格式 yyyy-MM-dd HH:mm:ss
 * @param colValue
 * @returns
 */
function dataFormat1(colValue){
	if(colValue!=null && colValue!=""){
        return $.omCalendar.formatDate(new Date(parseInt(colValue)), "yy-mm-dd H:i:s");
    }
	return "";
}
/**
 * 日期格式转换 格式 yyyy-MM-dd
 * @param colValue
 * @returns
 */
function dataFormat2(colValue){
	if(colValue!=null && colValue!=""){
        return $.omCalendar.formatDate(new Date(colValue), "yy-mm-dd");
    }
	return "";
}

/**
 * 日期格式转换 格式 yyyy-MM-dd
 * @param colValue
 * @returns
 */
function dataFormat3(colValue){
	if(colValue!=null && colValue!=""){
		return $.omCalendar.formatDate(new Date(colValue), "yy年mm月");
	}
	return "";
}



/**
 * 比较2个时间的大小
 * @param beginDate
 * @param endDate
 * @returns {boolean}
 */
function compareDate(beginDate,endDate){
    if (beginDate > endDate){
        return true
    }
    return false;
}

function showBoolean(colValue){
	if(true==colValue){
		return '<span style="color:red">是</span>';
	}else if(false==colValue){
		return "否";
	}else{
		return "";
	}
}
function orderStatusShow(colValue,row){
	var text;
	if("NOPAY" == colValue){
		text = "待付款";
	}else if("PAYFINISH" == colValue){
		text = "待发货";
		if(row != null && row.createDate != null){
			// <span style="color:red">是</span>
			var diff = new Date().getTime() - row.createDate;
			if(diff > 36 * 60 * 60 * 1000 && diff <= 48 * 60 * 60 * 1000){
				text = "<span style='color:#f1540e'>" + text +"</span>";
			}else if(diff > 48 * 60 * 60 * 1000){
				text = "<span style='color:red'>" + text +"</span>";
			}
		}
	}else if("SHIPPED" == colValue){
		text = "待收货";
	}else if("FINISHED" == colValue){
		text = "交易成功";
	}else if("CANCEL" == colValue){
		text = "交易取消";
	}else if("OVERTIME" == colValue){
		text = "超时未付款";
	}else if("CLOSEED" == colValue){
		text = "交易关闭";
	}else if("DELETED" == colValue){
		text = "已删除订单";
	} else if("WAITING_SHOP_BUYING" == colValue){
		text = "待成团";
	} else if("WAITING_FILL_ADDR" == colValue){
        text = "待填地址";
	} else if("WAIT_LOTTERY_DRAW" == colValue){
        text = "待开奖";
    }else{
		text = colValue;
	}
	return text;
}

function workOrderStatusShow(colValue,row){
	var text;
	if("AFTER_SALES_AUDIT" == colValue){
		text = "售后审核中";
		if(row != null && row.createDate != null){
			// <span style="color:red">是</span>
			var now = new Date().getTime();
			var diff = new Date().getTime() - row.createDate;
			if(diff > 18 * 60 * 60 * 1000 && diff <= 24 * 60 * 60 * 1000){
				text = "<span style='color:#f1540e'>" + text +"</span>";
			}else if(diff > 24 * 60 * 60 * 1000){
				text = "<span style='color:red'>" + text +"</span>";
			}
		}
	}else if("SELLER_HANDLED" == colValue){
		text = "商家已处理";
	}else if("SELLER_AGREED_REFUND" == colValue){
		text = "商家同意退款";
	}else if("USER_REVOKE" == colValue){
		text = "用户主动撤销";
	}else if("APPEAL_AUDIT" == colValue){
		text = "申诉审核中";
	}else if("REFUND_SUCCESS" == colValue){
		text = "退款成功";
	}else if("AFTER_SALES_CLOSURE" == colValue){
		text = "售后关闭";
	}else if("PLATFORM_HANDLED" == colValue){
		text = "平台介入";
	}else if("WAIT_USER_CONFIRM" == colValue){
        text = "待用户确认";
    }else if("ALL_COMPENSATION_BY_PLATFORM" == colValue){
		text = "平台介入全赔";
	}else{
		text = colValue;
	}
	return text;
}

function tradeStatusShow(colValue){
	if("TRADE_SUCCESS" == colValue){
		return "交易成功";
	}else if("TRADE_FINISHED" == colValue){
		return "交易完成";
	}else if("TRADE_FAIL" == colValue){
		return "交易失败";
	}else{
		return colValue;
	}
}

function refundStatusShow(colValue){
	if("SUCCESS" == colValue){
		return "退款成功";
	}else if("REFUNDING" == colValue){
        return "退款中";
	}else if("FAIL" == colValue){
        return "退款失败";
    }else{
		return "";
	}
}

function payTypeShow(colValue){
	if("ALIPAY" == colValue){
		return "支付宝";
	}else if("WEIXIN" == colValue){
		return "微信";
	}else if("APP_WEIXIN" == colValue){
        return "APP微信支付";
    }else if("QQQB" == colValue){
		return "QQ钱包";
	}else if("APP_ALIPAY" == colValue){
        return "APP支付宝支付";
    }else if("SFPAY" == colValue){
        return "顺手付";
    }else if("APP_QQQB" == colValue){
        return "APPQQ钱包支付";
    }else if("DELIVERYCODE" == colValue){
        return "提货码支付";
    }else if("PREPAIDCARDCODE" == colValue){
        return "礼品卡支付";
    }else if("CMBBANKPAY" == colValue){
        return "一网通支付";
    }else if("APP_CMBBANKPAY" == colValue){
        return "APP一网通支付";
    }else if("GIFTCODE" == colValue){
        return "礼品码支付";
    }else if("MINI_WEIXIN" == colValue){
        return "小程序微信支付";
    }else if("WEIXIN_H5" == colValue) {
		return "微信H5";
	}else if("MINI_ALIPAY" == colValue){
		return "支付宝小程序";
    }else if("WEIXIN_HIPO" == colValue){
		return "微信支付";
	}else if("APP_WEIXIN_HIPO" == colValue){
		return "APP微信支付";
	}else if("ALI_HIPO" == colValue){
		return "支付宝支付";
	}else if("GLOBAL_ALIPAY" == colValue){
		return "支付宝支付";
	}else if("APP_GLOBAL_ALIPAY" == colValue){
		return "APP支付宝支付";
	}else if ("MINI_GLOBAL_ALIPAY" == colValue){
		return "支付宝小程序支付";
	}else {
		return "未知"
	}
}

function channelSourceShow(colValue){
	if("ALIPAY" == colValue){
		return "支付宝";
	}else if("WEIXIN" == colValue){
		return "微信";
	}else if("QQ" == colValue){
		return "手机QQ";
	}else if("SFPAY" == colValue){
        return "顺手付";
    }else if("ANDROID_APP" == colValue){
        return "安卓APP";
    }else if("IOS_APP" == colValue){
        return "苹果App";
    }else if("browser" == colValue){
        return "浏览器";
    }else{
		return colValue;
	}
}

function checkStatusShow(colValue,rowData){
    if("NOSUBMIT" == colValue){
        return "待提交资料";
    }else if("NOCHECK" == colValue){
        return "待审核";
    }else if("PASS" == colValue){
        return "通过";
    }else if ("NOPASS" == colValue){
		var auditNote = rowData.auditNote;
    	return "未通过<img src='${base}/static/omui/images/nopass.png' onClick=\"alert('"+auditNote+"')\" style='margin-top: -5px'>";
    }else{
        return colValue;
    }
}



/***************
 * @param url:请求地址
 * @param params:key/value键值对类型的Object
 * @param fn:获取数据成功/失败的回调方法
 */
function ajaxCallback(url,params,fn){
	var flag = typeof(fn)=="function"?true:false;
	params= params==null?"":params;
    $.ajax({
         type: "post",
         dataType: "json",//返回json格式的数据
         url: url,
         data: params,
         success: function(data){
        	 if(flag){fn(data);}
         },
         error:function(xmlHttpRequest, textStatus, errorThrown){
        	 if(flag){fn();}
         }
     });
}

function loadTime1(){
	var oDate = new Date();
	$("#fgl_select").val(oDate.getFullYear() + "-" + (oDate.getMonth()+1) + "-" + oDate.getDate());
	$("#fgl_select1").val(oDate.getFullYear() + "-" + (oDate.getMonth()+1) + "-"+ oDate.getDate());
}


function defultTime2(myDate){
	var date = new Date(myDate.replace(/-/g,"/"));
	date.setMonth(date.getMonth()-2);
	return date.getFullYear() + "-" + (date.getMonth()+1) + "-" + date.getDate();
}
//售后原因
function AfterSaleReasonShow(colValue){
    if (colValue == "FULL_REFUND") {
        return '<span>全部退款</span>';
    }else if(colValue == "PARTIAL_REFUND") {
        return '<span>部分退款</span>';
    }else if(colValue == "NO_REFUND") {
        return '<span>不退款</span>';
    }else if(colValue == "RESEND") {
        return '<span>再寄一份</span>';
    }else if(colValue == "SPECIAL_HANDLE") {
        return '<span>特殊处理</span>';
    }else if(colValue == "PLATFORM_HANDLE") {
        return '<span>平台介入</span>';
    }else if(colValue == "OTHER_HANDLE") {
        return '<span>其他处理</span>';
    }else if(colValue == "OTHER_HANDLE") {
        return '<span>其他处理</span>';
    }else if(colValue == "SELLER_UNAGREED_REFUND_MONEY") {
        return '<span>商家不同意退款金额</span>';
    }
    else if(colValue == "SELLER_AGREED_REFUND_MONEY") {
        return '<span>商家同意退款金额</span>';
    }
    if(colValue){
        return colValue;
    }else{
        return "";
    }

}

function getDateBefore(date,num) {
    if (date instanceof Date){
        return new Date((date/1000 - 24*60*60*num)*1000);
    }else {
        return '';
    }
}

// 物流单号正则表达式
function addDeliveryNoReg() {
    $.validator.addMethod("deliveryNoReg", function(value) {
        var reg = /^(SF)[\d]{13}$/;
        var reg2 = /^[\d]{11,12}$/;
        if(value.substring(0,2) == 'SF'){
            if(value.match(reg)){
                return true;
            }
        }else {
            if(value.match(reg2)){
                return true;
            }
        }
        return false;
    }, '物流单号必须为11位，12位数字或SF开头加13位数字');
}