package org.sits.pr.api.util;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Calendar;
import java.util.Iterator;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.sits.pr.api.exception.custom.ImageFormatNotSupportedException;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class FileUploadUtil {

	/**
	 * Gets image dimensions for given file
	 * 
	 * @param imgFile image file
	 * @return dimensions of image
	 * @throws IOException if the file is not a known image
	 */
	public static Dimension getImageDimension(File imgFile) throws IIOException, IOException {

		ImageInputStream stream = ImageIO.createImageInputStream(imgFile);
		Iterator<ImageReader> imageReaders = ImageIO.getImageReaders(stream);

		if (!imageReaders.hasNext()) {
			throw new ImageFormatNotSupportedException("Image is corrupted, use a valid image");
		}

		while (imageReaders.hasNext()) {
			ImageReader reader = (ImageReader) imageReaders.next();

			try {
				reader.setInput(stream);
				int width = reader.getWidth(reader.getMinIndex());
				int height = reader.getHeight(reader.getMinIndex());
				return new Dimension(width, height);
			} catch (IIOException ex) {
				ex.printStackTrace();
				throw ex;
			} catch (IOException e) {
				e.printStackTrace();
				throw e;
			} finally {
				reader.dispose();
			}
		}

		return null;
	}

	public static File saveFile(String dirPath, MultipartFile file) throws IIOException, IOException {

		String fileName = "";
		Path uploadPath = Paths.get(dirPath);
		Path filePath;

		if (!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);

		}

		try (InputStream inputStream = file.getInputStream()) {
			fileName = file.getOriginalFilename();
			String fileExt = fileName.substring(fileName.lastIndexOf('.'));
			fileName = "Image_" + FileUploadUtil.getCalendarDateAndTimeForFileNameSuffix() + fileExt;

			filePath = uploadPath.resolve(fileName);
			Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException ioe) {
			throw new IOException("Could not save image file: " + fileName, ioe);
		}

		return filePath.toFile();

	}
	
	public static File saveThumbnail(String dirPath, File file, int thumbnailWidth, int thumbnailHeight) throws IOException
	{
		Path uploadPath = Paths.get(dirPath);
		
		if (!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);

		}
		
		BufferedImage img = new BufferedImage(thumbnailWidth, thumbnailHeight, BufferedImage.TYPE_INT_RGB);
		String fileExt = file.getName().substring(file.getName().lastIndexOf('.'));
		log.info("fileExt: "+fileExt);
		img.createGraphics().drawImage(ImageIO.read(file).getScaledInstance(thumbnailWidth, thumbnailHeight, Image.SCALE_SMOOTH),0,0,null);
		String fileName = "Thumbnail_" + FileUploadUtil.getCalendarDateAndTimeForFileNameSuffix() + fileExt;
		log.info("fileName: "+fileName);
		//filePath = uploadPath.resolve(fileName);
		//File thumbnailFile = filePath.toFile();
		log.info("fileName with File Path: "+dirPath+fileName);
		
		boolean isCreated = ImageIO.write(img, "png", new File(dirPath+fileName));
		
		
		log.info("thumbnailFile created: "+isCreated);
		
		return new File(dirPath+fileName);
	}
	
	public BufferedImage scale(BufferedImage source,double ratio) {
		  int w = (int) (source.getWidth() * ratio);
		  int h = (int) (source.getHeight() * ratio);
		  BufferedImage bi = getCompatibleImage(w, h);
		  Graphics2D g2d = bi.createGraphics();
		  double xScale = (double) w / source.getWidth();
		  double yScale = (double) h / source.getHeight();
		  AffineTransform at = AffineTransform.getScaleInstance(xScale,yScale);
		  g2d.drawRenderedImage(source, at);
		  g2d.dispose();
		  return bi;
		}

		private BufferedImage getCompatibleImage(int w, int h) {
		  GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		  GraphicsDevice gd = ge.getDefaultScreenDevice();
		  GraphicsConfiguration gc = gd.getDefaultConfiguration();
		  BufferedImage image = gc.createCompatibleImage(w, h);
		  return image;
		}

	public static double getImageSizeInKB(File file) {
		return file.length() / (1024);
	}

	public static double getImageSizeInMB(File file) {
		return file.length() / (1024 * 1024);
	}

	public static String getCalendarDateAndTimeForFileNameSuffix() {
		String nameSuffix = "";
		Calendar calendar = Calendar.getInstance();

		nameSuffix += calendar.get(Calendar.YEAR);
		nameSuffix += calendar.get((Calendar.MONTH) + 1);
		nameSuffix += calendar.get(Calendar.DAY_OF_MONTH);
		nameSuffix += calendar.get(Calendar.HOUR_OF_DAY);
		nameSuffix += calendar.get(Calendar.MINUTE);
		nameSuffix += calendar.get(Calendar.SECOND);
		nameSuffix += calendar.get(Calendar.MILLISECOND);

		return nameSuffix;
	}
}
