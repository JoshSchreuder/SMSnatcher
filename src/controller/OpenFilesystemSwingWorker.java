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
		MainFrame.getLocationField().setText(file.getAbsolutePath());
        MainFrame.getOptions().setProperty("LastDir", file.getAbsolutePath());
        try {
			List<File> fileList = FileLister.getFileListing(file);
			MainFrame.getStatusLabel().setText("Status: loading MP3 files");
			MainFrame.getProgressBar().setMinimum(0);
			MainFrame.getProgressBar().setValue(0);
			MainFrame.getProgressBar().setMaximum(fileList.size());
			for (File mp3File : fileList) {
				Vector<String> song = MP3TagHandler.getTags(mp3File);
				song.add(mp3File.getAbsolutePath());
				if (song != null) {
					((SongTableModel)MainFrame.getSongTable().getModel()).addRow(song);
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
		MainFrame.getStatusLabel().setText("Status: loading done.");
		return null;
	}

}
