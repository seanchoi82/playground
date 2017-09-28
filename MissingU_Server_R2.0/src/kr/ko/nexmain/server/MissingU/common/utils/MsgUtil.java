package kr.ko.nexmain.server.MissingU.common.utils;

import java.util.Locale;
import java.util.Map;

import kr.ko.nexmain.server.MissingU.common.Constants;
import kr.ko.nexmain.server.MissingU.common.model.Result;

import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.validation.BindingResult;

public class MsgUtil {
	private MessageSource messageSource;
	private MessageSourceAccessor accessor;
	private static final String MSG_CD_SUFFIX 	= ".msgCd";
	private static final String MSG_TEXT_SUFFIX = ".msgText";
	
	public void setMessageSource(MessageSource messageSource) {
		accessor = new MessageSourceAccessor(messageSource);
		this.messageSource = messageSource;
	}
	
	public String getMsgCd(String keyStr) {
		String msgCd = accessor.getMessage(keyStr+MSG_CD_SUFFIX);
		return msgCd;
	}
	
	public String getMsgCd(String keyStr, Locale locale) {
		String msgCd = accessor.getMessage(keyStr+MSG_CD_SUFFIX, locale);
		return msgCd;
	}
	
	public String getMsgText(String keyStr) {
		String msgText = accessor.getMessage(keyStr+MSG_TEXT_SUFFIX);
		return msgText;
	}
	
	public String getMsgText(String keyStr, Locale locale) {
		String msgText = accessor.getMessage(keyStr+MSG_TEXT_SUFFIX, locale);
		
		return msgText;
	}
	
	public String getMsgText(String keyStr, Object[] args, Locale locale) {
		String msgText = accessor.getMessage(keyStr+MSG_TEXT_SUFFIX, args, locale);
		
		return msgText;
	}
	
	
	
	public String getPropMsg(String keyStr) {
		String msgText = accessor.getMessage(keyStr);
		return msgText;
	}
	
	public String getPropMsg(String keyStr, Locale locale) {
		String msgText = accessor.getMessage(keyStr, locale);
		return msgText;
	}
	
/*	public Map<String,Object> getParamErrorMap(Map<String,Object> responseMap, BindingResult bindingResult) {
		Result result = new Result(
				Constants.ReturnCode.PARAM_ERROR,
				getMsgCd("comm.paramError"),
				getMsgText("comm.paramError"));
		responseMap.put("result", result);
		responseMap.put("bindingErrorField", bindingResult.getFieldError().getField());
		return responseMap;
	}*/
	
	public Map<String,Object> getParamErrorMap(Map<String,Object> responseMap, BindingResult bindingResult, String lang) {
		Result result = new Result(
				Constants.ReturnCode.PARAM_ERROR,
				getMsgCd("comm.paramError", new Locale(lang)),
				getMsgText("comm.paramError", new Locale(lang)));
		responseMap.put("result", result);
		responseMap.put("bindingErrorField", bindingResult.getFieldError().getField());
		return responseMap;
	}
	
	public Map<String,Object> getSystemErrorMap(Map<String,Object> responseMap) {
		Result result = new Result(
				Constants.ReturnCode.SYSTEM_ERROR,
				getMsgCd("comm.systemError"),
				getMsgText("comm.systemError"));
		responseMap.put("result", result);
		return responseMap;
	}
	
	public Map<String,Object> getSystemErrorMap(Map<String,Object> responseMap, String lang) {
		Result result = new Result(
				Constants.ReturnCode.SYSTEM_ERROR,
				getMsgCd("comm.systemError", new Locale(lang)),
				getMsgText("comm.systemError", new Locale(lang)));
		responseMap.put("result", result);
		return responseMap;
	}
	
	public Map<String,Object> getAuthErrorMapByLackOfPoint(Map<String,Object> responseMap) {
		Result result = new Result(
				Constants.ReturnCode.AUTH_ERROR,
				getMsgCd("comm.authError.lackOfPoint"),
				getMsgText("comm.authError.lackOfPoint"));
		responseMap.put("result", result);
		return responseMap;
	}
	
	public Map<String,Object> getAuthErrorMapByLackOfPoint(Map<String,Object> responseMap, String lang) {
		Result result = new Result(
				Constants.ReturnCode.AUTH_ERROR,
				getMsgCd("comm.authError.lackOfPoint", new Locale(lang)),
				getMsgText("comm.authError.lackOfPoint", new Locale(lang)));
		responseMap.put("result", result);
		return responseMap;
	}
	
	
}
