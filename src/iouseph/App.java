package iouseph;

import modele.SpotifyClient;

public class App {
	
	public static void main( String[] args) throws Exception {
		
	/*	SoundcloudClient c = new SoundcloudClient();
		// username and password in args
		c.retreive_token(args[0], args[1]);
		c.get_personnal_info();
		c.get_user_info("2020");
		c.get_user_info("20201");
		c.resolve("https://soundcloud.com/matas/hobnotropic");
		c.get_tracks();
		//c.search("trap");*/

		
		/*Deezer d = new Deezer();
		
		d.get_user_info("25315581");
		d.get_search("muse");*/
		
		SpotifyClient SpClient = new SpotifyClient();
		//SpClient.retreive_token();
		SpClient.get_personnal_info();
		// valid types : album, artist, playlist, and track
		SpClient.get_user_info("youssef.zemmahi");
	}

}
