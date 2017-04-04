import static org.junit.Assert.*;

import org.junit.Test;

import dev.wolveringer.jfuture.ProgressFuture;
import dev.wolveringer.jfuture.ProgressingTask;

public class FailingFutureTest {
	private static final RuntimeException TO_THROW = new RuntimeException();
	
	@Test
	public void testAsyncFailException() throws InterruptedException{
		ProgressFuture<Integer> future = new ProgressingTask<Integer>() {
			@Override
			protected Integer execute() {
				throw TO_THROW;
			}
		}.execurteTask().waitFor();
		
		assertTrue(future.isDone());
		assertTrue(future.isSuccessful() == false);
		assertEquals(TO_THROW, future.getException());
	}
	
	@Test
	public void testSyncFailException() {
	}
}
