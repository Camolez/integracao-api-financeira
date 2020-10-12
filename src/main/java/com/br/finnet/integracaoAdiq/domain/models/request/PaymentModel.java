package com.br.finnet.integracaoAdiq.domain.models.request;

import com.br.finnet.integracaoAdiq.domain.enums.CurrencyEnum;
import com.br.finnet.integracaoAdiq.domain.enums.ProductTypeEnum;
import com.br.finnet.integracaoAdiq.domain.enums.TransactionTypeEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDateTime;


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
    @Enumerated(EnumType.STRING)
    private TransactionTypeEnum transactionType;
    @Column(nullable = false)
    private Integer amount;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CurrencyEnum currencyCode;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ProductTypeEnum productType;
    @Column(nullable = false)
    private Integer installments;
    @Column(nullable = false)
    private String captureType;
    @Column(nullable = false)
    private Boolean recurrent;
    @JsonFormat(pattern="yyyy-MM-dd")
    @CreationTimestamp
    private LocalDateTime datePayment;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "FK_CARDINFO")
    private CardInfoModel cardInfoModel;

}
