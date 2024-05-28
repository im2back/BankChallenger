package io.github.im2back.usermicroservice.util;

import java.math.BigDecimal;

import io.github.im2back.usermicroservice.model.dto.TransferRequestDto;
import io.github.im2back.usermicroservice.model.dto.UserRegisterRequestDto;
import io.github.im2back.usermicroservice.model.entities.user.User;
import io.github.im2back.usermicroservice.model.entities.user.UserType;
import io.github.im2back.usermicroservice.model.entities.wallet.Wallet;

public class UtilsTest {

	public static final User userComum;
	public static final User userLogista;
	public static final Wallet walletComum;
	public static final Wallet walletLogista;
	
    //Inicialização 
    
    public static final UserRegisterRequestDto userRegisterRequest = 
    		new UserRegisterRequestDto("Antonio Jose", "1234567810", "antonio@gmail.com", "12345678", UserType.LOGISTA);
    
    public static final TransferRequestDto transferRequestDto = 
    		new TransferRequestDto(1l, 2l, new BigDecimal(50));

    static {
        // Inicialização do userComum e sua carteira
        userComum = new User(1L, "Claudio Serra", "123456789", "claudio@gmail.com", "12345678", UserType.COMUM, null);
        walletComum = new Wallet(1L, new BigDecimal(100), userComum);
        userComum.setWallet(walletComum);

        // Inicialização do userLogista e sua carteira
        userLogista = new User(2L, "Antonio Jose", "1234567810", "antonio@gmail.com", "12345678", UserType.LOGISTA, null);
        walletLogista = new Wallet(2L, new BigDecimal(0), userLogista);
        userLogista.setWallet(walletLogista);      
    }

}
