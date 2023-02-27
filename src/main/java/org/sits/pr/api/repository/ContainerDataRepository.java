package org.sits.pr.api.repository;

import org.sits.pr.api.entity.ContainerData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContainerDataRepository extends JpaRepository<ContainerData, Long> {

	ContainerData findByContainerDivId(String containerDivId);

}