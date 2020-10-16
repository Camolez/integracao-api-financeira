package com.br.finnet.integracaoAdiq.domain.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdiqGetTokenResponse implements Serializable {

    @JsonProperty(value = "accessToken")
    private String accessToken;
    @JsonProperty(value = "tokenType")
    private String tokenType;
    @JsonProperty(value = "expiresIn")
    private Integer expiresIn;
    @JsonProperty(value = "scope")
    private String scope;
}
