package org.xdemo.lock.basic;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockDemo {
	private LinkedList<String> products = new LinkedList<String>();

	private AtomicInteger cnt = new AtomicInteger(0);

	final Lock lock = new ReentrantLock();

	final Condition notFull = lock.newCondition();

	final Condition notEmpty = lock.newCondition();

	public void produce() throws InterruptedException {

		Thread.sleep(1000);
		lock.lock();
		while (products.size() > 9) {
			notFull.await();
		}
		String s = String.valueOf("prod-" + cnt.incrementAndGet());
		products.add(s);
		System.out.println(Thread.currentThread().getName() + " produce : " + s
				+ ", and size become : " + products.size());

		notEmpty.signalAll();

		lock.unlock();

	}

	public void consume() throws InterruptedException {

		Thread.sleep(2000);
		lock.lock();
		while (products.size() < 1) {
			notEmpty.await();
		}
		String s = products.pop();
		System.out.println(Thread.currentThread().getName() + " consume : " + s
				+ ", and size is : " + products.size() + " : "
				+ System.currentTimeMillis());
		notFull.signalAll();
		lock.unlock();
	}

	public static void main(String[] args) {
		final LockDemo demo = new LockDemo();
		for (int i = 0; i < 5; i++) {
			new Thread(new Runnable() {

				public void run() {
					while (true) {
						try {
							demo.produce();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}).start();

		}

		for (int i = 0; i < 5; i++) {
			new Thread(new Runnable() {
				public void run() {
					while (true) {
						try {
							demo.consume();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}

					}
				}
			}).start();

		}

	}
}
