package ANDREASERRANO_20240349.ANDREASERRANO_20240349.Repository;

import ANDREASERRANO_20240349.ANDREASERRANO_20240349.Entity.LibrosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LibrosRepository extends JpaRepository<LibrosEntity, Long> {
    Optional<LibrosEntity> FindById(Long id);
}
