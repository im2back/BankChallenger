package io.github.im2back.transfermicroservice.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import io.github.im2back.transfermicroservice.dto.TransferRequestDto;
import io.github.im2back.transfermicroservice.service.TransferService;

@WebMvcTest(TransferController.class)
@AutoConfigureJsonTesters
@AutoConfigureMockMvc
class TransferControllerTest {

	@MockBean
	private TransferService service;

	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private JacksonTester<TransferRequestDto> jacksonTransferRequestDto;

	@Test
	@DisplayName("Deveria incializar a transferencia e retornar 200ok em caso de sucesso")
	void transfer() throws Exception {
		// ARRANGE
		BDDMockito.doNothing().when(service).transfer(any(Long.class),any(Long.class),any(BigDecimal.class));
		
		var jsonRequest = jacksonTransferRequestDto.write(new TransferRequestDto(1l, 2l, new BigDecimal(100))).getJson();
		
		//ACT
		var response = mvc.perform(post("/transfer").contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequest)).andReturn().getResponse();
		
		//ASSERT
		assertEquals(200, response.getStatus(),"Deveria retornar status 200");
		BDDMockito.then(service).should().transfer(1l, 2l, new BigDecimal(100));
	}

}
