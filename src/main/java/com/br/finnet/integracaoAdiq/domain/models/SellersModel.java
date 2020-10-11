package com.br.finnet.integracaoAdiq.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "SELLER")
public class SellersModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idSellers;
    private String amount;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "FK_ITENS")
    private List<ItemsModel> itemsModel;

}
