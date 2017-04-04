import static org.junit.Assert.*;

import org.junit.Test;

import dev.wolveringer.jfuture.InstantProgressFuture;
import dev.wolveringer.jfuture.ObjectProgressFuture;

public class ProgressFutureTest {

	@Test
	public void testObjectGet() {
		ObjectProgressFuture<Integer> future = new InstantProgressFuture<Integer>(new Integer(1));
		
		assertTrue(future.isDone());
		assertTrue(future.isSuccessful());
		assertNotNull(future.get());
		assertEquals(future.get().intValue(), 1);
	}
	
}
