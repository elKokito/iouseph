package modele;

import org.json.JSONObject;

/**
 * Interface pour chaque client d'API public de l'application. Ceci est pour
 * repondre aux requis defini dans le tp1 1. outils de recherche de musique
 * independant des systemes de streaming. 2. gestion des listes de musique. 3.
 * reproduction des listes de musique. 4. bonus: system de parole de chanson.
 * 
 * @author Marcial Lopez-Ferrada, Youssef Zemmahi, Aymen Zalila
 *
 */
public interface Iapi {

	/**
	 * methode servant a faire une recherche de chason de facon general
	 * 
	 * @param search
	 *            String decrivant la recherche
	 * @return 
	 */
	public JSONObject get_search(String search);

	/**
	 * methode pour recuperer l'information d'un album avec son id
	 * 
	 * @param album_id
	 *            id de l'album
	 */
	public JSONObject get_album(String album_id);

	/**
	 * methode pour recuperer l'information d'un artist avec son id
	 * 
	 * @param artist_id
	 *            id de l'artiste
	 */
	public JSONObject get_artist(String artist_id);

	/**
	 * methode pour recuperer les genres disponible
	 * 
	 */
	public JSONObject get_genres();

	/**
	 * methode pour recupere de l'information sur un genre en particulier
	 * 
	 * @param genre_id
	 *            id du genre rechercher
	 */
	public JSONObject get_genre(String genre_id);
	
	public JSONObject get_playlists(String search);

	/**
	 * methode pour recuperer une playlist
	 * 
	 * @param playlist_id
	 *            id de la playlist rechercher
	 */
	public JSONObject get_playlist(String playlist_id);

	/**
	 * methode pour recupere de l'information sur une chanson en particulier
	 * 
	 * @param track_id
	 *            id de la chanson rechercher
	 */
	public JSONObject get_track(String track_id);

	/**
	 * methode pour recuperer les informations personnelles de l'utilisateur
	 * 
	 */
	public JSONObject get_personnal_info();

}
