/**
 * @author higor.robinn on 11/12/2024.
 */

package br.com.higor.domain;
import java.util.Objects;

public class Cliente {

    private String nome;
    private Long cpf;
    private Long tel;
    private String end;
    private Integer numero;
    private String cidade;
    private String estado;

    public Cliente(String nome, String cpf, String tel, String end, String numero, String cidade, String estado) {
        if (nome.isEmpty() || cpf.trim().isEmpty() || tel.trim().isEmpty() ||
                end.isEmpty() || numero.trim().isEmpty() || cidade.isEmpty() || estado.isEmpty()) {
            throw new IllegalArgumentException("Todos os campos devem ser preenchidos.");
        }


        this.nome = nome;
        this.cpf = Long.valueOf(cpf.trim());
        this.tel = Long.valueOf(tel.trim());
        this.end = end;

        try {
            this.numero = Integer.valueOf(numero.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Número deve ser um valor inteiro válido");
        }

        this.cidade = cidade;
        this.estado = estado;


    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getCpf() {
        return cpf;
    }

    public void setCpf(Long cpf) {
        this.cpf = cpf;
    }

    public Long getTel() {
        return tel;
    }

    public void setTel(Long tel) {
        this.tel = tel;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(cpf, cliente.cpf);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(cpf);
    }

    @Override
    public String toString() {
        return "Cliente: " + "\n" +
                "CPF = " + cpf + "\n" +
                "Nome = " + nome
                ;
    }
}
