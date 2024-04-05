package domain.playlists;

import domain.core.MusicLibrary;
import domain.core.SongLibraryEvent;
import domain.core.SongRemovedLibraryEvent;

/**
 * @author Inês Luz (fc57552), Marta Lourenço (fc58249)
 *
 * ManualPlaylist is a playlist that is created manually and implements all the features
 * of a normal playlist.
 *
 */
public class ManualPlaylist extends AbsPlaylist {
	
	/**
	 * Creates playlist with the given name and library
	 * 
	 * @param name the playlist's name
	 * @param library the library associated to the playlist
	 */
	public ManualPlaylist(String name, MusicLibrary library) {
		super(name,library);
	}
	
	/**
	 * Reaction to events, namely those emitted by the music library that 
	 * backs up this playlist (can affect the content of the playlist).
	 * In this playlist, the processed events are SongRemovedLibraryEvent.
	 */
	@Override
	public void processEvent(SongLibraryEvent e) {
		if(e instanceof SongRemovedLibraryEvent)
			super.processRemoved(e);
	}

}
