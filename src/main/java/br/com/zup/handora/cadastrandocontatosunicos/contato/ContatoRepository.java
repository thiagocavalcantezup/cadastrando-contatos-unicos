package br.com.zup.handora.cadastrandocontatosunicos.contato;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ContatoRepository extends JpaRepository<Contato, Long> {

    boolean existsByTelefoneAndDepartamentoId(String telefone, Long departamentoId);

}
