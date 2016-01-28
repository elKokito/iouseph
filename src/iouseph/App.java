package iouseph;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

public class App {
	
	public static void main( String[] args) throws ClientProtocolException, IOException {
		
		SoundcloudClient c = new SoundcloudClient();
		// username and password in args
		c.retreive_token(args[1], args[2]);
		//c.get_personnal_info();
		//c.get_user_info("2020");
		//c.get_user_info("20201");
		//c.resolve("https://soundcloud.com/matas/hobnotropic");
		//c.get_tracks();
		//c.search("trap");

		
		Deezer d = new Deezer();
		
		d.get_user_info("25315581");
		d.get_search("muse");
	}

}
