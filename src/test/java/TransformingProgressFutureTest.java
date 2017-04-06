import static org.junit.Assert.*;

import org.junit.Test;

import dev.wolveringer.jfuture.InstantProgressFuture;
import dev.wolveringer.jfuture.ProgressFuture;
import dev.wolveringer.jfuture.TransformProgressFuture;

public class TransformingProgressFutureTest {

	@Test
	public void testTransform() {
		int value = 22;
		ProgressFuture<Byte> future = new TransformProgressFuture<Integer, Byte>(new InstantProgressFuture<Integer>(new Integer(value))) {
			@Override
			protected Byte transform(Integer in) {
				return in.byteValue();
			}
		};
		assertTrue(future.isSuccessful());
		assertTrue(value == future.get().intValue());
	}
	
	@Test
	public void testFunctionTransform(){
		int value = 22;
		ProgressFuture<Byte> future = new TransformProgressFuture<Integer, Byte>(new InstantProgressFuture<Integer>(new Integer(value)), Integer::byteValue);
		assertTrue(future.isSuccessful());
		assertTrue(value == future.get().intValue());
	}

}
