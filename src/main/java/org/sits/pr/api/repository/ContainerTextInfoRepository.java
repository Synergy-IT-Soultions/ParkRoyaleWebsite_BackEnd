package org.sits.pr.api.repository;

import java.util.List;

import org.sits.pr.api.entity.ContainerTextInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ContainerTextInfoRepository extends JpaRepository<ContainerTextInfo, Long> {

	List<ContainerTextInfo> findByContainerDataIdAndContainerTextisActive(Long containerDataId, Integer containerTextisActive);

}