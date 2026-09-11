package uz.brb.java25.repository;

import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.brb.java25.entity.Region;
import uz.brb.java25.enums.Status;

@Repository
public interface RegionRepository extends JpaRepository<@NonNull Region, @NonNull Long> {
    Page<Region> findAllByStatusNot(Status status, Pageable pageable);
}
