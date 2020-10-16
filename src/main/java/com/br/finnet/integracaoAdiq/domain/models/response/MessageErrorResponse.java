package com.br.finnet.integracaoAdiq.domain.models.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public final class MessageErrorResponse {
    @ApiModelProperty(value = "Campo do erro", example = "name", required = true)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String field;

    @ApiModelProperty(value = "Mensagem de erro", example = "Invalid name.", required = true)
    private String message;

}