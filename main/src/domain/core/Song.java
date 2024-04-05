package domain.core;

import java.util.List;

import domain.facade.ISong;
import util.adts.RegExpMatchable;

/**
 * @author In�s Luz (fc57552), Marta Louren�o (fc58249)
 *
 * Song objects represent songs, where each song has a filename where the song
 * is located (mp3 format), its meta-info, how many times the song was played and
 * its rate.
 *
 */
public class Song implements ISong, RegExpMatchable {
	
	private SongMetaInfo info;
	private String fileName;
	private int timesPlayed;
	private Rate rate;

	/**
	 * Creates a Song object with the given parameters
	 * @param info the meta-info record of the song
	 * @param fileName the filename of the song
	 */
	public Song(SongMetaInfo info, String fileName) {
		this.info = info;
		this.fileName = fileName;
		timesPlayed = 0;
		rate = new Rate();
	}
	
	/**
	 * Increments the amount of times the song was played
	 */
	@Override
	public void incTimesPlayed() {
		timesPlayed++;
	}

	/**
	 * Returns how many times the song was played
	 * 
	 * @return how many times the song was played
	 * @ensure \result >= 0
	 */
	@Override
	public int getTimesPlayed() {
		return timesPlayed;
	}

	/**
	 * Returns the rate of the song
	 * 
	 * @return the rate of the song
	 * @ensures \result != null
	 */
	@Override
	public Rate getRating() {
		return rate;
	}

	/**
	 * Increments the rate of the song
	 */
	@Override
	public void incRating() {
		rate = new Rate(rate.getRate()+1);
	}

	/**
	 * Decrements the rate of the song
	 */
	@Override
	public void decRating() {
		rate = new Rate(rate.getRate()-1);
	}

	/**
	 * Returns the song title of the song
	 * 
	 * @return the song title of the song
	 */
	@Override
	public String getSongTitle() {
		return info.songTitle();
	}

	/**
	 * Returns the genre of the song
	 * 
	 * @return the genre of the song
	 */
	@Override
	public String getGenre() {
		return info.genre();
	}

	/**
	 * Returns the list of the artists of the song
	 * 
	 * @return the list of the artists of the song
	 */
	@Override
	public List<String> getArtists() {
		return info.artists();
	}

	/**
	 * Returns the album of the song
	 * 
	 * @return the album of the song
	 */
	@Override
	public String getAlbum() {
		return info.album();
	}

	/**
	 * Returns the filename of the song
	 * 
	 * @return the filename of the song
	 */
	@Override
	public String getFilename() {
		return fileName;
	}
	
	/**
	 * Checks if two songs are equal
	 * @param s the other song
	 * @return true if the songs are equal, false if otherwise
	 */
	public boolean equalsSong(Song s) {
		return fileName.equals(s.getFilename()) && rate.equalsRate(s.getRating()) && timesPlayed == s.getTimesPlayed()
			&& getSongTitle().equals(s.getSongTitle()) && getGenre().equals(s.getGenre())
			&& getArtists().equals(s.getArtists()) && getAlbum().equals(s.getAlbum());
	}

	/**
	 * Checks if the song matches the regex expression
	 * 
	 * @param regexp the regular expression (regex)
	 * @return true if the song matches the regex, false if otherwise
	 */
	@Override
	public boolean matches(String regexp) {
		return info.matches(regexp);
	}
	
	/**
	 * Returns a textual representation of the playlist
	 * 
	 * @return textual representation of the playlist
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(info.toString());
		sb.append(" --- ");
		sb.append(getRating().getRate());
		sb.append(" -- ");
		sb.append(timesPlayed);
		return sb.toString();
	}

}
