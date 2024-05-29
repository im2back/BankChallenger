package io.github.im2back.transfermicroservice.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import io.github.im2back.transfermicroservice.clienthttp.ClientResourceClient;
import io.github.im2back.transfermicroservice.dto.TransferRequestDto;
import io.github.im2back.transfermicroservice.dto.UserDto;
import io.github.im2back.transfermicroservice.service.util.NotificationRequestDto;
import io.github.im2back.transfermicroservice.validation.transfer.TransferValidations;

@ExtendWith(MockitoExtension.class)
class TransferServiceTest {

	@InjectMocks
	private TransferService transferService;

	@Spy
	private List<TransferValidations> transferValidations = new ArrayList<>();

	@Mock
	TransferValidations valid01;

	@Mock
	TransferValidations valid02;

	@Mock
	private AuthorizationService authorizationService;

	@Mock
	private NotificationService notificationService;

	@Mock
	ClientResourceClient clientResourceClient;

	@Test
	@DisplayName("Deveria fazer os chamados para realizar a transferencia e não retornar nada")
	void test() {

		// ARRANGE
		UserDto userDto = new UserDto(1l, "name", "123456", "jeff@gmail.com", "123456", "COMUM", new BigDecimal(100));
		ResponseEntity<UserDto> body = ResponseEntity.ok(userDto);

		Long idPayer = 1l;
		Long idPayee = 2l;
		BigDecimal value = new BigDecimal(100);

		transferValidations.add(valid01);
		transferValidations.add(valid02);

		BDDMockito.doNothing().when(authorizationService).finalizeTransfer();
		BDDMockito.when(clientResourceClient.findUser(idPayee)).thenReturn(body);

		// ACT
		transferService.transfer(idPayer, idPayee, value);

		// ASSERT
		BDDMockito.then(valid01).should().valid(idPayer, idPayee, value);
		BDDMockito.then(valid02).should().valid(idPayer, idPayee, value);

		verify(authorizationService, times(1)).finalizeTransfer();
	}

	@Test
	@DisplayName("Deveria acionar os serviços para finalizar a transferencia e não retornar nada")
	void receivePayment() {

		// ARRANGE
		Long idPayer = 1l;
		Long idPayee = 2l;
		BigDecimal value = new BigDecimal(100);

		UserDto userDto = new UserDto(1l, "name", "123456", "jeff@gmail.com", "123456", "COMUM", new BigDecimal(100));
		ResponseEntity<UserDto> body = ResponseEntity.ok(userDto);
		BDDMockito.when(clientResourceClient.findUser(idPayee)).thenReturn(body);

		var notifyDto = new NotificationRequestDto("jeff@gmail.com", "Pagamento recebido com sucesso!");

		// ACT
		transferService.receivePayment(idPayer, idPayee, value);

		// ASSERT
		BDDMockito.then(clientResourceClient).should().findUser(idPayee);
		BDDMockito.then(clientResourceClient).should().transfer(new TransferRequestDto(idPayer, idPayee, value));
		BDDMockito.then(notificationService).should().sendNotification(notifyDto);

	}

}
