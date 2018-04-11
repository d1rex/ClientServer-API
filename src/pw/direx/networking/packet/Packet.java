package pw.direx.networking.packet;

import java.util.ArrayList;

@SuppressWarnings("serial")
public class Packet extends ArrayList<Object> {
	
	public Packet(String identifier, Object... objects)
	{
		this.add(identifier);
		for(Object j : objects)
		{
			this.add(j);
		}
	}

	
	public String getIdentifier()
	{
		return this.get(0).toString();
	}
	
}
