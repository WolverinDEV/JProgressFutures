package dev.wolveringer.jfuture;

import lombok.Getter;

public abstract class ProgressingTask<ReturnType> extends ObjectProgressFuture<ReturnType> {
	@Getter 
	private boolean running = false;
	private Thread thandle;
	
	public synchronized void execurteTask(){
		if(running || isDone()) return;
		running = true;
		thandle = new Thread(()->{
			try {
				done(execute());
			}catch (Exception e) {
				error(e);
			}
		});
		thandle.start();
	} 
	
	
	protected abstract ReturnType execute();
}
