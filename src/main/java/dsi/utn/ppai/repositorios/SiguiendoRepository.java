package dsi.utn.ppai.repositorios;

import dsi.utn.ppai.entidades.SiguiendoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SiguiendoRepository extends JpaRepository<SiguiendoEntity, Long> {
}
