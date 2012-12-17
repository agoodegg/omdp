/*************************************************
Validator v1.01
code by 我佛山人
wfsr@cunite.com
http://www.cunite.com
*************************************************/
/*------------------------------
modify by 穆景洲
on 2007-2-1
修改功能：
1.添加注释，方便组内其它成员理解并调用


** 
 *  在页面上直接限制输入格式
 *  函数inputNumber()限制只允许输入0-9的数字以及小数点(.)
 *  用法：onKeyPress="inputNumber();"
 *  
 *  函数inputInteger()限制只允许输入0-9的数字
 *  用法：onKeyPress="inputInteger();"
 *
 *
 *  函数pasteNumber()限制只能粘贴数字以及小数点
 *  用法： onbeforepaste="pasteNumber();"
 *
 *  函数pasteInteger()限制只能粘贴数字
 *  用法： onbeforepaste="pasteNumber();"
 *
 *
 *  函数inputDate()限制只能输入0-9数字以及连接符"-"
 *  用法：onKeyPress="inputDate();"
 *  注意：此函数并不能确保输入格式为正确的日期格式，仍需正则表达式验证格式
 *  
 *  页面上要禁止粘贴操作，可以直接使用onbeforepaste="event.returnValue=false;"
 *  
 *********************************************************************************************************
---------------------------------*/
Validator = {
	
	/**定义正则表达式*/
	requiredOfJS : /.+/,
	Email : /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/,
	Phone : /^((\(\d{3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}$/,
	Mobile : /^((\(\d{3}\))|(\d{3}\-))?((13\d{9})|(15\d{9})|(188\d{8}))$/,
	Mobile158 : /^((\(\d{3}\))|(\d{3}\-))?((13\d{9})|(15\d{9}))$/,
	Mobile158NULL:/^((\(\d{3}\))|(\d{3}\-))?((13\d{9})|(15\d{9}))*$/,
	PhoneOrMobile: /^((\(\d{3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}$|((\(\d{3}\))|(\d{3}\-))?13\d{9}$/,
	PhoneOrMobile2: /^((\(\d{3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}$|((\(\d{3}\))|(\d{3}\-))?1\d{10}$/,
	Url : /^http:\/\/[A-Za-z0-9]+\.[A-Za-z0-9]+[\/=\?%\-&_~`@[\]\':+!]*([^<>\"\"])*$/,
	IdCard : /^\d{15}(\d{2}[A-Za-z0-9])?$/,	
	Currency : /^\d+(\.\d+)?$/,
	NullCurrency : /(^\d+(\.\d+)?$)|(^\s*$)/,
	Number : /^\d+$/,
	NumAndLetter : /^[0-9a-zA-Z]*$/,
	NullNumber : /^\d*$/,
	Zip : /^[1-9]\d{5}$/,
	NullZip : /(^[1-9]\d{5}$)|(^\s*$)/,
	QQ : /^[1-9]\d{4,8}$/,
	Integer : /^[-\+]?\d+$/,
	Double : /^[-\+]?\d+(\.\d+)?$/,
	English : /^[A-Za-z]+$/,
	English2 : /^[A-Za-z]*$/,
	Chinese : /^[\u0391-\uFFE5]+$/,
	ChineseOrEnglish: /^[A-Za-z]+$|[\u0391-\uFFE5]+$/,
	UnSafe : /^(([A-Z]*|[a-z]*|\d*|[-_\~!@#\$%\^&\*\.\(\)\[\]\{\}<>\?\\\/\'\"]*)|.{0,5})$|\s/,
	Normal : /^[^'"`]*$/,
	HTMLTag : /<\/?[A-Za-z]+>/,
	IsSafe : function(str){return !this.UnSafe.test(str);},
	IsNormal : function(str){return this.Normal.test(str);},
	IsHTMLTag : function(str){return !this.HTMLTag.test(str);},
	SafeString : "this.IsSafe(value)",
	LimitBSafe : "this.IsNormal(value)&&this.limit(this.LenB(value), getAttribute('min'), getAttribute('max'))&&this.IsHTMLTag(value)",
	LimitV : "this.limitV(value,getAttribute('min'), getAttribute('max'))",
	Limit : "this.limit(value.length,getAttribute('min'), getAttribute('max'))",
	LimitB : "this.limit(this.LenB(value), getAttribute('min'), getAttribute('max'))",
	LimitNullNumber : "this.limit(value.length,getAttribute('min'), getAttribute('max'))&&this['NullNumber'].test(value)",
	LimitNumber : "this.limit(value.length,getAttribute('min'), getAttribute('max'))&&this['Number'].test(value)",
	LimitNumAndLetter : "this.limit(value.length,getAttribute('min'), getAttribute('max'))&&this['NumAndLetter'].test(value)",
	Date : "this.IsDate(value, getAttribute('min'), getAttribute('format'))",
	Email2 : "this.IsEmail(value, getAttribute('min'), getAttribute('max'))",
	Repeat : "value == document.getElementsByName(getAttribute('to'))[0].value",	
	Range : "new Number(getAttribute('min')) <= new Number(value) && new Number(value) <= new Number(getAttribute('max'))",
	Compare : "this.compare(value,getAttribute('operator'),eval(getAttribute('to')))",
	CompareDate: "this.compareDateBefor(getAttribute('startDate'),value)",
	Custom : "this.Exec(value, getAttribute('regexp'))",
	Group : "this.MustChecked(getAttribute('name'), getAttribute('min'), getAttribute('max'))",
	ErrorItem : [document.forms[0]],
	ErrorMessage : ["以下原因导致提交失败：\t\t\t\t"],
	
	/**主函数*/
	Validate : function(theForm, mode){

		var obj = theForm || event.srcElement;
		var count = obj.elements.length;
		this.ErrorMessage.length = 1;
		this.ErrorItem.length = 1;
		this.ErrorItem[0] = obj;
		for(var i=0;i<count;i++){
			with(obj.elements[i]){
				var _dataType = getAttribute("dataType");
				if(typeof(_dataType) == "object" || typeof(this[_dataType]) == "undefined") 
					continue;
				this.ClearState(obj.elements[i]);
				value = trim(value);
				if(getAttribute("requiredOfJS") == "false" && value == "")
					continue;
				switch(_dataType){
					case "Date" :
					case "Repeat":
					case "Range" :
					case "Compare" :
					case "CompareDate":
					case "Custom" :
					case "Group" : 
					case "LimitV" :
					case "Limit" :
					case "LimitB" :
					case "SafeString" :
					case "LimitBSafe" :
					case "LimitNullNumber":
					case "LimitNumAndLetter":
					case "LimitNumber":
					case "Email2":
						if(!eval(this[_dataType])) {
							this.AddError(i, getAttribute("msg"));
						}
						break;
					default :
						if(!this[_dataType].test(value)){
							this.AddError(i, getAttribute("msg"));
						}
					break;
				}
			}
		}
		
		
		if(this.ErrorMessage.length > 1){
			mode = mode || 1;
			var errCount = this.ErrorItem.length;
			switch(mode){
				case 2 :
				for(var i=1;i<errCount;i++)
				this.ErrorItem[i].style.color = "red";
				case 1 :
				alert(this.ErrorMessage.join("\n"));
				this.ErrorItem[1].focus();
				break;
				case 3 :
					for(var i=1;i<errCount;i++){
						try{
							var span = document.createElement("SPAN");
							span.id = "__ErrorMessagePanel";
							span.style.color = "red";
							this.ErrorItem[i].parentNode.appendChild(span);
							span.innerHTML = this.ErrorMessage[i].replace(/\d+:/,"*");
						}catch(e){
							alert(e.description);
						}
					}
					this.ErrorItem[1].focus();
					break;
					default :
					alert(this.ErrorMessage.join("\n"));
					break;
			}
			return false;
		}
		return true;
	},
	limitV : function(len,min, max){
		min = min || 0;
		max = max || Number.MAX_VALUE;
		len = len -1 + 1;
		if(len==null || len=="")
		{
			return true;
		}
		return min <= len && len <= max;
	},
	limit : function(len,min, max){
		min = min || 0;
		max = max || Number.MAX_VALUE;
		return min <= len && len <= max;
	},
	LenB : function(str){
		return str.replace(/[^\x00-\xff]/g,"**").length;
	},
	ClearState : function(elem){
		with(elem){
			if(style.color == "red")
			style.color = "";
			var lastNode = parentNode.childNodes[parentNode.childNodes.length-1];
			if(lastNode.id == "__ErrorMessagePanel")
				parentNode.removeChild(lastNode);
		}
	},
	AddError : function(index, str){
		this.ErrorItem[this.ErrorItem.length] = this.ErrorItem[0].elements[index];
		this.ErrorMessage[this.ErrorMessage.length] = this.ErrorMessage.length + ":" + str;
	},
	Exec : function(op, reg){
		return new RegExp(reg,"g").test(op);
	},
	compare : function(op1,operator,op2){
		switch (operator) {
			case "NotEqual":
				return (op1 != op2);
			case "GreaterThan":
				return (op1 > op2);
			case "GreaterThanEqual":
				return (op1 >= op2);
			case "LessThan":
				return (op1 < op2);
			case "LessThanEqual":
				return (op1 <= op2);
			default:
				return (op1 == op2); 
		}
	},
	compareDateBefor: function(dateOne,dateTwo){
		dateOneNew=document.getElementById(dateOne).value;		
		strOne=dateOneNew.split("-");
		strTwo=dateTwo.split("-");
		var firstDate=new Date(strOne[0],strOne[1],strOne[2]);
		var secondDate=new Date(strTwo[0],strTwo[1],strTwo[2]);
		return(firstDate<=secondDate);
	},
	MustChecked : function(name, min, max){
		var groups = document.getElementsByName(name);
		var hasChecked = 0;
		min = min || 1;
		max = max || groups.length;
		for(var i=groups.length-1;i>=0;i--)
			if(groups[i].checked) hasChecked++;
				return min <= hasChecked && hasChecked <= max;
	},
	IsEmail : function(val, min, max){
		max = max || Number.MAX_VALUE;
		len = val.length;
		if(len>0){
			if(min&&min>=1){
				return len>=min&&len<=max&&this.Email.test(val);
			}
			else{
				return len<=max&&this.Email.test(val);
			}
		}
		else{
			if(min&&min>=1){
				return false;
			}
			else{
				return true;
			}
		}
	},
	IsDate : function(op, formatString){
		formatString = formatString || "ymd";
		var m, year, month, day;
		switch(formatString){
			case "ymd" :
			m = op.match(new RegExp("^((\\d{4})|(\\d{2}))([-./])(\\d{1,2})\\4(\\d{1,2})$"));
				if(m == null ) return false;
				day = m[6];
				month = m[5]--;
				year = (m[2].length == 4) ? m[2] : GetFullYear(parseInt(m[3], 10));
				break;
			case "dmy" :
				m = op.match(new RegExp("^(\\d{1,2})([-./])(\\d{1,2})\\2((\\d{4})|(\\d{2}))$"));
				if(m == null ) return false;
				day = m[1];
				month = m[3]--;
				year = (m[5].length == 4) ? m[5] : GetFullYear(parseInt(m[6], 10));
				break;
			default :
				break;
		}
		if(!parseInt(month)) return false;
		month = month==12 ?0:month; 
		var date = new Date(year, month, day);
		return (typeof(date) == "object" && year == date.getFullYear() && month == date.getMonth() && day == date.getDate());
		function GetFullYear(y){return ((y<30 ? "20" : "19") + y)|0;}
	}
}



/** 
 *  在页面上直接限制输入格式
 *  函数inputNumber()限制只允许输入0-9的数字以及小数点.
 *  用法：onKeyPress="inputNumber();"
 *  
 *  函数inputInteger()限制只允许输入0-9的数字
 *  用法：onKeyPress="inputInteger();"
 *
 *
 *  函数pasteNumber()限制只能粘贴数字以及小数点
 *  用法： onbeforepaste="pasteNumber();"
 *
 *  函数pasteInteger()限制只能粘贴数字
 *  用法： onbeforepaste="pasteInteger();"
 *
 *
 *  函数inputDate()限制只能输入0-9数字以及连接符"-"
 *  用法：onKeyPress="inputDate();"
 *  注意：此函数并不能确保输入格式为正确的日期格式，仍需正则表达式验证格式
 *  
 *  页面上要禁止粘贴操作，可以直接使用onbeforepaste="event.returnValue=false;"
 *  
 ****/

function inputNumber(){
	if((event.keyCode<48 || event.keyCode>57) && event.keyCode!=46)
		event.returnValue=false;
}

function inputInteger(){
	if(event.keyCode<48 || event.keyCode>57)
		event.returnValue=false;
}

function inputTel(){
	if((event.keyCode<48 || event.keyCode>57)&&event.keyCode!=45)
		event.returnValue=false;
}

function pasteNumber(){
	clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d|\.]/g,''));
}

function pasteInteger(){
	clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''));
}

function inputDate(){
	if((event.keyCode<48 || event.keyCode>57 )&&(event.keyCode!=45))
		event.returnValue=false;
}

function inputPhone(){
	if((event.keyCode<48 || event.keyCode>57 )&&(event.keyCode!=45)&&(event.keyCode!=40)&&(event.keyCode!=41))
		event.returnValue=false;
}

function inputEnglish(){
	if((event.keyCode<64 || event.keyCode>123 ))
		event.returnValue=false;
}



/**清除日期**/
function clearTextDate(obj){
	obj.value="";
}


/****---取得一个单选按钮的选中值---****/
function getCheckedRadioValue(objName){
	var temp=document.getElementsByName(objName);
	var value="";
	for (i=0;i<temp.length;i++){	  
		if(temp[i].checked){
			value=temp[i].value;
			return value;			
		}
	}		
}


/****--测试选中的单选按钮值是否与指定值相同---****/
function isCheckedRadio(objName,value){
	var tmp=getCheckedRadioValue(objName);
	return (tmp==value);		
}


/****比较两个日期的大小****/
function compteDataFun(dateOne,dateTwo){
	var dateOneNew=document.all[dateOne].value;	
	var dataTwoNew=document.all[dateTwo].value;
	strOne=dateOneNew.split("-");
	strTwo=dataTwoNew.split("-");
	var firstDate=new Date(strOne[0],strOne[1],strOne[2]);
	var secondDate=new Date(strTwo[0],strTwo[1],strTwo[2]);
	return(firstDate<=secondDate);
}

/*****获取选择的下拉列表框选中项的值*****/
function getSelectedOptionValue(objName){
	var opertion=document.all[objName];
	for(var i=0;i<opertion.length;i++){
		if(opertion[i].selected){
			opertion.focus();
			return opertion[i].value;
		}
	}
}

/**
检查是否上传文件的控件是否路径名是否是盘符开头
例如： C:\test.txt
如果有，返回真，否则，返回假
*/
function isHasDrive(uploadValue){
	var theFile = uploadValue;
	var regex = /[c-iC-I]:([^...]+)(.((xls)|(txt)))$/;
	var result = theFile.match(regex);
	
	if(result == null){
	    return false;
	}else
		return true;
}

//去左空格; 
function ltrim(s){ 
return s.replace( /^\s*/, ""); 
} 
//去右空格; 
function rtrim(s){ 
return s.replace( /\s*$/, ""); 
} 
//去左右空格; 
function trim(s){ 
return rtrim(ltrim(s)); 
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