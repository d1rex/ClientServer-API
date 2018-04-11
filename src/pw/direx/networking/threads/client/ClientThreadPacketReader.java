package pw.direx.networking.threads.client;

import java.io.ObjectInputStream;
import java.util.TimerTask;

import pw.direx.networking.client.Client;
import pw.direx.networking.client.PacketHandlerClient;
import pw.direx.networking.packet.Packet;

public class ClientThreadPacketReader extends TimerTask {

	@Override
	public void run()
	{
		try
		{
			ObjectInputStream objectInputStream = new ObjectInputStream(Client.getInstance().getSocket().getInputStream());
			Object object = objectInputStream.readObject();
			
			if(object instanceof Packet)
			{
				PacketHandlerClient packetHandlerClient = Client.getInstance().getPacketHandlers().get(((Packet) object).getIdentifier());
				if(packetHandlerClient != null)
				{
					packetHandlerClient.handlePacket((Packet) object);
					System.out.println("(Networking Client) Packet with identifier '" + ((Packet) object).getIdentifier() + "' parsed on to '" + packetHandlerClient.getClass().getName() + "'");
				}
				else
				{
					System.out.println("(Networking Client) Received a packet with an unknown identifier!?");
				}
			}
			else
			{
				System.out.println("(Networking Client) Received something that is actually NOT a packet!?");
			}
		}
		catch (Exception e)
		{
			System.out.println("(Networking Client) The server probably went offline. Shutting down!");
			Client.getInstance().getThreadManager().cancel();
		}
	}

}
