package io.github.im2back.usermicroservice.amqp;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.json.JacksonTester;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.im2back.usermicroservice.model.dto.TransferResponseDto;

@ExtendWith(MockitoExtension.class)
@AutoConfigureJsonTesters
class UserTransferPublishTest {

	@InjectMocks
	private UserTransferPublish userTransferPublish;

	@Mock
	RabbitTemplate rabbitTemplate;

	@Autowired
	private JacksonTester<TransferResponseDto> jacksonTransferResponseDto;

	@BeforeEach
	void setup() {
		JacksonTester.initFields(this, new ObjectMapper());
	}

	@Test
	@DisplayName("Deveria enviar uma menssagem com o objeto da reposta da transferencia")
	void responseTransfer() throws IOException {
		// ARRANGE
		TransferResponseDto trDto = new TransferResponseDto(1l, "sucess", true);
		var json = this.jacksonTransferResponseDto.write(trDto).getJson();

		// ACT
		userTransferPublish.responseTransfer(trDto);

		// ASSERT

		verify(rabbitTemplate, times(1)).convertAndSend("transfer.response", json);
	}

}
