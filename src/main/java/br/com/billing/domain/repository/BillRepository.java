package br.com.billing.domain.repository;

import br.com.billing.domain.entity.BillEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Transactional(readOnly = true)
@Repository
public interface BillRepository extends JpaRepository<BillEntity, UUID> {

    BillEntity findBillByIdAndSysuserId(UUID id, UUID userId);
}
