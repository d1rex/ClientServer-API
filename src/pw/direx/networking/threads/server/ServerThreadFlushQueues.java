package pw.direx.networking.threads.server;

import pw.direx.networking.packet.Packet;
import pw.direx.networking.server.Server;
import pw.direx.networking.server.User;

public class ServerThreadFlushQueues extends Thread {
	
	@Override
	public void run() 
	{
		while(true)
		{
			try
			{
				for(User user : Server.getInstance().getConnectedClients())
				{
					if(!user.getSendQueue().isEmpty())
					{
						Packet packet = user.getSendQueue().get(0);
						Server.getInstance().sendPacket(packet, user.getSocket());
						user.getSendQueue().remove(packet);
						Thread.sleep(120);
					}
				}
			}
			catch (Exception e) {}
		}
	}

}
