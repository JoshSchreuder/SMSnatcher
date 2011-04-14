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

import model.DataManager;
import view.MainFrame;

public class LoadDataSwingWorker extends SwingWorker<String, Object> {
	
	
	public LoadDataSwingWorker() {
	}

	protected String doInBackground() throws Exception {
		MainFrame.getOpenButton().setEnabled(false);
        MainFrame.getGoButton().setEnabled(false);
        MainFrame.getSongTable().setEnabled(false);
		
        if(DataManager.verifyResourcesOnDisk() == false) {
        	MainFrame.getStatusLabel().setText("Status: extracting DB files");
        	DataManager.extractResourcesToDisk();
        }
        MainFrame.getStatusLabel().setText("Status: loading DB files");
        DataManager.loadData();
		
		MainFrame.getOpenButton().setEnabled(true);
		MainFrame.getGoButton().setEnabled(true);
		MainFrame.getSongTable().setEnabled(true);
		MainFrame.getStatusLabel().setText("Status: loading done.");
		return null;
	}

}
