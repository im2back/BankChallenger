package io.github.im2back.usermicroservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import io.github.im2back.usermicroservice.model.entities.user.User;
import io.github.im2back.usermicroservice.repositories.UserRepository;
import io.github.im2back.usermicroservice.util.UtilsTest;
import io.github.im2back.usermicroservice.validation.user.UserRegistrationValidation;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	@Mock
	private UserRepository repository;

	@InjectMocks
	private UserService userService;

	@Spy
	private List<UserRegistrationValidation> userRegistrationValidation = new ArrayList<>();

	@Mock
	private UserRegistrationValidation valid01;
	@Mock
	private UserRegistrationValidation valid02;

	@Captor
	private ArgumentCaptor<User> userCaptor;

	@Test
	@DisplayName("deveria salvar um usuario no banco de dados e retornar um dto do mesmo")
	void saveUser() {

		// ARRANGE
		BDDMockito.when(repository.save(any())).thenReturn(UtilsTest.userLogista);
		userRegistrationValidation.add(valid01);
		userRegistrationValidation.add(valid02);

		// ACT
		var response = userService.saveUser(UtilsTest.userRegisterRequest);

		// ASSERT
		BDDMockito.then(valid01).should().valid(UtilsTest.userRegisterRequest);
		BDDMockito.then(valid02).should().valid(UtilsTest.userRegisterRequest);
		BDDMockito.then(repository).should().save(userCaptor.capture());

		assertEquals(UtilsTest.userRegisterRequest.identificationDocument(),
				userCaptor.getValue().getIdentificationDocument(),
				"Verifica se o usuario instanciado e salvo é correspondente ao parametro recebido");
		assertEquals(UtilsTest.userRegisterRequest.identificationDocument(), response.identificationDocument(),
				"Verifica se o Dto de User retornado pelo método corresponde ao parametro recebido");
		assertEquals(response.walletBalance(), new BigDecimal(0), "Verifica se a carteira foi gerado com saldo 0");

	}

	@Test
	@DisplayName("Deveria transferir valores entre usuários")
	void transfer() {

		// ARRANGE
		Optional<User> userPayer = Optional.ofNullable(UtilsTest.userComum);
		Optional<User> userPayee = Optional.ofNullable(UtilsTest.userLogista);

		BDDMockito.when(repository.findById(1l)).thenReturn(userPayer);
		BDDMockito.when(repository.findById(2l)).thenReturn(userPayee);
		// ACT
		userService.transfer(UtilsTest.transferRequestDto);

		// ASSERT
		BDDMockito.then(repository).should().saveAll(Arrays.asList(userPayee.get(), userPayer.get()));

		assertEquals(userPayer.get().getWallet().getBalance(), new BigDecimal(50),
				"Verificando se o saldo foi deduzido após a transferencia");
		assertEquals(userPayee.get().getWallet().getBalance(), new BigDecimal(50),
				"Verificando se o saldo foi depositado após a transferencia");

	}

	@Test
	@DisplayName("Deveria carregar um usuario apartir de um id")
	void findById() {

		// ARRANGE
		Optional<User> user = Optional.ofNullable(UtilsTest.userComum);
		Long id = 1l;
		BDDMockito.when(repository.findById(id)).thenReturn(user);

		// ACT
		var response = userService.findById(id);

		// ASSERT
		BDDMockito.then(repository).should().findById(id);
		assertEquals(UtilsTest.userComum.getIdentificationDocument(), response.getIdentificationDocument());
	}
	
	@Test
	@DisplayName("Deveria carregar um usuario apartir de um Document")
	void findByDocument() {

		// ARRANGE
		Optional<User> user = Optional.ofNullable(UtilsTest.userComum);
		String document = "123456789";
		BDDMockito.when(repository.findByIdentificationDocument(document)).thenReturn(user);

		// ACT
		var response = userService.findByDocument(document);

		// ASSERT
		BDDMockito.then(repository).should().findByIdentificationDocument(document);
		assertEquals(response.get().getIdentificationDocument(),document,
				"Verifica se o usuario retornado possui o mesmo documento do parametro");
	}
	
	@Test
	@DisplayName("Deveria carregar um usuario apartir de um email")
	void findByEmail() {

		// ARRANGE
		Optional<User> user = Optional.ofNullable(UtilsTest.userComum);
		String email = "claudio@gmail.com";
		BDDMockito.when(repository.findByEmail(email)).thenReturn(user);

		// ACT
		var response = userService.findByEmail(email);

		// ASSERT
		BDDMockito.then(repository).should().findByEmail(email);
		assertEquals(response.get().getEmail(),email,"Verifica se o usuario retornado possui o mesmo email do parametro");
	}

}
