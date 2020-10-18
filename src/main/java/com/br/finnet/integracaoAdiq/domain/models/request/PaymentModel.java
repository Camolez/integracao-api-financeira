package com.br.finnet.integracaoAdiq.domain.models.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentModel implements Serializable {
    @Pattern(regexp = "credit|debit", message = "FAVOR INFORMAR UM VALOR VALIDO: CREDIT OU DEBIT")
    private String transactionType;
    private Integer amount;
    @Pattern(regexp = "BRL", message = "FAVOR INFORMAR UMA MODEDA VALIDA: BRL")
    private String currencyCode;
    @Pattern(regexp = "avista|debito|lojista|emissor", message = "FAVOR INFORMAR UMA VALOR VALIDO: avista, debito, lojista OU emissor")
    private String productType;
    private Integer installments;
    @Pattern(regexp = "ac|pa", message = "FAVOR INFORMAR UMA VALOR VALIDO: ac OU pa")
    private String captureType;
    private Boolean recurrent;
}
