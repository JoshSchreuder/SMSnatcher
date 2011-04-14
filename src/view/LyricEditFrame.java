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
package view;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import controller.LyricBoxCancelListener;
import controller.LyricBoxOKListener;

/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class LyricEditFrame extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton cancelButton;
	private JButton okButton;
	private JTextArea lyricBox;
	private JScrollPane scrollPane;
	private String songLocation;
	private int row;

	public LyricEditFrame(String title, String location, String lyrics, int row) {
		this.setModal(true);
		this.setTitle(title);
		this.songLocation = location;
		this.row = row;
		lyricBox = new JTextArea();
		lyricBox.setText(lyrics);
		initGUI();
		lyricBox.select(0, 0);
		this.setVisible(true);
		BorderLayout thisLayout = new BorderLayout();
		getContentPane().setLayout(thisLayout);
	}

	private void initGUI() {
		try {
			{
				{
					scrollPane = new JScrollPane(lyricBox);
					{
						
						scrollPane.setViewportView(lyricBox);
						scrollPane.setPreferredSize(new java.awt.Dimension(437, 418));
						//lyricBox.setPreferredSize(new java.awt.Dimension(450, 500));
					}
				}
				{
					cancelButton = new JButton();
					getContentPane().add(scrollPane, BorderLayout.NORTH);
					getContentPane().add(cancelButton, BorderLayout.EAST);
					cancelButton.setText("Cancel");
					cancelButton.setPreferredSize(new java.awt.Dimension(215, 332));
					//cancelButton.setPreferredSize(new java.awt.Dimension(215, 187));
					cancelButton.addMouseListener(new LyricBoxCancelListener(this));
				}
				{
					okButton = new JButton();
					getContentPane().add(okButton, BorderLayout.CENTER);
					okButton.setText("OK");
					okButton.setPreferredSize(new java.awt.Dimension(225, 367));
					//okButton.setPreferredSize(new java.awt.Dimension(440, 268));
					okButton.addMouseListener(new LyricBoxOKListener(this));
				}
			}
			{
				this.setSize(450, 500);
				this.setMinimumSize(new Dimension(450, 500));
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public int getRow() {
		return row;
	}
	
	public String getSongLocation() {
		return songLocation;
	}
	
	public JTextArea getLyricBox() {
		return lyricBox;
	}

}
