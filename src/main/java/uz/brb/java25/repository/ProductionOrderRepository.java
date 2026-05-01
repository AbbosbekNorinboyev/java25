package uz.brb.java25.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.brb.java25.entity.ProductionOrderEntity;

import java.util.UUID;

@Repository
public interface ProductionOrderRepository extends JpaRepository<ProductionOrderEntity, UUID> {
    Page<ProductionOrderEntity> findAll(Pageable pageable);
}
