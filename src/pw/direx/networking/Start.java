package pw.direx.networking;

import pw.direx.networking.client.Client;
import pw.direx.networking.server.Server;

public class Start {
	
	public static void main(String[] args) throws Exception
	{
		//new Server((short) 1330);
		new Client("localhost", (short) 1330);
	}
	
}
