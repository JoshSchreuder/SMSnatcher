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
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

public class SongMeaningsScraper {
	
	public static String getLyrics(String artist, String title) throws Exception {
		// Check if user has a correction saved
		String artistMatch = DataManager.getArtistCorrections().get(artist);
		
		// If no correction saved, we must try and find the artist on site
		if(artistMatch == null)
			artistMatch = validateArtist(artist);
		
		// If finding artist didn't get an exact match, we must prompt user later
		if(artistMatch == null) {
			System.out.println("No artist match, we'll have to prompt later...");
			return null;
		}
		
		HashMap<String, String> songList = DataManager.getSongMap().get(artistMatch);
		if(songList == null) {
			System.out.println("No track present, have to parse them...");
			SMDataParser.parseSongsPage(artistMatch, DataManager.getArtistMap().get(artistMatch));
		}
		else {
			System.out.println("Tracks for artist are present, trying to find the right one");
			//System.out.println(songList);
			System.out.println("Checking that artist has song " + title);
			String songTitle = validateSong(artistMatch, title);
			
			// If finding song didn't get an exact match, we must prompt user later
			if(songTitle == null) {
				System.out.println("No song match, we'll have to prompt later...");
				return null;
			}
			
			System.out.println("The closest match we found for " + title + " was " + songTitle);
			String songURL = songList.get(songTitle);
			
			String lyrics = scrapeLyricsPage(songURL);
			//System.out.println(lyrics);
			return lyrics;
		}
		return null;
	}

	private static String scrapeLyricsPage(String songURL) {
		String lyrics = "";
		
		// Try to load page using Jsoup
		try {
			// Load page into Document
			Document doc = Jsoup.connect(songURL).get();
			// Get lyricBox from page
			Elements lyricBox = doc.getElementsByClass("box_gray_inner");
			// Remove ads
			lyricBox.get(0).getElementsByTag("div").remove();
			// Remove comments
			ParseUtils.removeComments(lyricBox.get(0));
			
			// We now have almost perfect lyrics.
			lyrics = lyricBox.get(0).html();
			TextNode t = TextNode.createFromEncoded(lyrics, "songmeanings");
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
			lyrics = " " + lyrics;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Lyrics not found!");
		}
		System.out.println("Done");
		return lyrics;
	}

	private static String validateSong(String artist, String title) {
		HashMap<String, String> songList = DataManager.getSongMap().get(artist);
		for(String songFromMap : songList.keySet()) {
			int levDist = StringUtils.getLevenshteinDistance(songFromMap.toUpperCase(), title.toUpperCase());
			double ratio = (songFromMap.length() - levDist + 0.0) / (songFromMap.length() + 0.0);
			if(ratio == 1.0) {
				System.out.println(songFromMap + " exactly matches");
				return songFromMap;
			}
			else if(ratio >= 0.5) {
				ArrayList<String> matches = DataManager.getSongMatches().get(artist + " " + title);
				if(matches == null) {
					matches = new ArrayList<String>();
					matches.add(songFromMap);
					DataManager.getSongMatches().put(artist + " " + title, matches);
				}
				else {
					matches.add(songFromMap);
					DataManager.getSongMatches().remove(artist + " " + title);
					DataManager.getSongMatches().put(artist + " " + title, matches);
				}
			}
		}
		return null;
	}

	public static String validateArtist(String artist) {
		HashMap<String,String> artists = DataManager.getArtistMap();
		for(String artistFromMap : artists.keySet()) {
			int levDist = StringUtils.getLevenshteinDistance(artistFromMap.toUpperCase(), artist.toUpperCase());
			double ratio = (artistFromMap.length() - levDist + 0.0) / (artistFromMap.length() + 0.0);
			if(ratio == 1.0) {
				System.out.println(artistFromMap + " exactly matches");
				return artistFromMap;
			}
			else if(ratio >= 0.5) {
				ArrayList<String> matches = DataManager.getArtistMatches().get(artist);
				if(matches == null) {
					matches = new ArrayList<String>();
					matches.add(artistFromMap);
					DataManager.getArtistMatches().put(artist, matches);
				}
				else {
					matches.add(artistFromMap);
					DataManager.getArtistMatches().remove(artist);
					DataManager.getArtistMatches().put(artist, matches);
				}
			}
		}
		return null;
	}
}
