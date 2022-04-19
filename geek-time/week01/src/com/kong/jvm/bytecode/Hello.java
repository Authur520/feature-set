package com.kong.jvm.bytecode;

/**
 * @author: xiangbinkong
 * @Date: 2021/6/24 13:15
 * @Description:
 */
public class Hello {

	public static void main(String[] args) {
		MathBase mathBase = new MathBase();
		mathBase.addition(1 , 2);
		mathBase.subtract(3, 2);
		mathBase.subtract(5,6);
		mathBase.divide(2,1);
		mathBase.intervalAddition(2, 19);

	}

}
