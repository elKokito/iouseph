package modele;

public class Track {
	private int id_;
	private String name_;
	private String externalUrl_;
	private float duration_;
	private String artist_;
	private String album_;

	/**
	 * @param id
	 * @param name
	 * @param url
	 * @param duration
	 * @param artist
	 * @param album
	 */
	Track(int id, String name, String url, float duration, String artist, String album) {
		id_ = id;
		name_ = name;
		externalUrl_ = url;
		duration_ = duration;
		artist_ = artist;
		album_ = album;
	}

	/**
	 * @return
	 */
	public int getId() {
		return id_;
	}

	/**
	 * @param id
	 */
	public void setId(int id) {
		this.id_ = id;
	}

	/**
	 * @return
	 */
	public String getName() {
		return name_;
	}

	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name_ = name;
	}

	/**
	 * @return
	 */
	public String getExternalUrl() {
		return externalUrl_;
	}

	/**
	 * @param externalUrl
	 */
	public void setExternalUrl_(String externalUrl) {
		this.externalUrl_ = externalUrl;
	}

	/**
	 * @return
	 */
	public float getDuration() {
		return duration_;
	}

	/**
	 * @param duration
	 */
	public void setDuration(float duration) {
		this.duration_ = duration;
	}

	/**
	 * @return
	 */
	public String getArtist() {
		return artist_;
	}

	/**
	 * @param artist
	 */
	public void setArtist(String artist) {
		this.artist_ = artist;
	}

	/**
	 * @return
	 */
	public String getAlbum() {
		return album_;
	}

	/**
	 * @param album
	 */
	public void setAlbum_(String album) {
		this.album_ = album;
	}

}
