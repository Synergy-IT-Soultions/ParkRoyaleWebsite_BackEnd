package org.sits.pr.api.service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

import org.sits.pr.api.entity.UserInfo;
import org.sits.pr.api.exception.custom.TokenExpiredException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TokenService {

    @Autowired
	private JwtEncoder encoder;
    
    @Autowired
   	private JwtDecoder decoder;
    
    @Value("${org.sits.pr.api.expireToken}")
	private long expireToken;
    
    @Autowired
    UserService userService;

    public String generateToken(Authentication authentication) {
        Instant now = Instant.now();
        
       // authentication.
        
        String scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
        
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(expireToken, ChronoUnit.HOURS))
                .subject(authentication.getName())
                .claim("scope", scope)
                .build();
       
        return this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
    
    public Long getUpdatedBy(String token) throws TokenExpiredException{
    	UserInfo userInfo = userService.getUserInfoByUserName(getUserNamefromToken(token)); 
    	return userInfo.getUserId();
    	
    }
    
    private String getUserNamefromToken(String strToken) throws TokenExpiredException{
    	//  validateToken(strToken);
          String strName = decoder.decode(strToken).getSubject();
          log.debug("strName: "+strName);
          return strName;
    }
    
    public void validateToken(String strToken) throws TokenExpiredException {
         Instant instant = decoder.decode(strToken).getExpiresAt();
         Instant now = Instant.now();
         ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(instant, ZoneId.systemDefault());
         // Get the time as a string
         String time = zonedDateTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
         if(instant.isAfter(now)) {
        	 throw new TokenExpiredException("Authentication Bearer Token Expired at " + time + ". Please login again!" );
         }
    }
}
