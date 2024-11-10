package dsi.utn.ppai.repositorios;

import dsi.utn.ppai.entidades.VarietalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VarietalesRepository extends JpaRepository<VarietalEntity, Long> {
}
