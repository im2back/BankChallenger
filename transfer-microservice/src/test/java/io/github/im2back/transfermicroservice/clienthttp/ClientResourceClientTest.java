package io.github.im2back.transfermicroservice.clienthttp;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import io.github.im2back.transfermicroservice.dto.TransferRequestDto;
import io.github.im2back.transfermicroservice.dto.UserDto;

@SpringBootTest
class ClientResourceClientTest {

	@MockBean
	private ClientResourceClient clientResourceClient;

	@Test
	@DisplayName("Deveria retornar UserDto ao chamar findUser")
	void findUser() {
		// ARRANGE
		UserDto userDto = new UserDto(1L, "antonio", "123456789", "antonio@gmail.com", "123456", "COMUM", BigDecimal.ZERO);
		BDDMockito.when(clientResourceClient.findUser(1L)).thenReturn(new ResponseEntity<>(userDto, HttpStatus.OK));
		
		// ACT
		ResponseEntity<UserDto> response = clientResourceClient.findUser(1L);

		// ASSERT
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(userDto, response.getBody());
	}
	
    @Test
    @DisplayName("Deveria retornar status 200 ao chamar transfer")
    void transfer() {
        // ARRANGE
        TransferRequestDto transferRequestDto = new TransferRequestDto(1L, 2L, BigDecimal.TEN);
        BDDMockito.when(clientResourceClient.transfer(transferRequestDto)).thenReturn(new ResponseEntity<>(HttpStatus.OK));

        // ACT
        ResponseEntity<Void> response = clientResourceClient.transfer(transferRequestDto);

        // ASSERT
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

}
