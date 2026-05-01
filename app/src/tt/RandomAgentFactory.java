package tt;

import edu.uky.cs.nil.tt.ClientFactory;

public class RandomAgentFactory extends ClientFactory {
	
	private final String url;
	private final int port;
	
	public RandomAgentFactory(String url, int port) {
		this.url = url;
		this.port = port;
	}
	
	@Override
	public String toString() {
		return "Random Agent Factory";
	}
	
	@Override
	protected void onStart() throws Exception {
		System.out.println(this + " has started.");
	}
	
	@Override
	protected RandomAgent create() {
		return new RandomAgent(url, port);
	}
	
	@Override
	protected void onClose() throws Exception {
		System.out.println(this + " has been closed.");
	}
	
	@Override
	protected void onStop() throws Exception {
		System.out.println(this + " has stopped.");
	}
}