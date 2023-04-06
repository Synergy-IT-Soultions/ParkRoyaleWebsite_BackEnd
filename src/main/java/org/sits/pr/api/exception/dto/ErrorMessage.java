package org.sits.pr.api.exception.dto;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessage {

	private String errorMessage = "";
	private String errorDesc="";
	private Map<String, String> errorAttributes;
}
