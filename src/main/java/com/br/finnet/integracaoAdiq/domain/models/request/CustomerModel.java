package com.br.finnet.integracaoAdiq.domain.models.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerModel implements Serializable {
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
}
