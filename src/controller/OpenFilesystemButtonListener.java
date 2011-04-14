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

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;

import view.MainFrame;

public class OpenFilesystemButtonListener  implements MouseListener {
	private File file = null;
	
	public void mouseClicked(MouseEvent e) {
		if(((JButton)e.getSource()).isEnabled()) {
			final JFileChooser fc = new JFileChooser(MainFrame.getOptions().getProperty("LastDir"));
			fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int returnVal = fc.showOpenDialog((Component)e.getSource());
			 if (returnVal == JFileChooser.APPROVE_OPTION) {
		        file = fc.getSelectedFile();
		        new OpenFilesystemSwingWorker(file).execute();
			} 
			else {
				if(MainFrame.getLocationField().getText().isEmpty()) {
					MainFrame.getLocationField().setText("");
					MainFrame.getGoButton().setEnabled(false);
				}
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
