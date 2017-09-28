<html>
<head>
<title>휴대폰 결제</title>
<link rel=stylesheet href="./css/pay_style.css">
<script language="JavaScript">

function CheckChar(F,EffectL,Msg)
{
        var txt_spe = "~`!#%%^&*+|\\=}{[]\'?><,";
        if (F.value.length < EffectL)
        {
                alert("DaouPay - "+Msg+" ");
                F.focus();
                return false;
        }
        for (var i=0; i < F.value.length; i++)
        {
                if (-1 != txt_spe.indexOf(F.value.charAt(i)))
                {
                        alert("DaouPay - "+Msg+" ");
                        return false;
                }
        }
        return true;
}

function jsDoSubmit()
{
	var daouform = document.daoupay_info;
	
	var ssn 		= (daouform.ssn1.value) + (daouform.ssn2.value);
	var mobileno 		= (daouform.mobileno1.value) + (daouform.mobileno2.value) + (daouform.mobileno3.value);
	var email 		= daouform.mail.value;
		
	daouform.userssn.value      = ssn;
	daouform.mobileno.value     = mobileno;
	daouform.email.value        = email;
	
	if(daouform.chk_protect.checked == false) {
		alert("약관에 동의하셔야 결제가 진행됩니다.");
		return;
	}
 
        if(!CheckChar(daouform.mobileno2, 3, "휴대폰번호를 정확히 입력하여 주십시오.")) return ;
 	if(!CheckChar(daouform.mobileno3, 4, "휴대폰번호를 정확히 입력하여 주십시오.")) return ;
 	
 	if(!CheckChar(daouform.ssn1, 6, "주민번호를 정확히 입력하여 주십시오.")) return ;
	if(!CheckChar(daouform.ssn2, 7, "주민번호를 정확히 입력하여 주십시오.")) return ;

	daouform.submit();
}

</script>
</head>

<body leftmargin="0" topmargin="0">
<form name="daoupay_info" action="MobileCert.php" method="post" >
<input type="hidden" name="orderno" value="<?=$_POST["orderno"]?>">
<input type="hidden" name="producttype" value="<?=$_POST["producttype"]?>">
<input type="hidden" name="billtype" value="<?=$_POST["billtype"]?>">
<input type="hidden" name="amount" value="<?=$_POST["amount"]?>">
<input type="hidden" name="mobileno" value="">
<input type="hidden" name="userssn" value="">
<input type="hidden" name="email" value="">
<input type="hidden" name="userid" value="<?=$_POST["userid"]?>">
<input type="hidden" name="username" value="<?=$_POST["username"]?>">
<input type="hidden" name="productcode" value="<?=$_POST["productcode"]?>">
<input type="hidden" name="productname" value="<?=$_POST["productname"]?>">
<input type="hidden" name="reservedindex1" value="<?=$_POST["reservedindex1"]?>">
<input type="hidden" name="reservedindex2" value="<?=$_POST["reservedindex2"]?>">
<input type="hidden" name="reservedstring" value="<?=$_POST["reservedstring"]?>">
<table border="0" cellspacing="0" cellpadding="0" width=470 >
  <tr>
    <td colspan=3 background="images/p_top.gif" width=470 height=46>
	 <table border="0" cellspacing="0" cellpadding="0" widht=460>
	  <tr>
	   <td width=20s></td>
	   <td  height="41" style="padding-bottom:5px;"><img src="images/p_title01.gif"></td>
	  </tr>
	 </table>
	</td>
  </tr>
  <tr>
    <td width=20 height="390" align=left background="images/p_left.gif"></td>
    <td width=460>
	 <table cellpadding="0" cellspacing="0" border="0">
        <tr>
          <td height=17 colspan="3">&nbsp;</td>
        </tr>
		<tr>
          <td height=22 colspan="3" align="right" width=440>휴대폰결제 > <span class="txt_p_green01">결제정보입력</span></td>
        </tr>
		<tr>
          <td colspan="3">
		   <table cellpadding="0" cellspacing="0" border="0" width=420 align="center">
		    <tr>
			 <td background="images/p_box_top.gif" width=420 height=4></td>
			</tr>
			<tr>
			 <td background="images/p_box_middle.gif">
			  <table cellpadding="5" cellspacing="0" border="0" >
			   <tr>
			    <td width=24 height=12 align="right"><img src="images/p_bul_01.gif"></td>
				<td height=12 ><span class=txt_p01>상품명</span></td>
				<td height=12>ㅣ</td>
				<td > <span class=txt_p02><?=$_POST["productname"]?></span> </td>
			   </tr>
			   <tr>
			    <td width=24 height=12 align="right"><img src="images/p_bul_01.gif"></td>
				<td><span class=txt_p01>이용금액</span></td>
				<td>ㅣ</td>
				<td><span class=txt_p02><?=number_format($_POST["amount"])?>원</span></td>
			   </tr>
			  </table>
			 </td>
			</tr>
			<tr>
			 <td background="images/p_box_bottom.gif" width=420 height=6></td>
			</tr>
		   </table>
		  </td>
        </tr>
		<tr>
		  <td height=20 colspan="3"> </td>
		</tr>
		<tr>
		  <td colspan=3>
		   <table cellpadding="0" cellspacing="0" border="0" width=460> 
		    <tr>
			 <td width=20></td>
			 <td><img src="images/p_img_001.gif"></td>
			</tr>
		   </table>
		  </td>
	    </tr>
		<tr>
		  <td colspan="3">
		   <table cellpadding="3" cellspacing="0" border="0" width=420 align="center">
		    <tr> 
			 <td colspan=2 height=2 bgcolor="#53980a"></td>
			</tr>
			<tr>
			 <td class="p_line_gray01" width=116 height=20><span class="txt_p_line01">휴대폰번호</span></td>
			 <td class="p_line_gray02" width=292 height=20><span class="txt_p_line02"><select name="mobileno1" >
			   <option value='010'>010</option>
			   <option value='011'>011</option>
			   <option value='016'>016</option>
			   <option value='017'>017</option>
			   <option value='018'>018</option>
			   <option value='019'>019</option>
			   </select>
			   -			   
			   <input name="mobileno2" type="text" size="10" maxlength=4> -
			   <input name="mobileno3" type="text" size="10" maxlength=4></span></td>
			</tr>
			<tr>
			 <td class="p_line_gray01" width=116 height=20><span class="txt_p_line01">통신사선택</span></td>
			 <td class="p_line_gray02" width=292 height=20><span class="txt_p_line02">
			   <input type="radio" name="mobilecompany" value="SKT" checked>SKT&nbsp;&nbsp;
			   <input type="radio" name="mobilecompany" value="KTF">KTF&nbsp;&nbsp;
			   <input type="radio" name="mobilecompany" value="LGT">LGT&nbsp;&nbsp;
			 </span></td>
			</tr>
			<tr>
			 <td class="p_line_gray01" width=116 height=20><span class="txt_p_line01">가입자주민번호</span></td>
			 <td class="p_line_gray02" width=292 height=20><span class="txt_p_line02">   
			     <input name="ssn1" type="text" size="10" maxlength=6> -
			     <input name="ssn2" type=password maxlength=7 size="15"></span></td>
			</tr>
			<tr>
			 <td class="p_line_gray01" width=116 height=20><span class="txt_p_line01">결제통보E-mail</span></td>
			 <td class="p_line_gray02" width=292 height=20><span class="txt_p_line02">   
			     <input name="mail" type="text" size="24"></span> <span class="txt_p_green02">[선택사항]</span></td>
			</tr>
		   </table>
		  </td>
		</tr>
		<tr>
		  <td height=7 colspan="3"></td>
		</tr>
		<tr>
		 <td colspan="3">
		  <table cellpadding="0" cellspacing="0" border="0" width=420>
		   <tr>
		    <td width=18></td>
		    <td width="158" height=19 ><input type="checkbox" name="chk_protect" value="checkbox" > 이용약관에 동의합니다.</td>
		    <td width="244" colspan="2" ><a href="#"><img src="images/p_bt_001.gif" alt="이용약관보기" border="0" Onclick="javascript:window.open('http://www.daoupay.co.kr/pay_etc/pay_agree_pop.htm', 'AgreeWindow', 'scrollbars=yes,width=450,height=600');"></a></td>
		   </tr>
		  </table>
		 </td>
		</tr>
		<tr>
		  <td height=30 colspan="3"></td>
		</tr>
		<tr>
		  <td width="211" align=right><a href="javascript:jsDoSubmit()"><img src="images/p_bt_ok.gif" alt="확인" border="0"></a></td>
		  <td width="38" ></td>
		  <td width="211" align=left ><a href="javascript:window.close()"><img src="images/p_bt_cancel.gif" alt="취소" border="0"></a></td>
		</tr>
		<tr>
		  <td height=76 colspan="3"></td>
		</tr>
		<tr>
		  <td colspan="3">
		    <table cellpadding="0" cellspacing="0" border="0" width=420 align="center">
			  <tr>
			    <td width=10 align="left" height=20>-</td>
				<td height=20><span class="txt_p03"><span class="txt_p_green02">확인</span> 버튼을 클릭하시면 휴대폰으로 <span class="txt_p_green02">승인번호</span>가 전송됩니다.</span></td>
			  </tr>
			  <tr>
			    <td width=10 align="left" height=20>-</td>
				<td  height=20><span class="txt_p03">휴대폰으로 결제한 금액은 다음달 요금 고지서에 <span class="txt_p_green02">소액결제</span> 항목으로 청구됩니다.</span></td>
			  </tr>
			<tr>
		  <td height=5 colspan="2"></td>
		</tr>
			  <tr>
		  <td height=1 colspan="2"   align="center" bgcolor=#5bb342></td>
		</tr>
			</table>
		  </td>
		</tr>
		
		<tr>
		  <td height=10 colspan="3" ></td>
		</tr>
		<tr>
		  <td colspan="3">
		    <table width="420" border="0" align="center" cellpadding="0" cellspacing="0">
			 <tr>
			  <td width="211"><img src="images/p_bul_02.gif"> 결제문의 : <span class="txt_p_green01">1588-5984</span></td>
			  <td width="209">&nbsp;</td>
			 </tr>
			 <tr>
			  <td width="211"><img src="images/p_bul_02.gif"> 결제내역조회 : <a href="http://www.daouPay.com" target="_blank">www.daoupay.com</a></td>
			  <td align="right" width="209">&nbsp;<img src="images/p_bul_02.gif"> 문의메일 : <a href="mailto:help@daoupay.com" target="_blank">help@daoupay.com</a></td>
			 </tr>
			</table>
		  </td>
		</tr>
	 </table>
	</td>
    <td background="images/p_right.gif" width=20 align=right></td>
  </tr>
  <tr>
    <td colspan=3 background="images/p_bottom.gif" width=500 height=9></td>
  </tr>
</table>
</form>
</body>
</html>
