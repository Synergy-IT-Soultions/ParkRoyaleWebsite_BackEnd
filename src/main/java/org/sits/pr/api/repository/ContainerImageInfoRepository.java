package org.sits.pr.api.repository;

import java.util.List;

import org.sits.pr.api.entity.ContainerImageInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ContainerImageInfoRepository extends JpaRepository<ContainerImageInfo, Long> {

	List<ContainerImageInfo> findByContainerDataIdAndContainerImageIsActive(Long containerDataId, Integer containerImageIsActive);

	@Query(value="SELECT T206.* "
			+ "  FROM T206_Container_Image_Info T206 "
			+ "  LEFT JOIN "
			+ "  T301_Image_Info T301 "
			+ "  ON T301.C301_Image_Info_id = t206.C301_Cont_Image_ID "
			+ "  WHERE T206.C204_Container_Data_id = ?1 "
			+ "  AND t206.C206_Cont_Image_Is_Active = 1"
			+ "  AND t301.C301_Image_Type = ?2", nativeQuery=true)
	List<ContainerImageInfo> findByContainerDataIdWithImageFilter(Long containerDataId, String imageType);
	
	@Query(value="SELECT T206.* "
			+ "  FROM T206_Container_Image_Info T206 "	
			+ "  WHERE T206.C204_Container_Data_id = ?1 "
			+ "  AND t206.C301_Cont_Image_ID = ?2 ", nativeQuery=true)
	ContainerImageInfo findByContainerDataIdAndImageInfoId(Long containerDataId, Long imageInfoId);
}