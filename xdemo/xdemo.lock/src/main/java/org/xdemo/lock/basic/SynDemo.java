package org.xdemo.lock.basic;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;


public class SynDemo {
	private LinkedList<String> products = new LinkedList<String>();

	private AtomicInteger cnt = new AtomicInteger(0);

	public void produce() throws InterruptedException {
		Thread.sleep(1000);

		synchronized (products) {
			while (products.size() > 9) {
				products.wait();
			}

			String s = String.valueOf("prod-" + cnt.incrementAndGet());
			products.add(s);
			System.out.println(Thread.currentThread().getName() + " produce : "
					+ s + ", and siz become : " + products.size());
			products.notifyAll();
		}

	}

	public void consume() throws InterruptedException {

		// System.out.println(Thread.currentThread().getName() +
		// " consume ggg ");
		Thread.sleep(2000);
		// System.out.println(Thread.currentThread().getName() + " sleep end ");
		synchronized (products) {
			while (products.size() < 1) {
				products.wait();
			}
			String s = products.pop();
			System.out.println(Thread.currentThread().getName() + " consume : "
					+ s + ", and siz is : " + products.size());
			products.notifyAll();
		}
	}

	public static void main(String[] args) {
		final SynDemo demo = new SynDemo();
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
