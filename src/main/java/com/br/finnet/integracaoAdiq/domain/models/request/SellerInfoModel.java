package com.br.finnet.integracaoAdiq.domain.models.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SellerInfoModel implements Serializable {
    private String orderNumber;
    private String softDescriptor;
    private Integer dynamicMcc;
    private String code3DS;
    private String urlSite3DS;
}
