import static org.junit.Assert.*;

import org.junit.Test;

import dev.wolveringer.jfuture.ProgressingTask;

public class CancelingFutureTaskTest {
	@Test
	public void testCancel(){
		ProgressingTask<Integer> task = new ProgressingTask<Integer>() {
			@Override
			protected Integer execute() {
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) { fail(); }
				return new Integer(200);
			}
		};
		task.execurteTask();
		
		assertTrue(task.cancel());
		assertTrue(task.isCanceled());
		
		assertTrue(task.isDone());
		assertFalse(task.isSuccessful());
		
		assertNotNull(task.getException());
		
		try {
			Thread.sleep(210);
		} catch (InterruptedException e) { fail(); }
	
		assertTrue(task.isCanceled());
		
		assertTrue(task.isDone());
		assertFalse(task.isSuccessful());
		
		assertNotNull(task.getException());
	}
}
