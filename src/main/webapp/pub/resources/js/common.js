/*
以下2函数实现鼠标点击表格中某行之后，该行改变颜色
select_tr：选中某行，改变颜色在onClick中调用
mouse_out_tr:移出某行，改变颜色在onMouseOut中调用
mouse_over_tr:鼠标指针移到某行，改变颜色，在onMouseOver中调用
**/
var sel_tr = null;
var sel_id = null;
function mouseOverFn(tr){
	$(tr).addClass('mouseOverCls')
}
function select_tr(tr,i)
{	
	$(sel_tr).removeClass('selCls');
	
	sel_id = i;
	sel_tr = tr;
	$(tr).addClass('selCls');
}

function mouse_out_tr(tr,i){
	$(tr).removeClass('mouseOverCls')
}

var isNumber = function (e) {  
    if ($.browser.msie) {  
        if ( ((event.keyCode > 47) && (event.keyCode < 58)) ||  
              (event.keyCode == 8) ) {  
            return true;  
        } else {  
            return false;  
        }  
    } else {  
        if ( ((e.which > 47) && (e.which < 58)) ||  
              (e.which == 8) ) {  
            return true;  
        } else {  
            return false;  
        }  
    }  
}



document.onkeydown = function () {
	 var e = event || window.event;
	 var keyASCII = parseInt(e.keyCode, 10);
	 var src = e.srcElement;
	 var tag = src.tagName.toUpperCase();
	 if(keyASCII == 13) {
	  return false;
	 }
	 if(keyASCII == 8) {
	  if(src.readOnly || src.disabled || (tag != "INPUT" && tag != "TEXTAREA")) {
	   return false;
	  }
	  if(src.type) {
	   var type = ("" + src.type).toUpperCase();
	   return type != "CHECKBOX" && type != "RADIO" && type != "BUTTON";
	  }
	 }
	 return true;
}

document.oncontextmenu = function(){
	return false;
}