package org.sits.pr.api.service;

import java.sql.Date;

import org.sits.pr.api.entity.ContainerData;
import org.sits.pr.api.entity.ContainerImageInfo;
import org.sits.pr.api.entity.ImageInfo;
import org.sits.pr.api.exception.custom.ContainerNotFoundException;
import org.sits.pr.api.repository.ContainerDataRepository;
import org.sits.pr.api.repository.ContainerImageInfoRepository;
import org.sits.pr.api.repository.ImageInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class ImageInfoService {

	@Autowired
	private ImageInfoRepository imageInfoRepository;

	@Autowired
	private ContainerDataRepository containerDataRepository;

	@Autowired
	private ContainerImageInfoRepository containerImageInfoRepository;

	@Value("${org.sits.pr.api.downloadURL}")
	private String downloadURL;
	
	@Value("${org.sits.pr.api.downloadThumbnailURL}")
	private String downloadThumbnailURL;

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ContainerNotFoundException.class)
	public ContainerImageInfo saveImageInfo(ImageInfo imageInfo) throws ContainerNotFoundException{

		imageInfo.setUpdatedDate(new Date(System.currentTimeMillis()));
		ImageInfo savedImageInfo = imageInfoRepository.save(imageInfo);
		savedImageInfo = updateDownloadURL(savedImageInfo);
		savedImageInfo.setContainerDivId(imageInfo.getContainerDivId());
		savedImageInfo.setUpdatedBy(imageInfo.getUpdatedBy());
		return updateContainerImageInfo(savedImageInfo);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ContainerNotFoundException.class)
	public ImageInfo updateDownloadURL(ImageInfo tempImageInfo) {
		
		long imageInfoId = tempImageInfo.getImageInfoId();
		log.debug("imageInfoId: " + imageInfoId);
		
		tempImageInfo.setImageURL(downloadURL + imageInfoId);
		log.debug("ImageURL: " + downloadURL + imageInfoId);
		
		tempImageInfo.setThumbnailURL(downloadThumbnailURL + imageInfoId);
		log.debug("ThumbnailURL: " + downloadThumbnailURL + imageInfoId);
		
		tempImageInfo = imageInfoRepository.save(tempImageInfo);
		return tempImageInfo;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ContainerNotFoundException.class)
	public ContainerImageInfo updateContainerImageInfo(ImageInfo imageInfo) throws ContainerNotFoundException {

		ContainerData containerData = containerDataRepository.findByContainerDivId(imageInfo.getContainerDivId());
		
		if(containerData == null || containerData.getContainerDataId() == null) {
			throw new ContainerNotFoundException("Container " + imageInfo.getContainerDivId() + " not found. Please input a valid container.");
		}
		
		ContainerImageInfo containerImageInfo = containerImageInfoRepository.findByContainerDataIdAndImageInfoId(
				containerData.getContainerDataId(), imageInfo.getImageInfoId());
		containerImageInfo = (containerImageInfo == null) ? new ContainerImageInfo() : containerImageInfo;
		containerImageInfo.setContainerDataId(containerData.getContainerDataId());
		containerImageInfo.setContainerImageIsActive(1);
		containerImageInfo.setUpdatedBy(imageInfo.getUpdatedBy());
		containerImageInfo.setUpdatedDate(new Date(System.currentTimeMillis()));
		containerImageInfo.setImageInfo(imageInfo);
		
		return containerImageInfoRepository.save(containerImageInfo);

	}
}
