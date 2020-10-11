package com.br.finnet.integracaoAdiq.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TRANSACTION")
public class PaymentModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "ID_TRANSACTION")
    private Integer idTransaction;
    @Column(nullable = false)
    private String transactionType;
    @Column(nullable = false)
    private Integer amount;
    @Column(nullable = false)
    private String currencyCode;
    @Column(nullable = false)
    private String productType;
    @Column(nullable = false)
    private Integer installments;
    @Column(nullable = false)
    private String captureType;
    @Column(nullable = false)
    private Boolean recurrent;
//    @MapsId
//    @OneToOne()
//    @JoinColumn(name = "FK_CARDINF0")
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "FK_CARDINFO")
//    @JoinTable(name = "PAYMONT_CARDINFO",joinColumns ={ @JoinColumn(name = "PAYMENT_ID", referencedColumnName = "ID_TRANSACTION") },
//        inverseJoinColumns ={ @JoinColumn(name = "CARDINFO_ID", referencedColumnName = "ID_CARDINFO") })
private CardInfoModel cardInfoModel;

}
