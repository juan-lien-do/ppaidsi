package dsi.utn.ppai.repositorios;

import dsi.utn.ppai.entidades.VinoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VinoRepository extends JpaRepository<VinoEntity, Long> {
}
