package io.github.im2back.transfermicroservice.validation.transfer;

import java.math.BigDecimal;

public interface TransferValidations {

	public void valid(Long idUserPayer, Long idUserPayee, BigDecimal value);

}

