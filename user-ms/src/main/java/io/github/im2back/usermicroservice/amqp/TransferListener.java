package io.github.im2back.usermicroservice.amqp;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.im2back.usermicroservice.model.dto.TransferRequestDto;
import io.github.im2back.usermicroservice.service.UserService;

@Component
public class TransferListener {
	
	@Autowired
	private UserService userService;
	
	@RabbitListener(queues = "transfer")
	public void receiveMessages(@Payload String msg) throws JsonMappingException, JsonProcessingException {
		var mapper = new ObjectMapper();
		
		TransferRequestDto datas = mapper.readValue(msg, TransferRequestDto.class);	
		
		userService.transfer(datas);
	}
	
	
	
}
