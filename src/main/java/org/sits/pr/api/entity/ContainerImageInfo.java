package org.sits.pr.api.entity;

import java.sql.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Table(name="T206_Container_Image_Info")
public class ContainerImageInfo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="C205_Container_Image_Info_Id")
	private Long containerImageInfoId;
	
	@Column(name="C204_Container_Data_id")
	private Long containerDataId;
		
	@Column(name="C206_Cont_Image_Is_Active")
	private Integer containerImageIsActive;
	
	@Column(name="C206_Cont_Image_Is_Linked")
	private Integer containerImageIsLinked;
	
	@Column(name="C101_Updated_By")
	private Long updatedBy;
	
	@Column(name="C206_Updated_Date")
	private Date updatedDate;
	
	@ManyToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    @JoinColumn(
            name = "C301_Cont_Image_id",
            referencedColumnName = "C301_Image_Info_Id"
            
    )
	private ImageInfo imageInfo;
	
	@Transient
	private final String editType = "Image";
	
}
