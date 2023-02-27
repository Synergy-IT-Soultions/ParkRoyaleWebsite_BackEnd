/**
 * 
 */
package org.sits.pr.api.exception.custom;

import javax.imageio.IIOException;

/**
 * @author vprasath
 *
 */
public class ImageFormatNotSupportedException extends IIOException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3454353534534L;

	/**
	 * @param message
	 */
	public ImageFormatNotSupportedException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ImageFormatNotSupportedException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	@Override
    public String toString() {
        return "None of Image Reader is able to read the image, so Image is corrupted or not an image, use a valid image";
    }
}
