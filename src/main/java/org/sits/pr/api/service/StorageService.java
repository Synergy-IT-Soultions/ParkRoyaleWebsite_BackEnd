package org.sits.pr.api.service;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.imageio.IIOException;
import org.sits.pr.api.entity.ImageInfo;
import org.sits.pr.api.model.ImageFileInfo;
import org.sits.pr.api.repository.ImageInfoRepository;
import org.sits.pr.api.util.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class StorageService {
	
	@Value("${org.sits.pr.api.directory.uploadedFiles}")
	private String dirPath;
	
	@Value("${org.sits.pr.api.thumbnailHeight}")
	private int thumbnailHeight;
	
	@Value("${org.sits.pr.api.thumbnailWidth}")
	private int thumbnailWidth;
	
	@Autowired
    private ImageInfoRepository imageInfoRepository;
	
	public ImageFileInfo uploadImage(MultipartFile file) throws IllegalStateException, IIOException, IOException {
		
		ImageFileInfo imageFileInfo =new ImageFileInfo();
		File uploadedFile =  FileUploadUtil.saveFile(dirPath, file);
		File thumbnailFile = FileUploadUtil.saveThumbnail(dirPath, uploadedFile, thumbnailWidth, thumbnailHeight);
		double fileSize = FileUploadUtil.getImageSizeInKB(uploadedFile);
		Dimension imageDimension = FileUploadUtil.getImageDimension(uploadedFile);
		
		imageFileInfo.setUploadedFileContextPath(uploadedFile.getAbsolutePath());
		imageFileInfo.setThumbnailFileContextPath(thumbnailFile.getAbsolutePath());
		imageFileInfo.setHeight(imageDimension.getHeight());
		imageFileInfo.setWidth(imageDimension.getWidth());
		imageFileInfo.setSize(fileSize);
		
		return imageFileInfo;
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
		imageInfoRepository.save(imageInfo);
	}

	public byte[] downloadThumbnailImage(Long imageInfoId) throws IOException{
		ImageInfo imageInfo = imageInfoRepository.findByImageInfoIdAndImageIsActive(imageInfoId, Integer.valueOf(1));
        String filePath=imageInfo.getThumbnailContextPath();
        byte[] images = Files.readAllBytes(new File(filePath).toPath());
        return images;
	}

}
