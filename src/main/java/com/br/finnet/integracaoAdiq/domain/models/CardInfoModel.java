package com.br.finnet.integracaoAdiq.domain.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "CARDINFO")
public class CardInfoModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CARDINFO")
    private Integer idCardInfo;
    private String vaultId;
    private String numberToken;
//    @Column(nullable = false)
    private String cardholderNme;
    private String securityCode;
    private String expirationMonth;
    private String expirationYear;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "FK_SELLERINFO")
    private SellerInfoModel sellerInfoModel;

}
