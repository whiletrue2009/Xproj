package org.xdemo.lock.rentrant;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class LockCachedDemo {
	String data = "";
	volatile boolean cacheValid = false;
	ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

	private AtomicInteger cnt = new AtomicInteger(0);
	
	private AtomicInteger chgCnt = new AtomicInteger(0);

	public void use(Object data) {
		try {
			Thread.sleep(Cnst.read_time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		cnt.incrementAndGet();
//		System.out.println(Thread.currentThread().getName() + " use data for "
//				+ cnt.incrementAndGet());
	}

	public int getChgCnt( ) {
		return chgCnt.get();
	}
	
	
	public int getCnt() {
		return cnt.get();
	}

	public void changeTag(boolean bool) {
		this.cacheValid = bool;
	}

	public boolean getTag() {
		return this.cacheValid;
	}

	public void processCachedData(String tdata) {
		rwl.readLock().lock();
		if (!cacheValid) {
			// Must release read lock before acquiring write lock
			rwl.readLock().unlock();
			rwl.writeLock().lock();
			// Recheck state because another thread might have acquired
			// write lock and changed state before we did.
			if (!cacheValid) {
				try {
					Thread.sleep(Cnst.write_time);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				data = tdata;
				chgCnt.incrementAndGet();
				cacheValid = true;
			}
			// Downgrade by acquiring read lock before releasing write lock
			rwl.readLock().lock();
			rwl.writeLock().unlock(); // Unlock write, still hold read
		}

		use(data);
		rwl.readLock().unlock();
	}

	public static void main(String[] args) {

		final LockCachedDemo cachedData2 = new LockCachedDemo();
		new Thread(new Runnable() {

			public void run() {
				while (cachedData2.getCnt() < Cnst.last_cnt) {
					try {
						Thread.sleep(20);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					cachedData2.changeTag(!cachedData2.getTag());
				}
			}
		}).start();

		final long a = System.currentTimeMillis();

		for (int i = 0; i < 20; i++) {
			new Thread(new Runnable() {

				public void run() {
					while (cachedData2.getCnt() < Cnst.last_cnt) {
						cachedData2.processCachedData("123");
					}
					System.out.println(System.currentTimeMillis() - a);
					System.out.println("end " + cachedData2.getChgCnt());
				}
			}).start();
		}

	}
}
