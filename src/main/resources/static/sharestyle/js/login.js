function checkfield(id,reg)
{
	var inp=document.getElementById(id)
	var va=inp.value;
	var alt=inp.alt;

	var span=document.getElementById(id+"Span")
	if(va==""||va==null)
	{
		span.innerHTML=alt+"不能为空"
		span.style.color="#e74c3c"
		return false;
	}else if(reg.test(va))
	{
		span.innerText=alt+"ok"
		span.style.color="#2ecc71"
		return true;
	}else
	{
		span.innerText=alt+"不符合规则"
		span.style.color="#e74c3c"
		return false;
	}
}
function ivtcheck()
{
	var inp=document.getElementById("ivt");
	var va =inp.value;

	var alt=inp.alt;

	var span=document.getElementById("ivtSpan");

	if(va==""||va==null)
	{
		span.innerHTML=alt+"不能为空"
		span.style.color="#e74c3c"
		return false;
	}else if(/^[a-zA-Z]+$/.test(va)){
		span.innerText=alt+"ok"
		span.style.color="#2ecc71"
		return true;
	}
	else{
		span.innerText=alt+"格式错误"
		span.style.color="#e74c3c"
		return false;
	}
}
function iphonecheck()
{
	// var phoneVal = document.getElementById("iphone")
	// var phoneReg = /(^1\d{10}$)|(^[0-9]\d{7}$)/;
	// if(!phoneReg.test(phoneVal)) {
	//    span.innerHTML=alt+"格式错误"
	//    span.style.color="#e74c3c"
	//    return false;
	// }else{
	// 	span.innerText=alt+"ok"
	// 	span.style.color="#2ecc71"
	// 	return true;

	// 	}
	checkfield("iphone",/^1[34578]\d{9}$/)


}
function inamecheck()
{
	var inp=document.getElementById("iname");
	var va =inp.value;

	var alt=inp.alt;

	var span=document.getElementById("inameSpan");

	if(va==""||va==null)
	{
		span.innerHTML=alt+"不能为空"
		span.style.color="#e74c3c"
		return false;
	}else
	{
		span.innerText=alt+"ok"
		span.style.color="#2ecc71"
		return true;
	}
}
function ipwdcheck()
{
	var inp=document.getElementById("ipwd");
	var va =inp.value;

	var alt=inp.alt;

	var span=document.getElementById("ipwdSpan");

	if(va==""||va==null)
	{
		span.innerHTML=alt+"不能为空"
		span.style.color="#e74c3c"
		return false;
	}else
	{
		span.innerText=alt+"ok"
		span.style.color="#2ecc71"
		return true;
	}
}
function Toiphonecheck()
{
	checkfield("Toiphone",/^1[34578]\d{9}$/)


}
function Toipwdcheck()
{
	var inp=document.getElementById("Toipwd");
	var va =inp.value;

	var alt=inp.alt;

	var span=document.getElementById("ToipwdSpan");

	if(va==""||va==null)
	{
		span.innerHTML=alt+"不能为空"
		span.style.color="#e74c3c"
		return false;
	}else
	{
		span.innerText=alt+"ok"
		span.style.color="#2ecc71"
		return true;
	}
}
function backivtcheck()
{
	var inp=document.getElementById("backivt");
	var va =inp.value;

	var alt=inp.alt;

	var span=document.getElementById("backivtSpan");

	if(va==""||va==null)
	{
		span.innerHTML=alt+"不能为空"
		span.style.color="#e74c3c"
		return false;
	}else
	{
		span.innerText=alt+"ok"
		span.style.color="#2ecc71"
		return true;
	}
}
function pstcheck()
{
	var inp=document.getElementById("pst");
	var va =inp.value;

	var alt=inp.alt;

	var span=document.getElementById("pstSpan");

	if(va==""||va==null)
	{
		span.innerHTML=alt+"不能为空"
		span.style.color="#e74c3c"
		return false;
	}else
	{
		span.innerText=alt+"ok"
		span.style.color="#2ecc71"
		return true;
	}
}
function backiphonecheck()
{
	checkfield("backiphone",/^\d{11}$/)


}