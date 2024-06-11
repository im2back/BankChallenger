package io.github.im2back.transfermicroservice.amqp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;

import java.io.IOException;
import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.json.JacksonTester;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.im2back.transfermicroservice.dto.TransferPublishDto;
import io.github.im2back.transfermicroservice.dto.TransferRequestDto;

@AutoConfigureJsonTesters
@ExtendWith(MockitoExtension.class)
class TransactionPublisherTest {

    @InjectMocks
    private TransactionPublisher transactionPublisher;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @Captor
    private ArgumentCaptor<String> captorJson;

    @Autowired
    private JacksonTester<TransferPublishDto> jacksonTransferPublishDto;

    @BeforeEach
    void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
    }

    @Test
    @DisplayName("Deveria enviar algo para a fila do rabbitmq")
    void issueTransfer() throws AmqpException, IOException {
        // ARRANGE
        Long id = 1L;
        TransferRequestDto requestDto = new TransferRequestDto(1L, 2L, new BigDecimal(100));
        TransferPublishDto publishDto = new TransferPublishDto(requestDto, id);

        // Converte publishDto para JSON
        var json = jacksonTransferPublishDto.write(publishDto).getJson();

        // Simula o comportamento do método convertAndSend
        doNothing().when(rabbitTemplate).convertAndSend("transfer", json);

        // ACT
        transactionPublisher.issueTransfer(publishDto);

        // ASSERT
        BDDMockito.then(rabbitTemplate).should().convertAndSend(anyString(), captorJson.capture());
        var jsonCaptured = captorJson.getValue();
        assertEquals(json, jsonCaptured);
    }
}


