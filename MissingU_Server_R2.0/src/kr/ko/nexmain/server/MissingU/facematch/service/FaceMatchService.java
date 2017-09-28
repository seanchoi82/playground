package kr.ko.nexmain.server.MissingU.facematch.service;

import java.util.Map;

import kr.ko.nexmain.server.MissingU.facematch.model.GetCandidatesReqVO;
import kr.ko.nexmain.server.MissingU.facematch.model.GetRankReqVO;
import kr.ko.nexmain.server.MissingU.facematch.model.UpdateResultReqVO;

public interface FaceMatchService {
	
	Map<String,Object> getCandidates(GetCandidatesReqVO inputVO);
	
	Map<String,Object> updateResult(UpdateResultReqVO inputVO);
	
	Map<String,Object> getRank(GetRankReqVO inputVO);
	
}
