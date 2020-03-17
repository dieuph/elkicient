package io.spring.core.log;

import java.util.UUID;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import java.net.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(of = "id")
public class LogWrapper {
	private String id;
	private Level level;
	private LogContext context;
	private String message;
	private Object[] parameters;
	private Throwable throwable;
	private Logger logger;
 
  @Value("${jdbc.ip}")
  private String ip;

	private static LogContext context(String context) {
		return LogContext.getContext(context.toUpperCase());
	}
	
	private static Level level(String level) {
		return Level.getLevel(level.toUpperCase());
	}
	
	public static LogWrapper from(LogRequest request) {
		LogWrapper wrapper = new LogWrapper();
    
		try {
      wrapper.setId(UUID.randomUUID().toString());
			wrapper.setLevel(level(request.getLevel()));
			wrapper.setMessage(request.getMessage());
			
			if (null != request.getThrowable()) {
				wrapper.setThrowable(((Throwable) Class.forName(request.getThrowable()).newInstance()));
			}
			
			wrapper.setLogger(LogFactory.forContext(context("Context")));
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			LogFactory.forContext(LogContext.PARSE)
					  .error("Error parsing request data.");
		}
		
		return wrapper;
	}
	
	public void log() {
		if (null != throwable) {
			logger.log(level, this.getIp() + " " + message + throwable.getCause());
		} else {
			logger.log(level, this.getIp() + " " + message);
		}
	}
 
 @ConfigurationProperties(prefix = "jdbc") 
  public @Data class ConfigProperties { 
    private String ip; 
  }
}
