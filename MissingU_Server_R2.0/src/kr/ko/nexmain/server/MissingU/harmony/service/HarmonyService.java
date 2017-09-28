package kr.ko.nexmain.server.MissingU.harmony.service;

import java.util.Map;

import kr.ko.nexmain.server.MissingU.common.model.CommReqVO;
import kr.ko.nexmain.server.MissingU.harmony.model.DetailInfoReqVO;
import kr.ko.nexmain.server.MissingU.harmony.model.TotalInfoReqVO;

public interface HarmonyService {
	/** 내 간편 궁합정보 조회 */
	Map<String,Object> getMySimpleInfo(CommReqVO inputVO);
	
	/** 종합 궁합정보 조회 */
	Map<String,Object> getTotalInfo(TotalInfoReqVO inputVO);
	
	/** 종합 궁합정보 Map 리턴 */
	Map<String,Object> getHarmonyResultMap(TotalInfoReqVO inputVO);
	
	/** 상세 궁합정보 조회 */
	Map<String,Object> getDetailInfo(DetailInfoReqVO inputVO);
	
}
