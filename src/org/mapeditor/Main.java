package org.mapeditor;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Main implements Runnable {
	public static void main(String[] p_agrs) {
		 try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} 
		 catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
		 catch (InstantiationException e) {
			e.printStackTrace();
		} 
		 catch (IllegalAccessException e) {
			e.printStackTrace();
		} 
		 catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		SwingUtilities.invokeLater(new Main());
	}

	@Override
	public void run() {
		new MainWindow();
	}
}
