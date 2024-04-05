package domain.core;

/**
 * @author In�s Luz (fc57552), Marta Louren�o (fc58249)
 *
 * A SongRemovedLibraryEvent is a concrete event of SongLibraryEvent (generic event)
 * which is created when a song in the library is removed.
 *
 */
public class SongRemovedLibraryEvent extends SongLibraryEvent{

	/**
	 * Creates a concrete event with a given modified song
	 * 
	 * @param modifedSong - a given modified song
	 */
	public SongRemovedLibraryEvent(Song modifedSong) {
		super(modifedSong);
	}
	
}
