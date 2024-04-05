package domain.core;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import util.adts.RegExpMatchable;

/**
 * @author In�s Luz (fc57552), Marta Louren�o (fc58249)
 *
 * SongMetaInfo objects are records that represent the meta-info of a song.
 * 
 */
public record SongMetaInfo(String songTitle, String genre, List<String> artists, String album) implements RegExpMatchable{

	/**
	 * Checks if the meta-info matches the regex expression
	 * 
	 * @param regexp the regular expression (regex)
	 * @return true if the meta-info matches the regex, false if otherwise
	 */
	@Override
	public boolean matches(String regexp) {
		String str = toString();
		Matcher matcher = Pattern.compile(regexp).matcher(str);
		return matcher.find();
	}
	
	/**
	 * Returns a textual representation of the playlist
	 * 
	 * @return textual representation of the playlist
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("[");
		String noValue = "unkown";
		
		if(songTitle != null)
			sb.append(songTitle);
		else
			sb.append(noValue);
		sb.append(", ");
		
		if(album != null)
			sb.append(album);
		else
			sb.append(noValue);
		sb.append(", ");
		
		if(genre != null)
			sb.append(genre);
		else
			sb.append(noValue);
		sb.append(", [");
		
		for(int i = 0; i < artists.size(); i++) {
			sb.append(artists.get(i));
			if(i != artists.size()-1)
				sb.append("; ");
			else
				sb.append("]]");
		}
			
		return sb.toString();
	}

}
