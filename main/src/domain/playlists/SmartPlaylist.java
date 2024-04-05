package domain.playlists;

import domain.core.MusicLibrary;
import domain.facade.ISong;

/**
 * @author Inês Luz (fc57552), Marta Lourenço (fc58249)
 *
 * SmartPlaylists represent Playlists that do not allowed manual addition/removal of songs.
 * This type of Playlist also does not allow changes of order through moveUpSelected method.
 * 
 * However, the rest of the features of a Playlist are present in SmartPlaylist.
 *
 */
public abstract class SmartPlaylist extends AbsPlaylist {
	
	/**
	 * Creates SmartPlaylist with the given name and library
	 * 
	 * @param name
	 * @param library
	 */
	protected SmartPlaylist(String name, MusicLibrary library) {
		super(name, library);
	}
	
	/**
	 * Automatically adds a song to the SmartPlaylist
	 * 
	 * @param song the song to be added
	 */
	protected void addAutomatic(ISong song) {
		super.add(song);
	}
	
	/**
	 * Automatically removes the song in the given index
	 * 
	 * @param index the index of the song to be removed
	 */
	protected void removeAutomatic(int index) {
		int ogSelected = super.getIndexSelected();
		if(ogSelected != index) {
			super.select(index);
			super.remove();
			super.select(ogSelected);
		} else
			super.remove();
	}
	
	/**
	 * Overrides the moveUpSelected method, which is not allowed in SmartPlaylist
	 * 
	 * @return false
	 */
	@Override
	public boolean moveUpSelected(int i) {
		return false;
	}
	
}
