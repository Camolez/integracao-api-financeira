package com.br.finnet.integracaoAdiq.domain.enums;

public enum CaptureTypeEnum {

    AC("ac"),
    PA("pa");

    private String captureType;

    CaptureTypeEnum(String captureType) {
        this.captureType = captureType;
    }

    public String getCaptureType() {
        return captureType;
    }
}
