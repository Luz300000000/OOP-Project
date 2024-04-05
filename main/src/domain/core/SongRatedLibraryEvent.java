package domain.core;

/**
 * @author In�s Luz (fc57552), Marta Louren�o (fc58249)
 *
 * A SongRatedLibraryEvent is a concrete event of SongLibraryEvent (generic event)
 * which is created when a song in the library is rated.
 *
 */
public class SongRatedLibraryEvent extends SongLibraryEvent{
	
	/**
	 * Creates a concrete event with a given modified song
	 * 
	 * @param modifedSong - a given modified song
	 */
	public SongRatedLibraryEvent(Song modifedSong) {
		super(modifedSong);
	}
	
}
