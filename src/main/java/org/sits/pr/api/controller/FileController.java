package org.sits.pr.api.controller;

import java.io.IOException;

import org.sits.pr.api.entity.ContainerImageInfo;
import org.sits.pr.api.entity.ImageInfo;
import org.sits.pr.api.model.ImageFileInfo;
import org.sits.pr.api.service.ImageInfoService;
import org.sits.pr.api.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Operation;



@RestController
@RequestMapping("/image")
public class FileController {
	
	@Autowired
	private StorageService storageService;
	
	@Autowired
	private ImageInfoService imageInfoService;
	
	
	@PostMapping(path="/upload", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	@Operation(summary="Upload Image for a container"
	, description="Upload image for a container, pass on the contianer id, image type, "
			+ "active flag and details of the image entered by the user. "
			+ "Image gets uploaded and the information gets updated in the database as well."
			+ "Same image uploaded to the same container, will update the existing record.")
	public ContainerImageInfo uploadImage(@RequestParam("file") MultipartFile uploadfile, ImageInfo imageInfo) throws Exception{
		ImageFileInfo uploadedFile;
		ContainerImageInfo containerImageInfo = null;
	
		uploadedFile = storageService.uploadImage(uploadfile, imageInfo.getImageType());
		
		if (uploadedFile != null ) {
			imageInfo.setImageContextPath(uploadedFile.getUploadedFileContextPath());
			imageInfo.setImageURL(uploadedFile.getUploadedFileContextPath());
			
			imageInfo.setThumbnailContextPath(uploadedFile.getThumbnailFileContextPath());
			imageInfo.setThumbnailURL(uploadedFile.getThumbnailFileContextPath());
			
			imageInfo.setImageHeight(uploadedFile.getHeight());
			imageInfo.setImageWidth(uploadedFile.getWidth());
			imageInfo.setImageSize(uploadedFile.getSize());
			
			containerImageInfo = imageInfoService.saveImageInfo(imageInfo);
			//imageInfo.setImageContextPath("");
			//imageInfo.setThumbnailContextPath("");
		}

		return containerImageInfo;
	}

	
	@GetMapping("/download/{imageInfoId}")
	@Operation(summary="Download Image for a container"
	, description="Download the image for the given image info id")
	public ResponseEntity<?> downloadImage(@PathVariable Long imageInfoId) throws IOException {
		byte[] imageData=storageService.downloadImage(imageInfoId);
		return ResponseEntity.status(HttpStatus.OK)
				.contentType(MediaType.valueOf("image/png"))
				.body(imageData);

	}
	
	@GetMapping("/thumbnail/download/{imageInfoId}")
	@Operation(summary="Download Image for a container"
	, description="Download the image for the given image info id")
	public ResponseEntity<?> downloadThumbnailImage(@PathVariable Long imageInfoId) throws IOException {
		byte[] imageData=storageService.downloadThumbnailImage(imageInfoId);
		return ResponseEntity.status(HttpStatus.OK)
				.contentType(MediaType.valueOf("image/png"))
				.body(imageData);

	}
	
	
	@PostMapping("/delete/{imageInfoId}")
	@Operation(summary="Delete Image for a container"
	, description="Inctivate the image for the given image info id. It won't hard delete the image")
	public ResponseEntity<?> deleteImage(@PathVariable Long imageInfoId) throws IOException {
		storageService.deleteImage(imageInfoId);
		return ResponseEntity.status(HttpStatus.OK).body("File Deleted Successfully.");

	}

}
