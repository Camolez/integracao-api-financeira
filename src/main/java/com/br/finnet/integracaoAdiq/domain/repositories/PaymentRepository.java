package com.br.finnet.integracaoAdiq.domain.repositories;

import com.br.finnet.integracaoAdiq.domain.models.request.PaymentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentModel, Integer>, JpaSpecificationExecutor<PaymentModel> {
}
