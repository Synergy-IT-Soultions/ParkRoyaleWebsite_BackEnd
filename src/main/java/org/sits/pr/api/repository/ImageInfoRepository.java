package org.sits.pr.api.repository;

import java.util.List;

import org.sits.pr.api.entity.ImageInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageInfoRepository extends JpaRepository<ImageInfo, Long> {

			List<ImageInfo> findByImageTypeAndImageIsActive(String imageType, Integer imageIsActive);
	
	        ImageInfo findByImageInfoIdAndImageIsActive(Long imageInfoId, Integer imageIsActive);

} 