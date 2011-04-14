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

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class DataManager {
	private static HashMap<String,String> artistMap;
	private static HashMap<String, HashMap<String, String>> songMap;
	private static HashMap<String, ArrayList<String>> artistMatches;
	private static HashMap<String, ArrayList<String>> songMatches;
	private static HashMap<String, String> artistCorrections;

	public static void importLegacyData(String filename) {
		try {
			String line = null;
			BufferedReader input = new BufferedReader(new FileReader(filename));
			while ((line = input.readLine()) != null) {
				String[] tokens = line.split(":");
				System.out.println(tokens[0] + " : " + tokens[1]);
				artistMap.put(tokens[0], tokens[1]);
			}
		} catch (IOException e) {
			System.out.println("Data file does not exist");
		}
	}

	@SuppressWarnings("unchecked")
	public static void loadData() {
		try {
			System.out.println("Loading artists file");
			FileInputStream fin = new FileInputStream("artists.db");
			ObjectInputStream ois = new ObjectInputStream(fin);
			artistMap = ((HashMap<String,String>)ois.readObject());
			ois.close();
		} 
		catch (Exception e) {
			//e.printStackTrace();
			// TODO: Warn the user that Artists DB does not exist and offer to get it
			System.out.println("Artists DB does not exist, we should warn the user");
		}
		
		try {
			System.out.println("Loading tracks file");
			FileInputStream fin = new FileInputStream("tracks.db");
			ObjectInputStream ois = new ObjectInputStream(fin);
			songMap = ((HashMap<String,HashMap<String,String>>)ois.readObject());
			ois.close();
		} 
		catch (Exception e) {
			//e.printStackTrace();
			// TODO: Warn the user that Artists DB does not exist and offer to get it
			System.out.println("Tracks DB does not exist, we should warn the user");
		}
		
		try {
			System.out.println("Loading artist correction file");
			FileInputStream fin = new FileInputStream("artistcorrect.db");
			ObjectInputStream ois = new ObjectInputStream(fin);
			artistCorrections = ((HashMap<String,String>)ois.readObject());
			ois.close();
		} 
		catch (Exception e) {
			//e.printStackTrace();
			// TODO: Warn the user that Artists DB does not exist and offer to get it
			System.out.println("Artist correction DB does not exist, we should warn the user");

		}
	}

	public static void saveData() {
		try {
			System.out.println("Saving artists file");
			FileOutputStream fout = new FileOutputStream("artists.db");
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(artistMap);
			oos.close();
			
			System.out.println("Saving tracks file");
			FileOutputStream fout2 = new FileOutputStream("tracks.db");
			ObjectOutputStream oos2 = new ObjectOutputStream(fout2);
			oos2.writeObject(songMap);
			oos2.close();
			
			System.out.println("Saving artist correction file");
			FileOutputStream fout3 = new FileOutputStream("artistcorrect.db");
			ObjectOutputStream oos3 = new ObjectOutputStream(fout3);
			oos3.writeObject(artistCorrections);
			oos3.close();
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
