package tt;

import edu.uky.cs.nil.tt.Arguments;
import edu.uky.cs.nil.tt.Client;

public class RandomAgentMain {
	
	public static final String NAME = "Tandem Tales Random Client";
	
	public static final String VERSION = "0.9.0";
	
	public static final String AUTHORS = "Stephen G. Ware";
	
	public static final String TITLE = NAME + " v" + VERSION + " by " + AUTHORS;
	
	/** The key used to print the usage instructions */
	public static final String HELP_KEY = "help";
	
	public static final String URL_KEY = "url";
	
	public static final String DEFAULT_URL = "localhost";
	
	public static final String PORT_KEY = "port";
	
	public static final int DEFAULT_PORT = Client.DEFAULT_PORT;
	
	public static final String USAGE = 
		rpad("-" + HELP_KEY) + "Print usage information.\n" +
		rpad("-" + URL_KEY + " <string>") + "Specifies the URL of the server (defaults to \"" + DEFAULT_URL + "\").\n" +
		rpad("-" + PORT_KEY + " <number>") + "Specifies the network port of the server (defaults to " + DEFAULT_PORT + ").";
	
	private static final String rpad(String string) {
		return String.format("%-16s", string);
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println(TITLE);
		// Help
		Arguments arguments = new Arguments(args);
		if(arguments.contains(HELP_KEY)) {
			System.out.println(USAGE);
			return;
		}
		// Network Settings
		String url = arguments.getValue(URL_KEY, DEFAULT_URL);
		if(url == null)
			url = DEFAULT_URL;
		int port;
		if(arguments.contains(PORT_KEY))
			port = Integer.parseInt(arguments.getValue(PORT_KEY, Integer.toString(DEFAULT_PORT)));
		else
			port = DEFAULT_PORT;
		// Agent Factory
		try(RandomAgentFactory factory = new RandomAgentFactory(url, port)) {
			factory.call();
		}
	}
}