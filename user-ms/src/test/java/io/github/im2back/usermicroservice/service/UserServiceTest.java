package io.github.im2back.usermicroservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import io.github.im2back.usermicroservice.model.entities.user.UserComum;
import io.github.im2back.usermicroservice.model.entities.user.UserGeneric;
import io.github.im2back.usermicroservice.model.entities.user.UserLogista;
import io.github.im2back.usermicroservice.repositories.UserRepository;
import io.github.im2back.usermicroservice.service.utils.NotificationRequestDto;
import io.github.im2back.usermicroservice.util.UtilsTest;
import io.github.im2back.usermicroservice.validation.user.UserRegistrationValidation;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	@Mock
	private UserRepository userRepository;

	@Mock
	private NotificationService notificationService;

	@InjectMocks
	private UserService userService;

	@Spy
	private List<UserRegistrationValidation> userRegistrationValidation = new ArrayList<>();

	@Mock
	private UserRegistrationValidation valid01;
	@Mock
	private UserRegistrationValidation valid02;

	@Captor
	private ArgumentCaptor<UserLogista> userCaptor;

	@Captor
	private ArgumentCaptor<UserGeneric> userGenericCaptor;

	@Test
	@DisplayName("deveria salvar um usuario no banco de dados e retornar um dto do mesmo")
	void saveUser() {

		// ARRANGE
		BDDMockito.when(userRepository.save(any())).thenReturn(UtilsTest.userLogista);
		userRegistrationValidation.add(valid01);
		userRegistrationValidation.add(valid02);

		// ACT
		var response = userService.saveUser(UtilsTest.userRegisterRequest);

		// ASSERT
		BDDMockito.then(valid01).should().valid(UtilsTest.userRegisterRequest);
		BDDMockito.then(valid02).should().valid(UtilsTest.userRegisterRequest);
		BDDMockito.then(userRepository).should().save(userCaptor.capture());

		assertEquals(UtilsTest.userRegisterRequest.identificationDocument(), userCaptor.getValue().getCnpj(),
				"Verifica se o usuario instanciado e salvo é correspondente ao parametro recebido");
		assertEquals(UtilsTest.userRegisterRequest.identificationDocument(), response.identificationDocument(),
				"Verifica se o Dto de User retornado pelo método corresponde ao parametro recebido");
		assertEquals(response.walletBalance(), new BigDecimal(0), "Verifica se a carteira foi gerado com saldo 0");

	}

	@Test
	@DisplayName("Deveria transferir valores entre usuários")
	void transfer() {

		// ARRANGE
		Optional<UserComum> userPayer = Optional.ofNullable(UtilsTest.userComum);
		Optional<UserGeneric> userComumUpCast = Optional.ofNullable(userPayer.get());

		Optional<UserLogista> userPayee = Optional.ofNullable(UtilsTest.userLogista);
		Optional<UserGeneric> userLogistaUpCast = Optional.ofNullable(userPayee.get());

		BDDMockito.when(userRepository.findById(1l)).thenReturn(userComumUpCast);
		BDDMockito.when(userRepository.findById(2l)).thenReturn(userLogistaUpCast);

		BDDMockito.doNothing().when(notificationService).sendNotification(any(NotificationRequestDto.class));
		;

		// ACT
		userService.transfer(UtilsTest.transferRequestDto);

		// ASSERTS
		BDDMockito.then(userRepository).should(Mockito.times(2)).save(userGenericCaptor.capture());
		assertEquals(1l, userGenericCaptor.getAllValues().get(0).getId(),
				"O id da primeiro chamada é o ID referente ao pagante");
		assertEquals(2l, userGenericCaptor.getAllValues().get(1).getId(),
				"O id da segunda chamada é o ID referente ao recebedor");

		UserComum userComumPayerCaptured = (UserComum) userGenericCaptor.getAllValues().get(0);
		assertEquals(new BigDecimal(50), userComumPayerCaptured.getWallet().getBalance(),
				"O saldo final após transferir  deve ser 50");

		UserLogista userLogistaPayerCaptured = (UserLogista) userGenericCaptor.getAllValues().get(1);
		assertEquals(new BigDecimal(50), userLogistaPayerCaptured.getWallet().getBalance(),
				"O saldo final após receber deve ser 50");
	}

	@Test
	@DisplayName("Deveria carregar um UserGeneric apartir de um id")
	void findById() {

		// ARRANGE
		Optional<UserGeneric> user = Optional.ofNullable(UtilsTest.userComum);
		BDDMockito.when(userRepository.findById(UtilsTest.userComum.getId())).thenReturn(user);

		// ACT
		var response = userService.findByIdReturnEntity(UtilsTest.userComum.getId());
		UserComum userComumResponse = (UserComum) response;

		// ASSERT
		BDDMockito.then(userRepository).should().findById(UtilsTest.userComum.getId());
		assertEquals(UtilsTest.userComum.getCpf(), userComumResponse.getCpf(),
				"O documento do User retornado pelo método deve ser o mesmo do user retornado pelo banco de dados");
	
		assertEquals(UtilsTest.userComum.getId(), response.getId(),
				"O ID do User retornado pelo método deve ser o mesmo do user retornado pelo banco de dados");
	}

	@Test
	@DisplayName("Deveria carregar um usuario e retornar um DTO do mesmo")
	void findByIdReturnDto() {

		// ARRANGE
		Long id = 1l;
		Optional<UserGeneric> user = Optional.ofNullable(UtilsTest.userComum);
		BDDMockito.when(userRepository.findById(id)).thenReturn(user);

		// ACT
		var response = userService.findByIdReturnDto(id);

		// ASSERT
		BDDMockito.then(userRepository).should().findById(id);
		assertEquals(id, response.id(),
				"O usuario retornado deveria ter o mesmo ID do parametro");
	}

}
