package com.br.finnet.integracaoAdiq.service;

import com.br.finnet.integracaoAdiq.domain.models.response.AdiqGetTokenModel;

public interface ApiAdiqService {

    AdiqGetTokenModel getAccessToken();
}
