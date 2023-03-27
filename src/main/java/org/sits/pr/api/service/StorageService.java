package org.sits.pr.api.service;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.imageio.IIOException;

import org.sits.pr.api.controller.ContentEditController;
import org.sits.pr.api.entity.ImageInfo;
import org.sits.pr.api.model.ImageFileInfo;
import org.sits.pr.api.repository.ImageInfoRepository;
import org.sits.pr.api.util.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StorageService {
	
	@Value("${org.sits.pr.api.directory.uploadedFiles}")
	private String dirPath;
	
	@Value("${org.sits.pr.api.thumbnailHeight}")
	private int thumbnailHeight;
	
	@Value("${org.sits.pr.api.thumbnailWidth}")
	private int thumbnailWidth;
	
	@Value("${org.sits.pr.api.imageMaxWidthCarousel}")
	private float imageMaxWidthCarousel;
	
	@Value("${org.sits.pr.api.imageMaxWidth}")
	private float imageMaxWidth;
	
	@Value("${org.sits.pr.api.imageMaxHeight}")
	private float imageMaxHeight;
	
	@Value("${org.sits.pr.api.imageTypesWithMaXWidth}")
	private String imageTypesWithMaXWidth;
	
	private float imageHeight;
	private float imageWidth;
	
	@Autowired
    private ImageInfoRepository imageInfoRepository;
	
	public ImageFileInfo uploadImage(MultipartFile file, String imageType) throws IllegalStateException, IIOException, IOException {
		
		ImageFileInfo imageFileInfo =new ImageFileInfo();
		File uploadedFile =  FileUploadUtil.saveFile(dirPath, file);
		File thumbnailFile = FileUploadUtil.saveThumbnail(dirPath, uploadedFile, thumbnailWidth, thumbnailHeight);
		double fileSize = FileUploadUtil.getImageSizeInKB(uploadedFile);
		Dimension imageDimension = FileUploadUtil.getImageDimension(uploadedFile);
		
		validateFileSize(imageDimension, imageType);
		
		imageFileInfo.setUploadedFileContextPath(uploadedFile.getAbsolutePath());
		imageFileInfo.setThumbnailFileContextPath(thumbnailFile.getAbsolutePath());
		imageFileInfo.setHeight(imageDimension.getHeight());
		imageFileInfo.setWidth(imageDimension.getWidth());
		imageFileInfo.setSize(fileSize);
		
		return imageFileInfo;
    }
	
	private  void validateFileSize(Dimension imageDimension, String imageType) throws IOException {
		if(imageTypesWithMaXWidth.contains(imageType))
		{
			imageHeight = imageMaxHeight;
			imageWidth = imageMaxWidthCarousel;
		}
		else {
			imageHeight = imageMaxHeight;
			imageWidth = imageMaxWidth;
		}
		
		
		if(imageDimension.getHeight() != imageHeight) {
		   throw new IOException("Image Height is not equal to " + imageHeight + "pixels.");
		}
		
		if(imageDimension.getWidth() != imageWidth) {
			throw new IOException("Image Width is not equal to " + imageWidth + "pixels.");
		}
	}

	public byte[] downloadImage(Long imageInfoId) throws IOException {
		ImageInfo imageInfo = imageInfoRepository.findByImageInfoIdAndImageIsActive(imageInfoId, Integer.valueOf(1));
        String filePath=imageInfo.getImageContextPath();
        byte[] images = Files.readAllBytes(new File(filePath).toPath());
        return images;
	}


	public void deleteImage(Long imageInfoId) {
		ImageInfo imageInfo = imageInfoRepository.findByImageInfoIdAndImageIsActive(imageInfoId, Integer.valueOf(1));
		imageInfo.setImageIsActive(0);
		imageInfo.setImageName("Image Deleted " + imageInfoId );
		imageInfoRepository.save(imageInfo);
	}

	public byte[] downloadThumbnailImage(Long imageInfoId) throws IOException{
		ImageInfo imageInfo = imageInfoRepository.findByImageInfoIdAndImageIsActive(imageInfoId, Integer.valueOf(1));
        String filePath=imageInfo.getThumbnailContextPath();
        byte[] images = Files.readAllBytes(new File(filePath).toPath());
        return images;
	}

}
