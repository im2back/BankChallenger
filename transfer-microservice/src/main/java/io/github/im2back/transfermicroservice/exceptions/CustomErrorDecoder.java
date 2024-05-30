package io.github.im2back.transfermicroservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import feign.Response;
import feign.codec.ErrorDecoder;
import io.github.im2back.transfermicroservice.service.exceptions.TransferValidationException;

public class CustomErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.status() == HttpStatus.NOT_FOUND.value()) {
            return new TransferValidationException("Recurso não encontrado");
        }
        return new HttpClientErrorException(HttpStatus.valueOf(response.status()), "Erro no cliente Feign");
    }
}