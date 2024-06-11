package io.github.im2back.usermicroservice.amqp;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.im2back.usermicroservice.model.dto.TransferResponseDto;

@Component
public class UserTransferPublish {
	@Autowired
	RabbitTemplate rabbitTemplate;

	private Object convertIntoJson(TransferResponseDto datas) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		var json = mapper.writeValueAsString(datas);

		return json;

	}

	public void responseTransfer(TransferResponseDto datas) throws JsonProcessingException {
		var json = convertIntoJson(datas);
		rabbitTemplate.convertAndSend("transfer.response", json);
	}

}
