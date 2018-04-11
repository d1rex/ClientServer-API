package pw.direx.networking.client;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.UUID;

import pw.direx.networking.packet.Packet;
import pw.direx.networking.threads.client.ClientThreadFlushQueue;
import pw.direx.networking.threads.client.ClientThreadPacketReader;

public class Client {
	
	private static Client instance;
	private Socket socket;
	private Timer threadManager;
	private UUID clientId = UUID.randomUUID();
	
	private ArrayList<Packet> sendQueue = new ArrayList<>();
	private HashMap<String, PacketHandlerClient> packetHandlers;
	
	public Client(String remoteAdress, Short port)
	{
		instance = this;
		try
		{
			this.threadManager = new Timer("THREAD_MANAGER", false);
			this.socket = new Socket(remoteAdress, port);
			this.socket.setKeepAlive(true);
			this.sendQueue.add(new Packet("_INTERNAL_LOGIN", this.clientId.toString()));
			this.packetHandlers = new HashMap<>();
			
			this.registerPacketHandler("_INTERNAL_LOGIN", new PacketHandlerClient() {
				
				@Override
				public void handlePacket(Packet packet) 
				{
					System.out.println("(Networking Client) Login confirmation received.");
				}
			});
			
			Runtime.getRuntime().addShutdownHook(new Thread(() -> {
				this.threadManager.cancel();
			}));
			
			Thread.sleep(2000);
			this.threadManager.schedule(new ClientThreadFlushQueue(), 20, 500);
			this.threadManager.schedule(new ClientThreadPacketReader(), 1000, 125);
		}
		catch (Exception e)
		{
			System.out.println("(Networking Client) Unable to connect to server at '" + remoteAdress + "'");
			e.printStackTrace();
		}
	}
	
	public void registerPacketHandler(String identifier, PacketHandlerClient packetHandlerClient)
	{
		this.packetHandlers.put(identifier, packetHandlerClient);
		System.out.println("(Networking Client) Packets with identifier '" + identifier + "' will now be handled by '" + packetHandlerClient.getClass().getName() + "'");
	}
	
	public void sendPacket(Packet packet)
	{
		this.sendQueue.add(packet);
	}
	
	public static Client getInstance()
	{
		return instance;
	}
	
	public ArrayList<Packet> getSendQueue()
	{
		return sendQueue;
	}
	
	public Socket getSocket()
	{
		return socket;
	}
	
	public UUID getClientID() 
	{
		return clientId;
	}
	
	public HashMap<String, PacketHandlerClient> getPacketHandlers() 
	{
		return packetHandlers;
	}
	
	public Timer getThreadManager()
	{
		return threadManager;
	}

}
