package io.github.im2back.usermicroservice.amqp;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.json.JacksonTester;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.im2back.usermicroservice.model.dto.TransferRequestDto;
import io.github.im2back.usermicroservice.service.UserService;

@ExtendWith(MockitoExtension.class)
@AutoConfigureJsonTesters
class TransferListenerTest {

	@InjectMocks
	private TransferListener transferListener;

	@Mock
	private UserService userService;

	@Autowired
	private JacksonTester<TransferRequestDto> jacksonTransferRequestDto;

	@BeforeEach
	void setup() {
		JacksonTester.initFields(this, new ObjectMapper());
	}

	@Test
	@DisplayName("Deveria consumir uma menssagem e acionar os serviços corretos")
	void receiveMessages() throws IOException {
		// ARRANGE
		TransferRequestDto transferRequestDto = new TransferRequestDto(1l, 2l, new BigDecimal(50), 1l);
		String payload = this.jacksonTransferRequestDto.write(transferRequestDto).getJson();

		// ACT
		transferListener.receiveMessages(payload);
		
		// ASSERT
		verify(userService, times(1)).transfer(transferRequestDto);
	}

}
