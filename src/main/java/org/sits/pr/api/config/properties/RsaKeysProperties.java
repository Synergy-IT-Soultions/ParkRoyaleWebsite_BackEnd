package org.sits.pr.api.config.properties;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Objects;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="rsa")
public class RsaKeysProperties {
	
	RSAPublicKey publicKey;
	RSAPrivateKey privateKey;
	
	public RsaKeysProperties(RSAPublicKey publicKey, RSAPrivateKey privateKey)
	{
		this.publicKey = publicKey;
		this.privateKey = privateKey;
	}
	
	public RSAPublicKey getPublicKey() {
		return publicKey;
	}
	
	public RSAPrivateKey getPrivateKey() {
		return privateKey;
	}
	
	@Override
	public String toString() {
		return "RsaKeys [publicKey=" + publicKey + ", privateKey=" + privateKey + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(privateKey, publicKey);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RsaKeysProperties other = (RsaKeysProperties) obj;
		return Objects.equals(privateKey, other.privateKey) && Objects.equals(publicKey, other.publicKey);
	}
	

}
