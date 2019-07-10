package Model.bo;

import java.io.Serializable;
public class PessoaJuridica extends Pessoa implements Serializable {

    private String cnpj;

    public PessoaJuridica() {
    }

    public PessoaJuridica(int id, String cnpj, String nome, String fone, String celular, String address, String email, String obs, String status, char tipo) {
        super(id, nome, fone, celular, address, email, obs, status, tipo);
        this.cnpj = cnpj;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    @Override
    public String toString() {
        return "PessoaJuridica{" + "cnpj=" + cnpj + '}';
    }

}
