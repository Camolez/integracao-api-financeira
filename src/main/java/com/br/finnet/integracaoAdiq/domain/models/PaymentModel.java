package com.br.finnet.integracaoAdiq.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GeneratorType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;


@Data
@Entity
@Table(name = "TRANSACTION")
public class PaymentModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long idTransaction;
//    @Column(name = "TRANSACTION_TYPE")
    private String transactionType;
    private Integer amount;
    private String currencyCode;
    private String productType;
    private Integer installments;
    private String captureType;
    private Boolean recurrent;

}
