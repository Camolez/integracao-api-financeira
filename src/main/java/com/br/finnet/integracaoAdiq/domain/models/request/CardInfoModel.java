package com.br.finnet.integracaoAdiq.domain.models.request;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardInfoModel implements Serializable {
    private String vaultId;
    private String numberToken;
    @NotEmpty(message = "Campo não pode ser vazio")
    @Size(min = 3, max = 30, message = "O campo deve conter entre {min} e {max}")
    private String cardholderName;
    private String securityCode;
    @Pattern(regexp = "visa|mastercard|amex|elo", message = "O campo brand deve conter visa, mastercard, amex ou elo")
    private String brand;
    private String expirationMonth;
    private String expirationYear;
}
