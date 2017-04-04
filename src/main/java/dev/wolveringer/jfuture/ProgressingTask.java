package dev.wolveringer.jfuture;

import java.util.concurrent.CancellationException;
import java.util.concurrent.Future;

import lombok.Getter;

public abstract class ProgressingTask<ReturnType> extends ObjectProgressFuture<ReturnType> {
	@Getter 
	private boolean running = false;
	@Getter
	private boolean canceled = false;
	private Thread thandle;
	
	public final synchronized ProgressingTask<ReturnType> execurteTask(){
		if(running || isDone()) return this;
		running = true;
		thandle = new Thread(()->{
			try {
				done(execute());
			}catch (Exception e) {
				error(e);
			}finally {
				running = false;
			}
		});
		thandle.start();
		return this;
	} 
	
	protected abstract ReturnType execute();
	
	public ProgressingTask<ReturnType> waitFor() throws InterruptedException{
		while(running) Thread.sleep(10);
		return this;
	}
	
	public boolean cancel(){
		if(thandle == null) return false;
		canceled = true;
		thandle.stop();
		error(new CancellationException("Execution canceled!"));
		return true;
	}
	
	@Override
	public Future<ReturnType> asJavaFuture() {
		return new ProgressFutureJavaImpl<ReturnType>(this){
			@Override
			public boolean cancel(boolean mayInterruptIfRunning) {
				return ((ProgressingTask<ReturnType>) handle).cancel();
			}
			@Override
			public boolean isCancelled() {
				return ((ProgressingTask<ReturnType>) handle).isCanceled();
			}
		};
	}
}
