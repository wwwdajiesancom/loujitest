package com.loujie.www.designpattern.signleton;

/**
 * 静态内部类模式(最好)
 * 
 * @see 1.私有构造函数
 * @see 2.私有的静态内部类,里面定义一个私有的静态对象
 * @see 3.对外的静态方法
 * @author loujie
 *
 */
public class InnerClassObject {

	// 2.私有的静态内部类,里面定义一个私有的静态对象
	private static class SubInnerClassObject {
		// 2.1静态的变量
		private static final InnerClassObject instance = new InnerClassObject();
	}

	// 1.私有构造函数
	private InnerClassObject() {
	}

	// 3.对外的方法
	public static InnerClassObject getInstance() {
		return SubInnerClassObject.instance;
	}

}
