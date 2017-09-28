package kr.ko.nexmain.server.MissingU.common.utils;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Thumbnail {
	
	public static void createImage(String loadFile, String saveFile, int zoom, int width, int height) {
		try {
			File save = new File(saveFile);
			FileInputStream fis = new FileInputStream(loadFile);
			BufferedImage im = ImageIO.read(fis);
		
			if (zoom<=0) zoom = 1;
			
			BufferedImage thumb = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics2D g2 = thumb.createGraphics();
		
			g2.drawImage(im, 0, 0, width, height, null);
			ImageIO.write(thumb, "jpg", save);
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
