package domain.playlists;

import java.util.Iterator;
import domain.core.MusicLibrary;
import domain.core.Rate;
import domain.core.SongLibraryEvent;
import domain.core.SongRatedLibraryEvent;
import domain.core.SongRemovedLibraryEvent;
import domain.facade.ISong;

/**
 * @author Inês Luz (fc57552), Marta Lourenço (fc58249)
 *
 * MostLikedSongsPlaylist is a type of SmartPlaylist that contains the most liked songs 
 * in the music library associated to it.
 *
 */
public class MostLikedSongsPlaylist extends SmartPlaylist {
	
	private static final int N = 5; // a definir
	
	/**
	 * Creates MostLikedSongsPlaylist for the given library
	 * 
	 * @param library the given library
	 */
	public MostLikedSongsPlaylist(MusicLibrary library) {
		super("Most Liked", library);
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
		addAutomatic(song);
		return super.size() <= N && super.getIndexSelected() == size() - 1 && super.getSelected().equals(song);
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
		super.removeAutomatic(getLowestRatedIndex());
		return !super.someSelected();
	}
	
	/**
	 * Gets the index of the lowest rated song in this playlist
	 * 
	 * @return index of the lowest rated song in this playlist
	 */
	private int getLowestRatedIndex() {
		Rate lowestRate = new Rate();
		Iterator<ISong> it = super.iterator();
		int index = 0;
		int i = 0;
		
		while(it.hasNext()) {
			Rate currentRate = it.next().getRating();
			if(currentRate.compareTo(lowestRate) <= 0) {
				lowestRate = currentRate;
				index = i;
			}
			i++;
		}
		
		return index;
	}
	
	/**
	 * Reaction to events, namely those emitted by the music library that 
	 * backs up this playlist (can affect the content of the playlist).
	 * In this playlist, the processed events are SongRatedLibraryEvent
	 * and SongRemovedLibraryEvent.
	 */
	@Override
	public void processEvent(SongLibraryEvent e) {
		if(e instanceof SongRatedLibraryEvent)
			add(e.getModifiedSong());
		else if(e instanceof SongRemovedLibraryEvent)
			super.processRemoved(e);
	}
	
}
