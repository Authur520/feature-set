package com.kong.jvm.classload;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author: xiangbinkong
 * @Date: 2021/6/24 14:37
 * @Description:
 */
public class MyClassLoad extends ClassLoader {

	public static void main(String[] args){
		final String resource = MyClassLoad.class.getResource("Hello.xlass").getFile();
		final MyClassLoad myClassLoad = new MyClassLoad();
		final byte[] bytes = myClassLoad.classFile2Bytes(resource);
		try {
			final Class<?> helloClass = myClassLoad.findClass("Hello", bytes);
			final Method hello = helloClass.getMethod("hello");
			hello.setAccessible(true);
			hello.invoke(helloClass.newInstance());
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}


	}

	/**
	 * 根据字节数组转换Class
	 * @param className
	 * @param classByte
	 * @return
	 * @throws ClassNotFoundException
	 */
	protected Class<?> findClass(String className, byte[] classByte) throws ClassNotFoundException {
		return defineClass(className, classByte, 0, classByte.length);
	}

	/**
	 * 根据路径获取文件字节
	 * @param filePath
	 * @return
	 */
	private  byte[] classFile2Bytes(String filePath) {
		byte[] classBytes = null;
		try(FileInputStream fileInputStream = new FileInputStream(filePath);
				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024)) {
			byte[] perByte = new byte[1];
			byte[] restoreBytes = new byte[1];
			byte b255 = (byte) 255;
			int n;
			while ((n = fileInputStream.read(perByte)) != -1) {
				restoreBytes[0] = (byte) (b255 - perByte[0]);
				byteArrayOutputStream.write(restoreBytes, 0, n);
			}
			classBytes = byteArrayOutputStream.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return classBytes;
	}
}
