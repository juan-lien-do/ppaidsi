package dsi.utn.ppai.persistencia.repositorios;

import dsi.utn.ppai.persistencia.entidades.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuariosRepository extends JpaRepository<UsuarioEntity, Long> {
}
