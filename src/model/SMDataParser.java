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
import java.util.HashMap;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SMDataParser {
	private static HashMap<String, String> artistMap;
	private static HashMap<String, HashMap<String, String>> songMap;
	
	public static void parseArtistDirectory() {
		artistMap = DataManager.getArtistMap();
		artistMap.clear();
		System.out.println("Parsing artists from SongMeanings");
		System.out.println("Parsing symbol based names");
		for(char c = 33 ; c <= 46 ; c++) {
			System.out.println("Parsing directory " + c);
			String url = "http://www.songmeanings.net/artist/directory/" + c + "/";
			parseArtistDirectoryPage(url);
		}
		
		for(char c = 91 ; c <= 96 ; c++) {
			System.out.println("Parsing directory " + c);
			String url = "http://www.songmeanings.net/artist/directory/" + c + "/";
			parseArtistDirectoryPage(url);
		}
		
		for(char c = 123 ; c <= 126 ; c++) {
			System.out.println("Parsing directory " + c);
			String url = "http://www.songmeanings.net/artist/directory/" + c + "/";
			parseArtistDirectoryPage(url);
		}
		
		System.out.println("Parsing letter based names");
		for(char c = 65 ; c <= 90 ; c++) {
			System.out.println("Parsing directory " + c);
			String url = "http://www.songmeanings.net/artist/directory/" + c + "/";
			parseArtistDirectoryPage(url);
		}
	}
	
	public static String parseSongsPage(String artist, String artistURL) {
		songMap = DataManager.getSongMap();
		System.out.println("Attempting to parse song titles from " + artistURL);
		// Try to load page using Jsoup
		try {
			// Load page into Document
			Connection c = Jsoup.connect(artistURL);
			c.timeout(100000);
			Document doc = c.get();
			// Check for any errors
			Elements errors = doc.select("strong");
			if (errors.get(0).text().contains("This artist is currently not available.")) {
				System.out.println("There is an error on this page, removing this artist");
				artistMap.remove(artist);
				return null;
			}
			
			// No errors, get the song list
			Elements songDarkRows = doc.getElementsByClass("row1");
			Elements songLightRows = doc.getElementsByClass("row0");
			extractSongs(artist, songDarkRows);
			extractSongs(artist, songLightRows);
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("Songs page for " + artist + " not found!");
		}
		return null;
	}
	
	public static void parseArtistDirectoryPage(String url) {
		// Try to load page using Jsoup
		try {
			// Load page into Document
			Connection c = Jsoup.connect(url);
			c.timeout(100000);
			Document doc = c.get();
			Elements artistDarkRows = doc.getElementsByClass("row1");
			Elements artistLightRows = doc.getElementsByClass("row0");
			
			extractArtists(artistDarkRows);
			extractArtists(artistLightRows);
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("Directory " + url + " not found!");
		}
	}
	
	public static void extractArtists(Elements rows) {
		Elements links = rows.select("a[href]");
		
		for(Element link : links) {
			String linkURL = link.attr("abs:href");
			String artist = link.text();
			artistMap.put(artist, linkURL);
		}
	}
	
	public static void extractSongs(String artist, Elements rows) {
		Elements links = rows.select("a[href]");
		
		for(Element link : links) {
			String linkURL = link.attr("abs:href");
			if (!linkURL.contains("#comment")) {
				String title = link.text();
				
				HashMap<String, String> songs = songMap.get(artist);
				System.out.println("******************************");
				System.out.println("Adding \'" + title + "\' to songDB");
				System.out.println("******************************");
				// Artist isn't in songMap yet...
				if(songs == null) {
					songs = new HashMap<String, String>();
					songs.put(title, linkURL);
					songMap.remove(artist);
					songMap.put(artist, songs);
				}
				else {
					songs.put(title, linkURL);
					songMap.remove(artist);
					songMap.put(artist, songs);
				}
			}
		}
	}
}
