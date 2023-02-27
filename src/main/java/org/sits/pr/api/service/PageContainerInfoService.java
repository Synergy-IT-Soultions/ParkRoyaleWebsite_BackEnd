package org.sits.pr.api.service;

import java.util.List;

import org.sits.pr.api.entity.PageContainerInfo;
import org.sits.pr.api.repository.PageContainerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PageContainerInfoService {
	
	@Autowired
	PageContainerRepository pageContainerRepository;
		
	public List<PageContainerInfo> getMappedContainerInfo(String containerDivId) {
		log.info ("Inside saveFrameDetails method of Page Service");
		return pageContainerRepository.getMappedContainerInfo(containerDivId);
	}

}
