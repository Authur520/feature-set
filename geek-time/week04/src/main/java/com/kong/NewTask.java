package com.kong;

import java.util.concurrent.Callable;

/**
 * @author: xiangbinkong
 * @Date: 2021/7/18 21:58
 * @Description:
 */
public class NewTask implements Callable<Integer> {
	@Override
	public Integer call() throws Exception {
		return getZero();
	}

	private Integer getZero() {
		return 0;
	}
}
