package io.github.im2back.transfermicroservice.amqp;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.im2back.transfermicroservice.dto.TransferPublishDto;

@Component
public class TransactionPublisher {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	private Object convertIntoJson(TransferPublishDto datas) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		var json = mapper.writeValueAsString(datas);
		return json;
	}

	public void issueTransfer(TransferPublishDto datas) throws JsonProcessingException {
		var json = convertIntoJson(datas);
		rabbitTemplate.convertAndSend("transfer", json);
	}
}
