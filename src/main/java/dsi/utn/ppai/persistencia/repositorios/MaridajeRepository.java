package dsi.utn.ppai.persistencia.repositorios;

import dsi.utn.ppai.persistencia.entidades.MaridajeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaridajeRepository extends JpaRepository<MaridajeEntity, Long> {
}
