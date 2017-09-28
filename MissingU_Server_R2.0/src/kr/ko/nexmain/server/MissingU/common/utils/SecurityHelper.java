package kr.ko.nexmain.server.MissingU.common.utils;

import kr.ko.nexmain.server.MissingU.common.security.AES256;

public class SecurityHelper extends AES256 {

	private static final String strKey = "1N2E3X4M5A6I7N8M9I0S1S2I3N4G5U6K";
	private static final String strIv = "M9I0S1S2I3N4G5U6";

	public SecurityHelper() {
		super(strKey, strIv);
	}

}
