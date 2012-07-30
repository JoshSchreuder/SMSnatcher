package controller;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import model.SMDataParser;

public class UpdateArtistsOptionListener implements ActionListener {

	public void actionPerformed(ActionEvent e) {
		if(JOptionPane.showConfirmDialog((Component)e.getSource(), "Are you sure you want to update?","This will take a long time, proceed?",JOptionPane.YES_NO_OPTION) == 0) {
			SMDataParser.parseArtistDirectory();
		}
	}

}
