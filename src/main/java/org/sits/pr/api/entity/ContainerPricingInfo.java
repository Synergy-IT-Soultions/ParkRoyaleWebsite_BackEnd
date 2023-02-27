package org.sits.pr.api.entity;

import java.sql.Date;

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
@Table(name="T207_Container_Pricing_Info")
public class ContainerPricingInfo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="C207_Container_Pricing_Info_Id")
	private Long containerPricingInfoId;
	
	@Column(name="C204_Container_Data_Id")
	private Long containerDataId;
	
	@Column(name="C207_Room_Type")
	private String roomType;
	
	@Column(name="C207_Room_Price")
	private Float roomPrice;
	
	@Column(name="C207_GST_Percentage")
	private Float gstPercentage;
	
	@Column(name="C207_GST_Calculated_Price")
	private Float gstCalculatedPrice;
	
	@Column(name="C207_Total_Price")
	private Float totalPrice;
	
	@Column(name="C207_Cont_Pricing_Is_Active")
	private Integer contPricingisActive;
	
	@Column(name="C101_Updated_By")
	private Long updatedBy;
	
	@Column(name="C207_Updated_Date")
	private Date updatedDate;
	
	@Transient
	private final String editType="Pricing"; 

}
