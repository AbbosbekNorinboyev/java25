package uz.brb.java25.repository;

import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.brb.java25.entity.Document;

@Repository
public interface DocumentRepository extends JpaRepository<@NonNull Document, @NonNull Long> {
}