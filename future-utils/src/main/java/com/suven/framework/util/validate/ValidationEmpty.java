package com.suven.framework.util.validate;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 
 * <pre>
 *  Tyrest
 *  File: ValidationUtil.java
 * 
 *  Tyrest, Inc.
 *  Copyright (C): 2016
 * 
 *  Description:
 *  TODO
 * 
 *  Notes:
 *  $Id: ValidationUtil.java  Tyrest\magintrursh $ 
 * 
 *  Revision History
 *  &lt;Date&gt;,			&lt;Who&gt;,			&lt;What&gt;
 *  2016年11月1日		magintrursh		Initial.
 *
 * </pre>
 */

public class ValidationEmpty {

	/**
	 * Check whether the Object has value or not.
	 * 
	 * @param aObj
	 * @return if the obj is empty
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isEmpty(Object aObj) {
		if (aObj instanceof String) {
			return isEmpty((String) aObj);
		} else if (aObj instanceof Long) {
			return isEmpty((Long) aObj);
		} else if (aObj instanceof java.util.Date) {
			return isEmpty((java.util.Date) aObj);
		} else if (aObj instanceof Collection) {
			return isEmpty((Collection) aObj);
		} else if (aObj instanceof Map) {
			return isEmpty((Map) aObj);
		} else if (aObj != null && aObj.getClass().isArray()) {
			return isEmptyArray(aObj);
		} else {
			return isNull(aObj);
		}
	}

	/**
	 * Check whether the String has value or not.
	 * 
	 * @param aStr
	 * @return if the string is empty
	 */
	public static boolean isEmpty(String aStr) {
		if (aStr == null || aStr.trim().isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Check whether the Long has value or not.
	 * 
	 * @param aLong
	 * @return if the Long is null
	 */
	public static boolean isEmpty(Long aLong) {
		if (aLong == null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Check whether a Collection object is empty.
	 * 
	 * @param c:
	 *            a java.util.Collection object
	 * @return if the Map is empty
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isEmpty(Collection c) {
		if (c == null || c.size() == 0) {
			return true;
		}
		return false;
	}

	/**
	 * Check whether a Map object is empty.
	 * 
	 * @param m:
	 *            a java.util.Map object
	 * @return if the Map is empty
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isEmpty(Map m) {
		if (m == null || m.size() == 0) {
			return true;
		}
		return false;
	}

	/**
	 * Check whether the Date has value or not.
	 * 
	 * @param aDate
	 * @return if the date is null
	 */
	public static boolean isEmpty(java.util.Date aDate) {
		if (aDate == null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Trim the specified String.
	 * 
	 * @param aStr
	 * @return the result string,"" return if string is NULL
	 */
	public static String trim(String aStr) {
		if (aStr == null) {
			return "";
		} else {
			return aStr.trim();
		}
	}

	/**
	 * Check whether the array is empty or not.
	 *
	 * @param array
	 * @return
	 */
	private static boolean isEmptyArray(Object array) {
		int length = 0;
		if (array instanceof int[]) {
			length = ((int[]) array).length;
		} else if (array instanceof byte[]) {
			length = ((byte[]) array).length;
		} else if (array instanceof short[]) {
			length = ((short[]) array).length;
		} else if (array instanceof char[]) {
			length = ((char[]) array).length;
		} else if (array instanceof float[]) {
			length = ((float[]) array).length;
		} else if (array instanceof double[]) {
			length = ((double[]) array).length;
		} else if (array instanceof long[]) {
			length = ((long[]) array).length;
		} else if (array instanceof boolean[]) {
			length = ((boolean[]) array).length;
		} else {
			length = ((Object[]) array).length;
		}
		if (length == 0) {
			return true;
		}
		return false;
	}

	/**
	 * Check whether the Object is null or not.
	 * 
	 * @param oStr
	 * @return if the object is NULL
	 */
	public static boolean isNull(Object oStr) {
		if (oStr == null) {
			return true;
		} else {
			return false;
		}
	}

	// ----------------- Add by Anderson Begin:---------------------
	/**
	 * Validation method for time. The support time format is "hh:mm" or
	 * "hh:mm:ss".
	 * 
	 * @param text
	 *            Input time value to be validated.
	 * @return Return true if validation ok, otherwise return false.
	 */
	public static boolean isTime(String text) {
		// Pattern p =
		// Pattern.compile("((([0]|[1])?\\d)|([2][0-3]))([\\:](([0-5]\\d)|[0](\\d))){1,2}");
		Pattern p = Pattern.compile("(([0][1-9])|([1][0-2]))([\\:](([0-5]\\d)|[0](\\d))){1,2}");
		return p.matcher(text).matches();
	}

	/**
	 * Validation method for Currency format.
	 * 
	 * @param text
	 *            Input currency value to be validated.
	 * @return Return true if validation ok, otherwise return false.
	 */
	public static boolean isCurrency(String text) {
		Pattern p = Pattern.compile("([+]|[-])?(\\s)*(\\d){1,}([.](\\d){1,2})?");
		return p.matcher(text).matches();
	}

	/**
	 * Validation method for Number Format (decimal is also included)
	 */
	public static boolean isNumber(String text) {
		if (isEmpty(text)) {
			return false;
		}
		// ----BUG ID 5473 ,Rocket.He, begin---
		try {
			Double.parseDouble(text);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
		// ----BUG ID 5473 ,Rocket.He, end---
	}

	/**
	 * Validation method for Number Format (decimal is excluded)
	 * 
	 * @param text
	 * @return Return true if validation ok, otherwise return false.
	 */
	public static boolean isNumeric(String text) {
		if (isEmpty(text)) {
			return false;
		}

		Pattern p = Pattern.compile("[0-9]*");
		return p.matcher(text.trim()).matches();
	}



	private static int getPosition(String[] patternArr, String regex) {
		for (int i = 0; i < patternArr.length; i++) {
			if (Pattern.compile(regex).matcher(patternArr[i]).matches()) {
				return i;
			}
		}
		throw new IllegalStateException("Cannot get the position by the RegExp");
	}

	private static char getSeparator(String pattern) {
		for (int i = 0; i < pattern.length(); i++) {
			char c = pattern.charAt(i);
			if (c < 65 || c > 90) {
				return c;
			}
		}
		throw new IllegalArgumentException("Inputted date pattern is fatal wrong.");
	}



	public static boolean equals(String str1, String str2) {
		return str1 != null ? str1.equals(str2) : str2 == null;
	}

	/**
	 * Check if object1 equals object2.
	 * 
	 * @param obj1
	 *            object1
	 * @param obj2
	 *            object2
	 * @return
	 */
	public static boolean equals(Object obj1, Object obj2) {
		boolean result;
		if (obj1 != null) {
			result = obj1.equals(obj2);
		} else {
			result = (obj2 == null);
		}
		return result;
	}

	/**
	 * Check if trim(str1) equals trim(str2). Null is parsed to ""(empty
	 * string).
	 * 
	 * @param str1
	 *            str1
	 * @param str2
	 *            str2
	 * @return
	 */
	public static boolean equalsWithTrim(String str1, String str2) {
		return ValidationEmpty.trim(str1).equals(ValidationEmpty.trim(str2));
	}

	/**
	 * Check if trim(str1) equalsIgnoreCase trim(str2). Null is parsed to
	 * ""(empty string).
	 * 
	 * @param str1
	 *            str1
	 * @param str2
	 *            str2
	 * @return
	 */
	public static boolean equalsIgnoreCaseWithTrim(String str1, String str2) {
		return ValidationEmpty.trim(str1).equalsIgnoreCase(ValidationEmpty.trim(str2));
	}

	public static boolean equalsIgnoreCase(String str1, String str2) {
		return str1 != null ? str1.equalsIgnoreCase(str2) : str2 == null;
	}

	public static boolean equals(Long l1, Long l2) {
		return l1 != null ? l1.equals(l2) : l2 == null;
	}




	/**
	 * Comparison Operator Array
	 */
	static String[] operators = new String[] { "<", ">", "=", "<=", ">=", "!=", "<>" };

	/**
	 * is Comparison Operator
	 * 
	 * @param str
	 *            String
	 * @return
	 */
	public static boolean isComparisonOperator(String string) {
		List<String> opList = Arrays.asList(operators);
		if (string != null) {
			return opList.contains(string.trim());
		}
		return false;
	}

}
