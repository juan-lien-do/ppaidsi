package dsi.utn.ppai.persistencia.repositorios;

import dsi.utn.ppai.persistencia.entidades.BodegaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BodegaRepository extends JpaRepository<BodegaEntity, Long> {
}
