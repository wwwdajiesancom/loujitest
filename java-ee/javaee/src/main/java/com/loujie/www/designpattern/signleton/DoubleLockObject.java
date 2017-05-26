package com.loujie.www.designpattern.signleton;

/**
 * 3.双重检查锁式
 * 
 * @see 1.[私有的构造函数]
 * @see 2.一个[私有的][已经实例化的][静态变量]
 * @see 3.一个[对外的],[获取变量的静态方法]
 * @author loujie
 *
 */
public class DoubleLockObject {

	// 2.一个[私有的][已经实例化的][静态变量]
	private static DoubleLockObject instance = null;

	// 1.[私有的构造函数]
	private DoubleLockObject() {

	}

	// 3.一个[对外的],[获取变量的静态方法]
	public static DoubleLockObject getInstance() {
		if (instance == null) {
			DoubleLockObject dlo = null;
			synchronized (DoubleLockObject.class) {
				dlo = instance;
				if (dlo == null) {
					synchronized (DoubleLockObject.class) {
						if (dlo == null) {
							dlo = new DoubleLockObject();
						}
					}
					instance = dlo;
				}
			}
		}
		return instance;
	}

}
