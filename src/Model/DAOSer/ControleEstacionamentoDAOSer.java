package Model.DAOSer;

import Model.bo.ControleEstacionamento;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControleEstacionamentoDAOSer{

    private final File file = new File(".\\src\\Serialização\\SerializacaoFinalControleDoEstacionamento.txt");
    
    public void Create(ControleEstacionamento veiculo) {
        Serialize<ControleEstacionamento> serialize = new Serialize();
        List<ControleEstacionamento> listVeiculo;
        listVeiculo = Retrieve();
        try {
            listVeiculo.add(veiculo);
            serialize.Serialize(file, (ArrayList) listVeiculo);
        } catch (IOException ex) {
            System.err.print("Erro: " + ex.getMessage());
        }
    }

    public void Delete(String placa) {
        Serialize<ControleEstacionamento> serialize = new Serialize();
        List<ControleEstacionamento> listVeiculo;
        listVeiculo = Retrieve();
        try {
            for (int i = 0; i < listVeiculo.size(); i++) {
                if (listVeiculo.get(i).getVeiculo().getPlaca().equalsIgnoreCase(placa)) {
                    listVeiculo.remove(i);
                    break;
                }
            }
            serialize.Serialize(file, (ArrayList) listVeiculo);
        } catch (IOException ex) {
            System.err.println("Erro: " + ex.getMessage());
        }
    }

    public List<ControleEstacionamento> Retrieve() {
        Serialize serialize = new Serialize();
        List<ControleEstacionamento> listVeiculo = new ArrayList<>();
        try {
            listVeiculo = serialize.DeSerialize(file);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PessoaFisicaDAOSer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listVeiculo;
    }

    public ControleEstacionamento Retrieve(String placa) {
        ArrayList<ControleEstacionamento> listaVeiculos;
        listaVeiculos = (ArrayList<ControleEstacionamento>) Retrieve();
        for (ControleEstacionamento atual : listaVeiculos) {
            if(atual.getVeiculo().getPlaca().equalsIgnoreCase(placa)){
                return atual;
            }
        }
        return null;
    }
}
