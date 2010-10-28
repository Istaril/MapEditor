package org.mapeditor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

public class ImageTilePanel extends JLayeredPane {
	private static final long serialVersionUID = 793708589425549264L;
	private ImageIcon m_background = new ImageIcon();
	private JLabel m_cursor = null;
	private JLabel m_backgroundLabel = null;
	private BufferedImage m_imageBackground = null;
	private BufferedImage m_selectedTile = null;
	private Point m_selectedTilePosition = null;
	
	public ImageTilePanel() {
		super();
		setBackground(Color.GRAY);
		m_imageBackground = ImageCache.load("./assets/default.png");
		ImageIcon imgCursor = new ImageIcon(ImageCache.load("./assets/cursor.png"));
		m_cursor = new JLabel(imgCursor);
		m_background.setImage(m_imageBackground);
		m_backgroundLabel = new JLabel(m_background);
		m_backgroundLabel.setSize(m_imageBackground.getWidth(), m_imageBackground.getHeight());
		m_backgroundLabel.addMouseListener(new MouseListener(this));
		
		m_cursor.setBounds(0, 0,  m_imageBackground.getWidth(), m_imageBackground.getHeight());
		add(m_backgroundLabel, JLayeredPane.DEFAULT_LAYER);
		
		m_cursor.setBounds(0, 0,  Constantes.TILE_SIZE, Constantes.TILE_SIZE);
		add(m_cursor, JLayeredPane.PALETTE_LAYER);
}
	
	public void setBackgroundImage(BufferedImage p_img) {
		m_imageBackground = p_img;
		m_background.setImage(m_imageBackground);
		Dimension d = new Dimension(p_img.getWidth(), p_img.getHeight());
		setSize(d);
		m_backgroundLabel.setSize(d);
		repaint();
	}
	
	public BufferedImage getSelectedTile() {
		return m_selectedTile;
	}
	
	public BufferedImage getBackgroundImage() {
		 return m_imageBackground;
	}
	
	public void changeSelectedTile(BufferedImage p_img) {
		if (p_img != null) {
			Graphics2D g = null;
	
			try {
				m_selectedTile = p_img;
				g = m_imageBackground.createGraphics();
				g.drawImage(m_selectedTile, m_selectedTilePosition.x, m_selectedTilePosition.y, Constantes.TILE_SIZE, Constantes.TILE_SIZE, null);
			}
			finally {
				if (g != null) {
					g.dispose();
				}
			}
		}
	}
	
	private class MouseListener extends MouseAdapter {
		private ImageTilePanel m_imagePanel = null;
		
		public MouseListener(ImageTilePanel p_imagePanel) {
			m_imagePanel = p_imagePanel;
		}
		
		@Override public void mousePressed(MouseEvent e) {
			Graphics2D g = null;
			
			try {			
				Point p = getImageLocation(e.getPoint());
				g = m_imageBackground.createGraphics();
				m_selectedTile = m_imageBackground.getSubimage(p.x, p.y, Constantes.TILE_SIZE, Constantes.TILE_SIZE);
				m_cursor.setBounds(p.x, p.y, Constantes.TILE_SIZE, Constantes.TILE_SIZE);
				m_selectedTilePosition = p;
			}
			finally {
				if (g != null) {
					g.dispose();
				}
			}
			
			m_imagePanel.repaint();
		}
	}
	
	@Override public void addMouseListener(java.awt.event.MouseListener p_event) {
		m_backgroundLabel.addMouseListener(p_event);
	}
	
	private Point getImageLocation(Point p_point) {
		Point p = new Point();
		p.x = p_point.x / Constantes.TILE_SIZE * Constantes.TILE_SIZE;
		p.y = p_point.y / Constantes.TILE_SIZE * Constantes.TILE_SIZE;
		return p;
	}
}
