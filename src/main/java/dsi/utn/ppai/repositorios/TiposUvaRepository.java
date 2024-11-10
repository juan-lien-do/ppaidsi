package dsi.utn.ppai.repositorios;

import dsi.utn.ppai.entidades.TipoUvaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TiposUvaRepository extends JpaRepository<TipoUvaEntity, Long> {
}
