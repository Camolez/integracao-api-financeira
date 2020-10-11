package com.br.finnet.integracaoAdiq.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "CUSTOMER")
public class CustomerModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCustomer;
    private String documentType;
    private String documentNumber;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String mobilePhoneNumber;
    private String address;
    private String complement;
    private String city;
    private String state;
    private String zipCode;
    private String ipAddress;
    private String country;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "FK_DEVICE")
    private DeviceInfoModel deviceInfoModel;
}
