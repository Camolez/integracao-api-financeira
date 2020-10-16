package com.br.finnet.integracaoAdiq.domain.models.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemsModel implements Serializable {
    private String id;
    private Integer amount;
    private String description;
    private Double ratePercent;
    private Integer rateAmount;
}
