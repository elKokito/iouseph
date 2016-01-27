package iouseph;

public interface Iapi {
	
	public void retreive_token();
	public void get_personnal_info();
	public void get_search(String search);// : track album artist playlist
	public void get_user_info(String user_id); // > albums artists charts flow folders followings followers history notifications permissions options personal_songs playlists podcasts radios recommendations tracks
	public void get_album(String album_id);// > comments fans tracks
	public void get_artist(String artist_id);// > top albums comments fans related radio playlists
	public void get_chart();// > tracks albums artists playlists
	public void get_comment(String comment_id);
	public void get_editorials();
	public void get_editorial(String editorial_id);// > selection charts releases
	//public void get_episode(String episode_id);// > bookmark
	public void get_genres();
	public void get_genre(String genre_id);// > artists podcasts radios
	public void get_options();
	public void get_playlist(String playlist_id);// > seen comments fans tracks
	public void get_podcast(String podcast_id);// > episodes
	public void get_radios();// > genres top lists 
	public void get_radio(String radio_id);// > tracks
	public void get_track(String track_id);
	//public void get_();
	
}
