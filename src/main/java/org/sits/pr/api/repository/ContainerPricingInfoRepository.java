package org.sits.pr.api.repository;

import java.util.List;

import org.sits.pr.api.entity.ContainerPricingInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContainerPricingInfoRepository extends JpaRepository<ContainerPricingInfo, Long> {

	List<ContainerPricingInfo> findByContainerDataIdAndContPricingisActive(Long containerDataId, Integer contPricingisActive);

}