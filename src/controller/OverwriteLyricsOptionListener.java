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

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JCheckBoxMenuItem;

import view.MainFrame;

public class OverwriteLyricsOptionListener implements ItemListener {

	@Override
	public void itemStateChanged(ItemEvent e) {
		if(((JCheckBoxMenuItem)e.getSource()).isSelected() == true)
			MainFrame.getOptions().setProperty("OverwriteLyrics", "true");
		else
			MainFrame.getOptions().setProperty("OverwriteLyrics", "false");
	}

}
