package model;

import view.MainFrame;

public class Logger {
	public static void LogToStatusBar(String text) {
		System.out.println(text);
		MainFrame.getStatusLabel().setText("Status: " + text);
	}
}
