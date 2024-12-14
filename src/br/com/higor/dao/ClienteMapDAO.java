/**
 * @author higor.robinn on 11/12/2024.
 */

package br.com.higor.dao;

import br.com.higor.domain.Cliente;

import java.util.Collection;
import java.util.Map;

public class ClienteMapDAO implements IClienteDAO{
    private Map<Long, Cliente> map;

    public ClienteMapDAO(Map<Long, Cliente> map) {
        this.map = map;
    }

    @Override
    public Boolean cadastrar(Cliente cliente) {
        if (this.map.containsKey(cliente.getCpf())) {
            return false;
        }

        this.map.put(cliente.getCpf(), cliente);
        return true;
    }

    @Override
    public void excluir(Long cpf) {
        Cliente cliente = this.map.get(cpf);

        if (cliente != null){
            this.map.remove(cpf);
        }
    }

    @Override
    public void alterar(Cliente cliente) {
         Cliente _cliente = this.map.get(cliente.getCpf());
         if (_cliente != null) {
             _cliente.setCidade(cliente.getCidade());
             _cliente.setEnd(cliente.getEnd());
             _cliente.setEstado(cliente.getEstado());
             _cliente.setNumero(cliente.getNumero());
             _cliente.setNome(cliente.getNome());
             _cliente.setTel(cliente.getTel());
         }
    }

    @Override
    public Cliente consultar(Long cpf) {
        return this.map.get(cpf);
    }

    @Override
    public Collection<Cliente> listarClientes() {
        return this.map.values();
    }
}
