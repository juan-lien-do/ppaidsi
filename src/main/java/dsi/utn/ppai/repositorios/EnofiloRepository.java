package dsi.utn.ppai.repositorios;

import dsi.utn.ppai.entidades.EnofiloEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnofiloRepository extends JpaRepository<EnofiloEntity, Long> {
}
