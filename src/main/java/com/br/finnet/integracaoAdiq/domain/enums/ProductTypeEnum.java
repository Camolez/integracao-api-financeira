package com.br.finnet.integracaoAdiq.domain.enums;

public enum ProductTypeEnum {
    AVISTA("avista"),
    DEBITO("debito"),
    LOJISTA("lojista"),
    EMISSOR("emissor");



    private String productType;

    ProductTypeEnum(String productType) {
        this.productType = productType;
    }

    public String getProductType() {
        return productType;
    }
}
