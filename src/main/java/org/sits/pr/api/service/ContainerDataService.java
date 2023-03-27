package org.sits.pr.api.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.sits.pr.api.entity.ContainerData;
import org.sits.pr.api.entity.ContainerImageInfo;
import org.sits.pr.api.entity.ContainerPricingInfo;
import org.sits.pr.api.entity.ContainerTextInfo;
import org.sits.pr.api.entity.ImageInfo;
import org.sits.pr.api.entity.PageContainerInfo;
import org.sits.pr.api.repository.ContainerDataRepository;
import org.sits.pr.api.repository.ContainerImageInfoRepository;
import org.sits.pr.api.repository.ContainerPricingInfoRepository;
import org.sits.pr.api.repository.ContainerTextInfoRepository;
import org.sits.pr.api.repository.ImageInfoRepository;
import org.sits.pr.api.repository.PageContainerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ContainerDataService {
	
	@Autowired
	PageContainerRepository pageContainerRepository;
	
	@Autowired
	ContainerDataRepository containerDataRepository;
	
	@Autowired
	ContainerTextInfoRepository containerTextInfoRepository;
	
	@Autowired
	ContainerImageInfoRepository containerImageInfoRepository;
	
	@Autowired
	ContainerPricingInfoRepository containerPricingInfoRepository;
	
	@Autowired
	ImageInfoRepository imageInfoRepository;
	
	public List<ContainerData> getEntireContainerData(String containerDivId) {
	 List<PageContainerInfo> pageContainers = pageContainerRepository.getMappedContainerInfo(containerDivId);
	 List <ContainerData> listContainerData = new ArrayList<ContainerData>();
	 pageContainers.forEach(pageContainer -> listContainerData.add(getContainerData(pageContainer.getContainerDivId())));
	 return listContainerData;
	}
	
	public ContainerData getContainerData(String containerDivId) {
		ContainerData containerData = getContainerHeader(containerDivId);
		Long containerDataId = containerData.getContainerDataId();
		containerData.setContainerTextInfo(getContainerTextInfo(containerDataId));
		containerData.setContainerImageInfo(getContainerImageInfo(containerDataId));
		containerData.setContainerPricingInfo(getContainerPricingInfo(containerDataId));
		
		return containerData;
	}
	
	public ContainerData getContainerHeader(String containerDivId) {
		return containerDataRepository.findByContainerDivId(containerDivId);
	}
	
	public List<ContainerTextInfo> getContainerTextInfo(Long containerDataId) {
		return containerTextInfoRepository.findByContainerDataIdAndContainerTextisActive(containerDataId, Integer.valueOf(1));
	}
	
	public List<ContainerImageInfo> getContainerImageInfo(Long containerDataId) {
		return containerImageInfoRepository.findByContainerDataIdAndContainerImageIsActive(containerDataId, Integer.valueOf(1));
	}
	
	public List<ContainerPricingInfo> getContainerPricingInfo(Long containerDataId) {
		return containerPricingInfoRepository.findByContainerDataIdAndContPricingisActive(containerDataId, Integer.valueOf(1));
	}
	

	public List<ContainerData> getEntireContainerDataWithImageFilter(String containerDivId, String imageType) {
		 List<PageContainerInfo> pageContainers = pageContainerRepository.getMappedContainerInfo(containerDivId);
		 List <ContainerData> listContainerData = new ArrayList<ContainerData>();
		 pageContainers.forEach(pageContainer -> listContainerData.add(getContainerDataWithImageFilter(pageContainer.getContainerDivId(), imageType)));
		 return listContainerData;
	}
	
	public ContainerData getContainerDataWithImageFilter(String containerDivId, String imageType) {
		ContainerData containerData = containerDataRepository.findByContainerDivId(containerDivId);
		Long containerDataId = containerData.getContainerDataId();
		containerData.setContainerTextInfo(getContainerTextInfo(containerDataId));
		containerData.setContainerImageInfo(getContainerImageInfoWIthImageFilter(containerDataId,imageType));
		containerData.setContainerPricingInfo(getContainerPricingInfo(containerDataId));
		
		return containerData;
	}
	
	public List<ContainerImageInfo> getContainerImageInfoWIthImageFilter(Long containerDataId, String imageType) {
		return containerImageInfoRepository.findByContainerDataIdWithImageFilter(containerDataId, imageType);
	}
	
	public List<ImageInfo> getImageInfoWIthImageFilter(String imageType) {
		return imageInfoRepository.findByImageTypeAndImageIsActive(imageType, Integer.valueOf(1));
	}

	@Transactional
	public ContainerTextInfo saveContainerText(ContainerTextInfo containerTextInfo) {
		return containerTextInfoRepository.save(containerTextInfo);
	}
	
	
	private ContainerTextInfo saveContainerText(ContainerTextInfo containerTextInfo, Long updatedBy) {
		containerTextInfo.setUpdatedBy(updatedBy);
		containerTextInfo.setUpdatedDate(new Date(System.currentTimeMillis()));
		return saveContainerText(containerTextInfo);
	}

	@Transactional
	public ContainerImageInfo saveContainerImage(ContainerImageInfo containerImageInfo) {
		return containerImageInfoRepository.save(containerImageInfo);
	}
	
	
	private ContainerImageInfo saveContainerImage(ContainerImageInfo containerImageInfo, Long updatedBy) {
		containerImageInfo.setUpdatedBy(updatedBy);
		containerImageInfo.setUpdatedDate(new Date(System.currentTimeMillis()));
		ImageInfo imageInfo = containerImageInfo.getImageInfo();
		if(imageInfo != null && ( imageInfo.getImageContextPath() == null || imageInfo.getImageContextPath() != "")) {
			 imageInfo = imageInfoRepository.findByImageInfoIdAndImageIsActive(imageInfo.getImageInfoId(), Integer.valueOf(1));
			 log.debug("Context Path: "+imageInfo.getImageContextPath());
			 containerImageInfo.setImageInfo(imageInfo);
		}
		
		return saveContainerImage(containerImageInfo);
	}
	
	@Transactional
	public ContainerPricingInfo saveContainerPricing(ContainerPricingInfo containerPricingInfo) {
		return containerPricingInfoRepository.save(containerPricingInfo);
	}
	
	
	private ContainerPricingInfo saveContainerPricing(ContainerPricingInfo containerPricingInfo, Long updatedBy) {
		containerPricingInfo.setUpdatedBy(updatedBy);
		containerPricingInfo.setUpdatedDate(new Date(System.currentTimeMillis()));
		return saveContainerPricing(containerPricingInfo);
	}

	@Transactional
	public ContainerData saveContainerData(ContainerData containerData) {
		
		log.debug("containerData: "+ containerData.toString());
		
		containerDataRepository.save(containerData);
		
		Long updatedBy = containerData.getUpdatedBy();
		
		List<ContainerTextInfo> textInfoList =  containerData.getContainerTextInfo();	
		if(textInfoList != null && !textInfoList.isEmpty()) 
			textInfoList.forEach(containerTextInfo -> saveContainerText(containerTextInfo, updatedBy));
		
		List<ContainerImageInfo> imageInfoList =  containerData.getContainerImageInfo();	
		if(imageInfoList != null && !imageInfoList.isEmpty()) 
			imageInfoList.forEach(containerImageInfo -> saveContainerImage(containerImageInfo, updatedBy));
		
		List<ContainerPricingInfo> pricingInfoList =  containerData.getContainerPricingInfo();	
		if(pricingInfoList != null && !pricingInfoList.isEmpty()) 
			pricingInfoList.forEach(containerPricingInfo -> saveContainerPricing(containerPricingInfo, updatedBy));
		
		return containerData;
	}

	public ImageInfo getImageInfo(Long imageInfoId) {
		return imageInfoRepository.findByImageInfoIdAndImageIsActive(imageInfoId, Integer.valueOf(1));
	}
	
}
