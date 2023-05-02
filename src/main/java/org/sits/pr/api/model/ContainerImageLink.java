package org.sits.pr.api.model;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ContainerImageLink {

	private String fromContainerDivId;
	private String toContainerDivId;
	private Long imageInfoId;
	private Integer isLinked;
	private Long updatedBy;
	private Date updatedDate;
}
