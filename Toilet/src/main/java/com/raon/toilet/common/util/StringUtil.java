
package com.raon.toilet.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Clob;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;


public class StringUtil extends StringUtils{
	
	public final  String INDENT = "    ";
	
    public StringUtil(){}
	
	/**
	 * 8859_1 에서 EUC-KR 로 인코딩 한다.
	 * 
	 * @param str
	 *            원본 String
	 * @return String 변환된 내용
	 */
	public  String toKR(String str) {
		String rstr = null;
		try {
			rstr = (str == null) ? "" : new String(str.getBytes("8859_1"), "euc-kr");
		} catch (java.io.UnsupportedEncodingException e) {
		}
		return rstr;
	}
	
	/**
	 * EUC-KR 에서 8859_1 로 인코딩 한다.
	 * 
	 * @param str
	 *            원본 String
	 * @return String 변환된 내용
	 */
	public  String toUS(String str) {
		String rstr = null;
		try {
			rstr = (str == null) ? "" : new String(str.getBytes("euc-kr"), "8859_1");
		} catch (java.io.UnsupportedEncodingException e) {
		}
		return rstr;
	}
	
	/**
	 * 지정한 길이 보다 길경우 지정한 길이에서 자른후 "..."을 붙여 준다.
	 * 그보다 길지 않을때는 그냥 돌려준다. char 단위로 계산 (한글도 1자)
	 * 
	 * @param str
	 *            원본 String
	 * @param amount
	 *            String 의 최대 길이 (이보다 길면 이 길이에서 자른다)
	 * @return String 변경된 내용
	 */
	public  String crop(String str, int amount) {
		if (str == null) {
			return "";
		}
		String result = str;
		if (result.length() > amount) {
			result = result.substring(0, amount) + "...";
		}
		return result;
	}
	
	/**
	 * 지정한 길이 보다 길경우 지정한 길이에서 자른후 맨뒷부분에 지정한 문자열을 붙여 준다.
	 * 그보다 길지 않을때는 그냥 돌려준다. char 단위로 계산 (한글도 1자)
	 * 
	 * @param str
	 *            원본 String
	 * @param amount
	 *            String 의 최대 길이 (이보다 길면 이 길이에서 자른다)
	 * @param trail
	 *            amount 보다 str 이 길경우 amount 만큼만 자른다음 trail 을 붙여 준다.
	 * @return String 변경된 내용
	 */
	public  String crop(String str, int amount, String trail) {
		if (str == null) {
			return "";
		}
		String result = str;
		if (result.length() > amount) {
			result = result.substring(0, amount) + trail;
		}
		return result;
	}
	
	/**
	 * 지정한 길이 보다 길경우 지정한 길이에서 자른후 맨뒷부분에 지정한 문자열을 붙여 준다.
	 * 그보다 길지 않을때는 그냥 돌려준다. Byte 단위로 계산 (한글 = 2자)
	 * <p>
	 * 
	 * @param str
	 *            원본 String
	 * @param amount
	 *            String 의 최대 길이 (이보다 길면 이 길이에서 자른다)
	 * @param trail
	 *            amount 보다 str 이 길경우 amount 만큼만 자른다음 trail 을 붙여 준다.
	 * @return String 변경된 내용
	 */
	public  String cropByte(String str, int amount, String trail) throws UnsupportedEncodingException {
		if (str == null) {
			return "";
		}
		String tmp = str;
		int slen = 0, blen = 0;
		char c;
		if (tmp.getBytes("euc-kr").length > amount) {
			while (blen + 1 < amount) {
				c = tmp.charAt(slen);
				blen++;
				slen++;
				if (c > 127) {
					blen++; // 2-byte character..
				}
			}
			tmp = tmp.substring(0, slen) + trail;
		}
		return tmp;
	}
	
	/**
	 * String 이 null 인지 체크 하여 null 이면 공백문자("")를 넘겨준다
	 * 
	 * @param str
	 *            체크할 String
	 * @return String
	 */
	public  String nullCheck(String str) {
		String strTmp;
		if (str == null) {
			strTmp = "";
		} else {
			strTmp = str;
		}
		return strTmp;
	}
	
	/**
	 * String 이 null 인지 체크 하여 null 이면 지정한값(replace)를 넘겨준다.
	 * 
	 * @param str
	 *            체크할 String
	 * @param replace
	 *            null 일 경우 대체할 문자열
	 * @return String
	 */
	public  String nullCheck(String str, String replace) {
		String strTmp;
		
		if (str == null) {
			strTmp = replace;
		} else {
			strTmp = str;
		}
		return strTmp;
	}
	
	/**
	 * 오늘 날자를 가져온다. (ex, 2002년 09월 13일)
	 * 
	 * @return 오늘날자
	 */
	public  String getToday() {
		GregorianCalendar calendar = new GregorianCalendar();
		String sDate = null; /* 출력 날짜 */
		
		try {
			DecimalFormat df = new DecimalFormat("00");
			sDate = calendar.get(Calendar.YEAR) + "년 " + df.format(calendar.get(Calendar.MONTH) + 1) + "월 "
					+ df.format(calendar.get(Calendar.DATE)) + "일 " + df.format(calendar.get(Calendar.HOUR_OF_DAY))
					+ "일 ";
			
			return sDate;
		} catch (Exception e) {
			return sDate;
		}
	}
	
	
	public  void indent(StringBuffer buf,int n) {
		for ( int i = 0; i < n; i++ ) {
			buf.append(INDENT);
		}
	}
    
    public  String removeCharacter(String str, String character)
        throws Exception
    {
        StringTokenizer st = null;
        StringBuffer buf = null;
        String result = null;
        result = str;
        for(int i = 0; i < character.length(); i++)
        {
            char c = character.charAt(i);
            st = new StringTokenizer(result, String.valueOf(c));
            buf = new StringBuffer();
            for(; st.hasMoreTokens(); buf.append(st.nextToken()));
            result = buf.toString();
        }

        return result;
    }

    public  String fixlength(int kind, int out_len, String str)
    {
        if(str == null)
            str = "";
        byte input[] = str.getBytes();
        byte temp[] = new byte[out_len];
        int in_len = input.length;
        if(kind == 2)
        {
            for(int i = 0; i < out_len; i++)
                temp[i] = 48;

        } else
        {
            for(int i = 0; i < out_len; i++)
                temp[i] = 32;

        }
        if(in_len > out_len)
            in_len = out_len;
        if(kind == 0)
        {
            for(int i = 0; i < in_len; i++)
                temp[i] = input[i];

        } else
        {
            int i = out_len - in_len;
            for(int j = 0; i < out_len; j++)
            {
                temp[i] = input[j];
                i++;
            }

        }
        String output = new String(temp, 0, out_len);
        if(output.length() == 0 && out_len > 0)
            temp[out_len - 1] = 32;
        output = new String(temp, 0, out_len);
        return output;
    }

    public  String cropByte(byte str)
    {
        if((char)str > '\177' || (char)str > '\177')
            return "KO";
        else
            return "EN";
    }

    public  String makeSpace(int out_len)
    {
        byte temp[] = new byte[out_len];
        for(int i = 0; i < out_len; i++)
            temp[i] = 32;

        String output = new String(temp, 0, out_len);
        return output;
    }

    public  int stoi(String str)
    {
        if(str == null)
            return 0;
        else
            return Integer.valueOf(str).intValue();
    }

    public  String itos(int i)
    {
        return (new Integer(i)).toString();
    }
    
    public  String ftos(float i)
    {
        return (new Float(i)).toString();
    }

    public  String fillSpace(String str, int size)
    {
        if(str == null)
            return "";
        if(str.length() >= size)
            return str;
        int spaces = size - str.length();
        StringBuffer sb = new StringBuffer(spaces);
        for(int i = 0; i < spaces; i++)
            sb.append(" ");

        return str.concat(sb.toString());
    }

    public  String fillZero(String str, int size)
    {
        if(str == null)
            return "";
        if(str.length() >= size)
            return str;
        int zeros = size - str.length();
        StringBuffer sb = new StringBuffer(str);
        for(int i = 0; i < zeros; i++)
            sb.insert(str.length(), '0');

        return sb.toString();
    }

    public  String insertZero(String str, int size)
    {
        if(str == null)
            return "";
        if(str.length() >= size)
            return str;
        int zeros = size - str.length();
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < zeros; i++)
        	sb.append('0');
        sb.append(str);

        return sb.toString();
    }

    public  String hanSubstring(String str, int offset, int len)
    {
        String output = "";
        try
        {
            byte input[] = str.getBytes();
            if(offset >= input.length)
                return output;
            if(offset + len > input.length)
                len = input.length - offset;
            output = new String(input, offset, len);
        }
        catch(Exception exception) { }
        return output;
    }

    public  String hanSubstring(String str, int offset)
    {
        byte input[] = str.getBytes();
        return hanSubstring(str, offset, input.length - offset);
    }


    public  String stringCheck(String strData)
    {
        if(strData == null)
            strData = "";
        return strData;
    }

    public  String convertField(String value, int length, String defaultValue, String type)
        throws Exception
    {
        String retData = null;
        try
        {
            if(value == null)
                value = defaultValue;
            if(value.equals(""))
                value = defaultValue;
            byte valueByte[] = value.getBytes();
            int valueLength = valueByte.length;
            byte defaultByte[] = new byte[1];
            if(type.equals("DIGIT"))
                defaultByte[0] = 48;
            else
                defaultByte[0] = 32;
            String defaultStr = new String(defaultByte);
            StringBuffer dataBuf = new StringBuffer();
            for(int i = 0; i < length - valueLength; i++)
                dataBuf.append(defaultStr);

            if(type.equals("DIGIT"))
                retData = dataBuf.toString() + value;
            else
                retData = value + dataBuf.toString();
        }
        catch(Exception e)
        {
            throw new Exception("[StringUtil.convertField] \uD544\uB4DC \uBCC0\uD658\uC911 \uC5D0\uB7EC\uAC00 \uBC1C\uC0DD\uD558\uC600\uC2B5\uB2C8\uB2E4 " + e.toString());
        }
        return retData;
    }

    public  String convertErrorField(String value, int length, String defaultValue, String type)
    {
        String retData = null;
        try
        {
            if(value == null)
                value = defaultValue;
            if(value.equals(""))
                value = defaultValue;
            byte valueByte[] = value.getBytes();
            int valueLength = valueByte.length;
            byte defaultByte[] = new byte[1];
            if(type.equals("DIGIT"))
                defaultByte[0] = 48;
            else
                defaultByte[0] = 32;
            String defaultStr = new String(defaultByte);
            StringBuffer dataBuf = new StringBuffer();
            for(int i = 0; i < length - valueLength; i++)
                dataBuf.append(defaultStr);

            if(type.equals("DIGIT"))
                retData = dataBuf.toString() + value;
            else
                retData = value + dataBuf.toString();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return retData;
    }

    public  String removeLastSpace(String str)
    {
        if(str == null || str.equals(""))
            return str;
        int len = str.length();
        int i;
        for(i = 0; i < len; i++)
            if(str.charAt(len - i - 1) != ' ')
                break;

        return str.substring(0, len - i);
    }

    public  String getStringReplace(String strKey, String values, String strVal)
    {
        StringTokenizer st = null;
        StringBuffer buff = new StringBuffer();
        try
        {
            st = new StringTokenizer(values, ",");
            if(st.countTokens() < 7)
                return values;
            String weekday = st.nextToken().trim();
            String saturday = st.nextToken().trim();
            String sunday = st.nextToken().trim();
            String holiday = st.nextToken().trim();
            String block = st.nextToken().trim();
            String errorcode = st.nextToken().trim();
            String servicetime = st.nextToken().trim();
            if("weekday".equals(strKey))
                weekday = strVal;
            if("saturday".equals(strKey))
                saturday = strVal;
            if("sunday".equals(strKey))
                sunday = strVal;
            if("holiday".equals(strKey))
                holiday = strVal;
            if("block".equals(strKey))
                block = strVal;
            if("errorcode".equals(strKey))
                errorcode = strVal;
            if("servicetime".equals(strKey))
                servicetime = strVal;
            buff.append(weekday);
            buff.append(",");
            buff.append(saturday);
            buff.append(",");
            buff.append(sunday);
            buff.append(",");
            buff.append(holiday);
            buff.append(",");
            buff.append(block);
            buff.append(",");
            buff.append(errorcode);
            buff.append(",");
            buff.append(servicetime);
        }
        catch(Exception exe)
        {
//            System.out.println("Exception");
        }
        return buff.toString();
    }
    
    public  String makeZero(String str, int size)
    {
        if(str == null)
            return "";
        if(size == 0)
            return str;
        int zeros = size;
        StringBuffer sb = new StringBuffer(str);
        for(int i = 0; i < zeros; i++)
            sb.insert(0, '0');

        return sb.toString();
    }

    
    
    /**
	* 특정 구분자로 연결되어 있는 문자열을 받아 문자열만을 추출한 후에
	* 그것을 ArrayList로 만들어 Return한다.
	* 입력되는 문자열은 자유로운 Delimiter로 사용가능하다.
	* 아래의 모든 문자열은 같은 결과를 나타낸다.<BR><BR>
	*
	* "dog^cat^hen^cock^"
	* "^dog^cat^hen^cock"
	* "dog^cat^hen^cock"
	* "^dog^cat^hen^cock^"
	*
	* @return java.util.ArrayList
	* @param pStr String
	* @param pDelimiter String
	*/
	public static List<String> getToken(String pStr, String pDelimiter) {

		List<String> al = new ArrayList<String>();

		if ( pStr == null || pStr == "" ) return al;
		if ( pDelimiter == null || pDelimiter == "" ) return al;

		int vStart = 0;
		int vEnd = pStr.length(); 
		boolean vFirst = false;

		if ( pStr.substring(0,pDelimiter.length()).equals(pDelimiter) ) vFirst = true;

		for ( int i = 0 ; vEnd > -1  ; i++ ) {

			if ( i > 0 )	vStart = vEnd+pDelimiter.length();

			vEnd = pStr.indexOf(pDelimiter, vStart);

			if ( vEnd > -1 ) {
				if ( !vFirst ) {
					al.add(pStr.substring(vStart,vEnd));
				}	else {
					 vFirst = false;
				}
			}
		}

		if ( ( vStart + pDelimiter.length() ) <= pStr.length() )
			al.add(pStr.substring(vStart,pStr.length()));

		return al;
	}
	
	/**
	 * @brief 값이 없으면 null로 변환한다.
	 *
	 * @param emptyStr 값이 없을 것으로 예상되는 데이터 또는 값이 없을 수 있는 데이터
	 * @return
	 */
	public  String makeNull(String emptyStr) {
		if ("".equals(emptyStr)) {
			emptyStr = null;
		}
		
		return emptyStr;
	}
	
	/**
	 * @brief 값이 없거나 null이면 ""로 변환한다.
	 *
	 * @param nullStr 값이 없을 것으로 예상되는 데이터 또는 값이 없을 수 있는 데이터
	 * @return
	 */
	public  String makeEmpty(String nullStr) {
		if (nullStr == null || nullStr.trim().equals("")) {
			nullStr = "";
		}
		
		return nullStr;
	}	
	
	/**
	 * str이 null 이거나 "", "    " 일경우 return true
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNull(String str) {
		return (str == null || (str.trim().length()) == 0);
	}

	public  boolean isNull(Object obj) {
		String str = null;
		if (obj instanceof String) {
			str = (String) obj;
		} else {
			return true;
		}
		return isNull(str);
	}
	
	/**
	 * String을 int형으로
	 * 
	 * @param value
	 * @return
	 */
	public static int parseInt(String value) {
		return parseInt(value, 0);
	}

	/**
	 * Object를 int형으로 defaultValue는 0이다.
	 * 
	 * @param value
	 * @return
	 */
	public  int parseInt(Object value) {
		String valueStr = replaceNull(value);
		return parseInt(valueStr, 0);
	}

	/**
	 * Object를 int형으로 Object가 null이면 defaultValue return
	 * 
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public  int parseInt(Object value, int defaultValue) {
		String valueStr = replaceNull(value);
		return parseInt(valueStr, defaultValue);
	}

	/**
	 * String을 int형으로 String이 숫자 형식이 아니면 defaultValue return
	 * 
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static int parseInt(String value, int defaultValue) {
		int returnValue = 0;
		if (isNull(value)) {
			returnValue = defaultValue;
		} else if (!org.apache.commons.lang.StringUtils.isNumeric(value)) {
			returnValue = defaultValue;
		} else {
			returnValue = Integer.parseInt(value);
		}
		return returnValue;
	}

	/**
	 * String을 long형으로 defaultValue는 0이다.
	 * 
	 * @param value
	 * @return
	 */
	public  long parseLong(String value) {
		return parseLong(value, 0);
	}

	/**
	 * String을 long형으로 잘못된 데이타 일시 return은 defaultValue
	 * 
	 * @param value
	 * @return
	 */
	public  long parseLong(String value, long defaultValue) {
		long returnValue = 0;
		if (isNull(value)) {
			returnValue = defaultValue;
		} else if (!org.apache.commons.lang.StringUtils.isNumeric(value)) {
			returnValue = defaultValue;
		} else {
			returnValue = Long.parseLong(value);
		}
		return returnValue;
	}
	
	/**
	 * null이 아닐때.
	 * 
	 * @param str
	 * @return
	 */
	public  boolean isNotNull(String str) {
		// isNull이 true이면 false false이면 true
		if (isNull(str)) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 널체크
	 * 
	 * @param obj
	 * @return
	 */
	public  boolean isNotNull(Object obj) {
		String str = null;
		if (obj instanceof String) {
			str = (String) obj;
		} else {
			return false;
		}
		return isNotNull(str);
	}

	/**
	 * 파라미터가 null 이거나 공백이 있을경우 "" 로 return
	 * 
	 * @param value
	 * @return
	 */
	public  String replaceNull(String value) {
		return replaceNull(value, "");
	}

	/**
	 * Object를 받아서 String 형이 아니거나 NULL이면 ""를 return String 형이면 형 변환해서 넘겨준다.
	 * 
	 * @param value
	 * @return
	 */
	public  String replaceNull(Object value) {
		Object rtnValue = value;
		if (rtnValue == null
				|| !"java.lang.String".equals(rtnValue.getClass().getName())) {
			rtnValue = "";
		}
		return replaceNull((String) rtnValue, "");
	}

	/**
	 * 파라미터로 넘어온 값이 null 이거나 공백이 포함된 문자라면 defaultValue를 return 아니면 값을 trim해서
	 * 넘겨준다.
	 * 
	 * @param value
	 * @param repStr
	 * @return
	 */
	public static  String replaceNull(String value, String defaultValue) {
		if (isNull(value)) {
			return defaultValue;
		}
		return value.trim();
	}

	/**
	 * Object를 받아서 String 형이 아니거나 NULL이면 defaultValue를 return String 형이면 형 변환해서
	 * 넘겨준다.
	 * 
	 * @param value
	 * @param repStr
	 * @return
	 */
	public  String replaceNull(Object value, String defaultValue) {
		String valueStr = replaceNull(value);
		if (isNull(valueStr)) {
			return defaultValue;
		}
		return valueStr.trim();
	}
	
	public  String nullToZero(String value) {
		if (value == null || value.equals("")) {
			value = "0";
		}
		return value;
	}
	
	/**
	 * 1byte(1048576)->1kb(1024) 
	 *
	 * @param size
	 * @return
	 * @author kyounghun
	 */
	public  String convertKB(long size){
		String kb = (long)(size-(long)(size/1E+3)*1E+3)+"kB";

		return kb;
	}
	
	/**
	 *
	 *
	 * @param doubleObj
	 * @param pattern
	 * @return
	 * @throws Exception
	 * @author kyounghun
	 */
	public  String convert(double doubleObj , String pattern ) 
	throws Exception 
	{
		DecimalFormat df = new DecimalFormat(pattern) ;
		return df.format(doubleObj).toString() ;
	}

	/**
	 * 절삭 
	 *
	 * @param doubleObj
	 * @param position
	 * @return
	 * @throws Exception
	 * @author kyounghun
	 */
	public  String convertFloor(String strObj , int position ) 
	throws Exception 
	{  
		double doubleObj = Double.parseDouble(strObj);
		String temp =  ""  ;
		String tempTwoDigit =  ""  ;
		int inx = 0 ;    
		String patternAttachedZero = "" ;
		String point ="." ;

		if ( position < 0 )
			throw new Exception (" Position 을 0 이상으로 설정 하십시오 " );
		if ( position == 0 )  point="" ; // 소수점이 포함되어 나타나는것 방지 

		temp =  
			convert( doubleObj ,  "#.0000000000000000000000000000000000"  ) ;  
		// BigDecimal 에서 표현하는 소수점 가장 끝자리까지 이므로 
		//반올림되어 오류가 나는 소지를 최소화 한다.
		inx = temp.lastIndexOf( "." ) ;
		tempTwoDigit = temp.substring( 0, (( inx + 1 )  +  position ) ) ;

		for ( int i =0 ; i < position ; i++ ) {
			patternAttachedZero += "0" ;
		}

		return  convert(
				Double.parseDouble( tempTwoDigit )  , 
				"#,##0" + point +  patternAttachedZero 
		) ;
	}
	
	/**
	 * 입력 바이트 길이만큼의 문자를 잘라 리턴해준다.-한글처리 
	 * @param str
	 * @param byteLen
	 * @return
	 */
	public  String cutString(String str,int byteLen){
		int len = 0;
	    String resultStr = "";
	    int rSize = 0;
	    if ( str.getBytes().length > byteLen ) {
	        for (rSize =0 ; rSize < str.length(); rSize++ ) {
	            if ( str.charAt( rSize ) > 0x007F )
	                len += 2;
	            else
	                len++;
	 
	            if ( len > byteLen )
	                break;
	        }
	        resultStr = str.substring( 0, rSize );
	    }
	    else resultStr = str;
	    return resultStr;
	}	
	
	/**
	 * 입력한 스트링이 비밀번호 형식을 만족하는지 bool 형식으로 리턴해준다
	 * 비밀번호 형식 체크 (영대문자, 영소문자, 숫자, 특수문자중 3가지를 조합하여 9자리 이상 20자리 이하로 패스워드 구성인지 확인)
	 * @param inputStr
	 * @return
	 */
	public  boolean checkPwdRegex(String inputStr ,String minLength){
		
		String regex1 = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{"+minLength+",30})"; //숫자,영소,영대
		String regex2 = "((?=.*[A-Z])(?=.*[a-z])(?=.*[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]).{"+minLength+",30})"; //영대,영소,특수문자
		String regex3 = "((?=.*\\d)(?=.*[A-Z])(?=.*[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]).{"+minLength+",30})"; //영대,숫자,특수문자
		String regex4 = "((?=.*\\d)(?=.*[a-z])(?=.*[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]).{"+minLength+",30})"; //영소,숫자,특수문자

		if(Pattern.compile(regex1).matcher(inputStr).matches() || Pattern.compile(regex2).matcher(inputStr).matches()
				||Pattern.compile(regex3).matcher(inputStr).matches()||Pattern.compile(regex4).matcher(inputStr).matches()){			
			return true;
		}
		return false;
	}


	public static String BIN2HEX(byte buf[]) {
		StringBuffer sb = new StringBuffer();
		for (int i=0; i<buf.length; i++)
		{
			sb.append(Integer.toHexString(0x0100+(buf[i]&0x00ff)).substring(1));
		}
		return sb.toString();		
	}
	
    
	public static byte[] HEX2BIN(String hex) {

    	byte[] byteArry = new byte[hex.length()/2];
    	for(int i=0; i< byteArry.length; i++)
    	{
    		byteArry[i]=(byte)Integer.parseInt(hex.substring(2*i, 2*i+2), 16);
 
    	}
    	return byteArry;
    }
	
	
	public static String clobToString(Clob clob) throws SQLException, IOException {

		if (clob == null) {
			return "";
		}
		StringBuffer strOut = new StringBuffer();
		String str = "";
		BufferedReader br = null;

		try {
			br = new BufferedReader(clob.getCharacterStream());
			while ((str = br.readLine()) != null) {
				strOut.append(str);
			}
		} catch (SQLException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		} finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (IOException e) {
				throw e;
			}
		}
		
		return strOut.toString();
	 }
	
	/**
	 * <pre>
	 * 숫자체크
	 * </pre>
	 * <pre>
	 * <b>Parameters:</b>
	 * ▶
	 * ▶
	 * </pre>
	 * <pre>
	 * <b>Returns:</b>
	 * 
	 * </pre>
	 * @author hjkim
	 */
	public static boolean isNumber(String s){
		return s.replaceAll("[+-]?\\d+","").equals("")? true:false;
	} 

}
