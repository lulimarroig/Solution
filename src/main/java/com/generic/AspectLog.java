package com.generic;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;

/**
 * Aspect class for the AspectJ Logging
 * 
 * @author Lucia
 */

//@Aspect
public class AspectLog
{
	
	private Logger log = Logger.getLogger(getClass());

    @After("execution(* com.searchRequests.CrazyAirSupplier.parseAndPerformRequest(..))")
    public void logCrazyAir(JoinPoint point) {
        log.info("CrazySupplier request: " + point.getArgs().toString());
        log.info(point);
    }
    
    @After("execution(* com.searchRequests.ToughJetSupplier.parseAndPerformRequest(..))")
    public void logToughJet(JoinPoint point) {
        log.info("CrazySupplier request: " + point.getArgs().toString());
        log.info(point);
    }

}
