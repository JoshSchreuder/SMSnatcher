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
package controller;

import javax.swing.SwingWorker;

import model.Logger;
import model.LyricWikiScraper;
import model.MP3TagHandler;
import model.SongMeaningsScraper;
import model.SongTableModel;

import org.apache.commons.lang3.text.WordUtils;

import view.MainFrame;

public class GetLyricsSwingWorker extends SwingWorker<String, Object> {
	
	private int numLyricsChanged = 0;

	protected String doInBackground() throws Exception {
		numLyricsChanged = 0;
		MainFrame.getSongTable().setEnabled(false);
		MainFrame.getOpenButton().setEnabled(false);
		MainFrame.getGoButton().setEnabled(false);
		SongTableModel model = ((SongTableModel)MainFrame.getSongTable().getModel());
		MainFrame.getProgressBar().setMinimum(0);
		MainFrame.getProgressBar().setValue(0);
		MainFrame.getProgressBar().setMaximum(model.getRowCount());
		
		for(int i = 0 ; i < model.getRowCount() ; i++) {
			String lyricsColumn = model.getValueAt(i, 2);
			MainFrame.getSongTable().scrollRectToVisible(MainFrame.getSongTable().getCellRect(i, 0, true));
			MainFrame.getSongTable().getSelectionModel().setSelectionInterval(i, i);
			// If lyrics are blank
			if(lyricsColumn.compareTo("") == 0) {
				Logger.LogToStatusBar("Getting lyrics " + (i+1) + " of " + model.getRowCount());
				String artist = model.getValueAt(i, 0);
				artist = artist.replace("[", "");
				artist = artist.replace("]", "");
				artist = WordUtils.capitalize(artist);
				String track = model.getValueAt(i, 1);
				track = track.replace("[", "");
				track = track.replace("]", "");
				track = WordUtils.capitalize(track);
				
				if(MainFrame.useLyricWiki() == true) {
					String lyrics = LyricWikiScraper.getLyrics(artist, track);
					// If artist has an & sign, try replacing with 'and' and search again for more results
					if(artist.contains("&") && (lyrics.compareTo("") == 0 || lyrics != null)) {
						Logger.LogToStatusBar("Artist contains &, searching again with 'And' instead");
						artist = artist.replace("&", " And ");
						lyrics = LyricWikiScraper.getLyrics(artist, track);
					}
					if(lyrics != null && lyrics.compareTo("") != 0) {
						model.setValueAt(lyrics, i, 2);
						MP3TagHandler.saveLyrics(lyrics, model.getValueAt(i, 3));
						numLyricsChanged += 1;
					}
				}
				else {
					String lyrics = SongMeaningsScraper.getLyrics(artist, track);
					// If artist has an & sign, try replacing with 'and' and search again for more results
					if(artist.contains("&") && (lyrics.compareTo("") == 0 || lyrics != null)) {
						Logger.LogToStatusBar("Artist contains &, searching again with 'And' instead");
						artist = artist.replace("&", " And ");
						lyrics = SongMeaningsScraper.getLyrics(artist, track);
					}
					
					if(lyrics.compareTo("") != 0) {
						model.setValueAt(lyrics, i, 2);
						MP3TagHandler.saveLyrics(lyrics, model.getValueAt(i, 3));
						numLyricsChanged += 1;
					}
				}
			}
			// Lyrics are not blank
			else {
				// If overwriteLyrics is set, we are overwriting them anyway
				if(MainFrame.overwriteLyrics() == true) {
					Logger.LogToStatusBar("Getting lyrics " + (i+1) + " of " + model.getRowCount());
					String artist = model.getValueAt(i, 0);
					artist = artist.replace("[", "");
					artist = artist.replace("]", "");
					String track = model.getValueAt(i, 1);
					track = track.replace("[", "");
					track = track.replace("]", "");
					if(MainFrame.useLyricWiki() == true) {
						String lyrics = LyricWikiScraper.getLyrics(artist, track);
						
						// If artist has an & sign, try replacing with 'and' and search again for more results
						if(artist.contains("&") && (lyrics.compareTo("") == 0 || lyrics != null)) {
							Logger.LogToStatusBar("Artist contains &, searching again with 'And' instead");
							artist = artist.replace("&", "And");
							lyrics = LyricWikiScraper.getLyrics(artist, track);
						}
						if(lyrics != null && lyrics.compareTo("") != 0) {
							model.setValueAt(lyrics, i, 2);
							MP3TagHandler.saveLyrics(lyrics, model.getValueAt(i, 3));
							numLyricsChanged += 1;
						}
					}
					else {
						String lyrics = SongMeaningsScraper.getLyrics(artist, track);
						// If artist has an & sign, try replacing with 'and' and search again for more results
						if(artist.contains("&") && (lyrics.compareTo("") == 0 || lyrics != null)) {
							Logger.LogToStatusBar("Artist contains &, searching again with 'And' instead");
							artist = artist.replace("&", "And");
							lyrics = SongMeaningsScraper.getLyrics(artist, track);
						}
						
						if(lyrics.compareTo("") != 0) {
							model.setValueAt(lyrics, i, 2);
							MP3TagHandler.saveLyrics(lyrics, model.getValueAt(i, 3));
							numLyricsChanged += 1;
						}
					}
				}
			}
			
			int value = MainFrame.getProgressBar().getValue() + 1;
	        if (value > MainFrame.getProgressBar().getMaximum()) {
	          value = MainFrame.getProgressBar().getMaximum();
	        }
	        MainFrame.getProgressBar().setValue(value);
		}
		
		MainFrame.getOpenButton().setEnabled(true);
		MainFrame.getGoButton().setEnabled(true);
		MainFrame.getSongTable().setEnabled(true);
		Logger.LogToStatusBar("Done getting lyrics (changed " + numLyricsChanged + " lyrics)");
		
		// TODO: Resolve artist / song mismatches
		
		return null;
	}
}
