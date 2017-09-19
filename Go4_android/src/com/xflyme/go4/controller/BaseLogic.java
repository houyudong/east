package com.xflyme.go4.controller;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @Description:逻辑处理基类，主要 用于添加/移除观察者
 * @author:wennan
 * @see:
 * @since:
 * @copyright © ciyun.cn
 * @Date:2014年8月18日
 * @param <T>
 */
public class BaseLogic<T>{
	
	/**
	 * 观察者列表
	 */
	private  List<T> observers= new ArrayList<T>();
	
	/**
	 * 
	 * @Description:添加观察者
	 * @author:wennan
	 * @see:
	 * @since:
	 * @copyright © ciyun.cn
	 * @Date:2014年8月18日
	 * @param <T>
	 */
	public synchronized void addObserver(T observer) {
		if(!observers.contains(observer)) {
			observers.add(observer);
		}
	}
	/**
	 * 移除观察者
	 * @Description:
	 * @param observer
	 * @see: 
	 * @since: 
	 * @author: wennan
	 * @date2014年8月18日
	 */
	public synchronized  void removeObserver(T observer) {
		observers.remove(observer);
	}
	/**
	 * 清空观察者
	 * @Description:
	 * @see: 
	 * @since: 
	 * @author: wennan
	 * @date:2014年8月18日
	 */
	public synchronized void clearObservers(){
		this.observers.clear();
	}
	/**
	 * 返回只读的所有的监听器列表
	 * @Description:
	 * @return
	 * @see: 
	 * @since: 
	 * @author: wennan
	 * @date:2014年8月18日
	 */
	public synchronized List<T> getObservers(){
		List<T> ls = new ArrayList<T>(observers.size());
		
		ls.addAll(observers);
		
		return ls;
	}
	
}
