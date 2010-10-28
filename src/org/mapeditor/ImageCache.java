package org.mapeditor;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.WeakHashMap;

import javax.imageio.ImageIO;

final public class ImageCache {
	private static final Map<String, BufferedImage> m_cache = new WeakHashMap<String, BufferedImage>();
	private static final Color KEY_COLOR_FOR_TRANSPARENCY = new Color(111, 111, 111);
	private static final Color TRANSPARENT_COLOR = new Color(111, 111, 111, 0);
	
	public static BufferedImage load(String p_path) {
		if (m_cache.get(p_path) == null) {
			Graphics2D g = null;
			try {
				BufferedImage source = ImageIO.read(new File(p_path));
				
				for(int i = 0; i < source.getHeight(); i++) {  
					for(int j = 0; j < source.getWidth(); j++) {  
						if(source.getRGB(j, i) == KEY_COLOR_FOR_TRANSPARENCY.getRGB()) {  
							source.setRGB(j, i, TRANSPARENT_COLOR.getRGB());
						}  
					}  
				}  
				
				m_cache.put(p_path, source);
			} 
			catch (IOException e) {
				e.printStackTrace();
				return null;
			}		
			finally {
				if (g != null) {
					g.dispose();
				}
			}
		}
		
		return m_cache.get(p_path);
	}
	
	public static BufferedImage load(File p_file) {		
		try {
			return load(p_file.getCanonicalPath());
		} 
		catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
