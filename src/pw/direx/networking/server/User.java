package pw.direx.networking.server;

import java.net.Socket;
import java.util.ArrayList;
import java.util.UUID;

import pw.direx.networking.packet.Packet;

public class User {
	
	private String userID;
	private Socket socket;
	private ArrayList<Packet> sendQueue = new ArrayList<>();
	
	public User(String userID, Socket socket) 
	{
		this.socket = socket;
		this.userID = userID;
	}
	
	public void addToSendQueue(Packet packet)
	{
		this.sendQueue.add(packet);
		System.out.println("(Networking Client) Packet '" + packet.getIdentifier() + "' added to queue for socket '" + this.socket.getRemoteSocketAddress() + "'"); 
	}
	
	public Socket getSocket()
	{
		return socket;
	}
	
	public ArrayList<Packet> getSendQueue() {
		return sendQueue;
	}
	
	public String getUserID()
	{
		return userID;
	}

}
