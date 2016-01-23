package iouseph;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

public class App {
	
	public static void main( String[] args) throws ClientProtocolException, IOException {
		
		SoundcloudClient c = new SoundcloudClient();
		c.get_token();
		System.out.println(c.token);
	}

}
