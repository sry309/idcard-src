package com.wyyw.IDCard.Core;

public class IDCard {

	// 号码计算公式
	static int[] coe = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };



	/**
	 * 字符串转int数组
	 * 
	 * @param str
	 *            算出来的身份证号
	 * @return str转成int的数组
	 */
	private static int[] String2Ints(String str) {
		int[] arr = new int[str.length()];
		for (int i = 0; i < str.length(); i++) {
			arr[i] = Character.getNumericValue(str.charAt(i));
		}
		return arr;
	}
	
	// 根据身份证前17位获取最后一位验证码
	public static String getV(String idcardnum){
		return getLastNum(getSum(String2Ints(idcardnum)));
	}
	
	public static int getSum(int IDnums[]) {
		int sum = 0;
		for (int i = 0; i < 17; i++) {
			sum += IDnums[i] * coe[i];
		}
		return sum;
	}

	public static String getLastNum(int num) {
		num = num % 11;
		switch (num) {
		case 0:
			num = 1;
			break;
		case 1:
			num = 0;
			break;
		case 2:
			num = 10;
			break;
		case 3:
			num = 9;
			break;
		case 4:
			num = 8;
			break;
		case 5:
			num = 7;
			break;
		case 6:
			num = 6;
			break;
		case 7:
			num = 5;
			break;
		case 8:
			num = 4;
			break;
		case 9:
			num = 3;
			break;
		case 10:
			num = 2;
			break;
		}
		
		String str = String.valueOf(num);
		if(str.equals("10")){
			// 这里要想想是返回10 还是返回X
		}
		
		
		return str;
	}

}
