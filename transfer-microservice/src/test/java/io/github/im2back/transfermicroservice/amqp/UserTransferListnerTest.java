package io.github.im2back.transfermicroservice.amqp;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.json.JacksonTester;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.im2back.transfermicroservice.dto.ResultTransferDto;
import io.github.im2back.transfermicroservice.service.TransactionService;

@AutoConfigureJsonTesters
@ExtendWith(MockitoExtension.class)
class UserTransferListnerTest {
	
	@Autowired
	private JacksonTester<ResultTransferDto> jacksonResultTransferDto;
	
	@InjectMocks
	private UserTransferListner userTransferListner;
	
	@Mock
	private TransactionService transactionService;

    @BeforeEach
    void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
    }

	@Test
	@DisplayName("Deveria receber uma menssagem e acionar um serviço com o parametro correto")
	void receiveMessages() throws IOException {
		//ARRANGE	
		ResultTransferDto response = new ResultTransferDto(1l, "message", true);
		var json = jacksonResultTransferDto.write(response).getJson();	
		BDDMockito.doNothing().when(transactionService).setStatus(1l, true, "message");
		
		//ACT
		userTransferListner.receiveMessages(json);
		
		//ASSERT
		BDDMockito.then(transactionService).should().setStatus(1l, true, "message");
	}

}
