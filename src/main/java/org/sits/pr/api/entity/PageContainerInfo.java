package org.sits.pr.api.entity;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="T202_Page_Container_Info")
public class PageContainerInfo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="C202_Page_Container_Info_id")
	private Long pageContainerInfoId;
	
	@Column(name="C201_Page_Info_Id")
	private Long pageInfoId;
	
	@Column(name="C202_Container_Div_id")
	private String containerDivId;
	
	@Column(name="C202_Container_Desc")
	private String containerDesc;
	
}
