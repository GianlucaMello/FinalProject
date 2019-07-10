/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.bo;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author gianl
 */
public class ControleEstacionamento implements Serializable{
    private Date data;
    private Veiculo veiculo;

    public ControleEstacionamento() {
    }

    public ControleEstacionamento(Date data, Veiculo veiculo){
        this.data = data;
        this.veiculo = veiculo;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }
    
    
    
    
    
}
