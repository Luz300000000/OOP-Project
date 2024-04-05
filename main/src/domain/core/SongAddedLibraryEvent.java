package domain.core;

/**
 * @author In�s Luz (fc57552), Marta Louren�o (fc58249)
 *
 * A SongAddedLibraryEvent is a concrete event of SongLibraryEvent (generic event)
 * which is created when a song is added to the library.
 *
 */
public class SongAddedLibraryEvent extends SongLibraryEvent{

	/**
	 * Creates a concrete event with a given modified song
	 * 
	 * @param modifedSong - a given modified song
	 */
	public SongAddedLibraryEvent(Song modifedSong) {
		super(modifedSong);
	}
	
}
