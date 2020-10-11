package com.br.finnet.integracaoAdiq.domain.enums;

import lombok.Data;

public enum TransactionTypeEnum {

    DEBIT("debit"),
    CREDIT("credit");

    private String typer;

    TransactionTypeEnum(String typer) {
        this.typer = typer;
    }

    public String getType() {
        return typer;
    }
}
