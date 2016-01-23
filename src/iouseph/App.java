package iouseph;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

public class App {
	
	public static void main( String[] args) throws ClientProtocolException, IOException {
		
		SoundcloudClient c = new SoundcloudClient();
		c.retreive_token();
		c.get_personnal_info();
		c.get_user_info("2020");
	}

}
