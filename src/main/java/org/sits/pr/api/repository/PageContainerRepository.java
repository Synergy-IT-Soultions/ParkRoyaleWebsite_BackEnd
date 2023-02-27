package org.sits.pr.api.repository;

import java.util.List;

import org.sits.pr.api.entity.PageContainerInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PageContainerRepository extends JpaRepository<PageContainerInfo, Long> {
	
	@Query(value = "SELECT T202A.C202_Page_Container_Info_id\n"
			+ "		 , T202A.C202_Container_Div_Id\n"
			+ "		 , T202A.C201_Page_Info_id\n"
			+ "		 , t202A.C202_Container_Desc\n"
			+ "	  FROM\n"
			+ "  ( SELECT T203.C202_Inner_Page_Container_Info_id C202_Page_Container_Info_id\n"
			+ "	  FROM T202_Page_Container_Info T202  \n"
			+ "	       LEFT OUTER JOIN \n"
			+ "		   T203_Container_Mapping T203\n"
			+ "	    ON T202.C202_Page_Container_Info_id = T203.C202_Outer_Page_Container_Info_id\n"
			+ "	 WHERE T202.C202_Container_Div_Id = :containerDivId\n"
			+ "  ) T202\n"
			+ "           LEFT OUTER JOIN  \n"
			+ "		   T202_Page_Container_Info T202A\n"
			+ "	    ON T202.C202_Page_Container_Info_id = T202A.C202_Page_Container_Info_id", nativeQuery = true)
	List<PageContainerInfo> getMappedContainerInfo(@Param("containerDivId") String containerDivId);
	

}

