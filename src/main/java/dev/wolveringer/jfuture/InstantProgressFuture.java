package dev.wolveringer.jfuture;

public class InstantProgressFuture<V> extends ObjectProgressFuture<V>{
	public InstantProgressFuture(V obj){
		done(obj);
	}
}