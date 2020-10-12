package com.br.finnet.integracaoAdiq.domain.enums;

public enum ProductTypeEnum {

    AVISTA("avista"),
    PARCELADO("parcelado");

    private String productType;

    ProductTypeEnum(String productType) {
        this.productType = productType;
    }

    public String getProductType() {
        return productType;
    }
}
