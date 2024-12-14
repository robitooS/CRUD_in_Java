/**
 * @author higor.robinn on 12/12/2024.
 */

package br.com.higor.app;

import br.com.higor.dao.ClienteMapDAO;
import br.com.higor.dao.IClienteDAO;
import br.com.higor.domain.Cliente;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;


public class AppMain {

    private static IClienteDAO IClienteDAO;

    public static void main(String[] args) {
        Map<Long, Cliente> clienteMap = new HashMap<>();
        IClienteDAO = new ClienteMapDAO(clienteMap);
        while (true) {
            String options = JOptionPane.showInputDialog(null, "Digite 1 para cadastro, 2 para consultar, 3 para exclusão, " +
                    "4 para alteração ou 5 para sair", "Opções", JOptionPane.INFORMATION_MESSAGE);

            // Valida a opção escolhida
            while (!isValid(options)) {
                if (options.isEmpty()) {
                    exit();
                }

                options = JOptionPane.showInputDialog(null, "Opção inválida! Digite 1 para cadastro, 2 para consultar, 3 para exclusão," +
                        "4 para alteração ou 5 para sair!", "Opções", JOptionPane.INFORMATION_MESSAGE);
            }

            // Processa a opção escolhida
            switch (options) {
                case "1":
                    cadastrarCliente();
                    break;
                case "2":
                    consultarCliente();
                    break;
                case "3":
                    excluirCliente();
                    break;
                case "4":
                    alterarCliente();
                    break;
                case "5":
                    exit();
                    return; // Fecha o programa
            }
        }
    }

    private static void alterarCliente() {
        String dados = JOptionPane.showInputDialog(null, "Informe o CPF do cliente para alterar seus dados", "Alterar", JOptionPane.INFORMATION_MESSAGE);

        // Verifica se o CPF foi informado
        if (dados == null || dados.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Você deve inserir um CPF!", "Erro", JOptionPane.ERROR_MESSAGE);
            return; // Sai do método se o CPF não for informado
        }

        long cpf;
        try {
            cpf = Long.parseLong(dados);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "CPF inválido! Deve ser um número.", "Erro", JOptionPane.ERROR_MESSAGE);
            return; // Sai do método se o CPF não for válido
        }

        Cliente cliente = IClienteDAO.consultar(cpf);
        if (cliente == null) {
            JOptionPane.showMessageDialog(null, "Esse cliente não está cadastrado!", "Erro", JOptionPane.ERROR_MESSAGE);
        } else {
            // Solicita os novos dados
            String newDados = JOptionPane.showInputDialog(null, "Informe os novos dados do cliente da seguinte forma: Nome,Telefone,Endereço,Número,Cidade,Estado", "Alterar", JOptionPane.INFORMATION_MESSAGE);

            if (newDados == null || newDados.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Você deve inserir os novos dados!", "Erro", JOptionPane.ERROR_MESSAGE);
                return; // Sai do método se os novos dados não forem informados
            }

            String[] _newDados = newDados.split(",");

            // Verifica se todos os campos foram preenchidos
            if (_newDados.length != 6) {
                JOptionPane.showMessageDialog(null, "Todos os novos dados devem ser preenchidos!", "Erro", JOptionPane.ERROR_MESSAGE);
                return; // Sai do método se não houver 6 campos
            }

            // Atualiza os dados do cliente
            cliente.setNome(_newDados[0]);
            cliente.setTel(Long.valueOf(_newDados[1]));
            cliente.setEnd(_newDados[2]);
            cliente.setNumero(Integer.valueOf(_newDados[3]));
            cliente.setCidade(_newDados[4]);
            cliente.setEstado(_newDados[5]);

            // Chama o método de alterar no DAO
            IClienteDAO.alterar(cliente);
            JOptionPane.showMessageDialog(null, "Dados do cliente alterados com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private static void excluirCliente() {
        String dados = JOptionPane.showInputDialog(null, "Informe o CPF do cliente para ser deletado", "Excluir", JOptionPane.INFORMATION_MESSAGE);
        Long _dados = Long.valueOf(dados);

        Cliente cliente = IClienteDAO.consultar(_dados);
        if (cliente == null) {
            JOptionPane.showMessageDialog(null, "Esse cliente não está cadastrado!", "Erro", JOptionPane.ERROR_MESSAGE);
        } else {
            IClienteDAO.excluir(_dados);
            JOptionPane.showMessageDialog(null, "Cliente deletado com sucesso!", "Excluir", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private static void consultarCliente() {
        String dados = JOptionPane.showInputDialog(null, "Informe o CPF do cliente para consulta abaixo", "Consultar", JOptionPane.INFORMATION_MESSAGE);
        Long _dados = Long.valueOf(dados);

        Cliente cliente = IClienteDAO.consultar(_dados);
        if (cliente == null) {
            JOptionPane.showMessageDialog(null, "Esse cliente não está cadastrado!", "Erro", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, cliente, "Consulta", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private static void cadastrarCliente() {
        while (true) {
            String dados = JOptionPane.showInputDialog(null, "Informe os dados do cliente separados por vírgula.\nNesse formato: Nome," +
                    "CPF,Telefone,Endereço,Número,Cidade,Estado", "Cadastro", JOptionPane.INFORMATION_MESSAGE);

            if (dados == null || dados.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Você deve inserir dados no campo!", "Erro", JOptionPane.ERROR_MESSAGE);
                continue; // Continua solicitando os dados
            }

            String[] _dados = dados.split(",");

            // Verifica se todos os campos foram preenchidos
            if (_dados.length != 7) {
                JOptionPane.showMessageDialog(null, "Todos os dados devem ser preenchidos!", "Erro", JOptionPane.ERROR_MESSAGE);
                continue; // Continua solicitando os dados
            }

            try {
                Cliente cliente = new Cliente(_dados[0], _dados[1], _dados[2], _dados[3], _dados[4], _dados[5], _dados[6]);
                if (IClienteDAO.cadastrar(cliente)) {
                    JOptionPane.showMessageDialog(null, "Cliente cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Cliente já cadastrado!", "Erro", JOptionPane.ERROR_MESSAGE);
                }
                break; // Sai do loop após cadastrar

            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private static void exit() {
        System.exit(0); // Encerra o programa
    }

    private static boolean isValid(String options) {
        return options.equals("1") || options.equals("2") || options.equals("3") || options.equals("4") || options.equals("5");
    }
}
