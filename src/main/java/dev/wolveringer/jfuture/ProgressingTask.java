package dev.wolveringer.jfuture;

import lombok.Getter;

public abstract class ProgressingTask<ReturnType> extends ObjectProgressFuture<ReturnType> {
	@Getter 
	private boolean running = false;
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
}
