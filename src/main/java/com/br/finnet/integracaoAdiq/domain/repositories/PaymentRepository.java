package com.br.finnet.integracaoAdiq.domain.repositories;

import com.br.finnet.integracaoAdiq.domain.models.PaymentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentModel, Integer> {
}
