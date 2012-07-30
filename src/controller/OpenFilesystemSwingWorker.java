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

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Vector;

import javax.swing.SwingWorker;

import model.FileLister;
import model.Logger;
import model.MP3TagHandler;
import model.SongTableModel;
import view.MainFrame;

public class OpenFilesystemSwingWorker extends SwingWorker<String, Object> {
	
	private File file;
	
	public OpenFilesystemSwingWorker(File file) {
		this.file = file;
	}

	protected String doInBackground() throws Exception {
		MainFrame.getOpenButton().setEnabled(false);
        MainFrame.getGoButton().setEnabled(false);
        MainFrame.getSongTable().setEnabled(false);
		((SongTableModel) MainFrame.getSongTable().getModel()).clearTable();
		Logger.LogToStatusBar(file.getAbsolutePath());
		MainFrame.getLocationField().setText(file.getAbsolutePath());
        MainFrame.getOptions().setProperty("LastDir", file.getAbsolutePath());
        try {
			List<File> fileList = FileLister.getFileListing(file);
			Logger.LogToStatusBar("Loading MP3 files");
			MainFrame.getProgressBar().setMinimum(0);
			MainFrame.getProgressBar().setValue(0);
			MainFrame.getProgressBar().setMaximum(fileList.size());
			for (File mp3File : fileList) {
				Logger.LogToStatusBar(mp3File.getName());
				Vector<String> song = MP3TagHandler.getTags(mp3File);
				if (song != null) {
					song.add(mp3File.getAbsolutePath());
					((SongTableModel)MainFrame.getSongTable().getModel()).addRow(song);
				}
				else {
					Logger.LogToStatusBar("There was an error loading this MP3!");
				}
				
				int value = MainFrame.getProgressBar().getValue() + 1;
		        if (value > MainFrame.getProgressBar().getMaximum()) {
		          value = MainFrame.getProgressBar().getMaximum();
		        }
		        MainFrame.getProgressBar().setValue(value);
			}
			MainFrame.getSongTable().setFillsViewportHeight(true);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		MainFrame.getOpenButton().setEnabled(true);
		MainFrame.getGoButton().setEnabled(true);
		MainFrame.getSongTable().setEnabled(true);
		Logger.LogToStatusBar("Loading done.");
		return null;
	}

}
