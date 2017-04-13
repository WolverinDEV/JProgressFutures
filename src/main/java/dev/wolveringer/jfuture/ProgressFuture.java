package dev.wolveringer.jfuture;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import lombok.RequiredArgsConstructor;

public interface ProgressFuture<V> {
	@RequiredArgsConstructor
	static class ProgressFutureJavaImpl<V> implements Future<V> {
		protected final ProgressFuture<V> handle;
		
		@Override
		public boolean cancel(boolean mayInterruptIfRunning) { return false; }

		@Override
		public boolean isCancelled() { return false; }

		@Override
		public boolean isDone() {
			return handle.isDone();
		}

		@Override
		public V get() throws InterruptedException, ExecutionException {
			V ret = handle.get();
			if(!handle.isSuccessful()) throw new ExecutionException(handle.getException());
			return ret;
		}

		@Override
		public V get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
			V ret = handle.get((int) timeout, unit);
			if(!handle.isSuccessful()) throw new ExecutionException(handle.getException());
			return ret;
		}
	}
	
	public static interface AsyncCallback<T> {
		public void done(T obj, Exception e);
	}
	public static interface TimedAsyncCallback<T> {
		public void done(T obj, Exception e, long timeDiff);
	}
	
	public V get();
	public V getOr(V obj);
	public void get(AsyncCallback<V> task);
	public void get(TimedAsyncCallback<V> task);
	
	public V get(int timeout, TimeUnit unit) throws TimeoutException;
	public V getOr(int timeout, TimeUnit unit, V obj);
	public void get(AsyncCallback<V> task, int timeout, TimeUnit unit);
	public void get(TimedAsyncCallback<V> task, int timeout, TimeUnit unit);
	
	public boolean isDone();
	
	public boolean isSuccessful();
	public Exception getException();
	
	public default Future<V> asJavaFuture(){
		return new ProgressFutureJavaImpl<V>(this);
	}
	
	public default void throwErrors() throws Exception {
		throwErrors(new Exception());
	}
	
	public default void throwErrors(Exception _default) throws Exception {
		if(!isDone()) return;
		if(isSuccessful()) return;
		
		Exception ex = getException();
		if(ex == null) throw _default;
		else throw ex;
	}
	
}