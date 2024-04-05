package domain.facade;

import domain.core.MusicLibrary;
import domain.playlists.PlaylistList;

/**
 * @author In�s Luz (fc57552), Marta Louren�o (fc58249)
 *
 * Initial object of the system, containing a MusicLibrary and PlaylistList and
 * its respective controllers.
 *
 */
public class LEITunes {

	private MusicLibrary library;
	private PlaylistList pList;
	private PlaylistListController plc;
	private MusicLibraryController mlc;
	
	/**
	 * Creates the initial object of the system
	 */
	public LEITunes() {
		library = new MusicLibrary();
		pList = new PlaylistList(library);
		plc = new PlaylistListController(pList, library);
		mlc = new MusicLibraryController(library);
	}
	
	/**
	 * Gets the PlaylistListController for this object
	 * 
	 * @return PlaylistListController
	 */
	public PlaylistListController getPlaylistController() {
		return plc;
	}
	
	/**
	 * Gets the MusicLibraryController for this object
	 * 
	 * @return MusicLibraryController
	 */
	public MusicLibraryController getMusicLibraryController() {
		return mlc;
	}
	
}
