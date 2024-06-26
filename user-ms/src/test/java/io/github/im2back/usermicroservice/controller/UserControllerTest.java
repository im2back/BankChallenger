package io.github.im2back.usermicroservice.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.im2back.usermicroservice.model.dto.TransferRequestDto;
import io.github.im2back.usermicroservice.model.dto.UserRegisterRequestDto;
import io.github.im2back.usermicroservice.model.dto.UserRegisterResponseDto;
import io.github.im2back.usermicroservice.service.UserService;
import io.github.im2back.usermicroservice.util.UtilsTest;

@WebMvcTest(UserController.class)
@AutoConfigureJsonTesters
@AutoConfigureMockMvc
class UserControllerTest {

	@MockBean
	private UserService userService;

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private JacksonTester<UserRegisterRequestDto> jacksonUserRegisterRequestDto;

	@Captor
	private ArgumentCaptor<UserRegisterRequestDto> captorUserRegisterRequestDto;

	@Captor
	private ArgumentCaptor<TransferRequestDto> captorTransferRequestDto;

	@Test
	@DisplayName("Deveria retornar um UserRegisterResponseDto apartir de um ID informado")
	void findUser() throws Exception {
		// ARRANGE
		Long id = 1l;
		BDDMockito.when(userService.findById(id)).thenReturn(UtilsTest.userComum);

		// ACT
		var response = mvc.perform(get("/users/" + id)).andReturn().getResponse();
		UserRegisterResponseDto objetoRecebido = objectMapper.readValue(response.getContentAsString(),
				UserRegisterResponseDto.class);

		// ASSERT
		BDDMockito.then(userService).should().findById(id);
		assertEquals(200, response.getStatus(), "Deveria retornar status 200 em caso de sucesso");
		assertEquals(id, objetoRecebido.id(), "O id do objeto retornado deve ser igual ao id fornecido");

	}

	@Test
	@DisplayName("Deveria salvar um usuário e retornar um dto UserRegisterResponseDto")
	void saveUser() throws Exception {
		// ARRANGE
		var jsonRequest = this.jacksonUserRegisterRequestDto.write(UtilsTest.userRegisterRequest).getJson();
		BDDMockito.when(userService.saveUser(any())).thenReturn(new UserRegisterResponseDto(UtilsTest.userLogista));

		// ACT
		var response = mvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(jsonRequest))
				.andReturn().getResponse();

		UserRegisterResponseDto objetoRecebido = objectMapper.readValue(response.getContentAsString(),
				UserRegisterResponseDto.class);

		// ASSERT
		BDDMockito.then(userService).should().saveUser(this.captorUserRegisterRequestDto.capture());
		assertEquals(UtilsTest.userRegisterRequest.identificationDocument(),
				captorUserRegisterRequestDto.getValue().identificationDocument(),
				"Verificando se o método de serviço foi chamado com o parametro correto");
		assertEquals(201, response.getStatus(), "Deveria retornar status 201 em caso de sucesso");
		assertEquals(objetoRecebido.identificationDocument(), UtilsTest.userRegisterRequest.identificationDocument(),
				"O documento do objeto retornado deve ser igual ao documento fornecido");

	}

}
