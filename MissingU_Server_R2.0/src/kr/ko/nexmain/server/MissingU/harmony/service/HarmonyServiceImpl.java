package kr.ko.nexmain.server.MissingU.harmony.service;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import kr.ko.nexmain.server.MissingU.common.Constants;
import kr.ko.nexmain.server.MissingU.common.model.CommReqVO;
import kr.ko.nexmain.server.MissingU.common.model.Result;
import kr.ko.nexmain.server.MissingU.common.utils.HarmonyUtil;
import kr.ko.nexmain.server.MissingU.common.utils.MsgUtil;
import kr.ko.nexmain.server.MissingU.friends.dao.FriendsDao;
import kr.ko.nexmain.server.MissingU.friends.model.FriendsEditReqVO;
import kr.ko.nexmain.server.MissingU.friends.model.FriendsVO;
import kr.ko.nexmain.server.MissingU.friends.model.SearchFriendsReqVO;
import kr.ko.nexmain.server.MissingU.harmony.dao.HarmonyDao;
import kr.ko.nexmain.server.MissingU.harmony.model.DetailInfoReqVO;
import kr.ko.nexmain.server.MissingU.harmony.model.TotalInfoReqVO;
import kr.ko.nexmain.server.MissingU.membership.dao.MembershipDao;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(timeout=15)
public class HarmonyServiceImpl implements HarmonyService {

	@Autowired
	private FriendsDao friendsDao;
	@Autowired
	private MembershipDao membershipDao;
	@Autowired
	private HarmonyDao harmonyDao;
	@Autowired
	private MsgUtil msgUtil;
	@Autowired
	private HarmonyUtil harmonyUtil;
	
	private Locale gLocale;
	
	
	/** 내 간편 궁합정보 조회 */
	public Map<String,Object> getMySimpleInfo(CommReqVO inputVO) {
		gLocale = new Locale(inputVO.getgLang());
		Map<String,Object> returnMap = new HashMap<String,Object>();
		Map<String,Object> responseMap = new HashMap<String,Object>();
		
		Map<String, Object> member = membershipDao.selectMemberByMemberId(inputVO);
		String myBirthDate = (String)member.get("birthDate");
		String myLunarSolarCd = (String)member.get("lunarSolarCd");
		
		String myLunarBirthDate	= "";
		String mySolarBirthDate	= "";
		if(Constants.LunarSolarCd.SOLAR.equalsIgnoreCase(myLunarSolarCd)) {
			//양력인 경우
			myLunarBirthDate = harmonyUtil.toLunar(myBirthDate);
			mySolarBirthDate = myBirthDate;
		} else {
			//음력인 경우
			myLunarBirthDate = myBirthDate;
			mySolarBirthDate = harmonyUtil.toSolar(myBirthDate);
		}
		
		Map<String,String>	myDayChunganMap	= harmonyUtil.getChunJiOfDay(mySolarBirthDate);
		String myDayChunganCd	= myDayChunganMap.get("chunganCd"); //일주 천간
		String myElementCdForOuter	= harmonyUtil.getElementCdByDayChunganCd(myDayChunganCd);
		
		Map<String,Object> harmonyMap = new HashMap<String,Object>();
		harmonyMap.put("myBloodTypeCd", member.get("bloodTypeCd"));
		harmonyMap.put("myNickName", member.get("nickName"));
		harmonyMap.put("mySignCd", harmonyUtil.getSignCd(mySolarBirthDate));
		harmonyMap.put("myElementCd", myElementCdForOuter);
		
		responseMap.put("harmony", harmonyMap);
		
		returnMap.put("response", responseMap);
		
		Result result = new Result(
				Constants.ReturnCode.SUCCESS,
				msgUtil.getMsgCd("comm.success.search", gLocale),
				msgUtil.getMsgText("comm.success.search", gLocale));
		returnMap.put("result", result);
		
		return returnMap;
	}
	
	/** 종합 궁합정보 조회 */
	public Map<String,Object> getTotalInfo(TotalInfoReqVO inputVO) {
		gLocale = new Locale(inputVO.getgLang());
		Map<String,Object> returnMap = new HashMap<String,Object>();
		Map<String,Object> responseMap = new HashMap<String,Object>();
		
		Map<String,Object> harmonyMap = getHarmonyResultMap(inputVO);
		
		responseMap.put("harmony", harmonyMap);
		returnMap.put("response", responseMap);
		
		Result result = new Result(
				Constants.ReturnCode.SUCCESS,
				msgUtil.getMsgCd("comm.success.search", gLocale),
				msgUtil.getMsgText("comm.success.search", gLocale));
		returnMap.put("result", result);
		
		return returnMap;
	}
	
	/** 종합 궁합정보 Map 리턴 */
	public Map<String,Object> getHarmonyResultMap(TotalInfoReqVO inputVO) {
		/***** 내 궁합정보 START *****/
		CommReqVO myVO = new CommReqVO();
		myVO.setgMemberId(inputVO.getgMemberId());
		Map<String, Object> myMember = membershipDao.selectMemberByMemberId(myVO);
		
		String myBirthDate		= (String)myMember.get("birthDate");
		String myLunarSolarCd 	= (String)myMember.get("lunarSolarCd");
		String myBloodTypeCd 	= (String)myMember.get("bloodTypeCd");
		String myNickName 		= (String)myMember.get("nickName");
		String mySex 			= (String)myMember.get("sex");
		
		String myLunarBirthDate	= "";
		String mySolarBirthDate	= "";
		if(Constants.LunarSolarCd.SOLAR.equalsIgnoreCase(myLunarSolarCd)) {
			//양력인 경우
			myLunarBirthDate = harmonyUtil.toLunar(myBirthDate);
			mySolarBirthDate = myBirthDate;
		} else {
			//음력인 경우
			myLunarBirthDate = myBirthDate;
			mySolarBirthDate = harmonyUtil.toSolar(myBirthDate);
		}
		
		Map<String,String>	myDayChunganMap	= harmonyUtil.getChunJiOfDay(mySolarBirthDate);
		String myDayChunganCd	= myDayChunganMap.get("chunganCd"); //일주 천간
		String myDayJijiCd		= myDayChunganMap.get("jijiCd");	//일주 지지
		
		String mySignCd		= harmonyUtil.getSignCd(mySolarBirthDate);
		String myAgeCycleCd = harmonyUtil.getJijiCd(myLunarBirthDate);
		String myElementCdForInner	= harmonyUtil.getElementCdByDayJijiCd(myDayJijiCd);
		String myElementCdForOuter	= harmonyUtil.getElementCdByDayChunganCd(myDayChunganCd);
		/***** 내 궁합정보 END *****/
		
		/***** 상대 궁합정보 START *****/
		Integer friendId = inputVO.getFriendId();
		String yourBirthDate		= "";
		String yourLunarSolarCd 	= "";
		String yourBloodTypeCd 		= "";
		String yourNickName 		= "";
		
		String yourLunarBirthDate	= "";
		String yourSolarBirthDate	= "";
		
		String yourSignCd			= "";
		String yourAgeCycleCd 		= "";
		String yourElementCdForInner	= "";
		String yourElementCdForOuter	= "";
		
		if(friendId != null && friendId > 0) { //친구 정보조회 서비스에서 호출 시
			CommReqVO yourVO = new CommReqVO();
			yourVO.setgMemberId(friendId);
			Map<String, Object> yourMember = membershipDao.selectMemberByMemberId(yourVO);
			
			yourBirthDate		= (String)yourMember.get("birthDate");
			yourLunarSolarCd 	= (String)yourMember.get("lunarSolarCd");
			yourBloodTypeCd 	= (String)yourMember.get("bloodTypeCd");
			yourNickName 		= (String)yourMember.get("nickName");
		} else {
			yourBirthDate 		= inputVO.getBirthDate();
			yourLunarSolarCd	= inputVO.getLunarSolarCd();
			yourBloodTypeCd		= inputVO.getBloodTypeCd();
			yourNickName		= inputVO.getNickName();
		}
		
		if(Constants.LunarSolarCd.SOLAR.equalsIgnoreCase(yourLunarSolarCd)) {
			//양력인 경우
			yourLunarBirthDate = harmonyUtil.toLunar(yourBirthDate);
			yourSolarBirthDate = yourBirthDate;
		} else {
			//음력인 경우
			yourLunarBirthDate = yourBirthDate;
			yourSolarBirthDate = harmonyUtil.toSolar(yourBirthDate);
		}
		
		Map<String,String>	yourDayChunganMap	= harmonyUtil.getChunJiOfDay(yourSolarBirthDate);
		String yourDayChunganCd	= yourDayChunganMap.get("chunganCd"); //일주 천간
		String yourDayJijiCd	= yourDayChunganMap.get("jijiCd");	//일주 지지
		
		yourSignCd 		= harmonyUtil.getSignCd(yourSolarBirthDate);
		yourAgeCycleCd 	= harmonyUtil.getJijiCd(yourLunarBirthDate);
		yourElementCdForInner	= harmonyUtil.getElementCdByDayJijiCd(yourDayJijiCd);
		yourElementCdForOuter	= harmonyUtil.getElementCdByDayChunganCd(yourDayChunganCd);
		/***** 상대 궁합정보 END *****/
		
		//궁합정보 조회를 위한 키 설정(key1 : 남자, key2 : 여자)
		String bloodKey1	= null;
		String bloodKey2	= null;
		String signKey1		= null;
		String signKey2		= null;
		String outerKey1	= null;
		String outerKey2	= null;
		String innerKey1	= null;
		String innerKey2	= null;
		if(Constants.SexCode.MALE.equalsIgnoreCase(mySex)) {
			bloodKey1	= myBloodTypeCd;
			bloodKey2	= yourBloodTypeCd;
			signKey1	= mySignCd;
			signKey2	= yourSignCd;
			outerKey1	= myDayChunganCd;
			outerKey2	= yourDayChunganCd;
			innerKey1	= myDayJijiCd;
			innerKey2	= yourDayJijiCd;
		} else {
			bloodKey1	= yourBloodTypeCd;
			bloodKey2	= myBloodTypeCd;
			signKey1	= yourSignCd;
			signKey2	= mySignCd;
			outerKey1	= yourDayChunganCd;
			outerKey2	= myDayChunganCd;
			innerKey1	= yourDayJijiCd;
			innerKey2	= myDayJijiCd;
		}
		
		//혈액형 궁합 정보
		Map<String,Object> inputMap = new HashMap<String,Object>();
		inputMap.put("key1", bloodKey1);
		inputMap.put("key2", bloodKey2);
		inputMap.put("gLang", inputVO.getgLang());
		Map<String,Object> bloodMap = harmonyDao.selectBloodHarmonyData(inputMap);
		Integer bloodHarmonyScore = (bloodMap == null) ? 0 : (Integer)bloodMap.get("score");
		
		//별자리 궁합 정보
		Map<String,Object> inputMap2 = new HashMap<String,Object>();
		inputMap2.put("key1", signKey1);
		inputMap2.put("key2", signKey2);
		inputMap2.put("gLang", inputVO.getgLang());
		Map<String,Object> signMap = harmonyDao.selectSignHarmonyData(inputMap2);
		Integer signHarmonyScore = (signMap == null) ? 0 : (Integer)signMap.get("score");
		
		//겉궁합 정보
		Map<String,Object> inputMap3 = new HashMap<String,Object>();
		inputMap3.put("key1", outerKey1);
		inputMap3.put("key2", outerKey2);
		inputMap3.put("gLang", inputVO.getgLang());
		Map<String,Object> outerMap = harmonyDao.selectOuterHarmonyData(inputMap3);
		Integer outerHarmonyScore = (outerMap == null) ? 0 : (Integer)outerMap.get("score");
		
		//속궁합 정보
		Map<String,Object> inputMap4 = new HashMap<String,Object>();
		inputMap4.put("key1", innerKey1);
		inputMap4.put("key2", innerKey2);
		inputMap4.put("gLang", inputVO.getgLang());
		Map<String,Object> innerMap = harmonyDao.selectInnerHarmonyData(inputMap4);
		Integer innerHarmonyScore	= (innerMap == null) ? 0 : (Integer)innerMap.get("score");
		Integer loveScore			= (innerMap == null) ? 0 : ((Double)innerMap.get("love")).intValue();
		Integer healthScore			= (innerMap == null) ? 0 : ((Double)innerMap.get("health")).intValue();
		Integer moneyScore			= (innerMap == null) ? 0 : ((Double)innerMap.get("money")).intValue();
		Integer childrenScore		= (innerMap == null) ? 0 : ((Double)innerMap.get("children")).intValue();
		Integer totalScore			= (innerMap == null) ? 0 : ((Double)innerMap.get("total")).intValue();
		
		Integer avgScore			= (innerHarmonyScore + outerHarmonyScore + bloodHarmonyScore + signHarmonyScore) / 4;
		
		Map<String,Object> harmonyMap = new HashMap<String,Object>();
		harmonyMap.put("myAgeCycleCd", myAgeCycleCd);
		harmonyMap.put("myBloodTypeCd", myBloodTypeCd);
		harmonyMap.put("myNickName", myNickName);
		harmonyMap.put("mySignCd", mySignCd);
		harmonyMap.put("myElementCdForInner", myElementCdForInner);
		harmonyMap.put("myElementCdForOuter", myElementCdForOuter);
		harmonyMap.put("myBirthDate", myBirthDate);
		harmonyMap.put("myLunarSolarCd", myLunarSolarCd);
		harmonyMap.put("myElementCd", myElementCdForOuter);
		
		harmonyMap.put("yourAgeCycleCd", yourAgeCycleCd);
		harmonyMap.put("yourBloodTypeCd", yourBloodTypeCd);
		harmonyMap.put("yourNickName", yourNickName);
		harmonyMap.put("yourSignCd", yourSignCd);
		harmonyMap.put("yourElementCdForInner", yourElementCdForInner);
		harmonyMap.put("yourElementCdForOuter", yourElementCdForOuter);
		harmonyMap.put("yourBirthDate", yourBirthDate);
		harmonyMap.put("yourLunarSolarCd", yourLunarSolarCd);
		harmonyMap.put("yourElementCd", yourElementCdForOuter);
		
		harmonyMap.put("totalHarmonyScore", avgScore);
		harmonyMap.put("outerHarmonyScore", outerHarmonyScore);
		harmonyMap.put("innerHarmonyScore", innerHarmonyScore);
		harmonyMap.put("loveScore", loveScore);
		harmonyMap.put("healthScore", healthScore);
		harmonyMap.put("moneyScore", moneyScore);
		harmonyMap.put("childrenScore", childrenScore);
		harmonyMap.put("bloodHarmonyScore", bloodHarmonyScore);
		harmonyMap.put("signHarmonyScore", signHarmonyScore);
		harmonyMap.put("innerHarmonyKeyValue", innerKey1 + "^" + innerKey2);
		harmonyMap.put("outerHarmonyKeyValue", outerKey1 + "^" + outerKey2);
		harmonyMap.put("bloodHarmonyKeyValue", bloodKey1 + "^" + bloodKey2);
		harmonyMap.put("signHarmonyKeyValue", mySignCd + "^S01");

		return harmonyMap;
	}
	
	/** 상세 궁합정보 조회 */
	public Map<String,Object> getDetailInfo(DetailInfoReqVO inputVO) {
		gLocale = new Locale(inputVO.getgLang());
		Map<String,Object> returnMap = new HashMap<String,Object>();
		Map<String,Object> responseMap = new HashMap<String,Object>();
		
		String harmonyType	= inputVO.getHarmonyType(); 
		String keyValue		= inputVO.getKeyValue();
		
		String[] keyList = keyValue.split("\\^");
		System.out.println("keyList : " + keyList.toString());
		System.out.println("keyValue[0] : " + keyList[0]);
		System.out.println("keyValue[1] : " + keyList[1]);
		String	key1 = keyList[0];
		String	key2 = keyList[1];
		
		Map<String,Object> inputMap = new HashMap<String,Object>();
		inputMap.put("key1", key1);
		inputMap.put("key2", key2);
		inputMap.put("gLang", inputVO.getgLang());
		
		Map<String,Object> detailMap = null;
		if(Constants.HarmonyType.BLOOD.equalsIgnoreCase(harmonyType)) {
			detailMap = harmonyDao.selectBloodHarmonyData(inputMap);
		} else if(Constants.HarmonyType.SIGN.equalsIgnoreCase(harmonyType)) {
			detailMap = harmonyDao.selectSignHarmonyData(inputMap);
		} else if(Constants.HarmonyType.INNER.equalsIgnoreCase(harmonyType)) {
			detailMap = harmonyDao.selectInnerHarmonyData(inputMap);
		} else if(Constants.HarmonyType.OUTER.equalsIgnoreCase(harmonyType)) {
			detailMap = harmonyDao.selectOuterHarmonyData(inputMap);
		}
		
		responseMap.put("detail", detailMap);
		
		returnMap.put("response", responseMap);
		
		Result result = new Result(
				Constants.ReturnCode.SUCCESS,
				msgUtil.getMsgCd("comm.success.search", gLocale),
				msgUtil.getMsgText("comm.success.search", gLocale));
		returnMap.put("result", result);
		
		return returnMap;
	}
	
	

}
