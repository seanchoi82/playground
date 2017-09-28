<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html lang="ko">
<head>
<meta charset="utf-8">
<%@ include file="/jsp/common/admin_header.jsp"%>
<title>MissingU Administrator</title>
<script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBJLUMZi6GXgXlCePslGwk2RNaYvstNFJs&sensor=false"></script>
<script type="text/javascript">
var marker;
var map;

	$(document).ready(function (){
		//blockUI 셋팅(모든 ajax 호출 시)
		//$(document).ajaxStart($.blockUI).ajaxStop($.unblockUI);
		
		$("#sex").val("${member.sex}").attr("selected", "selected"); 		//성별 셋팅
		$("#status").val("${member.status}").attr("selected", "selected");	//상태 셋팅
		$("#lunarSolarCd").val("${member.lunarSolarCd}").attr("selected", "selected");	//양음력구분 셋팅
		$("#bloodTypeCd").val("${member.bloodTypeCd}").attr("selected", "selected");	//혈액형 셋팅
		$("#birthTime").val("${member.birthTime}").attr("selected", "selected");	//탄생시간 셋팅
		$("#appearanceTypeCd").val("${member.appearanceTypeCd}").attr("selected", "selected");	//외모 셋팅
		$("#bodyTypeCd").val("${member.bodyTypeCd}").attr("selected", "selected");	//체형 셋팅
		$("#purposeCd").val("${member.purposeCd}").attr("selected", "selected");	//원하는만남 셋팅
		$("#hobbyCd").val("${member.hobbyCd}").attr("selected", "selected");	//취미 셋팅
		$("#drinkingHabitCd").val("${member.drinkingHabitCd}").attr("selected", "selected");	//음주습관 셋팅
		$("#smokingHabitCd").val("${member.smokingHabitCd}").attr("selected", "selected");	//흡연습관 셋팅
		$("#areaCd").val("${member.areaCd}").attr("selected", "selected");	//지역 셋팅
		$("#country").val("${member.country}").attr("selected", "selected");	//국가 셋팅
		$("#oneselfCertYn").val("${member.attr.oneselfCertification}").attr("selected", "selected");	//본인인증 여부 셋팅
		$("#locatoin").val("${member.locatoin}");
		
		var latLng = new google.maps.LatLng($("#gPosX").val(), $("#gPosY").val());
		var mapOptions = {
		center: latLng,
			zoom: 12,
			mapTypeId: google.maps.MapTypeId.ROADMAP, 
			navigationControl: true,
			navigationControlOptions: {style: google.maps.NavigationControlStyle.ZOOM_PAN}
		};
		
		map = new google.maps.Map(document.getElementById("map_canvas"), mapOptions);
		marker = new google.maps.Marker({ 
			   position: latLng, 
			   map: map,
			   draggable:true,
			   animation: google.maps.Animation.DROP,
			   title: '현재 위치'
		});
		
	});
	
	function marker_move(latitude, longitude) {
	    map.panTo(new google.maps.LatLng(latitude, longitude));
	    marker.setPosition(new google.maps.LatLng(latitude, longitude));
	}
</script>


</head>
<body>
<div id="container">
	<header>
		<div class="tmenu">
			<span>관리자</span>님 안녕하세요! | <a href="/logout.html">로그아웃</a>
		</div>
		<div class="logo">MissingU 관리자모드</div>		
		<nav>
			<jsp:include page="/jsp/common/admin_top_nav.jsp" flush="false">
			        <jsp:param name="selected" value="memberSearch"/>
			</jsp:include>
		</nav>
	</header>
	<div class="body">
		<aside>
			<ul class="ui-box ui-box-white ui-box-shadow">
				<li class="select"><a href="memberList.html">회원조회</a></li>
				<!-- <li><a href="memberSearch.html">(구)회원조회</a></li> -->
			</ul>
		</aside>
		<div class="content">
			<div class="ui-box ui-box-white ui-box-shadow">
				<!-- Content 시작 -->
				<h2 class="icon-arrow-green">회원 기본정보</h2>
				
				<form id="memberForm" enctype="multipart/form-data"  method="POST">
				<input type="hidden" id="actionType" name="actionType" value="${reqParams.actionType}" />
				
				<c:choose>
					<c:when test="${fn:length(member.gPosX) > 0 }">
							<input type="hidden" id="gPosX" name="gPosX" value="${member.gPosX}" />
							<input type="hidden" id="gPosY" name="gPosY" value="${member.gPosY}" />
					</c:when>
					<c:otherwise>
							<input type="hidden" id="gPosX" name="gPosX" value="37.53537045588363" />
							<input type="hidden" id="gPosY" name="gPosY" value="127.00006874177245" />
					</c:otherwise>
				</c:choose>					
				
				<div class="">
					<table class="tbl_nor" cellspacing="0" summary="회원 기본정보">
						<caption>회원 기본정보</caption>
						<colgroup>
							<col width="140">
							<col width="300">
							<col width="140">
							<col width="300">
						</colgroup>
						<tr>
							<th>회원메인사진</th>
							<td>
								<img id="img_mainPhoto" src="${member.mainPhotoUrl }" class="profile-img"/>
								<input type="file" id="uploadFile" name="uploadFile" />
								<input type="hidden" id="mainPhotoUrl" name="mainPhotoUrl">
							</td>
							<th>회원아이디</th>
							<td><input type="text" id="memberId" name="memberId" value="${member.memberId}" readonly="readonly"></td>
						</tr>
						<tr>
							<th>회원프로필</th>
							<td colspan="3">

								<c:if test="${ fn:length(member.attr.subPhoto01) > 0 }">
									<a href="${ member.attr.subPhoto01org }${ member.attr.subPhoto01Org }" class="nyroModal" title="프로필 01"><img src="${member.attr.subPhoto01 }" class="profile-img"/></a>
								</c:if>
								<c:if test="${ fn:length(member.attr.subPhoto02) > 0 }">
									<a href="${ member.attr.subPhoto02org }${ member.attr.subPhoto02Org }" class="nyroModal" title="프로필 02"><img src="${member.attr.subPhoto02 }" class="profile-img"/></a>
								</c:if>
								<c:if test="${ fn:length(member.attr.subPhoto03) > 0 }">
									<a href="${ member.attr.subPhoto03org }${ member.attr.subPhoto03Org }" class="nyroModal" title="프로필 03"><img src="${member.attr.subPhoto03 }" class="profile-img"/></a>
								</c:if>
								<c:if test="${ fn:length(member.attr.subPhoto04) > 0 }">
									<a href="${ member.attr.subPhoto04org }${ member.attr.subPhoto04Org }" class="nyroModal" title="프로필 04"><img src="${member.attr.subPhoto04 }" class="profile-img"/></a>
								</c:if>
								<c:if test="${ fn:length(member.attr.subPhoto05) > 0 }">
									<a href="${ member.attr.subPhoto05org }${ member.attr.subPhoto05Org }" class="nyroModal" title="프로필 05"><img src="${member.attr.subPhoto05 }" class="profile-img"/></a>
								</c:if>
								<c:if test="${ fn:length(member.attr.subPhoto06) > 0 }">
									<a href="${ member.attr.subPhoto06org }${ member.attr.subPhoto06Org }" class="nyroModal" title="프로필 06"><img src="${member.attr.subPhoto06 }" class="profile-img"/></a>
								</c:if>
								<c:if test="${ fn:length(member.attr.subPhoto07) > 0 }">
									<a href="${ member.attr.subPhoto07org }${ member.attr.subPhoto07Org }" class="nyroModal" title="프로필 07"><img src="${member.attr.subPhoto07 }" class="profile-img"/></a>
								</c:if>
								<c:if test="${ fn:length(member.attr.subPhoto08) > 0 }">
									<a href="${ member.attr.subPhoto08org }${ member.attr.subPhoto08Org }" class="nyroModal" title="프로필 08"><img src="${member.attr.subPhoto08 }" class="profile-img"/></a>
								</c:if>
							</td>
						</tr>
						<tr>
							<th>패스워드</th>
							<td colspan="3"><input type="text" id="password" name="password" >- 입력시에만 적용 됩니다.(복호화불가)</td>
						</tr>
						<tr>
							<th>로그인아이디</th>
							<td><input type="text" id="loginId" name="loginId" value="${member.loginId}" ></td>
							<th>닉네임</th>
							<td><input type="text" id="nickName" name="nickName" value="${member.nickName}" ></td>
						</tr>
						<tr>
							<th>성별</th>
							<td>
								<select id="sex" name="sex">
									<option value="">---- 선택 ----</option>
									<option value="G01001">남자</option>
									<option value="G01002">여자</option>
								</select>
							</td>
							<th>가입상태</th>
							<td>
								<select id="status" name="status">
									<option value="">---- 선택 ----</option>
									<option value="A">사용중</option>
									<option value="P">가입진행중</option>
									<option value="C">탈퇴</option>
								</select>
							</td>
						</tr>
						<tr>
							<th>GCM사용여부</th>
							<td><input type="text" id="gcmUseYn" name="gcmUseYn" value="${member.gcmUseYn}" ></td>
							<th>지역</th>
							<td>
								<select id="areaCd" name="areaCd" >
									<option value="">---- 선택 ----</option>
									<c:if test="${member.country== 'kr'}">
									<option value="A06001">서울</option>
									<option value="A06002">인천</option>
									<option value="A06003">경기</option>
									<option value="A06004">울산</option>
									<option value="A06005">대구</option>
									<option value="A06006">대전</option>
									<option value="A06007">광주</option>
									<option value="A06008">부산</option>
									<option value="A06009">경남</option>
									<option value="A06010">경북</option>
									<option value="A06011">전남</option>
									<option value="A06012">전북</option>
									<option value="A06013">충남</option>
									<option value="A06014">충북</option>
									<option value="A06015">강원</option>
									<option value="A06016">제주</option>
									<option value="A06017">해외,기타</option>
									</c:if>
									<c:if test="${member.country== 'jp'}">
										<option value='AJ6001'>北海道</option>
										<option value='AJ6002'>青森県</option>
										<option value='AJ6003'>岩手県</option>
										<option value='AJ6004'>宮城県</option>
										<option value='AJ6005'>秋田県</option>
										<option value='AJ6006'>山形県</option>
										<option value='AJ6007'>福島県</option>
										<option value='AJ6008'>茨城県</option>
										<option value='AJ6009'>栃木県</option>
										<option value='AJ6010'>群馬県</option>
										<option value='AJ6011'>埼玉県</option>
										<option value='AJ6012'>千葉県</option>
										<option value='AJ6013'>東京都</option>
										<option value='AJ6014'>神奈川県</option>
										<option value='AJ6015'>新潟県</option>
										<option value='AJ6016'>富山県</option>
										<option value='AJ6017'>石川県</option>
										<option value='AJ6018'>福井県</option>
										<option value='AJ6019'>山梨県</option>
										<option value='AJ6020'>長野県</option>
										<option value='AJ6021'>岐阜県</option>
										<option value='AJ6022'>静岡県</option>
										<option value='AJ6023'>愛知県</option>
										<option value='AJ6024'>三重県</option>
										<option value='AJ6025'>滋賀県</option>
										<option value='AJ6026'>京都府</option>
										<option value='AJ6027'>大阪府</option>
										<option value='AJ6028'>兵庫県</option>
										<option value='AJ6029'>奈良県</option>
										<option value='AJ6030'>和歌山県</option>
										<option value='AJ6031'>鳥取県</option>
										<option value='AJ6032'>島根県</option>
										<option value='AJ6033'>岡山県</option>
										<option value='AJ6034'>広島県</option>
										<option value='AJ6035'>山口県</option>
										<option value='AJ6036'>徳島県</option>
										<option value='AJ6037'>香川県</option>
										<option value='AJ6038'>愛媛県</option>
										<option value='AJ6039'>高知県</option>
										<option value='AJ6040'>福岡県</option>
										<option value='AJ6041'>佐賀県</option>
										<option value='AJ6042'>長崎県</option>
										<option value='AJ6043'>熊本県</option>
										<option value='AJ6044'>大分県</option>
										<option value='AJ6045'>宮崎県</option>
										<option value='AJ6046'>鹿児島県</option>
										<option value='AJ6047'>沖縄県</option>
										<option value='AJ6048'>その他</option>
									</c:if>
									<c:if test="${member.country== 'zh'}">
									
									</c:if>
								</select>
							</td>
						</tr>
						<tr>
							<th>GCM ID</th>
							<td colspan="3"><input type="text" id="gcmRegId" name="gcmRegId" value="${member.gcmRegId}" ></td>
						</tr>
						<tr>
							<th>생일</th>
							<td><input type="text" id="birthDate" name="birthDate" value="${member.birthDate}" class="datepicker"></td>
							<th>양음력 구분</th>
							<td>
								<select id="lunarSolarCd" name="lunarSolarCd" >
									<option value="">---- 선택 ----</option>
									<option value="A00101">양력</option>
									<option value="A00102">음력(편달)</option>
									<option value="A00103">음력(윤달)</option>
								</select>
							</td>
						</tr>
						<tr>
							<th>혈액형</th>
							<td>
								<select id="bloodTypeCd" name="bloodTypeCd">
									<option value="">---- 선택 ----</option>
									<option value="B01001">A</option>
									<option value="B01002">B</option>
									<option value="B01003">O</option>
									<option value="B01004">AB</option>
								</select>
							</td>
							<th>탄생시간</th>
							<td>
								<select id="birthTime" name="birthTime">
									<option value="">---- 선택 ----</option>
									<option value="0 ~ 1">0 ~ 1</option>
									<option value="1 ~ 2">1 ~ 2</option>
									<option value="2 ~ 3">2 ~ 3</option>
									<option value="3 ~ 4">3 ~ 4</option>
									<option value="4 ~ 5">4 ~ 5</option>
									<option value="5 ~ 6">5 ~ 6</option>
									<option value="6 ~ 7">6 ~ 7</option>
									<option value="7 ~ 8">7 ~ 8</option>
									<option value="8 ~ 9">8 ~ 9</option>
									<option value="9 ~ 10">9 ~ 10</option>
									<option value="10 ~ 11">10 ~ 11</option>
									<option value="11 ~ 12">11 ~ 12</option>
									<option value="12 ~ 13">12 ~ 13</option>
									<option value="13 ~ 14">13 ~ 14</option>
									<option value="14 ~ 15">14 ~ 15</option>
									<option value="15 ~ 16">15 ~ 16</option>
									<option value="16 ~ 17">16 ~ 17</option>
									<option value="17 ~ 18">17 ~ 18</option>
									<option value="18 ~ 19">18 ~ 19</option>
									<option value="19 ~ 20">19 ~ 20</option>
									<option value="20 ~ 21">20 ~ 21</option>
									<option value="21 ~ 22">21 ~ 22</option>
									<option value="22 ~ 23">22 ~ 23</option>
									<option value="23 ~ 24">23 ~ 24</option>
								</select>
							</td>
						</tr>
						<tr>
							<th>외모</th>
							<td>
								<select id="appearanceTypeCd" name="appearanceTypeCd">
									<option value="">---- 선택 ----</option>
									<option value="A02001">터프가이</option>
									<option value="A02002">꽃미남</option>
									<option value="A02003">야성적인섹시함</option>
									<option value="A02004">지적학구파</option>
									<option value="A02005">온순하고편안함</option>
									<option value="A02006">카리스마</option>
									<option value="A02007">샤프함</option>
									<option value="A02008">평범함</option>
									<option value="A03001">V라인</option>
									<option value="A03002">청순가련형</option>
									<option value="A03003">지적인캐리어우먼</option>
									<option value="A03004">섹시함</option>
									<option value="A03005">동안</option>
									<option value="A03006">귀여움</option>
									<option value="A03007">중성적</option>
								</select>
							</td>
							<th>체형</th>
							<td>
								<select id="bodyTypeCd" name="bodyTypeCd">
									<option value="">---- 선택 ----</option>
									<option value="A04001">근육질</option>
									<option value="A04002">초콜렛복근</option>
									<option value="A04003">균형잡힌섹시몸</option>
									<option value="A04004">빼빼로형</option>
									<option value="A04005">뚱뚱함</option>
									<option value="A04006">보통</option>
									<option value="A04007">통통함</option>
									<option value="A05001">S라인몸매</option>
									<option value="A05002">글래머</option>
									<option value="A05003">날씬한섹시형</option>
									<option value="A05004">통통한섹시형</option>
									<option value="A05005">아담사이즈</option>
									<option value="A05006">뚱뚱한체형</option>
									<option value="A05007">마른체형</option>
								</select>
							</td>
						</tr>
						<tr>
							<th>원하는 만남</th>
							<td>
								<select id="purposeCd" name="purposeCd">
									<option value="">---- 선택 ----</option>
									<option value="A07001">애인만들기</option>
									<option value="A07002">채팅친구</option>
									<option value="A07003">만남친구</option>
									<option value="A07004">좋은친구찾기</option>
									<option value="A07005">편하게연락하는사이</option>
									<option value="A07006">함께영화볼사람</option>
									<option value="A07007">드라이브</option>
									<option value="A07008">여행파트너</option>
									<option value="A07009">미팅해요</option>
									<option value="A07010">차한잔</option>
									<option value="A07011">스키장가실분</option>
									<option value="A07012">문자친구</option>
									<option value="A07013">나이트갈사람</option>
									<option value="A07014">바다보러가요</option>
									<option value="A07015">외국여행가요</option>
									<option value="A07016">골프파트너</option>
									<option value="A07017">등산모임</option>
									<option value="A07018">낚시친구</option>
									<option value="A07029">레져,스포츠</option>
									<option value="A07030">외계인찾아요</option>
								</select>
							</td>
							<th>취미</th>
							<td>
								<select id="hobbyCd" name="hobbyCd">
									<option value="">---- 선택 ----</option>
									<option value="A08001">드라이브</option>
									<option value="A08002">독서</option>
									<option value="A08003">영화</option>
									<option value="A08004">운동</option>
									<option value="A08005">공연관람</option>
									<option value="A08006">음주가무</option>
									<option value="A08007">게임한판</option>
									<option value="A08008">맛있는것먹기</option>
									<option value="A08009">사진찍기</option>
									<option value="A08010">여행</option>
									<option value="A08011">기타</option>
								</select>
							</td>
						</tr>
						<tr>
							<th>음주습관</th>
							<td>
								<select id="drinkingHabitCd" name="drinkingHabitCd">
									<option value="">---- 선택 ----</option>
									<option value="A09001">전혀안함</option>
									<option value="A09002">상황에따라</option>
									<option value="A09003">자주즐김</option>
								</select>
							</td>
							<th>흡연습관</th>
							<td>
								<select id="smokingHabitCd" name="smokingHabitCd">
									<option value="">---- 선택 ----</option>
									<option value="A10001">전혀안함</option>
									<option value="A10002">상황에따라</option>
									<option value="A10003">자주즐김</option>
								</select>
							</td>
						</tr>
						<tr>
							<th>국가</th>
							<td>
								<select id="country" name="country">
									<option value="">---- 선택 ----</option>
									<option value="kr">한국</option>
									<option value="jp">일본</option>
									<option value="zh">중국</option>
								</select>
							</td>
							<th>본인인증 여부</th>
							<td>
								<select id="oneselfCertYn" name="oneselfCertYn">
									<option value="">---- 전체 ----</option>
									<option value="Y">Y</option>
									<option value="N">N</option>
								</select>
							</td>
						</tr>
						<tr>
							<th>자기소개</th>
							<td colspan="3">
								<textarea rows="6" id="selfPr" name="selfPr" >${member.selfPr}</textarea>
							</td>
						</tr>
						<tr>
							<th>현재주소</th>
							<td colspan="3"><input type="text" id="location" name="location" value="${member.location}" ></td>
						</tr>
						<tr>
							<th>현재위치</th>
							<td colspan="3">
								<div id="map_canvas" style="width: 100%; height: 400px"></div>
							</td>
						</tr>
					</table>
				</div>
				</form>
				
				<div class="ui-btns alignLeft">
					<button id="btn_back" data-click-callback="back()">뒤로</button>
					<button id="btn_search" data-click-callback="save()">저장</button>
					<button id="btn_search" data-click-callback="openGivePointPop('givePointPop')">포인트 부여</button>
					<button id="btn_delete" data-click-callback="deleteMember()">회원삭제</button>
				</div>
				
				<div class="pt30">
				</div>
				
				
				<!-- Content 끝 -->
			</div>
			
			<div class="ui-box ui-box-white ui-box-shadow">
				<!-- Content 시작 -->
				<h2 class="icon-arrow-green">회원 속성정보</h2>
				
				<form id="memberAttrForm">
				<div class="">
					<table class="tbl_nor" cellspacing="0" summary="회원 속성">
						<caption>회원 속성정보</caption>
						<colgroup>
							<col width="140">
							<col width="300">
							<col width="140">
							<col width="300">
						</colgroup>
						<tr>
							<th>보유 포인트</th>
							<td><input type="text" id="point" name="point" value="${pointInfo.itemCnt}" > Point</td>
							<th>정액권 만기 날짜</th>
							<td><input type="text" id="passExpireDay" name="passExpireDay" value="${member.attr.passExpireDay}" ></td>
						</tr>
						<tr>
							<th>마지막 로그인 날짜</th>
							<td><input type="text" id="lastLoginDay" name="lastLoginDay" value="${member.attr.lastLoginDay}" ></td>
							<th></th>
							<td></td>
						</tr>
						
					</table>
				</div>
				
				<br />
				<br />
				<!-- Content 시작 -->
				<h2 class="icon-arrow-green">포인트 사용내역 | <a href="/missingu/admin/pointUseHistList.html?cond_memberId=${ reqParams.memberId }" target="_blank">더보기</a></h2>
				
				<div class="">
					<table class="tbl_nor" cellspacing="0" summary="조회결과">
						<caption>조회결과</caption>
						<colgroup>
							<col width="100">
							<col width="150">
							<col width="150">
							<col />
						</colgroup>
						<tr id="tb_header">
							<th>충전/사용</th>
							<th>사용포인트</th>
							<th>잔여포인트</th>
							<th>사용일시</th>
						</tr>
						<c:forEach items="${ pointHistory.pointUseHistList }" var="item">
							<tr class="desc">
								<td hidden="true"></td>
								<td>
								<c:choose>
									<c:when test="${ item.eventTypeCd eq '1' }">충전</c:when>
									<c:otherwise>사용</c:otherwise>
								</c:choose>
								</td>
								<td>${ item.usePoint }</td>
								<td>${ item.remainPoint}</td>
								<td>${ item.createdDate}</td>
							</tr>
							<tr class="desc">
								<td colspan='5' style='padding:5px; background:#f2f2f2;'>${ item.useDesc}</td>
							</tr>
						</c:forEach>
					</table>
					
					
				</div>
				</form>
				
				
				<!-- Content 끝 -->
			</div>
			
			<ol class="info">
				<li>.</li>
			</ol>
		</div>
	</div>
</div>

<!-- 포인트 부여 팝업 레이어 -->
<div id="givePointPop" style="display:none;">
	<div class="content">
		<div class="ui-box ui-box-white ui-box-shadow">
			<h2 class="icon-arrow-green">포인트 부여</h2>
			
			<form id="givePointForm">
			<table class="tbl_nor" cellspacing="0" summary="쪽지발송">
				<caption>포인트 부여</caption>
				<colgroup>
					<col width="140">
				</colgroup>
				<tr>
					<th>보유 포인트</th>
					<td>
						<input type="text" id="memberPoint" name="memberPoint" readonly="readonly" /> Point
					</td>
				</tr>
				<tr>
					<th>부여 할 포인트</th>
					<td>
						<input type="text" id="giftPoint" name="giftPoint" />
					</td>
				</tr>
			</table>
			<input id="pop_memberId" name="pop_memberId" type="hidden" value="${member.memberId}">
			</form>
			
			<div class="ui-btns">
				<button id="btn_msg_close" data-click-callback="javascript:$.unblockUI();">닫기</button>
				<button id="btn_msg_send" data-click-callback="callGivePoint()">포인트 부여</button>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">

	function back() {
		location.href="/missingu/admin/memberSearch.html";	
	}
	
	function memberSearch() {
		location.href="/missingu/admin/memberSearch.html";
	}
	
	//포인트 부여 팝업 오픈
	function openGivePointPop(popupId) {
		if($('#actionType').val() == 'modify') {
			$('#memberPoint').val($('#point').val());
			showPopup(popupId);
		}
		else {
			alert("아직 저장되지 않았습니다.");
		}
	}
	
	function deleteMember() {
		if(confirm("정말 삭제 하시겠습니까? 회원 상태 변경이 아닌 실제 계정이 삭제 됩니다.")) {
			$.ajax({
				url : "/missingu/admin/deleteMember.html",
				type : "POST",
				cache : false,
				data : $("#memberForm").serialize(),
				dataType : "json",
				success : function(data) {
					//$("#memberPoint").html(data.response.memberPoint).append(" P");
					var rsltCd = data.result.rsltCd;
					var rsltMsg = data.result.rsltMsg;
					
					if(rsltCd == 0) { //저장성공
						alert("성공");
						back();
					} else {
						alert(data.result.rsltMsg);
					}
				},
				error : function(data) {
					alert('<spring:message code="comm.systemError.msgText" />');
				}
			});
		}
	}
	
	//회원저장
	function save() {
		if(!validateSaveParams()) {
			return false;
		}
		
		$("#gPosX").val(marker.getPosition().lat());
		$("#gPosY").val(marker.getPosition().lng());
		
		if( $("#uploadFile").val() == '') {
			saveWithoutFile();
		} else {
			saveWithFile();
		}
	}
	
	//회원저장 요청에 File 있음
	function saveWithFile() {
	    var options = {
			//target:        '#output1',   // target element(s) to be updated with server response
			//beforeSubmit:  showRequest,  // pre-submit callback
			//success:       showResponse  // post-submit callback
			url : "/missingu/admin/saveMember.html",
			type : "POST",
			dataType : "json",
			success : function(data) {
				$.unblockUI();
				//$("#memberPoint").html(data.response.memberPoint).append(" P");
				var rsltCd = data.result.rsltCd;
				
				if(rsltCd == 0) { //저장성공
					alert("성공");
					if(data.response != undefined) {
						$("#memberId").val(data.response.memberId);
					}
					location.href="/missingu/admin/memberEdit.html?actionType=modify&memberId="+$("#memberId").val();
				} else {
					alert(data.result.rsltMsg);
				}
			},
			error : function(data) {
				$.unblockUI();
				alert('<spring:message code="comm.systemError.msgText" />');
			}
			// other available options:
			//url:       url         // override for form's 'action' attribute
			//type:      type        // 'get' or 'post', override for form's 'method' attribute
			//dataType:  null        // 'xml', 'script', or 'json' (expected server response type)
			//clearForm: true        // clear all form fields after successful submit
			//resetForm: true        // reset the form after successful submit
			
			// $.ajax options can be used here too, for example:
			//timeout:   3000
		};
		// bind form using 'ajaxForm'
		$('#memberForm').ajaxSubmit(options);
		$.blockUI();
		
		
	}
	
	//회원저장 요청에 File 없음
	function saveWithoutFile() {
		$.ajax({
			url : "/missingu/admin/saveMember.html",
			type : "POST",
			//contentType : "multipart/form",
			cache : false,
			data : $("#memberForm").serialize(),
			dataType : "json",
			success : function(data) {
				$.unblockUI();
				//$("#memberPoint").html(data.response.memberPoint).append(" P");
				var rsltCd = data.result.rsltCd;
				if(rsltCd == 0) { //저장성공
					alert("성공");
					if(data.response != undefined) {
						$("#memberId").val(data.response.memberId);
					}
					location.href="/missingu/admin/memberEdit.html?actionType=modify&memberId="+$("#memberId").val();
				} else {
					alert(data.result.rsltMsg);
				}
				
			},
			error : function(data) {
				$.unblockUI();
				alert('<spring:message code="comm.systemError.msgText" />');
			}
		});
		$.blockUI();
		
	}

	function validateSaveParams() {
		if($("#loginId").val() == '') {
			alert("로그인ID를 입력해 주세요.");
			$("#loginId").focus();
			return false;
		}
		if($("#nickName").val() == '') {
			alert("닉네임을 입력해 주세요.");
			$("#nickName").focus();
			return false;
		}
		
		return true;
	}
	
	//포인트 부여 서비스 호출
	function callGivePoint() {
		if(confirm($('#giftPoint').val() + " 포인트를 부여 하시겠습니까?")) {
			$.ajax({
				url : "/missingu/admin/givePoint.html",
				type : "POST",
				cache : false,
				data : $("#givePointForm").serialize(),
				dataType : "json",
				success : function(data) {
					$.unblockUI();
					var rsltCd = data.result.rsltCd;
					
					if(rsltCd == 0) { //조회성공
						alert("성공");
						location.href="/missingu/admin/memberEdit.html?actionType=modify&memberId="+$("#memberId").val();
					} else {
						alert(data.result.rsltMsg);
					}
				},
				error : function(data) {
					$.unblockUI();
					alert('<spring:message code="comm.systemError.msgText" />');
				}
			});
			$.blockUI();
		}
	}
	
</script>
</body>
</html>