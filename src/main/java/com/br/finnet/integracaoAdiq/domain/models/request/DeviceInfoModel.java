package com.br.finnet.integracaoAdiq.domain.models.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceInfoModel {
    private String httpAcceptBrowserValue;
    private String httpAcceptContent;
    private String httpBrowserLanguage;
    private String httpBrowserJavaEnabled;
    private String httpBrowserJavaScriptEnabled;
    private String httpBrowserColorDepth;
    private String httpBrowserScreenHeight;
    private String httpBrowserScreenWidth;
    private String httpBrowserTimeDifference;
    private String userAgentBrowserValue;
}
