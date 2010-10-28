package org.mapeditor;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;

import org.mapeditor.fileFilters.ImageFilter;

public class MainWindow extends JFrame {
	private static final long serialVersionUID = 3787214343111930415L;
	private ImageTilePanel m_mapImagePanel = new ImageTilePanel();
	private ImageTilePanel m_tileSetImagePanel = new ImageTilePanel();
	
	public MainWindow() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenuItem fileOpen = new JMenuItem("Open Tile set...");
		fileOpen.addActionListener(new OpenImageMenu(this));
		fileMenu.add(fileOpen);
		menuBar.add(fileMenu);
		setJMenuBar(menuBar);
		
		getContentPane().setBackground(Color.BLUE);
		getContentPane().setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(1, 1, 1, 1);
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.LINE_START;
		m_mapImagePanel.addMouseListener(new MapChangeTile(this));
		m_mapImagePanel.setBackgroundImage(new BufferedImage(1024, 1024, BufferedImage.TYPE_INT_ARGB));
		
		JScrollPane mapPanel = new JScrollPane(m_mapImagePanel);
		mapPanel.setPreferredSize(new Dimension(256, 256));
		mapPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		mapPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		getContentPane().add(mapPanel, c);
		
		c.gridx = 1;
		c.gridy = 0;
		c.insets = new Insets(1, 1, 1, 1);
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.LINE_END;
		m_tileSetImagePanel.setMinimumSize(new Dimension(64, 32));
		JScrollPane tilePanel = new JScrollPane(m_tileSetImagePanel);
		tilePanel.setPreferredSize(new Dimension(256, 256));
		tilePanel.revalidate();
		getContentPane().add(tilePanel, c);
		
		pack();
		setVisible(true);
	}
	
	final private class OpenImageMenu implements ActionListener {
		private Component m_component = null;
		
		public OpenImageMenu(Component p_component) {
			m_component = p_component;
		}
		
		@Override public void actionPerformed(ActionEvent e) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileFilter(new ImageFilter());
			int returnType = fileChooser.showOpenDialog(m_component);
			
			if (returnType == JFileChooser.APPROVE_OPTION) {
				m_tileSetImagePanel.setBackgroundImage(ImageCache.load((fileChooser.getSelectedFile())));
				
			}
		}	
	}
	
	final private class MapChangeTile extends MouseAdapter  {
		private MainWindow m_windows = null;
		
		public MapChangeTile(MainWindow p_windows) {
			m_windows = p_windows;
		}
		
		@Override public void mousePressed(MouseEvent e) {
			m_windows.m_mapImagePanel.changeSelectedTile(m_tileSetImagePanel.getSelectedTile());
		}	
	}
}
