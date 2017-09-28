package kr.ko.nexmain.server.MissingU.harmony.dao;

import java.util.Map;

public interface HarmonyDao {
	/** 혈액형 궁합 데이타 조회 */
	Map<String,Object> selectBloodHarmonyData(Map<String,Object> inputMap);
	
	/** 별자리 궁합 데이타 조회 */
	Map<String,Object> selectSignHarmonyData(Map<String,Object> inputMap);
	
	/** 속궁합 데이타 조회 */
	Map<String,Object> selectInnerHarmonyData(Map<String,Object> inputMap);
	
	/** 겉궁합 데이타 조회 */
	Map<String,Object> selectOuterHarmonyData(Map<String,Object> inputMap);
	
}