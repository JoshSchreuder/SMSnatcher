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
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JScrollPane;

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
public class ArtistMatcherFrame extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton cancelButton;
	private JButton okButton;
	private JScrollPane scrollPane;
	private String songLocation;
	private int row;
	//private ArrayList<String> matches;

	public ArtistMatcherFrame(String title, ArrayList<String> matches) {
		this.setModal(true);
		this.setTitle(title);
		//this.matches = matches;
		initGUI();
		GridBagLayout thisLayout = new GridBagLayout();
		this.setVisible(true);
		thisLayout.rowWeights = new double[] {0.1, 0.1};
		thisLayout.rowHeights = new int[] {7, 7};
		thisLayout.columnWeights = new double[] {0.1, 0.1, 0.1, 0.1};
		thisLayout.columnWidths = new int[] {7, 7, 7, 7};
		getContentPane().setLayout(thisLayout);
	}

	private void initGUI() {
		try {
			{
				{
					cancelButton = new JButton();
					getContentPane().add(cancelButton, new GridBagConstraints(2, 2, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
					cancelButton.setText("Cancel");
					cancelButton.setPreferredSize(new java.awt.Dimension(133, 29));
					//cancelButton.addMouseListener(new LyricBoxCancelListener(this));
				}
				{
					okButton = new JButton();
					getContentPane().add(okButton, new GridBagConstraints(0, 2, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
					okButton.setText("OK");
					okButton.setPreferredSize(new java.awt.Dimension(128, 29));
					//okButton.addMouseListener(new LyricBoxOKListener(this));
				}
				{
					scrollPane = new JScrollPane();
					getContentPane().add(scrollPane, new GridBagConstraints(0, 0, 4, 2, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
					scrollPane.setPreferredSize(new java.awt.Dimension(268, 214));
				}
			}
			{
				this.setSize(500, 500);
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

}
