package com.br.finnet.integracaoAdiq.controller.handlers.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErroResponseModel implements Serializable {
    private String mensagem;

}
