package org.mapeditor;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Utils {
	public final static String jpeg = "jpeg";
	public final static String jpg = "jpg";
	public final static String gif = "gif";
	public final static String tiff = "tiff";
	public final static String tif = "tif";
	public final static String png = "png";
	public final static String bmp = "bmp";
	
	/*
	 * Get the extension of a file.
	 */  
	public static String getExtension(File f) {
    String ext = null;
    String s = f.getName();
    int i = s.lastIndexOf('.');

    if (i > 0 &&  i < s.length() - 1) {
        ext = s.substring(i+1).toLowerCase();
    }
    return ext;
	}
	
	public static BufferedImage copy(BufferedImage p_img) {
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
			ImageIO.write(p_img, "JPG", out);
			
			return ImageIO.read(in);
		} 
		catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
