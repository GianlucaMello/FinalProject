package Model.bo;

import java.io.Serializable;

public class Veiculo extends Id implements Serializable {

    private String tipo, placa, cor;
    private Versao versao;
    private int anoModelo;
    private PessoaFisica pessoaFisica;
    private PessoaJuridica pessoaJuridica;

    public Veiculo() {
    }

    public Veiculo(int id, String tipo, String placa, int anoModelo,
            PessoaFisica pessoaFisica, String cor, Versao versao) {
        super(id);
        this.tipo = tipo;
        this.placa = placa;
        this.versao = versao;
        this.anoModelo = anoModelo;
        this.pessoaFisica = pessoaFisica;
        this.cor = cor;
    }

    public Veiculo(int id, String tipo, String placa, int anoModelo,
            PessoaJuridica pessoaJuridica, String cor, Versao versao) {
        super(id);
        this.tipo = tipo;
        this.placa = placa;
        this.versao = versao;
        this.anoModelo = anoModelo;
        this.pessoaJuridica = pessoaJuridica;
        this.cor = cor;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String tipoProp) {
        this.cor = tipoProp;
    }

    public PessoaFisica getPessoaFisica() {
        return pessoaFisica;
    }

    public void setPessoaFisica(PessoaFisica pessoaFisica) {
        this.pessoaFisica = pessoaFisica;
    }

    public PessoaJuridica getPessoaJuridica() {
        return pessoaJuridica;
    }

    public void setPessoaJuridica(PessoaJuridica pessoaJuridica) {
        this.pessoaJuridica = pessoaJuridica;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public Versao getVersao() {
        return versao;
    }

    public void setVersao(Versao versao) {
        this.versao = versao;
    }

    public int getAnoModelo() {
        return anoModelo;
    }

    public void setAnoModelo(int anoModelo) {
        this.anoModelo = anoModelo;
    }

}
