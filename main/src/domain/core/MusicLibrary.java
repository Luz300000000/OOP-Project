package domain.core;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import domain.facade.ISong;
import domain.player.Player;
import domain.player.PlayerFactory;
import util.adts.ArrayQListWithSelection;
import util.adts.QListWithSelection;
import util.observer.AbsSubject;
import util.observer.Subject;

/**
 * @author In�s Luz (fc57552), Marta Louren�o (fc58249)
 *
 * This class consist in representing music libraries where it can be selected
 * one song at a time, or none.
 * 
 * The library notifies its observers each time a song is added, removed or rated.
 * At the same time, the library also is notified when the player changes states
 * 
 */
public class MusicLibrary extends AbsSubject<SongLibraryEvent>
						  implements QListWithSelection<Song>, Subject<SongLibraryEvent>, PropertyChangeListener {	
	
	private QListWithSelection<Song> library;
	private Song playing;
	private Player player;
	
	/**
	 * Creates a new MusicLibrary object
	 */
	public MusicLibrary() {
		super();
		library = new ArrayQListWithSelection<>();
		playing = null;
		player = PlayerFactory.INSTANCE.getPlayer();
		player.addListener(this);
	}
	
	/**
	 * Plays the selected song
	 * 
	 * @requires someSelected()
	 * @ensures isPlaying()
	 */
	public void play() {
		if(isPlaying())
			stop();
		playing = library.getSelected();
		player.load(playing.getFilename());
		player.play();
	}
	
	/**
	 * Returns if a song is playing from the library
	 * 
	 * @return true if a song is playing form the library
	 */
	public boolean isPlaying() {
		return playing != null;
	}
	
	/**
	 * Stops the playing song
	 * 
	 * @ensures playing == null
	 */
	public void stop() {
		playing = null;
		player.stop();
	}
	
	/**
	 * Increments the rate of the selected song, if it applies
	 * Emits an event of type SongRatedLibraryEvent
	 * 
	 * @requires someSelected()
	 * @ensures \old(rate) < MAX_RATE ==> rate == \old(rate) + 1
	 * @ensures \old(rate) == MAX_RATE ==> rate == \old(rate)
	 */	
	public void incRateSelected() {
		getSelected().incRating();
		emitEvent(new SongRatedLibraryEvent(getSelected()));
	}
	
	/**
	 * Decrements the rate of the selected song, if it applies
	 * Emits an event of type SongRatedLibraryEvent
	 * 
	 * @requires someSelected()
	 * @ensures \old(rate) > MIN_RATE ==> rate == \old(rate) - 1
	 * @ensures \old(rate) == MIN_RATE ==> rate == \old(rate)
	 */
	public void decRateSelected() {
		getSelected().decRating();
		emitEvent(new SongRatedLibraryEvent(getSelected()));
	}
	
	/**
	 * Returns an iterable structure with all songs from the library that match the given regex
	 * 
	 * @requires size() > 0
	 * @param reexp a given regular expression (regex)
	 * @return a list with all songs from the library that match the given regex
	 */
	public Iterable<ISong> getMatches(String reexp) {
		List<ISong> matchedSongs = new ArrayList<>();
		
		for (ISong song : library)
			if (song.matches(reexp))
				matchedSongs.add(song);
		
		return matchedSongs;
	}
	
	/**
	 * Returns an iterable structure with all songs from the library
	 * 
	 * @requires size() > 0
	 * @return a list with all songs from the library
	 */
	public Iterable<ISong> getSongs() {
		List<ISong> allSongs = new ArrayList<>();
		
		for (ISong song : library)
			allSongs.add(song);
		
		return allSongs;
	}
	
	/**
	 * Returns the number of songs in the library
	 * 
	 * @return the number of songs in the library
	 * @ensures \return >= 0
	 */
	@Override
	public int size() {
		return library.size();
	}

	/**
	 * Returns the song at position i in the library
	 * 
	 * @param i the position of the song to return
	 * @requires 0 <= i < size()
	 * @return the song at position i
	 */
	@Override
	public Song get(int i) {
		return library.get(i);
	}

	/**
	 * Returns an iterator over the songs in the library
	 * 
	 * @return an iterator over the songs in this library in proper sequence
	 */
	@Override
	public Iterator<Song> iterator() {
		return library.iterator();
	}

	/**
	 * Reaction to property change events, according to those emitted by the player (change of states)
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if(isPlaying()) {
			if(evt.getNewValue().equals(Player.PlayingState.ENDED)){
				playing.incTimesPlayed();
			} else if(evt.getNewValue().equals(Player.PlayingState.STOPED))
				stop();
		}
	}

	/**
	 * Selects song at position i in the library
	 * 
	 * @param i the position of the element to be selected
	 * @requires 0 <= i <= size()
	 * @ensures someSelected() && getIndexSelected() == i
	 * 						   && size == \old(size())
	 */
	@Override
	public void select(int i) {
		library.select(i);
	}

	/**
	 * Adds a song to the end of the library
	 * Emits an event of type SongAddedLibraryEvent
	 * 
	 * @param e the song to be added
	 * @requires song != null
	 * @ensures size() == \old(size()) + 1
	 */
	@Override
	public void add(Song e) {
		library.add(e);
		emitEvent(new SongAddedLibraryEvent(e));
	}

	/**
	 * Returns true if a song is selected
	 * 
	 * @return true if a song is selected, false otherwise
	 */
	@Override
	public boolean someSelected() {
		return library.someSelected();
	}

	/**
	 * Returns the index of the selected song
	 * 
	 * @requires someSelected()
	 * @return the index of the selected song
	 * @ensures 0 <= \return < size()
	 */
	@Override
	public int getIndexSelected() {
		return library.getIndexSelected();
	}

	/**
	 * Selects the next song if it applies, otherwise no song is selected
	 * 
	 * @requires someSelected()
	 * @ensures size() == \old(size())
	 * @ensures if \old getIndexSelected() < size() - 1
	 *          then getIndexSelected() = \old getIndexSelected() + 1 
	 *          else !someSelected()
	 */
	@Override
	public void next() {
		if (getIndexSelected() < size() - 1)
			library.next();
		else
			library.select(-1);
	}

	/**
	 * Selects the previous song if it applies, otherwise no song is selected
	 * 
	 * @requires someSelected()
	 * @ensures size() == \old(size())
	 * @ensures if \old getIndexSelected() > 0
	 *          then getIndexSelected() = \old getIndexSelected() - 1 
	 *          else !someSelected()
	 */
	@Override
	public void previous() {
		if (getIndexSelected() > 0)
			library.previous();
		else
			library.select(-1);
	}

	/**
	 * Removes the selected song from the library, if possible
	 * Emits an event of type SongRemovedLibraryEvent
	 * 
	 * @ensures \old someSelected() ==> !someSelected() && size() == \old(size()) - 1
	 * @ensures \old !someSelected() ==> !someSelected() && size() == \old(size())
	 */
	@Override
	public void remove() {
		if (someSelected()) {
			Song selected = getSelected();
			library.remove();
			emitEvent(new SongRemovedLibraryEvent(selected));
		}
	}

	/**
	 * Returns the selected song
	 * 
	 * @requires someSelected()
	 * @return the selected song
	 * @ensures \return != null
	 */
	@Override
	public Song getSelected() {
		return library.getSelected();
	}
	
	/**
	 * Returns the player
	 * 
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}
	
	/**
	 * Checks if two libraries are equal
	 * @param m the other library
	 * @return true if the libraries are equal, false if otherwise
	 */
	public boolean equalsLibrary(MusicLibrary m) {
		boolean areEquals = true;
		if (size() == m.size()) {
			for (int i = 0; i < size(); i++) {
				if (!get(i).equalsSong(m.get(i)))
					areEquals = false;
			}	
		}
		else
			areEquals = false;
		
		return areEquals;
	}

	/**
	 * Returns a textual representation of the playlist
	 * 
	 * @return textual representation of the playlist
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		int i = 0;
		
		for(Song s : library) {
			sb.append(i);
			sb.append(" ");
			sb.append(s);
			sb.append("\n");
			i++;
		}
		
		return sb.toString();
	}
}
