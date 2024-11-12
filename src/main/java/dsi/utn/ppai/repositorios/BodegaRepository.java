package dsi.utn.ppai.repositorios;

import dsi.utn.ppai.entidades.BodegaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BodegaRepository extends JpaRepository<BodegaEntity, Long> {
}
