package org.sits.pr.api.controller;

import java.sql.Date;

import org.sits.pr.api.config.BearerTokenWrapper;
import org.sits.pr.api.entity.ContainerData;
import org.sits.pr.api.entity.ContainerImageInfo;
import org.sits.pr.api.entity.ContainerPricingInfo;
import org.sits.pr.api.entity.ContainerTextInfo;
import org.sits.pr.api.service.ContainerDataService;
import org.sits.pr.api.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/content")
@Slf4j
public class ContentEditController  {
	
	@Autowired
	ContainerDataService containerDataService;
	
	@Autowired
	TokenService tokenService;
	
	@Autowired
	BearerTokenWrapper tokenWrapper;
	
	// api to edit a text inside a container
	
	@PostMapping("/save/container/text")
	@Operation( summary = "Save Container Text"
	,description = "Save the edited Container Text by passing the JSON of ContainerTextInfo Object, "
			+ "if the primary key of this object 'containerTextInfoId' is missing in the JSON, "
			+ "a new entry will be created otherwise existing will be updated. "
			+ "Note, the foreign key containerDataId need to be present to use this API. Use this"
			+ "API to save one key value pair at a time, for example Lable Name: Price, Lable Description $500 "
			+ "Both can be updated and saved at one go because both are saved in the same row"
			+ "This API can also be used to soft delete the ContainerTextInfo by passing containerTextInfoId, and "
			+ "all non-null column values in the JSON and setting the 'containerTextIsActive' to 0 "
			)
	
	public ContainerTextInfo saveContainerText(@RequestBody  ContainerTextInfo containerTextInfo) {
		log.info("Inside savePageFrame Method of PageController");
		containerTextInfo.setUpdatedBy(tokenService.getUpdatedBy(tokenWrapper.getToken())); // need to be replaced when JWT is being implemented
		containerTextInfo.setUpdatedDate(new Date(System.currentTimeMillis()));
		return containerDataService.saveContainerText(containerTextInfo);
	}
	
	// api to edit a image inside a container
	
	@PostMapping("/save/container/image")
	@Operation(summary="Save Container Image"
	, description="Save the edited Container Image by passing the JSON of ContainerImageInfo Object, which should contain ImageInfo object "
			+ "if the primary key of this object 'containerImageInfoId' is missing in the JSON, "
			+ "a new entry will be created otherwise existing will be updated. "
			+ "Note, the foreign key containerDataId need to be present to use this API. "
			+ "If ImageInfo object is not present, fetch it first, then create JSON object and call this method."
			+ "This API can also be used to soft delete the ContainerImageInfo by passing containerImageInfoId and "
			+ "all non-null column values in the JSON and setting the 'containerImageIsActive' to 0 ")
	
	public ContainerImageInfo saveContainerImage(@RequestBody  ContainerImageInfo containerImageInfo) {
		log.info("Inside savePageFrame Method of PageController");
		containerImageInfo.setUpdatedBy(tokenService.getUpdatedBy(tokenWrapper.getToken())); // need to be replaced when JWT is being implemented
		containerImageInfo.setUpdatedDate(new Date(System.currentTimeMillis()));
		return containerDataService.saveContainerImage(containerImageInfo);
	}
	
	
	// api to pricing inside a container
	@Operation(summary="Save Container Text"
			, description="Save the edited Container Pricing by passing the JSON of ContainerPricingInfo Object, "
					+ "if the primary key of this object 'containerPricingInfoId' is missing in the JSON, "
					+ "a new entry will be created otherwise existing will be updated. "
					+ "Note, the foreign key containerDataId need to be present to use this API. Use this"
					+ "API to save one row of Pricing Information"
					+ "This API can also be used to soft delete the ContainerPricingInfo by passing containerPricingInfoId and "
					+ "all non-null column values in the JSON and setting the 'containerPricingIsActive' to 0 ")
					
	@PostMapping("/save/container/pricing")
	public ContainerPricingInfo saveContainerPricing(@RequestBody  ContainerPricingInfo containerPricingInfo) {
		log.info("Inside savePageFrame Method of PageController");
		containerPricingInfo.setUpdatedBy(tokenService.getUpdatedBy(tokenWrapper.getToken())); // need to be replaced when JWT is being implemented
		containerPricingInfo.setUpdatedDate(new Date(System.currentTimeMillis()));
		return containerDataService.saveContainerPricing(containerPricingInfo);
	}
	
	// api to save an entire container
	@Operation(summary="Save Container Details"
			, description="Save the edited Container details by passing the JSON of ContainerData Object, "
					+ "this Object can contain List of ContainerTextInfo, List of ContainerImageInfo,"
					+ " List of ContainerPricingInfo, Container Header. if the primary key of this object "
					+ " 'containerDataId' is missing then an error will be thrown, new containers cannot "
					+ "be created using this API. ")
					
	@PostMapping("/save/container")
	public ContainerData saveContainerData(@RequestBody  ContainerData containerData) throws Exception{
		log.info("Inside savePageFrame Method of PageController");
		if (containerData.getContainerDataId() == null)
			throw new Exception("Container information is missing");
		containerData.setUpdatedBy(tokenService.getUpdatedBy(tokenWrapper.getToken())); // need to be replaced when JWT is being implemented
		containerData.setUpdatedDate(new Date(System.currentTimeMillis()));
		return containerDataService.saveContainerData(containerData);
	}

}
