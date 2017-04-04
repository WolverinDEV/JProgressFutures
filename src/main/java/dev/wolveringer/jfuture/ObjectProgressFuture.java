package dev.wolveringer.jfuture;

public class ObjectProgressFuture<V> extends BasicProgressFuture<V> {
	private V obj = null;
	private boolean done = false;
	private Exception exception;
	
	@Override
	public boolean isDone() {
		return done;
	}
	
	@Override
	public boolean isSuccessful() {
		return isDone() && exception == null;
	}
	
	@Override
	public Exception getException() {
		return exception;
	}
	
	@Override
	public V get() {
		while(!isDone()) try { Thread.sleep(5); } catch (Exception e) { }
		return obj;
	}
	
	protected void done(V obj){
		this.obj = obj;
		this.done = true;
	}
	
	protected void error(Exception ex){
		this.exception = ex;
		this.done = true;
	}
}