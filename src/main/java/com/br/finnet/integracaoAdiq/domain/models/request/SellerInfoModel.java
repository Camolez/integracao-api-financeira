package com.br.finnet.integracaoAdiq.domain.models.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "SELLER_INFO")
public class SellerInfoModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idSellerInfo;
    private String orderNumber;
    private String softDescriptor;
    private Integer dynamicMcc;
    private String code3DS;
    private String urlSite3DS;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "FK_CUSTOMER")
    private CustomerModel customerModel;
}
