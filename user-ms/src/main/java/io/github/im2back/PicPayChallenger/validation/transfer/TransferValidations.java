package io.github.im2back.PicPayChallenger.validation.transfer;

import java.math.BigDecimal;

import io.github.im2back.PicPayChallenger.model.entities.user.User;

public interface TransferValidations {

	public void valid(User userPayer, User userPayee, BigDecimal value);

}
