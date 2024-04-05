package domain.playlists;

import java.util.Iterator;

import domain.core.MusicLibrary;
import util.adts.ArrayQListWithSelection;
import util.adts.QListWithSelection;

/**
 * @author Inês Luz (fc57552), Marta Lourenço (fc58249)
 *
 * PlaylistList represents a list of Playlists associated with a certain MusicLibrary.
 * A Playlist can be selected in this list.
 *
 */
public class PlaylistList implements QListWithSelection<Playlist> {
	
	private QListWithSelection<Playlist> playlists;
	
	/**
	 * Creates the list of playlists associated with the given library
	 * 
	 * @param library the given library
	 */
	public PlaylistList(MusicLibrary library) {
		playlists = new ArrayQListWithSelection<>();
		Playlist mostLiked = new MostLikedSongsPlaylist(library);
		Playlist mostRecentlyAdded = new MostRecentlyAddedSongsPlaylist(library);
		playlists.add(mostLiked);
		playlists.add(mostRecentlyAdded);
	}
	
	/**
	 * If there's a playlist selected and this playlist has a song selected,
	 * play that song. If there is a song currently playing in the selected playlist,
	 * interrupt it.
	 */
	public void play() {
		if (playlists.getSelected().someSelected()) {
			if (playlists.getSelected().isPlaying())
				playlists.getSelected().stop();
			playlists.getSelected().play();
		}
	}
	
	/**
	 * Checks if there is any playlist that's currently playing a song.
	 * 
	 * @return true if there is any playlist that's currently playing a song, false otherwise
	 */
	public boolean isPlaying() {
		boolean isPlaying = false;
		
		for (Playlist playlist : playlists) 
			isPlaying = playlist.isPlaying();
		
		return isPlaying;
	}
	
	/**
	 * Stops any song that's currently playing in the playlists.
	 * 
	 * @requires isPlaying()
	 */
	void stop() {
		for (Playlist playlist : playlists)
			if (playlist.isPlaying())
				playlist.stop();
	}
	
	/**
	 * Returns the number of playlists in the list
	 * 
	 * @return the number of playlists in the list
	 * @ensures \return >= 0
	 */
	@Override
	public int size() {
		return playlists.size();
	}

	/**
	 * Returns the playlist at position i
	 * 
	 * @param i the position of the playlist to return
	 * @requires 0 <= i < size()
	 * @return the playlist at position i
	 */
	@Override
	public Playlist get(int i) {
		return playlists.get(i);
	}
	
	/**
	 * Returns an iterator over the playlists in the list
	 * 
	 * @return an iterator over the playlist in this list in proper sequence.
	 */
	@Override
	public Iterator<Playlist> iterator() {
		return playlists.iterator();
	}
	
	/**
	 * Selects the playlist at position i.
	 * 
	 * @param i the position of the playlist to select
	 * @requires 0 <= i < size()
	 */
	@Override
	public void select(int i) {
		playlists.select(i);
	}

	/**
	 * Adds a playlist at the end of the list and selects it.
	 * 
	 * @param e the element to be added and selected
	 */
	@Override
	public void add(Playlist e) {
		playlists.add(e);
	}
	
	/**
	 * Returns true if some playlist is selected.
	 * 
	 * @return true if some playlist is selected, false otherwise
	 */
	@Override
	public boolean someSelected() {
		return playlists.someSelected();
	}

	/**
	 * Gets the index of the selected playlist.
	 * 
	 * @return the index of the selected playlist
	 */
	@Override
	public int getIndexSelected() {
		return playlists.getIndexSelected();
	}

	/**
	 * Selects the next playlist, if any. Otherwise, no playlist is selected.
	*/
	@Override
	public void next() {
		if (getIndexSelected() < size() - 1)
			playlists.next();
		else
			playlists.select(-1);
	}

	/**
	 * Selects the previous playlist, if any. Otherwise, no playlist is selected.
	*/
	@Override
	public void previous() {
		if (getIndexSelected() > 0)
			playlists.previous();
		else
			playlists.select(-1);
	}

	/**
	 * Removes the selected playlist from the list, if possible.
	 *
	 * @return true if the playlist was removed, false otherwise
	 */
	@Override
	public void remove() {
		if (someSelected())
			playlists.remove();
	}

	/**
	 * Gets the selected playlist.
	 * 
	 * @requires someSelected()
	 * @return the selected playlist
	 */
	@Override
	public Playlist getSelected() {
		return playlists.getSelected();
	}
	
	/**
	 * Returns a textual representation of the playlist
	 * 
	 * @return textual representation of the playlist
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		for(Playlist p : playlists) {
			sb.append(p.toString());
			sb.append("\n");
		}
		
		return sb.toString();
	}
	
}
