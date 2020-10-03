package com.company.myapp.utilities;

import java.util.concurrent.atomic.AtomicInteger;

public class UniqueIdGenerator {
	static AtomicInteger atomicInteger = new AtomicInteger();
	public static int getUniqueID() {
		return atomicInteger.incrementAndGet();
	}
}
