package com.br.finnet.integracaoAdiq.domain.models.request;

import com.br.finnet.integracaoAdiq.domain.enums.CaptureTypeEnum;
import com.br.finnet.integracaoAdiq.domain.enums.CurrencyEnum;
import com.br.finnet.integracaoAdiq.domain.enums.ProductTypeEnum;
import com.br.finnet.integracaoAdiq.domain.enums.TransactionTypeEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentModel implements Serializable {
    @Pattern(regexp = "credit|debit", message = "O campo TransactionType deve ser debit ou credit")
    private String transactionType;
    private Integer amount;
    private String currencyCode; //TODO ENUM PATTERN STRING
    private String productType;
    private Integer installments;
    private String captureType;
    private Boolean recurrent;
}
