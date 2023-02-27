package org.sits.pr.api.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class LoggingAspect {
	
	public LoggingAspect()
	{
		log.info("Logging Aspect Initialized");
	}

	@Before("execution(* org.sits.*..*.*(..))")
	public void beforeLogger(JoinPoint joinPoint)
	{
		log.info("Before call of method "+ joinPoint.getSignature() + " from class " +joinPoint.getTarget().getClass());
	}
	
	@After("execution(* org.sits.*..*(..))")
	public void afterLogger(JoinPoint joinPoint)
	{
		log.info("After call of method "+ joinPoint.getSignature() + " from class " +joinPoint.getTarget().getClass());
	}

}
