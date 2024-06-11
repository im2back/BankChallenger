package io.github.im2back.usermicroservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.github.im2back.usermicroservice.amqp.UserTransferPublish;
import io.github.im2back.usermicroservice.model.dto.TransferResponseDto;
import io.github.im2back.usermicroservice.model.entities.user.User;
import io.github.im2back.usermicroservice.repositories.UserRepository;
import io.github.im2back.usermicroservice.util.UtilsTest;
import io.github.im2back.usermicroservice.validation.transfer.TransferValidations;
import io.github.im2back.usermicroservice.validation.user.UserRegistrationValidation;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	@Mock
	private UserRepository repository;

	@InjectMocks
	private UserService userService;

	@Mock
	private NotificationService notificationService;

	@Mock
	private UserTransferPublish userTransferPublish;

	@Spy
	private List<UserRegistrationValidation> userRegistrationValidation = new ArrayList<>();

	@Spy
	private List<TransferValidations> transferValidations = new ArrayList<>();

	@Mock
	private UserRegistrationValidation valid01;
	@Mock
	private UserRegistrationValidation valid02;

	@Mock
	private TransferValidations valid04;
	@Mock
	private TransferValidations valid03;

	@Captor
	private ArgumentCaptor<User> userCaptor;
	
	@Captor
	private ArgumentCaptor<Iterable<User>> captor;

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
	@DisplayName("Deveria fazer uam tranferencia e não retornar nada em caso de sucesso")
	void transfer() throws JsonProcessingException {

		// ARRANGE
		transferValidations.add(valid03);
		transferValidations.add(valid04);
		
		Optional<User> user = Optional.ofNullable(UtilsTest.userComum);
		Optional<User> user2 = Optional.ofNullable(UtilsTest.userLogista);
		BDDMockito.when(repository.findById(1l)).thenReturn(user);
		BDDMockito.when(repository.findById(2l)).thenReturn(user2);
		
		BDDMockito.doNothing().when(notificationService).sendNotification(any());
					
		TransferResponseDto transferResponseDto = new TransferResponseDto(1l, "sucess", true);
		
		// ACt
		userService.transfer(UtilsTest.transferRequestDto);

		// ASSERT
		BDDMockito.then(valid03).should().valid(UtilsTest.transferRequestDto.idPayer(),
				UtilsTest.transferRequestDto.idPayee(), UtilsTest.transferRequestDto.value());
		BDDMockito.then(valid04).should().valid(UtilsTest.transferRequestDto.idPayer(),
				UtilsTest.transferRequestDto.idPayee(), UtilsTest.transferRequestDto.value());
		BDDMockito.then(repository).should().findById(1l);
		BDDMockito.then(repository).should().findById(2l);
		
		verify(repository,times(1)).saveAll(captor.capture());
		Iterable<User> savedUsers = captor.getValue();
		
		for (User u : savedUsers) {
		    if (u.getId().equals(UtilsTest.userLogista.getId())) {
		        assertEquals(50, u.getWallet().getBalance().intValue(),"Verificando se a transferencia foi feita"); 
		    }
		
		verify(userTransferPublish,times(1)).responseTransfer(transferResponseDto);	
		verify(notificationService,times(1)).sendNotification(any());
	}

}}
