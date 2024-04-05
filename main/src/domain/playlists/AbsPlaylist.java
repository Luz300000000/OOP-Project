package domain.playlists;

import java.beans.PropertyChangeEvent;
import java.util.Iterator;

import domain.core.MusicLibrary;
import domain.core.Song;
import domain.core.SongLibraryEvent;
import domain.facade.ISong;
import domain.player.Player;
import util.adts.ArrayQListWithSelection;
import util.adts.QListWithSelection;

/**
 * @author Inês Luz (fc57552), Marta Lourenço (fc58249)
 *
 * Playlists have a name and consist of a list of distinct musics from a music library.
 * 
 * Removing a song from the library leads to automatic removal of the song from the 
 * playlist, if it is there.
 * 
 * Addition/removal of songs may be completely automatic and, hence, manual addition/removal 
 * might not be possible. The same applies to changes of order through moveUpSelected method.
 * 
 * At any time, one song in the playlist may be selected and one song of the 
 * playlist may be playing (i.e. the play action was performed via the playlist). 
 * A playlist keeps track that a song was played (until the end) through the playlist.
 *
 */
public abstract class AbsPlaylist implements Playlist {
	
	private String name;
	private MusicLibrary library;
	private QListWithSelection<ISong> playlist;
	private ISong playing;
	private Player player;
	
	/**
	 * Creates playlist with the given name and library
	 * 
	 * @param name the playlist's name
	 * @param library the library associated to the playlist
	 */
	protected AbsPlaylist(String name, MusicLibrary library) {
		this.name = name;
		this.library = library;
		this.playlist = new ArrayQListWithSelection<>();
		playing = null;
		
		player = library.getPlayer();
		player.addListener(this);
		
		library.registerListener(this);
	}
	
	/**
	 * Returns the number of songs in the playlist
	 * 
	 * @return the number of songs in the playlist
	 * @ensures \return >= 0
	 */
	@Override
	public int size() {
		return playlist.size();
	}
	
	/**
	 * Returns the selected song
	 * 
	 * @return the selected song
	 * @requires someSelected()
	 * @ensures \return != null
	 */
	@Override
	public ISong getSelected() {
		return playlist.getSelected();
	}
	
	/**
	 * Returns true if some element is selected
	 * 
	 * @return true if some element is selected, false otherwise
	 */
	@Override
	public boolean someSelected() {
		return playlist.someSelected();
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
		int ogSize = playlist.size();
		if(song instanceof Song s && !inPlaylist(song)) {
			playlist.add(s);
				playlist.select(playlist.size()-1);
		}
		return playlist.size() == ogSize+1 && playlist.someSelected() && playlist.getIndexSelected() == playlist.size()-1;
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
		boolean wasRemoved = false;
		
		if (someSelected()) {
			playlist.remove();
			
			if (!someSelected())
				wasRemoved = true;
		}
		return wasRemoved;
	}
	
	/**
	 * Selects song at position i
	 * 
	 * @param i the position denoting the element to be selected
	 * @requires 0 <= i < size()
	 * @ensures someSelected() && getIndexSelected() == i &&
	 * 								size() == \old(size()) 
	 */
	@Override
	public void select(int i) {
		playlist.select(i);
	}
	
	/**
	 * Moves the current selected song up to position i, 
	 * shifting down all elements in the playlist from 
	 * positions i+1 to \old getIndexSelected()-1, 
	 * if movement in the playlist is possible 
	 * 
	 * @param i the index where this element is going to be moved
	 * @requires someSelected() && 0 <= i < getIndexSelected()
	 * @ensures \return ==> someSelected() && 
	 * 					getIndexSelected() == i  && 
	 * 					size() == \old(size()) 
	 */
	@Override
	public boolean moveUpSelected(int i) {
		QListWithSelection<ISong> newPlaylist = new ArrayQListWithSelection<>();
		ISong selected = playlist.getSelected();
		playlist.remove();
		
		int j = 0;
		for(ISong s : playlist) {
			if(j==i)
				newPlaylist.add(selected);
			newPlaylist.add(s);
			j++;
		}
		
		newPlaylist.select(i);
		playlist = newPlaylist;
		
		return playlist.getIndexSelected() == i && playlist.getSelected().equals(selected); // nao tenho a certeza
	}
	
	/**
	 * Returns the index of the selected element, if any	 
	 * 
	 * @return the index of the selected element, if any
	 * @requires someSelected()
	 * @ensures 0 <= \return < size()
	 */
	@Override
	public int getIndexSelected() {
		return playlist.getIndexSelected();
	}
	
	/**
	 * Selects the next element, if any. Otherwise, no element is selected.
	 *
	 * @requires someSelected() 
	 * @ensures if \old getIndexSelected() < size() - 1
	 *          then getIndexSelected() = \old getIndexSelected() + 1 
	 *          else !someSelected()
	 * @ensures size() == \old(size()) 
	 */
	@Override
	public void next() {
		if (getIndexSelected() < size() - 1)
			playlist.next();
		else
			playlist.select(-1);
	}
	
	/**
	 * Selects the previous element, if any. Otherwise, no element is selected.
	 *
	 * @requires someSelected() 
	 * @ensures if \old getIndexSelected() > 0
	 *          then getIndexSelected() = \old getIndexSelected() - 1 
	 *          else !someSelected() 
	 * @ensures size() == \old(size()) 
	 */
	@Override
	public void previous() {
		if (getIndexSelected() > 0)
			playlist.previous();
		else
			playlist.select(-1);
	}
	
	/**
	 * Returns the name of the playlist
	 * 
	 * @return the name of the playlist
	 * @ensures \result != null
	 */
	@Override
	public String getName() {
		return name;
	}
	
	/**
	 * Returns if a song is playing and the play action has been performed via the playlist
	 * 
	 * @return true if a song is playing and the play action was done through the playlist,
	 *         false otherwise
	 */
	@Override
	public boolean isPlaying() {
		return playing != null;
	}
	
	/**
	 * Plays the selected song
	 * 
	 * @requires someSelected()
	 * @ensures isPlaying()
	 */
	@Override
	public void play() {
		if(isPlaying())
			stop();
		playing = playlist.getSelected();
		player.load(playing.getFilename());
		player.play();
	}
	
	/**
	 * Stops the playing song
	 * 
	 * @requires isPlaying()
	 */
	@Override
	public void stop() {
		library.stop();
	}
	
	/**
	 * Returns an iterator over the songs in the playlist
	 * 
	 * @return  an iterator over the songs in the playlist in proper sequence.
	 */
	@Override
	public Iterator<ISong> iterator() {
		return playlist.iterator();
	}
	
	/**
	 * Reaction to property change events, namely those emitted by the player
	 * (can affect the selected song and song being played)
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if(isPlaying()) {
			if(evt.getNewValue().equals(Player.PlayingState.ENDED)){
				playing.incTimesPlayed();
				next();
				if(someSelected())
					play();
				else
					stop();
			} else if(evt.getNewValue().equals(Player.PlayingState.STOPED))
				stop();
		}
	}
	
	/**
	 * Processes the SongRemovedLibraryEvent
	 * 
	 * @param e the event received
	 */
	public void processRemoved(SongLibraryEvent e) {
		Song removed = e.getModifiedSong();
		boolean removedInPlaylist = false;
		int index = 0;
		Iterator<ISong> it = iterator();
		
		while(it.hasNext() && !removedInPlaylist) {
			if(it.next().equals(removed)) {
				removedInPlaylist = true;
				index++;
			}
		}
		
		if(removedInPlaylist) {
			int ogSelected = getIndexSelected();
			if(ogSelected != index) {
				select(index);
				remove();
				select(ogSelected);
			} else
				remove();
		}
	}
	
	/**
	 * Returns a textual representation of the playlist
	 * 
	 * @return textual representation of the playlist
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("*-- Playlist ");
		int i = 0;
		
		sb.append(name);
		sb.append("--*\n");
		
		for(ISong s : playlist) {
			sb.append(i);
			sb.append(" ");
			sb.append(s.toString());
			sb.append("\n");
			i++;
		}
		
		return sb.toString();
	}
	
	public boolean inPlaylist(ISong s) {
		boolean in = false;
		Iterator<ISong> it = iterator();
		
		while(it.hasNext() && !in)
			in |= it.next().equals(s);
		
		return in;
	}
	
}
