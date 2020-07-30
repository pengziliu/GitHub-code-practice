function exportF(P) {
	if (P == null) {
		P = {};
	}
	var title = P.title != null ? P.title : "导出对话框";
	var exportDiv = $('<div id="exportForm" style="display: none;" title="' + title + '" >'
			+ '<div id="pbar" style="width:300px;line-height:30px;height:30px;margin:30px auto"></div>'
			+ '<div id="exprotMsg" style="color:red;margin:0 auto; text-align:center"></div></div>');
	// 弹出对话框
	exportDiv.omDialog({
		autoOpen : true,
		height : 250,
		width : 600,
		modal : true
	});
	$.ajax({
		type : "post",
		dataType : 'json',
		url : P.initUrl,
		async : false,
		success : function(data) {
			if (0 != data.progress) {
				return false;
			}
			var exportForm = '<form action="' + P.exportUrl + '" method="post">';
			if(P.exportData != null){
				$.each(P.exportData, function(name, value) {
					exportForm += '<input name="' + name + '" value="' + value + '" />';
				});
			}
			exportForm += '</form>';
			var $exportForm = $(exportForm);
            $(document.body).append($exportForm);
            $exportForm.submit();
			$("#pbar").show();
			$("#pbar").omProgressbar("value", 0);
			$('#pbar').omProgressbar({
				text : "已完成{value}%",
				width : 300
			});
			displayProgress(P.getProcessUrl);
		},
		error : function(data) {
			return false;
		}
	});
}

//数据导出进度条
function displayProgress(pUrl) {
	var value = $("#pbar").omProgressbar("value");
	if (value == 100) {
		$('#exprotMsg').show();
		closeProcess(3);
		return true;
	}
	$.ajax({
		type : "post",
		dataType : 'json',
		url : pUrl,
		async : false,
		success : function(data) {
			value = data.progress;
		},
		error : function(data) {
			return false;
		}
	});
	$('#exprotMsg').html("导出过程中请勿关闭页面");
	$("#pbar").omProgressbar("value", value);
	$("#pbar").omProgressbar("text", '已完成{value}%');
	setTimeout(function() {
		displayProgress(pUrl);
	}, 1000);
}

function closeProcess(seconds){
	if(seconds == 0){
		$('#exportForm').omDialog('close');
		$('#exportForm').remove();
		return;
	}
	$('#exprotMsg').html("导出完成,"+seconds+"秒后关闭该对话框");
	setTimeout(function() {
		seconds -= 1;
		closeProcess(seconds)
	}, 1000);
}


//////////////////////////////////////////////////////////////////////////////////////////////////////
///// 新更新的导出JS
function exportUtil(P) {
    if (P == null) {
        P = {};
    }
    if (P.exportUrl == null){
        return;
    }
    var sequenceId = Math.round(Math.random()*100000);
    var exportForm = $("<form>");
    exportForm.attr("style","display:none");
    exportForm.attr("method","POST");
    exportForm.attr("action",P.exportUrl);
    if(P.extraParam != null){
        if(Array.isArray(P.extraParam)){
            for (var key in P.extraParam){
                var exData = P.extraParam[key];
                if(exData.name && exData.value && exData.value != ""){
                    var input = $("<input>");
                    input.attr("type","hidden");
                    input.attr("name", exData.name);
                    input.attr("value",exData.value);
                    exportForm.append(input);
                }
            }
        }else{
            $.each(P.extraParam,function (name, value) {
                var input = $("<input>");
                input.attr("type","hidden");
                input.attr("name", name);
                input.attr("value",value);
                exportForm.append(input);
            })
        }
    }
    var input = $("<input>");
    input.attr("type","hidden");
    input.attr("name", "export");
    input.attr("value","export");
    exportForm.append(input);
	var input2 = $("<input>");
	input2.attr("type","hidden");
	input2.attr("name", "sequenceId");
	input2.attr("value",sequenceId);
	exportForm.append(input2);
	$(document.body).append(exportForm);
    exportForm.submit();
    
    var title = P.title != null ? P.title : "导出对话框";
	var exportDiv = $('<div id="exportForm" style="display: none;" title="' + title + '" >'
			+ '<div id="pbar" style="width:300px;line-height:30px;height:30px;margin:30px auto"></div>'
			+ '<div id="exprotMsg" style="color:red;margin:0 auto; text-align:center"></div></div>');
	// 弹出对话框
	exportDiv.omDialog({
		autoOpen : true,
		height : 250,
		width : 600,
		modal : true
	});
	$('#exprotMsg').html("导出过程中请勿关闭页面");
	$("#pbar").show();
	var value = 0;
	$("#pbar").omProgressbar("value", value);
	$('#pbar').omProgressbar({
		text : "已完成{value}%",
		width : 300
	});
	setTimeout(updateProcess(P.exportUrl,sequenceId),1000);
}

function updateProcess(queryUrl,sequenceId){
    $.ajax({
        type: 'POST',
        url : queryUrl,
        data : {"export": "getProcess","sequenceId":sequenceId},
        success: function (data) {
            if(data.status == "SUCCESS"){
                var process = Number(data.message);
                $("#pbar").omProgressbar("value", process);
                $("#pbar").omProgressbar("text", '已完成{process}%');
                // 定时更新进度条
                if(process < 100){
                    setTimeout(function() {
                        updateProcess(queryUrl,sequenceId);
                    }, 1000);
                }else{
                    autoCloseProcess(3);
                }
            }
        }
    });
}

function autoCloseProcess(remainSeconds) {
    if(remainSeconds <= 0){
    	$('#exportForm').omDialog('close');
		$('#exportForm').remove();
		return;
    }
    $('#exprotMsg').html("导出完成,"+remainSeconds+"秒后关闭该对话框");
    setTimeout(function() {
        autoCloseProcess(remainSeconds-1);
    }, 1000);
}

