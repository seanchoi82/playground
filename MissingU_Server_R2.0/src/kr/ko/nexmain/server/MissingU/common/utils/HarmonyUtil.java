package kr.ko.nexmain.server.MissingU.common.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import kr.ko.nexmain.server.MissingU.common.Constants;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibm.icu.util.ChineseCalendar;

public class HarmonyUtil {
	protected static Logger log = LoggerFactory.getLogger(HarmonyUtil.class);
	
	private Calendar cal;
	private ChineseCalendar cc;
	
	public HarmonyUtil() {
		cal = Calendar.getInstance();
		cc = new ChineseCalendar();
	}
	
	/**
	 * @param yyyymmdd
	 * @return
	 */
	public boolean validateYYYYMMDD(String yyyymmdd) {
		boolean result = true;
		if(StringUtils.isEmpty(yyyymmdd)) {
			log.debug("Input date is null");
			return false;
		}
		String date = yyyymmdd.trim();
		if(date.length() != 8) {
			log.debug("length is not 8");
			return false;
		}
		
		return result;
	}
	
	/**
	 * 양력생일로 별자리 코드 리턴
	 * @param yyyymmdd 양력생일(YYYYMMDD)
	 * @return 
	 */
	public String getSignCd(String yyyymmdd) {
		log.debug("-------------- {}() START --------------", "getSignCd");
		String signCd="";
		if(!validateYYYYMMDD(yyyymmdd)) {
			return signCd;
		}
		
		String date = yyyymmdd.trim();
		Integer mmddNum = Integer.parseInt(date.substring(4));
		
		if (mmddNum > 1222 || mmddNum < 121 ){ //염소자리 
			signCd = "S12";
		} else if (mmddNum > 120 && mmddNum < 220 ){ //물병자리 
			signCd = "S01";
		} else if (mmddNum > 219 && mmddNum < 321 ){ //물고기자리 
			signCd = "S02";
		} else if (mmddNum > 320 && mmddNum < 421 ){ //양자리 
			signCd = "S03";
		} else if (mmddNum > 420 && mmddNum < 522 ){ //황소자리 
			signCd = "S04";
		} else if (mmddNum > 521 && mmddNum < 622 ){ //쌍동이자리 
			signCd = "S05";
		} else if (mmddNum > 621 && mmddNum < 724 ){ //게자리 
			signCd = "S06";
		} else if (mmddNum > 723 && mmddNum < 824 ){ //사자자리 
			signCd = "S07";
		} else if (mmddNum > 823 && mmddNum < 924 ){ //처녀자리 
			signCd = "S08";
		} else if (mmddNum > 923 && mmddNum < 1024 ){ //천칭자리 
			signCd = "S09";
		} else if (mmddNum > 1023 && mmddNum < 1123 ){ //전갈자리 
			signCd = "S10";
		} else if (mmddNum > 1122 && mmddNum < 1223 ){ //궁수자리 
			signCd = "S11";
		}
		
		log.debug("-------------- {}() END --------------", "getSignCd");
		return signCd;
	}
	
	/**
	 * 음력 -> 양력 변환
	 * @param yyyymmdd 음력생일(YYYYMMDD)
	 * @return
	 */
	public String toSolar(String yyyymmdd) {
		log.debug("-------------- {}() START --------------", "toSolar");
		log.debug("inputDate : {}",yyyymmdd);
		
		String solarDate = yyyymmdd;
		if(!validateYYYYMMDD(yyyymmdd)) {
			return solarDate;
		}
		
		cc.set(ChineseCalendar.EXTENDED_YEAR, Integer.parseInt(yyyymmdd.substring(0,4))+2637);
		cc.set(ChineseCalendar.MONTH, Integer.parseInt(yyyymmdd.substring(4,6))-1);
		cc.set(ChineseCalendar.DAY_OF_MONTH, Integer.parseInt(yyyymmdd.substring(6)));
		
		//cal.setTimeInMillis(cc.getTimeInMillis());
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		solarDate = dateFormat.format(cc.getTime());
		
		log.debug("solarDate : {}",solarDate);
		log.debug("-------------- {}() END --------------", "toSolar");
		return solarDate;
	}
	
	/**
	 * 양력 -> 음력 변환
	 * @param yyyymmdd 양력생일(YYYYMMDD)
	 * @return
	 */
	public String toLunar(String yyyymmdd) {
		log.debug("-------------- {}() START --------------", "toLunar");
		log.debug("inputDate : {}",yyyymmdd);
		
		String lunarDate = yyyymmdd;
		if(!validateYYYYMMDD(yyyymmdd)) {
			return lunarDate;
		}
		
		cal.set(Calendar.YEAR, Integer.parseInt(yyyymmdd.substring(0,4)));
		cal.set(Calendar.MONTH, Integer.parseInt(yyyymmdd.substring(4,6))-1);
		cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(yyyymmdd.substring(6)));
		
		cc.setTimeInMillis(cal.getTimeInMillis());
		
		int y = cc.get(ChineseCalendar.EXTENDED_YEAR)-2637;
		int m = cc.get(ChineseCalendar.MONTH)+1;
		int d = cc.get(ChineseCalendar.DAY_OF_MONTH);
		
		StringBuilder sb = new StringBuilder();
		sb.append(UTL.lpad(y, 4));
		sb.append(UTL.lpad(m, 2));
		sb.append(UTL.lpad(d, 2));
		
		lunarDate = sb.toString();
		
		log.debug("lunarDate : {}",lunarDate);
		log.debug("-------------- {}() END --------------", "toLunar");
		return lunarDate;
	}
	
	/**
	 * 음력생일로 천간 코드 리턴
	 * @param yyyymmdd 음력생일
	 * @return
	 */
	public String getChunganCd(String yyyymmdd) {
		log.debug("-------------- {}() START --------------", "getChunganCd");
		log.debug("inputDate : {}",yyyymmdd);
		
		String chunganCd = "";
		if(!validateYYYYMMDD(yyyymmdd)) {
			return chunganCd;
		}
		
		cc.set(ChineseCalendar.EXTENDED_YEAR, Integer.parseInt(yyyymmdd.substring(0,4))+2637);
		cc.set(ChineseCalendar.MONTH, Integer.parseInt(yyyymmdd.substring(4,6))-1);
		cc.set(ChineseCalendar.DAY_OF_MONTH, Integer.parseInt(yyyymmdd.substring(6)));
		
		int ageCycle = cc.get(ChineseCalendar.YEAR);
		System.out.println("ageCycle : " + ageCycle);
		
		switch(ageCycle % 10) {
		case 1 :
			chunganCd = Constants.ChunganCd.GAB;
			break;
		case 2 :
			chunganCd = Constants.ChunganCd.EUL;
			break;
		case 3 :
			chunganCd = Constants.ChunganCd.BYOUNG;
			break;
		case 4 :
			chunganCd = Constants.ChunganCd.JUNG;
			break;
		case 5 :
			chunganCd = Constants.ChunganCd.MU;
			break;
		case 6 :
			chunganCd = Constants.ChunganCd.GI;
			break;
		case 7 :
			chunganCd = Constants.ChunganCd.KYUNG;
			break;
		case 8 :
			chunganCd = Constants.ChunganCd.SHIN;
			break;
		case 9 :
			chunganCd = Constants.ChunganCd.IM;
			break;
		case 0 :
			chunganCd = Constants.ChunganCd.GAE;
			break;
		default :
			log.debug("No Matching ChunganCd");
		}
		
		log.debug("chunganCd : {}", chunganCd);
		log.debug("-------------- {}() END --------------", "getChunganCd");
		return chunganCd;
	}
	
	/**
	 * 음력생일로 지지 코드 리턴
	 * @param yyyymmdd 음력생일
	 * @return
	 */
	public String getJijiCd(String yyyymmdd) {
		log.debug("-------------- {}() START --------------", "getJijiCd");
		log.debug("inputDate : {}",yyyymmdd);
		
		String jijiCd = "";
		if(!validateYYYYMMDD(yyyymmdd)) {
			return jijiCd;
		}
		
		cc.set(ChineseCalendar.EXTENDED_YEAR, Integer.parseInt(yyyymmdd.substring(0,4))+2637);
		cc.set(ChineseCalendar.MONTH, Integer.parseInt(yyyymmdd.substring(4,6))-1);
		cc.set(ChineseCalendar.DAY_OF_MONTH, Integer.parseInt(yyyymmdd.substring(6)));
		
		int ageCycle = cc.get(ChineseCalendar.YEAR);
		System.out.println("ageCycle : " + ageCycle);
		
		switch(ageCycle % 12) {
		case 1 :
			jijiCd = Constants.JijiCd.JA;
			break;
		case 2 :
			jijiCd = Constants.JijiCd.CHOOK;
			break;
		case 3 :
			jijiCd = Constants.JijiCd.IN;
			break;
		case 4 :
			jijiCd = Constants.JijiCd.MYO;
			break;
		case 5 :
			jijiCd = Constants.JijiCd.JIN;
			break;
		case 6 :
			jijiCd = Constants.JijiCd.SA;
			break;
		case 7 :
			jijiCd = Constants.JijiCd.OH;
			break;
		case 8 :
			jijiCd = Constants.JijiCd.ME;
			break;
		case 9 :
			jijiCd = Constants.JijiCd.SHIN;
			break;
		case 10 :
			jijiCd = Constants.JijiCd.YOO;
			break;
		case 11 :
			jijiCd = Constants.JijiCd.SOOL;
			break;
		case 0 :
			jijiCd = Constants.JijiCd.HAE;
			break;
		default :
			log.debug("No Matching JijiCd");
		}
		
		log.debug("getJijiCd : {}", jijiCd);
		log.debug("-------------- {}() END --------------", "getJijiCd");
		return jijiCd;
	}
	
	/**
	 * 음력생일로 오행코드 리턴
	 * @param yyyymmdd
	 * @return
	 */
	public String getElementCd(String yyyymmdd) {
		log.debug("-------------- {}() START --------------", "getElementCd");
		log.debug("inputDate : {}",yyyymmdd);
		
		String elementCd = "";
		if(!validateYYYYMMDD(yyyymmdd)) {
			return elementCd;
		}
		
		String chunganCd = getChunganCd(yyyymmdd);
		
		if(Constants.ChunganCd.GAB.equalsIgnoreCase(chunganCd)
				|| Constants.ChunganCd.EUL.equalsIgnoreCase(chunganCd)) {
			elementCd = Constants.FiveElement.MOK;
		} else if (Constants.ChunganCd.BYOUNG.equalsIgnoreCase(chunganCd)
				|| Constants.ChunganCd.JUNG.equalsIgnoreCase(chunganCd)) {
			elementCd = Constants.FiveElement.WHA;
		} else if (Constants.ChunganCd.MU.equalsIgnoreCase(chunganCd)
				|| Constants.ChunganCd.GI.equalsIgnoreCase(chunganCd)) {
			elementCd = Constants.FiveElement.TOU;
		} else if (Constants.ChunganCd.KYUNG.equalsIgnoreCase(chunganCd)
				|| Constants.ChunganCd.SHIN.equalsIgnoreCase(chunganCd)) {
			elementCd = Constants.FiveElement.GUM;
		} else if (Constants.ChunganCd.IM.equalsIgnoreCase(chunganCd)
				|| Constants.ChunganCd.GAE.equalsIgnoreCase(chunganCd)) {
			elementCd = Constants.FiveElement.SU;
		}
		
		log.debug("elementCd : {}", elementCd);
		log.debug("-------------- {}() END --------------", "getElementCd");
		return elementCd;
	}
	
	/**
	 * 일주천간으로 오행코드 리턴
	 * @param yyyymmdd
	 * @return
	 */
	public String getElementCdByDayChunganCd(String dayChunganCd) {
		log.debug("-------------- {}() START --------------", "getElementCdByDayChunganCd");
		log.debug("dayChunganCd : {}",dayChunganCd);
		
		String elementCd = "";
		if(StringUtils.isEmpty(dayChunganCd)) {
			return elementCd;
		}
		
		if(Constants.ChunganCd.GAB.equalsIgnoreCase(dayChunganCd)
				|| Constants.ChunganCd.EUL.equalsIgnoreCase(dayChunganCd)) {
			elementCd = Constants.FiveElement.MOK;
		} else if (Constants.ChunganCd.BYOUNG.equalsIgnoreCase(dayChunganCd)
				|| Constants.ChunganCd.JUNG.equalsIgnoreCase(dayChunganCd)) {
			elementCd = Constants.FiveElement.WHA;
		} else if (Constants.ChunganCd.MU.equalsIgnoreCase(dayChunganCd)
				|| Constants.ChunganCd.GI.equalsIgnoreCase(dayChunganCd)) {
			elementCd = Constants.FiveElement.TOU;
		} else if (Constants.ChunganCd.KYUNG.equalsIgnoreCase(dayChunganCd)
				|| Constants.ChunganCd.SHIN.equalsIgnoreCase(dayChunganCd)) {
			elementCd = Constants.FiveElement.GUM;
		} else if (Constants.ChunganCd.IM.equalsIgnoreCase(dayChunganCd)
				|| Constants.ChunganCd.GAE.equalsIgnoreCase(dayChunganCd)) {
			elementCd = Constants.FiveElement.SU;
		}
		
		log.debug("elementCd : {}", elementCd);
		log.debug("-------------- {}() END --------------", "getElementCdByDayChunganCd");
		return elementCd;
	}
	
	/**
	 * 일주천간으로 오행코드 리턴
	 * @param yyyymmdd
	 * @return
	 */
	public String getElementCdByDayJijiCd(String dayJijiCd) {
		log.debug("-------------- {}() START --------------", "getElementCdByDayJijiCd");
		log.debug("dayJijiCd : {}",dayJijiCd);
		
		String elementCd = "";
		if(StringUtils.isEmpty(dayJijiCd)) {
			return elementCd;
		}
		
		if(Constants.JijiCd.IN.equalsIgnoreCase(dayJijiCd)
				|| Constants.JijiCd.MYO.equalsIgnoreCase(dayJijiCd)) {
			elementCd = Constants.FiveElement.MOK;
		} else if (Constants.JijiCd.SA.equalsIgnoreCase(dayJijiCd)
				|| Constants.JijiCd.OH.equalsIgnoreCase(dayJijiCd)) {
			elementCd = Constants.FiveElement.WHA;
		} else if (Constants.JijiCd.CHOOK.equalsIgnoreCase(dayJijiCd)
				|| Constants.JijiCd.JIN.equalsIgnoreCase(dayJijiCd)
				|| Constants.JijiCd.ME.equalsIgnoreCase(dayJijiCd)
				|| Constants.JijiCd.SOOL.equalsIgnoreCase(dayJijiCd)) {
			elementCd = Constants.FiveElement.TOU;
		} else if (Constants.JijiCd.SHIN.equalsIgnoreCase(dayJijiCd)
				|| Constants.JijiCd.YOO.equalsIgnoreCase(dayJijiCd)) {
			elementCd = Constants.FiveElement.GUM;
		} else if (Constants.JijiCd.JA.equalsIgnoreCase(dayJijiCd)
				|| Constants.JijiCd.HAE.equalsIgnoreCase(dayJijiCd)) {
			elementCd = Constants.FiveElement.SU;
		}
		
		log.debug("elementCd : {}", elementCd);
		log.debug("-------------- {}() END --------------", "getElementCdByDayJijiCd");
		return elementCd;
	}
	
	/**
	 * 양력생일로 일주 천간지지 리턴
	 * @param yyyymmdd 양력생일
	 * @return
	 */
	public Map<String,String> getChunJiOfDay(String yyyymmdd){
		log.debug("-------------- {}() START --------------", "getChunJiOfDay");
		log.debug("yyyymmdd : {}",yyyymmdd);
		
		Map<String,String> resultMap = null;
		if(!validateYYYYMMDD(yyyymmdd)) {
			return resultMap;
		}
		
		// 1881년의 날수와 현재까지의 날수의 차를 구해서 나머지로 날을 구한다.
		int syear	= Integer.parseInt(yyyymmdd.substring(0,4));
		int smonth	= Integer.parseInt(yyyymmdd.substring(4,6));
		int sday	= Integer.parseInt(yyyymmdd.substring(6));

		int[] m = new int[]{31, 0, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
		
		int k11 = (int)(syear-1);

		int td2 = k11*365 + (int)(k11/4) - (int)(k11/100) + (int)(k11/400);
	
		//int temp2 = k11*365 + (int)(k11/4) - (int)(k11/100) + (int)(k11/400);
		
		boolean ll = syear%400==0 || syear%100!=0 && syear%4==0;
		
		if(ll){
			m[1] = 29;
		}else{
			m[1] = 28;
		}
		
		for(int i=0; i<smonth-1; i++) 
			td2 = td2 + (int)m[i];
		
		td2 = td2 + (int)sday;

		int r1 = (td2 - 686657)%10;  //천간
		int r2 = (td2 - 686657)%12;  // 지지
		
		//미싱유에서 정의한 코드로 변환
		String[] chunganArray	= new String[]{
				Constants.ChunganCd.EUL, Constants.ChunganCd.BYOUNG, Constants.ChunganCd.JUNG,
				Constants.ChunganCd.MU, Constants.ChunganCd.GI, Constants.ChunganCd.KYUNG,
				Constants.ChunganCd.SHIN, Constants.ChunganCd.IM, Constants.ChunganCd.GAE,
				Constants.ChunganCd.GAB}; //천간
		String[] jijiArray		= new String[]{
				Constants.JijiCd.ME, Constants.JijiCd.SHIN, Constants.JijiCd.YOO,
				Constants.JijiCd.SOOL, Constants.JijiCd.HAE, Constants.JijiCd.JA,
				Constants.JijiCd.CHOOK, Constants.JijiCd.IN, Constants.JijiCd.MYO,
				Constants.JijiCd.JIN, Constants.JijiCd.SA, Constants.JijiCd.OH}; //지지
		
		resultMap = new HashMap<String,String>();
		resultMap.put("chunganCd", chunganArray[r1]);
		resultMap.put("jijiCd", jijiArray[r2]);
		
		
		log.debug("resultMap : {}", resultMap.toString());
		log.debug("-------------- {}() END --------------", "getChunJiOfDay");
		return resultMap;
	}
	
	
	public static void main(String[] args) {
		HarmonyUtil hu = new HarmonyUtil();
		//System.out.println(hu.getSignCd("19820511"));
		//System.out.println(hu.toSolar("19810106"));
		//System.out.println(hu.toLunar("19810210"));
//		System.out.println(hu.getChunganCd("20130418"));
//		System.out.println(hu.getJijiCd("20130418"));
//		System.out.println(hu.getElementCd("20130418"));
		System.out.println(hu.getChunJiOfDay("19820511"));
		System.out.println(hu.getChunJiOfDay("19820512"));
		
	}
	
	
}
