/**
 * @author higor.robinn on 11/12/2024.
 */

package br.com.higor.dao;
import br.com.higor.domain.Cliente;
import java.util.Collection;

public interface IClienteDAO {

    Boolean cadastrar(Cliente cliente);
    void excluir(Long cpf);
    void alterar(Cliente cliente);
    Cliente consultar(Long cpf);
    Collection<Cliente> listarClientes();

}
