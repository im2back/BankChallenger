package io.github.im2back.transfermicroservice.amqp;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.im2back.transfermicroservice.dto.ResultTransferDto;
import io.github.im2back.transfermicroservice.service.TransactionService;

@Component
public class UserTransferListner {

	@Autowired
	private TransactionService transactionService;

	@RabbitListener(queues = "transfer.response")
	public void receiveMessages(@Payload String msg) throws JsonMappingException, JsonProcessingException {
		var mapper = new ObjectMapper();
		ResultTransferDto datas = mapper.readValue(msg, ResultTransferDto.class);

		transactionService.setStatus(datas.idTransfer(), datas.isAuthorize(), datas.message());
	}

}
