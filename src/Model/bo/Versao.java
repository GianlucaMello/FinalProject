package Model.bo;

import java.io.Serializable;

public class Versao extends Id implements Serializable {

    private String versao, categoria, tipo;
    private float motor;
    private Modelo modelo;

    public Versao() {
    }

    public Versao(int id, String versao, String categoria, String tipo, float motor, Modelo modelo) {
        super(id);
        this.versao = versao;
        this.categoria = categoria;
        this.tipo = tipo;
        this.motor = motor;
        this.modelo = modelo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getVersao() {
        return versao;
    }

    public void setVersao(String versao) {
        this.versao = versao;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public float getMotor() {
        return motor;
    }

    public void setMotor(float motor) {
        this.motor = motor;
    }

    public Modelo getModelo() {
        return modelo;
    }

    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
    }

    @Override
    public String toString() {
        return "Versao{" + "versao=" + versao + ", categoria=" + categoria + ", motor=" + motor + ", modelo=" + modelo + '}';
    }
}
