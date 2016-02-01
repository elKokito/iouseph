package modele;

public class Track {
	private int id_;
	private String title_;
	private String externalUrl_;
	private float duration_;
	private String artist_;
	private String album_;
	private String image_;
	private String source_;

	/**
	 * @param id
	 * @param title
	 * @param url
	 * @param duration
	 * @param artist
	 * @param album
	 */
	Track(int id, String title, String url, float duration, String artist, String album, String image, String source) {
		id_ = id;
		title_ = title;
		externalUrl_ = url;
		duration_ = duration;
		artist_ = artist;
		album_ = album;
		image_ = image;
		source_ = source;
	}

	public Track() {
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
	public String getTitle() {
		return title_;
	}

	/**
	 * @param name
	 */
	public void setTitle(String title) {
		this.title_ = title;
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

	public String getImage_() {
		return image_;
	}

	public void setImage_(String image_) {
		this.image_ = image_;
	}

	public String getSource_() {
		return source_;
	}

	public void setSource_(String source_) {
		this.source_ = source_;
	}

	@Override
	public String toString() {
		return "Track [title_=" + title_ + ", externalUrl_=" + externalUrl_
				+ ", duration_=" + duration_ + ", artist_=" + artist_
				+ ", album_=" + album_ + ", image_=" + image_ + ", source_="
				+ source_ + "]";
	}
	
	

}
