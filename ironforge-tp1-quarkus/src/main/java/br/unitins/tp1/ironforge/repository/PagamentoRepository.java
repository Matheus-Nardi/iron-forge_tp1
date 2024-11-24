package br.unitins.tp1.ironforge.repository;

import br.unitins.tp1.ironforge.model.pagamento.Pagamento;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PagamentoRepository implements PanacheRepository<Pagamento> {

    public Pagamento findByChavePix(String chave) {
        return find("FROM Pix p WHERE p.chave = ?1", chave).firstResult();
    }

    public Pagamento findByCodigoBoleto(String codigo) {
        return find("FROM Boleto b WHERE b.codigoBarras = ?1", codigo).firstResult();
    }


}
