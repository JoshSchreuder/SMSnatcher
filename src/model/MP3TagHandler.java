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

import java.io.File;
import java.io.IOException;
import java.util.Vector;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.CannotWriteException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;

public class MP3TagHandler {
	public static Vector<String> getTags(File mp3) {
		try {
			//System.out.println("Getting tags for file " + mp3.getAbsolutePath());
			AudioFile f = AudioFileIO.read(mp3);
			Tag tag = f.getTag();
			Vector<String> song = new Vector<String>();
			song.add(tag.getFirst(FieldKey.ARTIST));
			song.add(tag.getFirst(FieldKey.TITLE));
			song.add(tag.getFirst(FieldKey.LYRICS));
			return song;
		} catch (CannotReadException e1) {
			// TODO Auto-generated catch block
			// e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			// e1.printStackTrace();
		} catch (TagException e1) {
			// TODO Auto-generated catch block
			// e1.printStackTrace();
		} catch (ReadOnlyFileException e1) {
			// TODO Auto-generated catch block
			// e1.printStackTrace();
		} catch (InvalidAudioFrameException e1) {
			// TODO Auto-generated catch block
			// e1.printStackTrace();
		}
		return null;
	}
	
	public static int saveLyrics(String lyrics, String fileLocation) {
		try {
			File mp3 = new File(fileLocation);
			AudioFile f = AudioFileIO.read(mp3);
			Tag tag = f.getTag();
			tag.setField(FieldKey.LYRICS, lyrics);
			f.commit();
			return 1;
		} catch (CannotReadException e1) {
			// TODO Auto-generated catch block
			// e1.printStackTrace();
		} catch (CannotWriteException e1) {
			// TODO Auto-generated catch block
			// e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			// e1.printStackTrace();
		} catch (TagException e1) {
			// TODO Auto-generated catch block
			// e1.printStackTrace();
		} catch (ReadOnlyFileException e1) {
			// TODO Auto-generated catch block
			// e1.printStackTrace();
		} catch (InvalidAudioFrameException e1) {
			// TODO Auto-generated catch block
			// e1.printStackTrace();
		}
		return 0;
	}
}
