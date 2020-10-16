package com.br.finnet.integracaoAdiq.domain.entities;


import com.br.finnet.integracaoAdiq.domain.enums.CaptureTypeEnum;
import com.br.finnet.integracaoAdiq.domain.enums.CurrencyEnum;
import com.br.finnet.integracaoAdiq.domain.enums.ProductTypeEnum;
import com.br.finnet.integracaoAdiq.domain.enums.TransactionTypeEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TRANSACTION")
public class PaymentEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String paymentId;
    private String authorizationCode;
    private String orderNumber;
    private Integer amount;
    private String transactionType;
    private String currencyCode;
    private String captureType;
    private String productType;
    private String documentNumber;
    private String firstName;
    private String lastName;
    private String city;
    private String state;
    private String country;
    private String statusPayment = "ATIVO";
    @CreationTimestamp
    private LocalDateTime datePayment;
}