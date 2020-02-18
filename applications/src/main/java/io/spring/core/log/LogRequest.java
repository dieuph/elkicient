package io.spring.core.log;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.Data;

@Data
@JsonRootName("log")
public class LogRequest {
	@NotBlank(message = "can't be empty")
	private String level;

	@NotBlank(message = "can't be empty")
	private String context;

	private String throwable;

    @NotBlank(message = "can't be empty")
    private String message;
    
}