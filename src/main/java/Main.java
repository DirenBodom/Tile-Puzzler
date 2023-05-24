import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;

public class Main {
	public static void main(String[] args) {
		// Open native library
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		// Instantiate Imgcodecs class
		Imgcodecs imageCodecs = new Imgcodecs();
		String path = getPath("BoatDefault.jpg");
		Mat img = imageCodecs.imread(path);
		MatOfByte mat = new MatOfByte();
		Imgcodecs.imencode(".jpg", img, mat);
		byte[] byteArray = mat.toArray();
		InputStream in = new ByteArrayInputStream(byteArray);
		BufferedImage buf = null;
		try {
			buf = ImageIO.read(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JFrame fr = new JFrame();
		fr.getContentPane().add(new JLabel(new ImageIcon(buf)));
		fr.pack();
		fr.setVisible(true);
		
		System.out.println("Image loaded.");
		
	}
	/**
	 * 
	 * @param s A string containing a path
	 * @return Formatted string with double slashes and no leading forward slash
	 */
	public static String pathFormatter(String s) {
		// Remove the leading forward slash then convert all remaining forward slashes to backslashes.
		String s1 = s.substring(1);
		return s1.replace("/", "\\\\");
	}
	/**
	 * 
	 * @param fileName The name of the file we want. This file should be in the resources folder.
	 * @return Return the path of the file as a string.
	 */
	public static String getPath(String fileName) {
		String path = Main.class.getResource(fileName).getFile();
		try {
			path = URLDecoder.decode(path, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pathFormatter(path);
	}
}
