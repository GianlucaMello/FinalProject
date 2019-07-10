package Model.bo;

import java.io.Serializable;

public class PessoaFisica extends Pessoa implements Serializable {

    private String rg, cpf;

    public PessoaFisica() {
    }

    public PessoaFisica(int id, String cpf, String rg, String nome, String fone, String celular, String address,
            String email, String obs, String status, char tipo) {
        super(id, nome, fone, celular, address, email, obs, status, tipo);
        this.cpf = cpf;
        this.rg = rg;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    @Override
    public String toString() {
        return "PessoaFisica{" + "cpf=" + cpf + ", rg=" + rg + '}';
    }
}
