package com.kong.jvm.bytecode;

/**
 * @author: xiangbinkong
 * @Date: 2021/6/24 13:17
 * @Description: 算数基础类
 */
public class MathBase {

	/**
	 * 整数相加
	 * @param a
	 * @param b
	 * @return
	 */
	public int addition(int a, int b){
		return a + b;
	}

	/**
	 * 区间累加
	 * @param begin
	 * @param end
	 * @return
	 */
	public int intervalAddition(int begin, int end){

		int count = 0;

		if (begin >= end){
			return count;
		}

		for (int i = begin; i < end; i++){
			count++;
		}
		return count;
	}


	/**
	 * 整数相减
	 * @param x
	 * @param y
	 * @return
	 */
	public int subtract(int x, int y){
		return x - y;
	}

	/**
	 * 浮点数相乘
	 * @param a
	 * @param b
	 * @return
	 */
	public float multiply(float a, float b){
		return a * b;
	}

	/**
	 * 浮点数相除
	 * @param x
	 * @param y
	 * @return
	 */
	public double divide(double x, double y){
		if (y == 0){
			return 0D;
		}
		return x / y;
	}


}
