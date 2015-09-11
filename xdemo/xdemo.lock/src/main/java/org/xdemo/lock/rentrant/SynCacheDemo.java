package org.xdemo.lock.rentrant;

import java.util.concurrent.atomic.AtomicInteger;

public class SynCacheDemo {
	String data;

	volatile boolean cacheValid = false;

	Object lock = new Object();
	
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
	
	
	public int getCnt( ) {
		return cnt.get();
	}
	

	public void changeTag(boolean bool) {
		this.cacheValid = bool;
	}

	public boolean getTag() {
		return this.cacheValid;
	}
	
	
	public void processCachedData(String tData) {
		synchronized (lock) {
			if (!cacheValid) {
				try {
					Thread.sleep(Cnst.write_time);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				chgCnt.incrementAndGet();
				data = tData;
				cacheValid = true;
			}
			use(data);
		}
	}

//	public synchronized void processCachedData2(String tData) {
//		if (!cacheValid) {
//			try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//			data = tData;
//			cacheValid = true;
//		}
//		use(data);
//	}
	
	
	
	public static void main(String[] args) {
		final SynCacheDemo cachedData1 = new SynCacheDemo();
		new Thread(new Runnable() {

			public void run() {
				while (cachedData1.getCnt() < Cnst.last_cnt) {
					try {
						Thread.sleep(20);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					cachedData1.changeTag(!cachedData1.getTag());
				}
			}
		}).start();
		
		final long a = System.currentTimeMillis();
		
		
		for(int i=0;i<20;i++){
			new Thread(new Runnable() {

				public void run() {
					while(cachedData1.getCnt() < Cnst.last_cnt){
						cachedData1.processCachedData("123");
					}
					System.out.println(System.currentTimeMillis() - a);
					System.out.println("end " + cachedData1.getChgCnt());
				}
			}).start();
		}
		
	}

}
