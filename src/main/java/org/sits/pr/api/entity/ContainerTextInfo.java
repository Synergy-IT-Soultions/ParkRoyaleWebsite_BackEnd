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
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Table(name="T205_Container_Text_Info")
public class ContainerTextInfo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="C205_Container_Text_Info_Id")
	private Long containerTextInfoId;
	
	@Column(name="C204_Container_Data_Id")
	private Long containerDataId;
	
	@Column(name="C205_Cont_Text_Label_Name")
	private String containerTextLabelName;
	
	@Column(name="C205_Cont_Text_Label_Value")
	private String containertextLabelValue;
	
	@Column(name="C205_Cont_Text_Label_Sequence")
	private String containertextLabelSequence;
	
	@Column(name="C205_Cont_Text_Is_Active")
	private Integer containerTextisActive;
	
	@Column(name="C101_Updated_By")
	private Long updatedBy;
	
	@Column(name="C205_Updated_Date")
	private Date updatedDate;
	
	@Transient
	private final String editType = "Text";

}
