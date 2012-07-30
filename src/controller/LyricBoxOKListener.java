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

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import model.Logger;
import model.MP3TagHandler;

import view.LyricEditFrame;
import view.MainFrame;

public class LyricBoxOKListener implements MouseListener {

	private LyricEditFrame lyricEditFrame;
	
	public LyricBoxOKListener(LyricEditFrame lyricEditFrame) {
		this.lyricEditFrame = lyricEditFrame;
	}
	
	public void mouseClicked(MouseEvent e) {
		// Save lyrics from box to track
		String lyrics = lyricEditFrame.getLyricBox().getText();
		Logger.LogToStatusBar("Mouse clicked OK to save, saving lyrics...");
		MP3TagHandler.saveLyrics(lyrics, lyricEditFrame.getSongLocation());
		MainFrame.getSongTable().getModel().setValueAt(lyrics, lyricEditFrame.getRow(), 2);
		lyricEditFrame.dispose();
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
