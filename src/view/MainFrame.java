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
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringBufferInputStream;
import java.util.Properties;
import java.util.logging.LogManager;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;

import model.SongTableModel;
import controller.AboutMenuListener;
import controller.ExitMenuListener;
import controller.GoButtonListener;
import controller.LoadDataSwingWorker;
import controller.OpenFilesystemButtonListener;
import controller.OverwriteLyricsOptionListener;
import controller.RowDoubleClickListener;
import controller.UseLyricWikiOptionListener;
import controller.UseSongMeaningsOptionListener;
import controller.WindowCloseListener;

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
@SuppressWarnings({ "serial", "deprecation" })
public class MainFrame extends javax.swing.JFrame {

	{
		//Set Look & Feel
		try {
			javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
			/* if ("GTK look and feel".equals(javax.swing.UIManager.getLookAndFeel().getName())){
				javax.swing.UIManager.put("FileChooserUI", "eu.kostia.gtkjfilechooser.ui.GtkFileChooserUI");
			} */
			LogManager.getLogManager().readConfiguration(new StringBufferInputStream("org.jaudiotagger.level = OFF"));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	private JMenuBar menuBar;
	private static JCheckBoxMenuItem overwriteLyricsOption;
	private static JMenuItem useSongMeaningsOption;
	private static JCheckBoxMenuItem useLyricWikiOption;
	private JMenuItem aboutItem;
	private JMenu optionsMenu;
	private static JButton goButton;
	private static JTable musicList;
	private JMenu helpMenu;
	private static JProgressBar progressBar;
	private static JLabel statusTextLabel;
	private JPanel statusPanel;
	private static JTextField fileLocationTextField;
	private static JButton openButton;
	private JMenuItem exitItem;
	private JMenu fileMenuItem;
	private static Properties options = new Properties();
	
	public static String VERSION_NUM = "v0.07 Beta";

	public MainFrame() {
		super();
		initGUI();
		this.setTitle("SMSnatcher "+VERSION_NUM);
		this.setVisible(true);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowCloseListener());
		try {
			FileInputStream in = new FileInputStream(getSettingsDirectory()+"/options.ini");
			options.load(in);
			setOptionsInGUI();
		} catch (Exception e) {
			// There was an error with config, need to set defaults
			System.out.println("Options file does not exist, creating default!");
			options.setProperty("LastDir", System.getProperty("user.home"));
			options.setProperty("OverwriteLyrics", "false");
			options.setProperty("UseLyricWiki", "true");
			options.setProperty("UseSongMeanings", "false");
			setOptionsInGUI();
		}
		new LoadDataSwingWorker().execute();
	}
	
	public static File getSettingsDirectory() {
	    String userHome = System.getProperty("user.home");
	    if(userHome == null) {
	        throw new IllegalStateException("user.home==null");
	    }
	    File home = new File(userHome);
	    File settingsDirectory = new File(home, ".smsnatcher");
	    if(!settingsDirectory.exists()) {
	        if(!settingsDirectory.mkdir()) {
	            throw new IllegalStateException(settingsDirectory.toString());
	        }
	    }
	    return settingsDirectory;
	}
	
	public static void setOptionsInGUI() {
		if (options.getProperty("OverwriteLyrics").compareTo("true") == 0)
			MainFrame.overwriteLyricsOption.setSelected(true);
		else
			MainFrame.overwriteLyricsOption.setSelected(false);
		
		if (options.getProperty("UseLyricWiki").compareTo("true") == 0)
			MainFrame.useLyricWikiOption.setSelected(true);
		else
			MainFrame.useLyricWikiOption.setSelected(false);
		
		if (options.getProperty("UseSongMeanings").compareTo("true") == 0)
			MainFrame.useSongMeaningsOption.setSelected(true);
		else
			MainFrame.useSongMeaningsOption.setSelected(false);
	}
	
	public static void saveOptions() {
		try {
			FileOutputStream out = new FileOutputStream(getSettingsDirectory()+"/options.ini");
			options.store(out, "---Last Updated---");
			out.close();
		} catch (IOException e) {
			System.out.println("Error saving options file");
		}
	}
	
	public static JTextField getLocationField() {
		return fileLocationTextField;
	}
	
	public static JButton getGoButton() {
		return goButton;
	}
	
	public static JButton getOpenButton() {
		return openButton;
	}
	
	public static Properties getOptions() {
		return options;
	}
	
	public static JTable getSongTable() {
		return musicList;
	}
	
	public static JProgressBar getProgressBar() {
		return progressBar;
	}
	
	public static JLabel getStatusLabel() {
		return statusTextLabel;
	}
	
	public static boolean overwriteLyrics() {
		if(overwriteLyricsOption.isSelected())
			return true;
		else
			return false;
	}
	
	public static boolean useSongMeanings() {
		if(useSongMeaningsOption.isSelected())
			return true;
		else
			return false;
	}
	
	public static boolean useLyricWiki() {
		if(useLyricWikiOption.isSelected())
			return true;
		else
			return false;
	}
	
	private void initGUI() {
		try {
			GridBagLayout thisLayout = new GridBagLayout();
			thisLayout.rowWeights = new double[] {0.1, 0.1, 0.1, 0.1};
			thisLayout.rowHeights = new int[] {1, 1, 1, 2};
			thisLayout.columnWeights = new double[] {0.1, 0.1};
			thisLayout.columnWidths = new int[] {1, 7};
			getContentPane().setLayout(thisLayout);
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			{
				fileLocationTextField = new JTextField();
				getContentPane().add(fileLocationTextField, new GridBagConstraints(0, 0, 1, 2, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
				fileLocationTextField.setBounds(12, 12, 351, 43);
				fileLocationTextField.setEditable(false);
				fileLocationTextField.setEnabled(false);
			}
			{
				openButton = new JButton();
				getContentPane().add(openButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
				openButton.setText("Open");
				openButton.setBounds(375, 12, 66, 19);
				openButton.setLayout(null);
				openButton.addMouseListener(new OpenFilesystemButtonListener());
			}
			{
				statusPanel = new JPanel();
				BorderLayout statusPanelLayout = new BorderLayout();
				statusPanel.setLayout(statusPanelLayout);
				getContentPane().add(statusPanel, new GridBagConstraints(0, 3, 2, 1, 0.0, 0.8, GridBagConstraints.LINE_END, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
				statusPanel.setBounds(-4, 269, 469, 31);
				statusPanel.setBorder(BorderFactory.createEtchedBorder(BevelBorder.LOWERED));
				{
					statusTextLabel = new JLabel();
					statusPanel.add(statusTextLabel, BorderLayout.WEST);
					statusTextLabel.setText("Status: ");
					statusTextLabel.setBounds(20, 2, 321, 26);
				}
				{
					progressBar = new JProgressBar();
					statusPanel.add(progressBar, BorderLayout.EAST);
					progressBar.setBounds(353, 5, 89, 21);
				}
			}
			{
				goButton = new JButton();
				getContentPane().add(goButton, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
				goButton.setText("Go!");
				goButton.setBounds(375, 36, 66, 19);
				goButton.setEnabled(false);
				goButton.addMouseListener(new GoButtonListener());
			}
			{
				JScrollPane scrollPane = new JScrollPane(musicList);
				getContentPane().add(scrollPane, new GridBagConstraints(0, 2, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
				scrollPane.setBounds(12, 61, 429, 192);
				{
					musicList = new JTable(new SongTableModel());
					BorderLayout musicListLayout = new BorderLayout();
					musicList.setLayout(musicListLayout);
					scrollPane.setViewportView(musicList);
					musicList.setFillsViewportHeight(true);
					musicList.addMouseListener(new RowDoubleClickListener());
					musicList.setAutoscrolls(true);
					musicList.setEnabled(false);
					//musicList.setBounds(14, 110, 427, 190);
				}
			}
			{
				menuBar = new JMenuBar();
				setJMenuBar(menuBar);
				{
					fileMenuItem = new JMenu();
					menuBar.add(fileMenuItem);
					fileMenuItem.setText("File");
					{
						exitItem = new JMenuItem();
						fileMenuItem.add(exitItem);
						exitItem.setText("Exit");
						exitItem.addActionListener(new ExitMenuListener());
					}
				}
				{
					optionsMenu = new JMenu();
					menuBar.add(optionsMenu);
					optionsMenu.setText("Options");
					{
						overwriteLyricsOption = new JCheckBoxMenuItem();
						optionsMenu.add(overwriteLyricsOption);
						overwriteLyricsOption.setText("Overwrite Lyrics");
						overwriteLyricsOption.addItemListener(new OverwriteLyricsOptionListener());
					}
					{
						useLyricWikiOption = new JCheckBoxMenuItem();
						optionsMenu.add(useLyricWikiOption);
						useLyricWikiOption.setText("Use LyricWiki");
						useLyricWikiOption.addItemListener(new UseLyricWikiOptionListener());
					}
					{
						useSongMeaningsOption = new JCheckBoxMenuItem();
						optionsMenu.add(useSongMeaningsOption);
						useSongMeaningsOption.setText("Use SongMeanings");
						useSongMeaningsOption.addItemListener(new UseSongMeaningsOptionListener());
					}
				}
				{
					helpMenu = new JMenu();
					menuBar.add(helpMenu);
					helpMenu.setText("Help");
					{
						aboutItem = new JMenuItem();
						helpMenu.add(aboutItem);
						aboutItem.setText("About");
						aboutItem.addMouseListener(new AboutMenuListener());
					}
				}
			}
			pack();
			this.setMinimumSize(new Dimension(600, 600));
			this.setSize(600, 600);
		} catch (Exception e) {
		    //add your error handling code here
			e.printStackTrace();
		}
	}
}
