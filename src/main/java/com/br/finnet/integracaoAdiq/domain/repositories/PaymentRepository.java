package com.br.finnet.integracaoAdiq.domain.repositories;

import com.br.finnet.integracaoAdiq.domain.models.PaymentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentModel, Long> {
}
