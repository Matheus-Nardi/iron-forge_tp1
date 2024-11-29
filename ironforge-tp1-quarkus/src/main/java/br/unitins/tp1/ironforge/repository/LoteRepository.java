package br.unitins.tp1.ironforge.repository;

import br.unitins.tp1.ironforge.model.Lote;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class LoteRepository implements PanacheRepository<Lote> {

    // Vai dar buxa, pois cada empresa possui seu codigo de lote. Logo, precisamos
    // transformar esse código em unico, Adicionar id, nome , algo do tipo
    public Lote findByCodigo(String codigo) {
        return find("codigo LIKE ?1", "%" + codigo + "%").firstResult();
    }

    /**
     * 
     * @param id
     * @return retorna o whey com o lote mais antigo e com quantidade em estoque
     *         (maior que 0)
     */

    public Lote findByWhey(Long idWhey) {
        StringBuffer jpql = new StringBuffer();
        jpql.append("SELECT ");
        jpql.append(" l ");
        jpql.append(" FROM ");
        jpql.append(" Lote l ");
        jpql.append(" WHERE ");
        jpql.append(" l.wheyProtein.id = ?1 ");
        jpql.append(" AND l.quantidade > 0 ");
        jpql.append(" ORDER BY l.dataFabricacao ");
        return find(jpql.toString(), idWhey).firstResult();
    }

    // Esse método retorna o total em estoque de determinado whey
    public Integer findByIdWheyQtdeTotal(Long idWhey) {
        Object result = find("SELECT SUM(l.quantidade) " +
                             "FROM Lote l " +
                             "WHERE l.wheyProtein.id = ?1 " +
                             "AND l.quantidade > 0", idWhey)
                        .firstResult();
        return result != null ? ((Number) result).intValue() : 0;
    }
    

}
    