package org.sits.pr.api.controller;

import java.util.List;

import org.sits.pr.api.entity.ContainerData;
import org.sits.pr.api.entity.ContainerImageInfo;
import org.sits.pr.api.entity.ContainerPricingInfo;
import org.sits.pr.api.entity.ContainerTextInfo;
import org.sits.pr.api.entity.ImageInfo;
import org.sits.pr.api.service.ContainerDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/content")
public class ContentFetchController {
	
	@Autowired
	ContainerDataService containerDataService;
	
	@GetMapping("/get/container/header/{id}")
	@Operation(summary="Get Container Header"
		, description="Get Container Header by passing the Container Div Id, "
				+ "For example, 'home-roompricing-id'")
	public String getContainerHeader(@PathVariable("id") String containerDivId) {
		//log.debug(containerDivId)
		ContainerData containerData= containerDataService.getContainerHeader(containerDivId);
		return containerData.getContainerHeader();
	}
	
	@GetMapping("/get/container/texts/{id}")
	@Operation(summary="Get list of Container Text"
	, description="Get list of Container text that need to be showed inside the container by "
			+ "passing the Container Div Id, For example, 'home-roompricing-id'")
	public List<ContainerTextInfo> getContainerText(@PathVariable("id") String containerDivId) {
		//log.debug(containerDivId)
		ContainerData containerData= containerDataService.getContainerHeader(containerDivId);
		return containerDataService.getContainerTextInfo(containerData.getContainerDataId());
	}
	
	@GetMapping("/get/container/images/{id}")
	@Operation(summary="Get list of Container Images"
	, description="Get list of Container images that need to be showed inside the container by "
			+ "passing the Container Div Id, For example, 'home-roompricing-id'")
	public List<ContainerImageInfo> getContainerImages(@PathVariable("id") String containerDivId) {
		//log.debug(containerDivId)
		ContainerData containerData= containerDataService.getContainerHeader(containerDivId);
		return containerDataService.getContainerImageInfo(containerData.getContainerDataId());
	}
	
	@GetMapping("/get/container/images/{id}/{imageType}")
	@Operation(summary="Get list of Container Images filtered by Image Type"
	, description="Get list of Container images that need to be showed inside the container by passing the Container Div Id and Image Type, "
			+ "For example, if 'Gallery' container needs to show only images of 'Rooms' then  container id would be 'gallery-id' and Image Type "
			+ "would be 'ROOMS'. Query the table T301_IMAGE_INFO and select distinct of C301_Image_Type to know all the image types available ")
	public List<ContainerImageInfo> getContainerImages(@PathVariable("id") String containerDivId, @PathVariable("imageType") String imageType) {
		//log.debug(containerDivId)
		ContainerData containerData= containerDataService.getContainerHeader(containerDivId);
		return containerDataService.getContainerImageInfoWIthImageFilter(containerData.getContainerDataId(),imageType);
	}
	
	@GetMapping("/get/container/pricing/{id}")
	@Operation(summary="Get list of Tariff Information"
	, description="Get list of Pricing/Tarrif information that need to be showed inside the container "
			+ "by passing the Container Div Id, For example, 'home-roompricing-id'")
	public List<ContainerPricingInfo> getContainerPricingInfo(@PathVariable("id") String containerDivId) {
		//log.debug(containerDivId)
		ContainerData containerData= containerDataService.getContainerHeader(containerDivId);
		return containerDataService.getContainerPricingInfo(containerData.getContainerDataId());
		
	}
	
	@GetMapping("/get/container/details/{id}")
	@Operation(summary="Get entire Container Information at once"
	, description="Get entire container information, such as header, list of text, list of images, list of pricing information "
			+ "that need to be showed inside the container by passing the Container Div Id, For example, 'home-roompricing-id'")
	public ContainerData getContainerDetails(@PathVariable("id") String containerDivId) {
		//log.debug(containerDivId)
		return containerDataService.getContainerData(containerDivId);
	}
	
	@GetMapping("/get/container/details/{id}/{imageType}")
	@Operation(summary="Get entire Container Information at once with images filtered for image type passed"
	, description="Get entire container information, such as header, list of text, list of images filtered by Image Type, list of pricing information "
			+ "that need to be showed inside the container by passing the Container Div Id, For example, 'home-roompricing-id'."
			+ "Query the table T301_IMAGE_INFO and select distinct of C301_Image_Type to know all the image types available")
	public ContainerData getContainerDetails(@PathVariable("id") String containerDivId, @PathVariable("imageType") String imageType) {
		//log.debug(containerDivId)
		return containerDataService.getContainerDataWithImageFilter(containerDivId, imageType);
	}
	
	
	@GetMapping("/get/container/group-details/{id}")
	@Operation(summary="Get list of Container Information that are created one inside the other"
	, description="Get list of container information that are created one inside the other, each of the container contains a header, list of text, "
			+ "list of images , list of pricing information that need to be showed inside the container by passing the Container Div Id."
			+ " For example, Room Pricing container contains four containers Bed Room 1, Bed Room 2, Bed Room 3, Bed Room 4, "
			+ "This API returns list of 5 Container Data objects containing the Room Pricing and the four Bed Rooms details")
	public List<ContainerData> getContainerData(@PathVariable("id") String containerDivId) {
		//log.debug(containerDivId)
		return containerDataService.getEntireContainerData(containerDivId);
	}
	
	@GetMapping("/get/container/group-details/{id}/{imageType}")
	@Operation(summary="Get list of Container Information that are created one inside the other"
	, description="Get list of container information that are created one inside the other, each of the container contains a header, list of text, "
			+ "list of images filtered by Image Type, list of pricing information that need to be showed inside the container by passing the Container Div Id."
			+ " For example, Room Pricing container contains four containers Bed Room 1, Bed Room 2, Bed Room 3, Bed Room 4, "
			+ "This API returns list of 5 Container Data objects containing the Room Pricing and the four Bed Rooms details")
	public List<ContainerData> getContainerData(@PathVariable("id") String containerDivId, @PathVariable("imageType") String imageType) {
		//log.debug(containerDivId)
		return containerDataService.getEntireContainerDataWithImageFilter(containerDivId, imageType);
	}
	
		
	@GetMapping("/get/image/list/{imageType}")
	@Operation(summary="Get list of Images for a provided image type"
	, description="Get list of images for provide image type, this will pull all the images mapped for a "
			+ "specific type, irrespective of the container, this API can be used to "
			+ "populate drop down values showing particular type of images")
	public List<ImageInfo> getImageInfo(@PathVariable("imageType") String imageType) {
		//log.debug(containerDivId)
		return containerDataService.getImageInfoWIthImageFilter(imageType);
	}
	
	@GetMapping("/get/image/{imageInfoId}")
	@Operation(summary="Get Image Details for the provided image id"
	, description="Get details of an image for the provided Image Id")
	public ImageInfo getImageInfo(@PathVariable("imageInfoId") Long imageInfoId) {
		//log.debug(containerDivId)
		return containerDataService.getImageInfo(imageInfoId);
	}
	
	
	

}
