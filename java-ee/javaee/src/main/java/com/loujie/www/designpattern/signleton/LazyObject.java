package com.loujie.www.designpattern.signleton;

/**
 * 2.懒汉式单例
 * 
 * @see 1.私有的构造函数
 * @see 2.一个[私有的][静态变量],并且[没有实例化]
 * @see 3.[对外]提供一个[唯一的][静态方法][获取这个对象],需要是[线程安全的]方法
 * 
 * @author loujie
 *
 */
public class LazyObject {

	// 2.一个[私有的][静态变量],并且[没有实例化]
	private static LazyObject lObject = null;

	// 1.私有的构造函数
	private LazyObject() {
	}

	// 3.[对外]提供一个[唯一的][静态方法][获取这个对象],需要是[线程安全的]方法
	public synchronized static LazyObject getInstance() {
		if (lObject == null) {
			lObject = new LazyObject();
		}
		return lObject;
	}

}
