package domain.facade;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

import domain.core.MusicLibrary;
import domain.core.Song;
import domain.core.SongMetaInfo;

/**
 * @author Inês Luz (fc57552), Marta Lourenço (fc58249)
 *
 * A controller that manages the interactions with MusicLibrary
 *
 */
public class MusicLibraryController {
	
	private MusicLibrary library;

	/**
	 * Creates the controller for the given library
	 * 
	 * @param library the given library
	 */
	public MusicLibraryController(MusicLibrary library) {
		this.library = library;
	}
	
	/**
	 * Gets the number of songs in the library
	 * 
	 * @return the number of songs in the library
	 */
	public int numberOfSongs() {
		return library.size();
	}

	/**
	 * Adds a song to the library from its file name
	 * 
	 * @param filename the file name of the song to be added
	 */
	public void addSong(String filename){
		try {
			Mp3File mp3 = new Mp3File(filename);
			if(mp3.hasId3v2Tag())
				library.add(createSongId3v2(mp3.getId3v2Tag(),filename));
			else if(mp3.hasId3v1Tag())
				library.add(createSongId3v1(mp3.getId3v1Tag(),filename));
		} catch (UnsupportedTagException | InvalidDataException | IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Selects the song at position i in the library
	 * 
	 * @param i the index of the song to be selected in the library
	 */
	public void selectSong(int i) {
		library.select(i);
	}
	
	/**
	 * Gets the selected song in the library, if any
	 * 
	 * @return the selected song in the library
	 */
	public Optional<ISong> getSelectedSong(){
		ISong selected = null;
		if(library.someSelected())
			selected = library.getSelected();
		return Optional.ofNullable(selected);
	}
	
	/**
	 * Removes the selected song in the library, if any
	 */
	public void removeSelectedSong() {
		library.remove();
	}
	
	/**
	 * Plays the selected song in the library, if any. If the song is played until the end,
	 * the number of times it has been played is increased.
	 */
	public void play() {
		if(getSelectedSong().isPresent()) 
			library.play();
	}
	
	/**
	 * Stops the song that is currently playing
	 */
	public void stop() {
		if(getSelectedSong().isPresent() && library.isPlaying())
			library.stop();
	}
	
	/**
	 * Increases the rating of the song that is currently selected in the library
	 */
	public void incRateSelected() {
		Optional<ISong> s = getSelectedSong();
		if(s.isPresent())
			library.incRateSelected();
	}
	
	/**
	 * Decreases the rating of the song that is currently selected in the library
	 */
	public void decRateSelected() {
		Optional<ISong> s = getSelectedSong();
		if(s.isPresent())
			library.decRateSelected();
	}
	
	/**
	 * Returns an iterable structure with all songs from the library that match the given regex
	 * 
	 * @requires size() > 0
	 * @param reexp a given regular expression (regex)
	 * @return a list with all songs from the library that match the given regex
	 */
	public Iterable<ISong> getMatches(String reexp){
		return library.getMatches(reexp);
	}
	
	/**
	 * Returns an iterable structure with all songs from the library
	 * 
	 * @requires size() > 0
	 * @return a list with all songs from the library
	 */
	public Iterable<ISong> getSongs() {
		return library.getSongs();
	}
	
	/**
	 * Creates a song given an ID3v2 tag and its file name
	 * 
	 * @param id the given ID3v2 tag
	 * @param fileName the given file name
	 * @return the song created with the given ID3v2 tag and file name
	 */
	private Song createSongId3v2(ID3v2 id, String fileName) {
		String title = id.getTitle();
		String genre = id.getGenreDescription();
		List<String> artists = new ArrayList<>();
		artists.add(id.getArtist());
		String album = id.getAlbum();
		return new Song(new SongMetaInfo(title,genre,artists,album),fileName);
	}
	
	/**
	 * Creates a song given an ID3v1 tag and its file name
	 * 
	 * @param id the given ID3v1 tag
	 * @param fileName the given file name
	 * @return the song created with the given ID3v1 tag and file name
	 */
	private Song createSongId3v1(ID3v1 id, String fileName) {
		String title = id.getTitle();
		String genre = id.getGenreDescription();
		List<String> artists = new ArrayList<>();
		artists.add(id.getArtist());
		String album = id.getAlbum();
		return new Song(new SongMetaInfo(title,genre,artists,album),fileName);
	}
	
	/**
	 * Returns a textual representation of the playlist
	 * 
	 * @return textual representation of the playlist
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("*****MUSIC LIBRARY****\n");
		sb.append(library.toString());
		
		return sb.toString();
	}
	
}
