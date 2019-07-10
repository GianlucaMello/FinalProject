package Model.bo;

import java.io.Serializable;

/**
 *
 * @author gianl
 */
public class Pessoa extends Id implements Serializable {

    private char tipo;
    private String nome, address, email, obs, status, fone, celular;
    public Pessoa() {
    }

    public Pessoa(String nome, String fone,String celular, String address, String email, String obs, String status, char tipo) {
        this.nome = nome;
        this.fone = fone;
        this.address = address;
        this.email = email;
        this.obs = obs;
        this.status = status;
        this.celular = celular;
        this.tipo = tipo;
    }

    public Pessoa(int id, String nome, String fone, String celular, String address, String email, String obs, String status, char tipo) {
        super(id);
        this.nome = nome;
        this.fone = fone;
        this.address = address;
        this.email = email;
        this.obs = obs;
        this.status = status;
        this.celular = celular;
        this.tipo = tipo;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public char getTipo() {
        return tipo;
    }

    public void setTipo(char tipo) {
        this.tipo = tipo;
    }
    
    

    public String getCelular() {
        return celular;
    }
    

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getFone() {
        return fone;
    }

    public void setFone(String fone) {
        this.fone = fone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    @Override
    public String toString() {
        return "Pessoa{" + "nome=" + nome + ", fone=" + fone + ", address=" + address + ", email=" + email + '}';
    }
}
