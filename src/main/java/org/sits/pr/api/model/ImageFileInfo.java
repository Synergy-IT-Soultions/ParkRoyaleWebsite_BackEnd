package org.sits.pr.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageFileInfo {
	
	private String uploadedFileContextPath;
	private String thumbnailFileContextPath;
	private double height;
	private double width;
	private double size;

}
