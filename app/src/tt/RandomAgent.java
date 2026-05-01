package tt;

import java.util.List;
import java.util.Random;

import edu.uky.cs.nil.tt.Client;
import edu.uky.cs.nil.tt.Role;
import edu.uky.cs.nil.tt.io.Connect;
import edu.uky.cs.nil.tt.world.Ending;
import edu.uky.cs.nil.tt.world.State;
import edu.uky.cs.nil.tt.world.Status;
import edu.uky.cs.nil.tt.world.Turn;
import edu.uky.cs.nil.tt.world.World;

public class RandomAgent extends Client {
	
	private static final double PASS_PROBABILITY = 0.3;
	private static final double SUCCEED_PROBABILITY = 0.9;
	private static final long DELAY = 3000;
	private static int nextID = 0;
	public final int id = nextID++;
	private final Random random = new Random();
	
	public RandomAgent(String url, int port) {
		super("random", null, null, null, url, port);
	}
	
	@Override
	public String toString() {
		return "Random Agent " + id;
	}
	
	@Override
	protected void onConnect(Connect connect) {
		System.out.println(this + " has connected to the server.");
	}
	
	@Override
	protected void onStart(World world, Role role, State state) {
		System.out.println(this + " has started its session as the " + role + " in world \"" + world.getName() + "\".");
	}
	
	@Override
	protected int onChoice(Status status) {
		int choice;
		// If my partner has proposed a move, accept or reject it.
		if(isProposal(status)) {
			if(random.nextDouble() < SUCCEED_PROBABILITY)
				choice = 0; // succeed
			else
				choice = 1; // fail
		}
		// If this is a normal turn and the agent has at least one choice...
		else if(status.getChoices().size() > 1) {
			// Decide if I will act or pass.
			if(random.nextDouble() < PASS_PROBABILITY)
				choice = status.getChoices().size() - 1; // pass
			else
				choice = random.nextInt(status.getChoices().size() - 1); // act
		}
		// If the agent has no choices, pass.
		else
			choice = 0;
		// Wait a random amount of time before sending the choice.
		try {
			Thread.sleep(random.nextLong(DELAY));
		}
		catch(InterruptedException exception) {
			// If interrupted, return choice immediately.
		}
		// Return the choice.
		System.out.println(this + " chooses: \"" + status.getChoices().get(choice).getDescription() + "\".");
		return choice;
	}
	
	private static final boolean isProposal(Status status) {
		List<Turn> choices = status.getChoices();
		return choices.size() == 2 &&
			choices.get(0).type == Turn.Type.SUCCEED &&
			choices.get(1).type == Turn.Type.FAIL;
	}
	
	@Override
	protected void onEnd(Ending ending) throws Exception {
		System.out.println(this + " has ended its session: \"" + ending.getDescription() + "\".");
	}
	
	@Override
	protected void onStop(String message) throws Exception {
		System.out.println(this + " has stopped" + (message == null ? "" : " because \"" + message + "\"") + ".");
	}
	
	@Override
	protected void onDisconnect() throws Exception {
		System.out.println(this + " has disconnected.");
	}
}