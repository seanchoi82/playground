package kr.ko.nexmain.server.MissingU.common.service;

import java.util.Map;

public class BaseService {

	protected void makePaging(Map<String, Object> params) {
		
		//페이징 변수 int로 변환
		try {
			long pageSize = Long.parseLong((String)params.get("pageSize"));
			params.put("pageSize", pageSize);
		}catch(Exception e) {
			params.put("pageSize", 20);
		}
		
		try {
			long startRow = Long.parseLong(params.get("startRow") + "");
			params.put("startRow", startRow);
		}catch(Exception e) {
			params.put("startRow", 0);
		}
	}
}
