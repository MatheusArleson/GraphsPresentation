package br.com.xavier.graphs.representation.util;

import java.nio.charset.Charset;
import java.util.regex.Pattern;

import br.com.xavier.graphs.representation.util.checkers.NullChecker;

public class StringUtil {
	
	//XXX CONSTRUCTOR
	//defeat instantiation
	private StringUtil() {}
	
	public static String toString(byte[] content, Charset encoding) {
		NullChecker.checkNullParameter(content, encoding);
		
		return new String(content, encoding);
	}
	
	public static int getMaximumNumber(String str, String delimiter){
		NullChecker.checkNullParameter(str, delimiter);
		
		str = str.replaceAll(" ", "");
		String[] testArray = str.split(Pattern.compile(delimiter).toString());
		int max = Integer.MIN_VALUE;

		for (int i = 0; i < testArray.length; i++) {
		     if (Integer.parseInt(testArray[i]) > max) {
		         max = Integer.parseInt(testArray[i]);
		     }
		}
		
		return max;
	}
	
	//XXX SINGLE STRING METHODS
	
	public static boolean isNull(String str){
		return str == null;
	}
	
	private static boolean isEmpty(String str){
		return str.isEmpty();
	}
	
	public static boolean isNullOrEmpty(String str){
		return isNull(str) || isEmpty(str);
	}
	
	public static boolean isNotNull(String str){
		return !isNull(str);
	}
	
	public static boolean isNotNullOrEmpty(String str){
		return isNotNull(str) && !isEmpty(str); 
	}

	//XXX MULTIPLE STRINGS METHODS
	
	public static boolean anyNull(String... strings){
		return doCheck(true, false, strings);
	}
	
	public static boolean anyEmpty(String... strings){
		return doCheck(false, true, strings);
	}
	
	public static boolean anyNullOrEmpty(String... strings){
		return doCheck(true, true, strings);
	}
	
	private static boolean doCheck(boolean checkNull, boolean checkEmpty, String... strings){
		if(strings == null){
			return true;
		}
		
		for (String str : strings) {
			if( checkNull && isNull(str) ) { return true; }
			if( checkEmpty && isEmpty(str) ) { return true; }
		}
		
		return false;
	}

}
