package domain.core;
import util.observer.Event;

/**
 * @author In�s Luz (fc57552), Marta Louren�o (fc58249)
 *
 * SongLibraryEvent objects represent generic events for a song in a library.
 * The concrete events are SongAddedLibraryEvent, SongRemovedLibraryEvent and SongRatedLibraryEvent.
 *
 */
public abstract class SongLibraryEvent implements Event{

	private Song modifiedSong;
	
	/**
	 * Creates a generic event with a given song that was modified
	 * 
	 * @param modifedSong - a given song that was modified
	 */
	protected SongLibraryEvent(Song modifedSong) {
		this.modifiedSong = modifedSong;
	}
	
	/**
	 * Returns the modified song
	 * 
	 * @return the modified song
	 */
	public Song getModifiedSong() {
		return modifiedSong;
	}
	
}
