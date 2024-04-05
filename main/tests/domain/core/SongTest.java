package domain.core;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import domain.facade.ISong;

public class SongTest {
	
	private ISong song;

	@BeforeEach
	public void init() {
		List<String> artists = new ArrayList<>();
		artists.add("a1");
		artists.add("a2");
		SongMetaInfo info = new SongMetaInfo("titleA", "genreA", artists, "albumA");
		song = new Song(info, "filenameA");
	}
	
	@Test
	@DisplayName("Test 'incTimesPlayed' method")
	public void testIncTimesPlayed() {
		song.incTimesPlayed();
		assertEquals(1, song.getTimesPlayed());
	}
	
	@Test
	@DisplayName("Test 'incRating' method")
	public void testIncRating() {
		song.incRating();
		assertEquals(1, song.getRating().getRate());
	}
	
	@Test
	@DisplayName("Test 'decRating' method")
	public void testDecRating() {
		song.incRating();
		song.incRating();
		song.decRating();
		assertEquals(1, song.getRating().getRate());
	}
	
	@Test
	@DisplayName("Test 'matches' method")
	public void testMatches() {
		String regex = "^\\[[^\\[\\],]+\\s*,\\s*[^\\[\\],]+\\s*,\\s*[^\\[\\]," +
				   "]+\\s*,\\s*\\[[^\\[\\]]+\\]\\]\\s*" +
				   "---\\s*\\d+\\s*--\\s*\\d+$";
		
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(song.toString());
		
		assertTrue(matcher.matches());
		
	}

}
