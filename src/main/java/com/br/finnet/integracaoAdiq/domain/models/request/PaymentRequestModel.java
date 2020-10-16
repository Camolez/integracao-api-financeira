package com.br.finnet.integracaoAdiq.domain.models.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequestModel {
    @Valid
    @NotNull(message = "O campo Payment é obrigatório")
    private PaymentModel payment;
    @NotNull(message = "O campo CardInfo é obrigatório")
    @Valid
    private CardInfoModel cardInfo;
    @NotNull(message = "O campo SellerInfo é obrigatório")
    @Valid
    private SellerInfoModel sellerInfo;
    @NotNull(message = "O campo Customer é obrigatório")
    @Valid
    private CustomerModel customer;
    @NotNull(message = "O campo DeviceInfo é obrigatório")
    @Valid
    private DeviceInfoModel deviceInfo;
    @NotNull(message = "O campo Sellers é obrigatório")
    @Valid
    private List<SellersModel> sellers;
    @NotNull(message = "O campo GrantType é obrigatório")
    private String grantType;
    @NotNull(message = "O campo Authorization é obrigatório")
    private String authorization;
}
