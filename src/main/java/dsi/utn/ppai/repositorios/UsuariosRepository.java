package dsi.utn.ppai.repositorios;

import dsi.utn.ppai.entidades.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuariosRepository extends JpaRepository<UsuarioEntity, Long> {
}
