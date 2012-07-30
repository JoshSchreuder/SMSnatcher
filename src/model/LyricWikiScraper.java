/* 
Copyright (C) 2011 Josh Schreuder
This file is part of SMSnatcher.

SMSnatcher is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

SMSnatcher is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with SMSnatcher.  If not, see <http://www.gnu.org/licenses/>. 
*/
package model;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

public class LyricWikiScraper {
	
	public static String getLyrics(String artist, String title) {
		// Prepare artist and title for LyricWiki's URL format
		artist = artist.replace(' ', '_');
		String mod_title = title.replace(' ', '_');
		
		Logger.LogToStatusBar("Getting lyrics ("+artist+" : " +mod_title+")!");
		String url = "http://lyrics.wikia.com/"+artist+":"+mod_title;
		Logger.LogToStatusBar(url);
		String lyrics = "";
		
		// Try to load page using Jsoup
		try {
			// Load page into Document
			Document doc = Jsoup.connect(url).get();
			// Get lyricBox from page
			Elements lyricBox = doc.select("div.lyricbox");
			//System.out.println(lyricBox.hasText());
			if (!lyricBox.hasText()) {
				Logger.LogToStatusBar("Lyrics not found!");
				return "";
			}
			// Remove ads and junk
			lyricBox.get(0).select("div.rtMatcher").remove();
			lyricBox.get(0).select("div.lyricsbreak").remove();
			// Remove comments
			ParseUtils.removeComments(lyricBox.get(0));
			
			// We now have almost perfect lyrics.
			lyrics = lyricBox.get(0).html();
			TextNode t = TextNode.createFromEncoded(lyrics, "lyricwiki");
			lyrics = t.getWholeText();
			//System.out.println(lyrics);
			//Remove minimal HTML tags, leaving newlines intact
			lyrics = lyrics.replaceAll("<br />", "");
			lyrics = lyrics.replaceAll("<i>", "");
			lyrics = lyrics.replaceAll("</i>", "");
			lyrics = lyrics.replaceAll("<b>", "");
			lyrics = lyrics.replaceAll("</b>", "");
			lyrics = lyrics.replaceAll("<p>", "");
			lyrics = lyrics.replaceAll("</p>", "");
			
			lyrics = lyrics.replaceAll("&lt;", "<");
			lyrics = lyrics.replaceAll("&gt;", ">");
			lyrics = lyrics.replaceAll("�", "\'");
			
			// Check if LyricWiki has full lyrics or only portion
			if(lyrics.contains("we are not licensed to display the full lyrics")) {
				return "";
			}
			else if(lyricBox.get(0).select("a").attr("title").contains("Instrumental")) {
				return "Instrumental";
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Logger.LogToStatusBar("Lyrics not found!");
		}
		Logger.LogToStatusBar("Done");
		return lyrics;
	}
}
