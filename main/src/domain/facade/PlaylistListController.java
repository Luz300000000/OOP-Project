package domain.facade;

import java.util.Iterator;

import domain.core.MusicLibrary;
import domain.playlists.ManualPlaylist;
import domain.playlists.Playlist;
import domain.playlists.PlaylistList;

/**
 * @author Inês Luz (fc57552), Marta Lourenço (fc58249)
 *
 * A controller that manages the interactions with the PlaylistList associated with a given library
 *
 */
public class PlaylistListController {
	
	private PlaylistList playlists;
	private MusicLibrary library;
	
	/**
	 * Creates the controller for the playlists associated with a given library
	 * 
	 * @param playlists the given playlists
	 * @param library the given library
	 */
	public PlaylistListController(PlaylistList playlists, MusicLibrary library) {
		this.playlists = playlists;
		this.library = library;
	}
	
	/**
	 * Creates a new playlist and adds it to the playlists
	 * 
	 * @param name name of the new playlist
	 */
	public void createPlaylist(String name) {
		playlists.add(new ManualPlaylist(name,library));
		playlists.select(playlists.size()-1);
	}
	
	/**
	 * Selects the playlist at position i in the playlists list
	 * 
	 * @param i the index of the playlist to be selected in the playlists list
	 */
	public void selectPlaylist(int i) {
		playlists.select(i);
	}
	
	/**
	 * Checks if there is a playlist selected in the playlists list
	 * 
	 * @return true if there is a playlist selected in the playlists list, false otherwise
	 */
	public boolean somePlaylistSelected() {
		return playlists.someSelected();
	}
	
	/**
	 * Gets the selected playlist in the playlists list
	 * 
	 * @requires somePlaylistSelected()
	 * @return
	 */
	public Playlist getSelectedPlaylist() {
		return playlists.getSelected();
	}
	
	/**
	 * Removes the selected playlist in the playlists list, if any
	 */
	public void removePlaylist() {
		if(somePlaylistSelected())
			playlists.remove();
	}
	
	/**
	 * Returns an iterator over the playlists in the list
	 * 
	 * @return an iterator over the playlist in this list in proper sequence.
	 */
	public Iterator<Playlist> iterator() {
		return playlists.iterator();
	}
	
	/**
	 * Gets the number of songs in the library
	 * 
	 * @return the number of songs in the library
	 */
	public int numberOfSongs() {
		return playlists.getSelected().size();
	}
	
	/**
	 * If there is a song selected in the library, add it to the selected playlist
	 * 
	 * @requires somePlaylistSelected()
	 */
	public void addSong() {
		if(library.someSelected())
			playlists.getSelected().add(library.getSelected());
	}
	
	/**
	 * 
	 * @param i
	 * @requires somePlaylistSelected() && 0<=i<getSelectedPlaylist().numberOfSongs()
	 */
	public void selectSong(int i) {
		playlists.getSelected().select(i);
	}
	
	/**
	 * Checks if there is a playlist selected in the playlists list: if there is, also checks
	 * if there is a selected song in that playlist
	 * 
	 * @return true if both of the conditions are satisfied, false otherwise
	 */
	public boolean someSongSelected() {
		boolean selected = somePlaylistSelected();
		if(selected)
			selected &= getSelectedPlaylist().someSelected();
		return selected;
	}
	
	/**
	 * Removes the selected song in the selected playlist in the playlists list
	 * 
	 * @requires someSongSelected()
	 */
	public void removeSelectedSong() {
		getSelectedPlaylist().remove();
	}
	
	/**
	 * Selects the next song in the selected playlist in the playlists list
	 * 
	 * @requires somePlaylistSelected()
	 */
	public void nextSong() {
		getSelectedPlaylist().next();
	}
	
	/**
	 * Selects the previous song in the selected playlist in the playlists list
	 * 
	 * @requires somePlaylistSelected()
	 */
	public void previousSong() {
		getSelectedPlaylist().previous();
	}
	
	/**
	 * Plays the selected song in the selected playlist in the playlists list, if any.
	 * If the song is played until the end, the number of times it has been played is
	 * increased and plays the next songs in the playlist (in order)
	 */
	public void play() {
		if(someSongSelected()) 
			playlists.play();
		
	}
	
	/**
	 * Stops the song that is currently playing in the selected playlist in the playlists list
	 */
	public void stop() {
		Playlist selected = getSelectedPlaylist();
		if(selected.isPlaying())
			selected.stop();
	}
	
	/**
	 * Returns a textual representation of the playlist
	 * 
	 * @return textual representation of the playlist
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("***** PLAYLISTS *****\n");
		sb.append(playlists.toString());
		
		return sb.toString();
	}
	
}
