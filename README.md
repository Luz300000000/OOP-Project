## Introduction and Main Goal
- The project goal was to practice Object-Oriented Programming principles taught in this course, such as Design Patterns and best practices.
- Our main focus was implementing a digital music manager and player, containing features such as:
	- Supporting songs in mp3 format, considering a basic set of use cases around music libraries and playlists
	- Allowing to upload songs to a library and from a library
 	- Being able to search, select and play individual songs from a library
  	- Creating smart playlists (lists of automatically managed songs) pre-defined but also creating playlists manually
  	- Managing the songs contained in a manual playlist and play songs from any playlist.

## Description for the Implementation
### Packages General Organization
- **ui:** classes responsible exclusively for the application interface
- **domain:** classes whose responsibility relates only to the application domain
- **services:** classes whose responsibility relates to the use of external services
- **util:** classes that were designed specifically for the application, but potentially reusable in other contexts

### Design Decisions
- Additionally, we also made some specific implementation decisions, regarding the architecture provided for the application:
	- In order to facilitate interactions between the music player and the respective classes responsible for the operations on songs, we decided to communicate directly with the Player, both in `Playlist` and in `MusicLibrary`. Now, each of the objects are independent in the way that each respective controller invokes its methods to play the songs, namely the `play()` method, which allowed us to resolve some conflicts during the development of the project.
	- Regarding the `AbsPlaylist` class, we used the `Player` **Factory** to obtain its instance but also to implement the operations performed on the songs and change properties in response to the `Player` states; We placed `MusicLibrary` as **Listener** for `Player`.
---
#### Additional Notes
- For reading songs metadata from mp3 files, we used the following open source: library https://github.com/mpatric/mp3agic
- Since the design of the application interface is not the focus of this course, the teachers provided us with a rudimentary implementation of a graphical interface (implemented with Eclipse swt).
- Additionally, we also implemented JUnit unit tests for the `ArrayQListWithSelection` and `Song` classes.
- All classes were properly documented with JavaDoc.
- Languages/Technologies used: Java and JUnit

