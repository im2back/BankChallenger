package io.github.im2back.usermicroservice.exceptions;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StandardErrorBeanValidation {

	private Integer status;
	private String error;
	private List<String> message;
	private String path;
}
