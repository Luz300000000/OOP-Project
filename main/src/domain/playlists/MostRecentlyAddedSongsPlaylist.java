package domain.playlists;

import domain.core.MusicLibrary;
import domain.core.SongAddedLibraryEvent;
import domain.core.SongLibraryEvent;
import domain.core.SongRemovedLibraryEvent;
import domain.facade.ISong;

/**
 * @author Inês Luz (fc57552), Marta Lourenço (fc58249)
 *
 * MostRecentlyAddedSongsPlaylist is a type of SmartPlaylist that contains the most recently
 * added songs in the music library associated to it.
 *
 */
public class MostRecentlyAddedSongsPlaylist extends SmartPlaylist {
	
	private static final int N = 5;
	
	/**
	 * Creates MostRecentlyAddedSongsPlaylist for the given library
	 * 
	 * @param library the given library
	 */
	public MostRecentlyAddedSongsPlaylist(MusicLibrary library) {
		super("Most Recently Added", library);
	}
	
	/**
	 * Adds a song to the end of the playlist, if it
	 * does not exist yet and selects it,
	 * if addition is possible
	 *
	 * @param song the element to be added
	 * @requires song != null 
	 * @return true if the song was added to the playlist, false otherwise
	 * @ensures \result ==> size() == \old(size()) + 1 &&
	 * 						someSelected() && 
	 * 						getIndexSelected() == size() - 1
	 */
	@Override
	public boolean add(ISong song) {
		while(super.size() >= N)
			remove();
		super.addAutomatic(song);
		return size() <= N && super.getIndexSelected() == size() - 1;
	}
	
	/**
	 * Removes the selected element from the playlist, if possible
	 *
	 * @return true if the song was removed, false otherwise
	 * @ensures \return && \old someSelected()  ==> 
	 * 					!someSelected() && size() == \old(size()) - 1
	 * @ensures !\return ==> \old someSelected() == someSelected()
	 * 							&& size() == \old(size()) 
	 */
	@Override
	public boolean remove() {
		super.removeAutomatic(0);
		return !super.someSelected();
	}
	
	/**
	 * Reaction to events, namely those emitted by the music library that 
	 * backs up this playlist (can affect the content of the playlist).
	 * In this playlist, the processed events are SongAddedLibraryEvent
	 * and SongRemovedLibraryEvent.
	 */
	@Override
	public void processEvent(SongLibraryEvent e) {
		if(e instanceof SongAddedLibraryEvent)
			add(e.getModifiedSong());
		else if(e instanceof SongRemovedLibraryEvent)
			super.processRemoved(e);
	}
	
}
