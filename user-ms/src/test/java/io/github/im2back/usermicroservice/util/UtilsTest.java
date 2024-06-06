package io.github.im2back.usermicroservice.util;

import java.math.BigDecimal;

import io.github.im2back.usermicroservice.model.dto.TransferRequestDto;
import io.github.im2back.usermicroservice.model.dto.UserRegisterRequestDto;
import io.github.im2back.usermicroservice.model.entities.user.UserComum;
import io.github.im2back.usermicroservice.model.entities.user.UserLogista;
import io.github.im2back.usermicroservice.model.entities.wallet.WalletComum;
import io.github.im2back.usermicroservice.model.entities.wallet.WalletLogista;

public class UtilsTest {

	public static final UserComum userComum;
	public static final UserLogista userLogista;
	public static final WalletComum walletComum;
	public static final WalletLogista walletLogista;
	
    //Inicialização 
    
    public static final UserRegisterRequestDto userRegisterRequest = 
    		new UserRegisterRequestDto("Antonio Jose", "12345678911234", "antonio@gmail.com", "12345678");
    
    public static final TransferRequestDto transferRequestDto = 
    		new TransferRequestDto(1l, 2l, new BigDecimal(50));

    static {
        // Inicialização do userComum e sua carteira
        walletComum = new WalletComum(1L, new BigDecimal(100), null);
        userComum = new UserComum(1L, "Claudio Serra", "12345678",  "12345678910","claudio@gmail.com", walletComum);
       
   

        // Inicialização do userLogista e sua carteira
        walletLogista = new WalletLogista(2L, new BigDecimal(0), null);
        userLogista = new UserLogista(2L, "Antonio Jose", "12345678","12345678911234", "antonio@gmail.com",  walletLogista);    
      
    }

}
