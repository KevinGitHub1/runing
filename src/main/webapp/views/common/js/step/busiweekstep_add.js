$(function () {
	initAdd();
	//提交
	$(".submit").on("click",function(){
		checkAdd();
	});
});
//初始化
function initAdd(){
		$(".bswspUser").val("");
		$(".bswspCdate").val("");
		$(".bswspWeek").val("");
		$(".bswspStep").val("");
}
//检查提交
function checkAdd(){
	    if($.trim($(".bswspUser").val()) == ""){
		alert("所属用户不能为空，请填写完再提交！");
		$(".bswspUser").focus();
		return false;
    }
		    if($.trim($(".bswspCdate").val()) == ""){
		alert("创建时间不能为空，请填写完再提交！");
		$(".bswspCdate").focus();
		return false;
    }
		    if($.trim($(".bswspWeek").val()) == ""){
		alert("所属周不能为空，请填写完再提交！");
		$(".bswspWeek").focus();
		return false;
    }
		    if($.trim($(".bswspStep").val()) == ""){
		alert("步数不能为空，请填写完再提交！");
		$(".bswspStep").focus();
		return false;
    }
	
	var r=confirm("是否确认增加？");
	if (r==true){
		var msgObject = parent.layer.msg('处理中，请等待……', {
			icon: 16,
			shade: 0.4,
			time: waitImgTime //（如果不配置，默认是3秒）
		}, function(index){
			//do something
			parent.layer.close(index);
		});
		Add(msgObject);
	}
}
//提交
function Add(msgObject){
	    var bswspUser = $(".bswspUser").val();
		    var bswspCdate = $(".bswspCdate").val();
		    var bswspWeek = $(".bswspWeek").val();
		    var bswspStep = $(".bswspStep").val();
		var str = '';
			        str+='&bswspUser='+encodeURIComponent(bswspUser)
					        str+='&bswspCdate='+encodeURIComponent(bswspCdate)
					        str+='&bswspWeek='+encodeURIComponent(bswspWeek)
					        str+='&bswspStep='+encodeURIComponent(bswspStep);
				getOData(str,"busiWeekStep/add/busiWeekStep",{
		fn:function(oData){
			window.parent.refreshList();
			alert("增加成功！");
		},
		fnerr:function(oData){
			parent.layer.close(msgObject);
		}
	});
}