# Tandem Tales Random Agent (Java) #

This is is an automated [Tandem Tales](http://tandemtales.net) agent written in
Java that makes random choices according to a simple algorithm:
- If the agent is responding to a proposed action, it has a 90% chance to choose
  for the action to succeed.
- If the agent is taking a normal turn, it has a 30% chance to pass control to
  its partner. If it does not pass, it chooses a non-pass action uniformly at
  random.

## Usage ##

This agent is written in [Java](https://www.java.com) but runs in
[Docker](http://www.docker.com/). A Docker container is like lightweight virtual
machine that includes an operating system along with the Java Development Kit,
the code for this agent, and the libraries needed to run Tandem Tales. This
means you can use this agent as long as you have Docker installed, and you don't
need to install Java or any Tandem Tales libraries on your system.

Assuming you have both [Git](https://git-scm.com) and
[Docker](http://www.docker.com/) installed and on your path, you can download
and build this agent like so:
```
git clone https://github.com/sgware/tt-random-agent-java
cd tt-random-agent-java
docker build -t tt-random-agent-java .
```

To run this agent, assuming a
[Tandem Tales server](https://github.com/sgware/tt-server) is already running
locally that it can connect to:
```
docker run --env-file .env tt-random-agent-java
```

This container also includes everything you need to run and test this agent
locally:
```
docker run --env-file .env -it tt-random-agent-java test
```

When you run the above command, the container will start a Tandem Tales server,
run this agent and connect it to the server, and then start a
[test client](https://github.com/sgware/tt-test-client) which connects to the
server as a player. The test client will print out simple state updates and
action choices to the terminal, allowing you to play a session with this agent.

You can also explore this container from the Linux Bash shell:
```
docker run --env-file .env -it tt-random-agent-java bash
```

## Make this Agent Your Own ##

The main purpose of this project is to provide an example of how to create a
Tandem Tales agent using Java and package it as a Docker image. We encourage you
to fork this project and change the
[source files](https://github.com/sgware/tt-random-agent-java/tree/main/app/src/tt).

You should probably rename the
[main file](https://github.com/sgware/tt-random-agent-java/tree/main/app/src/tt/RandomAgentMain.java)
(the class that contains the `public static void main(String[])` method) from
`RandomAgentMain.java` to something like `MyAgentMain.java`. When you rename
this file, be sure to also change the `main` variable in the
[environment variables files](https://github.com/sgware/tt-random-agent-java/tree/main/.env)
like this:
```
main=tt.MyAgentMain
```

You'll probably also want to rename the
[client file](https://github.com/sgware/tt-random-agent-java/tree/main/app/src/tt/RandomAgent.java)
(the class that extends `Client`) to something like `MyAgent.java`. When you do
that, be sure to update the `create` method in the
[factory file](https://github.com/sgware/tt-random-agent-java/tree/main/app/src/tt/RandomAgentFactory.java)
(the class that extends `ClientFactory`) like this:
```
protected MyAgent create() {
	return new MyAgent(url, port);
}
```

To change the name the agent uses when it connects to the server from `random`
to something like `myagent`, change the string that is passed to the
[Client constructor](https://sgware.github.io/tt-server/edu/uky/cs/nil/tt/Client.html#%3Cinit%3E(java.lang.String,java.lang.String,edu.uky.cs.nil.tt.Role,java.lang.String,java.lang.String,int))
in the
[client file](https://github.com/sgware/tt-random-agent-java/tree/main/app/src/tt/RandomAgent.java)
like this:
```
private MyAgent(String url, int port) {
	super("myagent", null, null, null, url, port);
}
```

This random agent can play in any story world as either role. If your agent is
designed only for a specific story world or role, you can also set those options
in the constructor like this:
```
// This agent only plays in the 'tutorial' world in the game master role.
private MyAgent(String url, int port) {
	super("myagent", "tutorial", Role.GAME_MASTER, null, url, port);
}
```

When you change the agent's name, be sure to also change the `name` variable in
the
[environment variables files](https://github.com/sgware/tt-random-agent-java/tree/main/.env).
If your agent only plays in a specific world, be sure to change `world` too so
that world will run when you test the agent:
```
name=myagent
world=tutorial
```

If you are going to send your agent to someone else to run on their server, they
will probably change the `password` environment variable to something private to
their server. This allows them to ensure that only your agent can connect using
the name `myagent`. You generally do not need to change the `password` variable
yourself. It's also possible to hard-code a password in the client constructor,
but this is *not recommended*, because it will make it difficult for others to
run your agent with a password private to their server.

If your agent is going to use the external API, then you might want to set the
`apikey` environment variable also. 

> [!WARNING]
> Do not commit or distribute your environment variable file if you set the
> `apikey`!

If you are going to send your agent to someone else to run on their server, they
should provide their own API key. You typically *should not* distribute an agent
to others using your own API key.

## License ##

The Java source code, Dockerfile, shell scripts, and Tandem Tales software used
in this agent were developed by Stephen G. Ware PhD, Associate Professor of
Computer Science at the University of Kentucky. Development was sponsored in
part by a grant from the US National Science Foundation, #2145153.

Tandem Tales is released under the
[GNU General Public License version 3.0](https://www.gnu.org/licenses/gpl-3.0.en.html)
(GPL 3). This means you are free to share and modify this software, even for
commercial purposes, as long as you give credit to the original creators and you
also release your modifications under the GPL 3 license. See the license file
for details. The University of Kentucky retains all right not specifically
granted.

This agent uses other software via [Docker](http://docker.com), including the
[Ubuntu Linux](http://ubuntu.com/) operating system,
[Eclipse Temurin](http://adoptium.net/temurin) JDK, and applications like
[GNU Screen](http://www.gnu.org/software/screen/) and
[Git](http://git-scm.com/). Each of these projects has its own license terms.