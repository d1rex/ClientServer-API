package pw.direx.networking.threads.server;

import java.io.ObjectInputStream;
import java.net.Socket;

import pw.direx.networking.packet.Packet;
import pw.direx.networking.server.Server;
import pw.direx.networking.server.User;

public class ServerThreadRegisterClients extends Thread {
	
	private Socket socket;
	
	public ServerThreadRegisterClients(Socket socket) 
	{
		this.socket = socket;
	}
	
	@Override
	public void run() 
	{
		try
		{
			ObjectInputStream objectInputStream = new ObjectInputStream(this.socket.getInputStream());
			Object object = objectInputStream.readObject();
			
			if(object instanceof Packet)
			{
				Packet loginPacket = (Packet) object;
				if(loginPacket.getIdentifier().equalsIgnoreCase("_INTERNAL_LOGIN"))
				{
					Server.getInstance().getConnectedClients().add(new User(loginPacket.get(1).toString(), socket));
					Server.getInstance().sendPacket(new Packet("_INTERNAL_LOGIN", "Success."), socket);
					System.out.println("(Networking Server) Client '" + socket.getRemoteSocketAddress() + "' connected with id '" + loginPacket.get(1).toString() + "'");
					return;
				}
				else
				{
					new ServerThreadRegisterClients(socket).start();
					return;
				}
			}
		}
		catch (Exception e) {e.printStackTrace();}
		super.run();
	}

}
