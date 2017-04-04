import static org.junit.Assert.*;

import org.junit.Test;

import dev.wolveringer.jfuture.ProgressingTask;

public class AsyncProgressFutureTest {

	@Test
	public void test() {
		ProgressingTask<Integer> future = new ProgressingTask<Integer>() {
			@Override
			protected Integer execute() {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					fail();
				}
				return new Integer(11);
			}
		};
		
		assertTrue(future.isDone() == false);
		assertTrue(future.isSuccessful() == false);
		
		future.execurteTask();
		
		assertTrue(future.isRunning());
		assertTrue(future.isDone() == false);
		assertTrue(future.isSuccessful() == false);
		
		try {
			Thread.sleep(120);
		} catch (InterruptedException e) {
			fail();
		}
		
		assertTrue(future.isRunning() == false);
		assertTrue(future.isDone());
		assertTrue(future.isSuccessful());
		
		assertEquals(future.get().intValue(), 11);
	}
}
