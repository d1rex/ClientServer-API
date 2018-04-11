package pw.direx.networking.threads.server;

import java.io.IOException;
import java.net.Socket;
import java.util.TimerTask;

import pw.direx.networking.server.Server;

public class ServerThreadListening extends Thread {
	

	@Override
	public void run() 
	{
		System.out.println("(Networking Server) Waiting for someone to connect...");
		while(true)
		{
			try 
			{
				Socket socket = Server.getInstance().getServerSocket().accept();
				socket.setKeepAlive(true);
				new ServerThreadRegisterClients(socket).start();
				System.out.println("(Networking Server) Waiting for someone to connect...");
			} 
			catch (IOException e) 
			{}
		}
	}

}
