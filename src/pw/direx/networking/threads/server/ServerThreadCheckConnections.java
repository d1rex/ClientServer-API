package pw.direx.networking.threads.server;

import pw.direx.networking.server.Server;
import pw.direx.networking.server.User;

public class ServerThreadCheckConnections extends Thread {
	
	@Override
	public void run() 
	{
		super.run();
		while(true)
		{
			try
			{
				for(User user : Server.getInstance().getConnectedClients())
				{
					if(user.getSocket().getInputStream().read() == -1)
					{
						System.out.println("(Networking Server) Client '" + user.getSocket().getRemoteSocketAddress() + "' disconnected.");
						Server.getInstance().getConnectedClients().remove(user);
					}
					Thread.sleep(80);
				}
			}
			catch (Exception e) {}
		}
	}

}
