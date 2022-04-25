package br.com.zup.handora.cadastrandocontatosunicos.departamento;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartamentoRepository extends JpaRepository<Departamento, Long> {

    boolean existsBySigla(String sigla);

}
