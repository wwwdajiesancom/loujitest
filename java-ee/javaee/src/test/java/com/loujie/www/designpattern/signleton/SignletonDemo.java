package com.loujie.www.designpattern.signleton;

import org.junit.Test;

public class SignletonDemo {

	@Test
	public void test1() throws InterruptedException {
		for (int i = 0; i < 100; i++) {
			final int j = i;
			Thread thread1 = new Thread(new Runnable() {
				@Override
				public void run() {
					System.err.println(EnumObject.INSTANCE.getProperty("name") + 1 + j);
				}
			});
			Thread thread2 = new Thread(new Runnable() {
				@Override
				public void run() {
					System.err.println(EnumObject.INSTANCE.getProperty("name") + 2 + j);
				}
			});
			Thread thread3 = new Thread(new Runnable() {
				@Override
				public void run() {
					System.err.println(EnumObject.INSTANCE.getProperty("name") + 3 + j);
				}
			});
			Thread thread4 = new Thread(new Runnable() {
				@Override
				public void run() {
					System.err.println(EnumObject.INSTANCE.getProperty("name") + 4 + j);
				}
			});
			Thread thread5 = new Thread(new Runnable() {
				@Override
				public void run() {
					System.err.println(EnumObject.INSTANCE.getProperty("name") + 5 + j);
				}
			});
			Thread thread6 = new Thread(new Runnable() {
				@Override
				public void run() {
					System.err.println(EnumObject.INSTANCE.getProperty("name") + 6 + j);
				}
			});
			Thread thread7 = new Thread(new Runnable() {
				@Override
				public void run() {
					System.err.println(EnumObject.INSTANCE.getProperty("name") + 7 + j);
				}
			});
			Thread thread8 = new Thread(new Runnable() {
				@Override
				public void run() {
					System.err.println(EnumObject.INSTANCE.getProperty("name") + 8 + j);
				}
			});
			Thread thread9 = new Thread(new Runnable() {
				@Override
				public void run() {
					System.err.println(EnumObject.INSTANCE.getProperty("name") + 9 + j);
				}
			});

			thread1.start();
			thread2.start();
			thread3.start();
			thread4.start();
			thread5.start();
			thread6.start();
			thread7.start();
			thread8.start();
			thread9.start();
		}

		Thread.sleep(2000);
	}

}
