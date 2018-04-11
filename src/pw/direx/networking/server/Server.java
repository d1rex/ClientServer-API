package pw.direx.networking.server;

import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;

import pw.direx.networking.packet.Packet;
import pw.direx.networking.threads.server.ServerThreadCheckConnections;
import pw.direx.networking.threads.server.ServerThreadFlushQueues;
import pw.direx.networking.threads.server.ServerThreadListening;
import pw.direx.networking.threads.server.ServerThreadPacketReader;

public class Server {
	
	private static Server instance;
	private ServerSocket serverSocket;
	private Timer threadManager;
	private ArrayList<User> connectedClients = new ArrayList<>();
	private HashMap<String, PacketHandlerServer> packetHandlers = new HashMap<>();

	public Server(Short port)
	{
		instance = this;
		
		try
		{
			this.serverSocket = new ServerSocket(port);
			this.threadManager = new Timer(false);
			
			new ServerThreadPacketReader().start();
			new ServerThreadCheckConnections().start();
			new ServerThreadFlushQueues().start();
			new ServerThreadListening().start();
		}
		catch (Exception e) 
		{
			System.out.println("(Networking Server) Unable to listen on port " + port);
		}
	}
	
	public void enqueuePacket(Packet packet, Socket socket)
	{
		for(User user : this.connectedClients)
		{
			if(user.getSocket() == socket)
			{
				user.addToSendQueue(packet);
			}
		}
	}
	
	public HashMap<String, PacketHandlerServer> getPacketHandlers()
	{
		return packetHandlers;
	}
	
	public void registerPacketHandler(String identifier, PacketHandlerServer packetHandlerServer)
	{
		this.packetHandlers.put(identifier, packetHandlerServer);
		System.out.println("(Networking Client) Packets with identifier '" + identifier + "' will now be handled by '" + packetHandlerServer.getClass().getName() + "'");
	}
	
	public void sendPacket(Packet packet, Socket socket)
	{
		new Thread(() -> {
			try
			{
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
				objectOutputStream.writeObject(packet);
				objectOutputStream.flush();
				System.out.println("(Networking Server) Packet '" + packet.getIdentifier() + "' sent to '" + socket.getRemoteSocketAddress() + "'");
			}
			catch (Exception e) {}
		}).start();
	}
	
	public static Server getInstance()
	{
		return instance;
	}
	
	public ServerSocket getServerSocket()
	{
		return serverSocket;
	}
	
	public Timer getThreadManager()
	{
		return threadManager;
	}
	
	public ArrayList<User> getConnectedClients() 
	{
		return connectedClients;
	}
	
}
