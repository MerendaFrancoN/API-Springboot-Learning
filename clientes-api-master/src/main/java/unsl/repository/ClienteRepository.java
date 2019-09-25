package unsl.repository;

import unsl.entities.Cliente;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
	Cliente findByDni(@Param("dni") Long dni);
}
