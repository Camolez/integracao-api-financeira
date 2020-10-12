package com.br.finnet.integracaoAdiq.domain.models.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "DEVICE_INFO")
public class DeviceInfoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDeviceInfo;
//    private String httpAcceptBrowserValue;
//    private String httpAcceptContent;
//    private String httpBrowserLanguage;
//    private String httpBrowserJavaEnabled;
//    private String httpBrowserJavaScriptEnabled;
//    private String httpBrowserColorDepth;
//    private String httpBrowserScreenHeight;
//    private String httpBrowserScreenWidth;
//    private String httpBrowserTimeDifference;
//    private String userAgentBrowserValue;
//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "FK_SELLERS")
//    private SellersModel sellersModel;
}
