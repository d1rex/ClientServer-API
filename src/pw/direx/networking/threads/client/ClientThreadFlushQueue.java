package pw.direx.networking.threads.client;

import java.io.ObjectOutputStream;
import java.util.TimerTask;

import pw.direx.networking.client.Client;
import pw.direx.networking.packet.Packet;

public class ClientThreadFlushQueue extends TimerTask {

	@Override
	public void run()
	{
		try
		{
			
			for(Packet packet : Client.getInstance().getSendQueue())
			{
				try
				{
					ObjectOutputStream objectOutputStream = new ObjectOutputStream(Client.getInstance().getSocket().getOutputStream());
					objectOutputStream.writeObject(packet);
					objectOutputStream.flush();
					System.out.println("(Networking Client) Packet with identifier '" + packet.getIdentifier() + "' sent.");
					try
					{
						Client.getInstance().getSendQueue().remove(0);
					}
					catch (Exception e) {}
				}
				catch (Exception e)
				{
					System.out.println("(Networking Client) Unable to flush packet queue. Did the server go offline? Shutting down.");
					e.printStackTrace();
					Client.getInstance().getThreadManager().cancel();
				}
				Thread.sleep(40);
			}
		}
		catch (Exception e)
		{
			
		}
	}

}
