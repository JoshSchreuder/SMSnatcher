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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import view.MainFrame;

public class DataManager {
	private static HashMap<String,String> artistMap;
	private static HashMap<String, HashMap<String, String>> songMap;
	private static HashMap<String, ArrayList<String>> artistMatches;
	private static HashMap<String, ArrayList<String>> songMatches;
	private static HashMap<String, String> artistCorrections;

	public static void importLegacyData(String filename) {
		try {
			String line = null;
			BufferedReader input = new BufferedReader(new FileReader(MainFrame.getSettingsDirectory()+"/"+filename));
			while ((line = input.readLine()) != null) {
				String[] tokens = line.split(":");
				System.out.println(tokens[0] + " : " + tokens[1]);
				artistMap.put(tokens[0], tokens[1]);
			}
			input.close();
		} catch (IOException e) {
			Logger.LogToStatusBar("Data file does not exist");
		}
	}
	
	public static boolean verifyResourcesOnDisk() {
		File file = new File(MainFrame.getSettingsDirectory()+"/artists.db");
		if(!file.exists()) {
			return false;
		}
		else {
			if(file.length() < 100)
				return false;
		}
			
		
		file = new File(MainFrame.getSettingsDirectory()+"/artistcorrect.db");
		if(!file.exists()) {
			return false;
		}
		else {
			if(file.length() < 100)
				return false;
		}
		
		return true;
	}
	
	public static void extractResourcesToDisk() {
		MainFrame.getProgressBar().setMinimum(0);
		MainFrame.getProgressBar().setValue(0);
		
		InputStream fis = ClassLoader.getSystemResourceAsStream("db.zip");
		ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));
		ZipEntry entry;
		BufferedOutputStream dest = null;
		final int BUFFER = 2048;
		
		try {
	         while((entry = zis.getNextEntry()) != null) {
				System.out.println("Extracting: " +entry);
				int count;
				byte data[] = new byte[BUFFER];
				// write the files to the disk
				FileOutputStream fos = new FileOutputStream(MainFrame.getSettingsDirectory()+"/"+entry.getName());
				dest = new 
				      BufferedOutputStream(fos, BUFFER);
				    while ((count = zis.read(data, 0, BUFFER)) 
				      != -1) {
				       dest.write(data, 0, count);
				    }
				    dest.flush();
				    dest.close();
	         }
	         zis.close();
		} 
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public static void loadData() {
		MainFrame.getProgressBar().setMinimum(0);
		MainFrame.getProgressBar().setValue(0);
		MainFrame.getProgressBar().setMaximum(3);
		try {
			Logger.LogToStatusBar("Loading artists file");
			FileInputStream fin = new FileInputStream(MainFrame.getSettingsDirectory()+"/artists.db");
			ObjectInputStream ois = new ObjectInputStream(fin);
			artistMap = ((HashMap<String,String>)ois.readObject());
			ois.close();
			MainFrame.getProgressBar().setValue(1);
		} 
		catch (Exception e) {
			//e.printStackTrace();
			// TODO: Warn the user that Artists DB does not exist and offer to get it
			Logger.LogToStatusBar("Artists DB does not exist, we should warn the user");
		}
		
		try {
			System.out.println("Loading tracks file");
			FileInputStream fin = new FileInputStream(MainFrame.getSettingsDirectory()+"/tracks.db");
			ObjectInputStream ois = new ObjectInputStream(fin);
			songMap = ((HashMap<String,HashMap<String,String>>)ois.readObject());
			ois.close();
			MainFrame.getProgressBar().setValue(2);
		} 
		catch (Exception e) {
			//e.printStackTrace();
			// TODO: Warn the user that Artists DB does not exist and offer to get it
			Logger.LogToStatusBar("Tracks DB does not exist, we should warn the user");
		}
		
		try {
			System.out.println("Loading artist correction file");
			FileInputStream fin = new FileInputStream(MainFrame.getSettingsDirectory()+"/artistcorrect.db");
			ObjectInputStream ois = new ObjectInputStream(fin);
			artistCorrections = ((HashMap<String,String>)ois.readObject());
			ois.close();
			MainFrame.getProgressBar().setValue(3);
		} 
		catch (Exception e) {
			//e.printStackTrace();
			// TODO: Warn the user that Artists DB does not exist and offer to get it
			Logger.LogToStatusBar("Artist correction DB does not exist, we should warn the user");

		}
		SongMeaningsScraper.addArtistCorrection("Final Fantasy", "Owen Pallett");
		SongMeaningsScraper.addArtistCorrection("+44", "(+44)");
		SongMeaningsScraper.addArtistCorrection("The Last Bison", "Bison");
		SongMeaningsScraper.addArtistCorrection("TTNG", "This Town Needs Guns");
	}

	public static void saveData() {
		MainFrame.getProgressBar().setMinimum(0);
		MainFrame.getProgressBar().setValue(0);
		MainFrame.getProgressBar().setMaximum(3);
		
		try {
			Logger.LogToStatusBar("Saving artists file");
			FileOutputStream fout = new FileOutputStream(MainFrame.getSettingsDirectory()+"/artists.db");
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(artistMap);
			oos.close();
			MainFrame.getProgressBar().setValue(1);
			
			Logger.LogToStatusBar("Saving tracks file");
			FileOutputStream fout2 = new FileOutputStream(MainFrame.getSettingsDirectory()+"/tracks.db");
			ObjectOutputStream oos2 = new ObjectOutputStream(fout2);
			oos2.writeObject(songMap);
			oos2.close();
			MainFrame.getProgressBar().setValue(2);
			
			Logger.LogToStatusBar("Saving artist correction file");
			FileOutputStream fout3 = new FileOutputStream(MainFrame.getSettingsDirectory()+"/artistcorrect.db");
			ObjectOutputStream oos3 = new ObjectOutputStream(fout3);
			oos3.writeObject(artistCorrections);
			oos3.close();
			MainFrame.getProgressBar().setValue(3);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static HashMap<String, String> getArtistMap() {
		if(artistMap == null)
			artistMap = new HashMap<String, String>();
		return artistMap;
	}
	
	public static HashMap<String, HashMap<String, String>> getSongMap() {
		if(songMap == null)
			songMap = new HashMap<String, HashMap<String, String>>();
		return songMap;
	}
	
	public static void setArtistMap(HashMap<String, String> readObject) {
		artistMap = readObject;
	}
	
	public static void setSongMap(
			HashMap<String, HashMap<String, String>> readObject) {
		songMap = readObject;
	}
	
	public static HashMap<String, ArrayList<String>> getSongMatches() {
		if(songMatches == null)
			songMatches = new HashMap<String, ArrayList<String>>();
		return songMatches;
	}
	
	public static HashMap<String, ArrayList<String>> getArtistMatches() {
		if(artistMatches == null)
			artistMatches = new HashMap<String, ArrayList<String>>();
		return artistMatches;
	}

	public static HashMap<String, String> getArtistCorrections() {
		if(artistCorrections == null)
			artistCorrections = new HashMap<String, String>();
		return artistCorrections;
	}
}
