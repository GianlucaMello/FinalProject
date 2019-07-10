package Model.DAOSer;

import Model.bo.Veiculo;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VeiculoDAOSer implements InterfaceDAO<Veiculo> {

    private final File file = new File(".\\src\\Serialização\\SerializacaoFinalVeiculo.txt");

    @Override
    public void Create(Veiculo veiculo) {
        Serialize<Veiculo> serialize = new Serialize();
        List<Veiculo> listVeiculo;
        listVeiculo = Retrieve();
        try {
            listVeiculo.add(veiculo);
            serialize.Serialize(file, (ArrayList) listVeiculo);
        } catch (IOException ex) {
            System.err.print("Erro: " + ex.getMessage());
        }
    }

    @Override
    public void Delete(int id) {
        Serialize<Veiculo> serialize = new Serialize();
        List<Veiculo> listVeiculo;
        listVeiculo = Retrieve();
        for (int i = 0; i < listVeiculo.size(); i++) {
            if (listVeiculo.get(i).getId() == id) {
                listVeiculo.remove(i);
                break;
            }
        }
        for (int i = 0; i < listVeiculo.size(); i++) {
            listVeiculo.get(i).setId(i + 1);
        }
        try {
            serialize.Serialize(file, (ArrayList) listVeiculo);
        } catch (IOException ex) {
            System.err.println("Erro: " + ex.getMessage());
        }
    }

    @Override
    public List<Veiculo> Retrieve() {
        Serialize serialize = new Serialize();
        List<Veiculo> listVeiculo = new ArrayList<>();
        try {
            listVeiculo = serialize.DeSerialize(file);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PessoaFisicaDAOSer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listVeiculo;
    }

    @Override
    public Veiculo Retrieve(int id) {
        List<Veiculo> listVeiculo;
        listVeiculo = Retrieve();
        if (listVeiculo.get(id - 1).getId() == id) {
            return listVeiculo.get(id - 1);
        } else {
            return null;
        }
    }

    @Override
    public void Update(int id, Veiculo veiculo) {
        List<Veiculo> listVeiculo = Retrieve();
        Serialize<Veiculo> serialize = new Serialize();
        for (int i = 0; i < listVeiculo.size(); i++) {
            if (listVeiculo.get(i).getId() == id) {
                listVeiculo.set(id - 1, veiculo);
                break;
            }
        }
        try {
            serialize.Serialize(file, (ArrayList) listVeiculo);
        } catch (IOException ex) {
            System.err.println("Erro: " + ex.getMessage());
        }
    }
}
