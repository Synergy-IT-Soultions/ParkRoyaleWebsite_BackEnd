package org.sits.pr.api.entity;

import java.sql.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="T204_Container_Data")
/* Entity for ContainerData */
public class ContainerData {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="C204_Container_Data_id")
	private Long containerDataId;
	
	@Column(name="C202_Page_Container_Info_id")
	private Long pageContainerInfoId;
	
	@Column(name="C202_Container_Div_Id")
	private String containerDivId;
	
	@Column(name="C204_Container_Header")
	private String containerHeader;
	
	@Column(name="C101_Updated_By")
	private Long updatedBy;
	
	@Column(name="C204_Updated_Date")
	private Date updatedDate;
	
	@Transient
	private final String editType= "Header";
	
	@Transient
	private List<ContainerTextInfo> containerTextInfo;
	
	@Transient
	private List<ContainerImageInfo> containerImageInfo;
	
	@Transient
	private List<ContainerPricingInfo> containerPricingInfo;
	
		
}
