package org.sits.pr.api.entity;

import java.sql.Date;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="T301_Image_Info", uniqueConstraints={@UniqueConstraint(name = "UQ__T301_Ima__53812C6CABC206A8", columnNames = {"C301_Image_Name"})})
public class ImageInfo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="C301_Image_Info_Id")
	private Long imageInfoId;
	
	@Column(name="C301_Image_Name")
	@Length(min = 3, max = 100, message = "Image Name cannot be blank,  must be at least 3 characters and cannot be more than 100 characters")
	private String imageName;
	
	@Column(name="C301_Image_Description")
	private String imageDescription;
	
	@Column(name="C301_Image_Type")
	@Length(min = 3, max = 100, message = "Image Type cannot be blank, must be at least 3 characters and cannot be more than 100 characters")
	private String imageType;
	
	@Column(name="C301_Image_Alt")
	private String imageAlt;
	
	@Column(name="C301_Image_URL")
	private String imageURL;
	
	@Column(name="C301_Thumbnail_URL")
	private String thumbnailURL;
	
	@JsonIgnore
	@Column(name="C301_Image_Context_Path")
	private String imageContextPath;
	
	@JsonIgnore
	@Column(name="C301_Thumbnail_Context_Path")
	private String thumbnailContextPath;
	
	@Column(name="C301_Image_Height")
	private Double imageHeight;
	
	@Column(name="C301_Image_Width")
	private Double imageWidth;
	
	@Column(name="C301_Image_Size")
	private Double imageSize;
	
	@Min(0)
	@Max(1)
	@Column(name="C301_Image_Is_Active")
	private Integer imageIsActive;
	
	@Column(name="C101_Updated_By")
	@NotNull(message = "Updated By cannot be empty and need to be the logged in user id.")
    private Long updatedBy;
	
	@Column(name="C301_Updated_Date")
    private Date updatedDate;
	
	@Transient
	private String containerDivId;

}
