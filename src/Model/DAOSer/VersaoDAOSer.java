package Model.DAOSer;

import Model.bo.Modelo;
import Model.bo.Versao;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VersaoDAOSer implements InterfaceDAO<Versao> {

    private final File file = new File(".\\src\\Serialização\\SerializacaoFinalVersao.txt");

    @Override
    public void Create(Versao versao) {
        Serialize<Versao> serialize = new Serialize();
        List<Versao> listVersao;
        listVersao = Retrieve();
        try {
            listVersao.add(versao);
            serialize.Serialize(file, (ArrayList) listVersao);
        } catch (IOException ex) {
            System.err.print("Erro: " + ex.getMessage());
        }
    }

    @Override
    public void Delete(int id) {
        Serialize<Versao> serialize = new Serialize();
        List<Versao> listVersao;
        listVersao = Retrieve();
        for (int i = 0; i < listVersao.size(); i++) {
            if (listVersao.get(i).getId() == id) {
                listVersao.remove(i);
                break;
            }
        }
        for (int i = 0; i < listVersao.size(); i++) {
            listVersao.get(i).setId(i + 1);
        }
        try {
            serialize.Serialize(file, (ArrayList) listVersao);
        } catch (IOException ex) {
            System.err.println("Erro: " + ex.getMessage());
        }
    }

    @Override
    public List<Versao> Retrieve() {
        Serialize serialize = new Serialize();
        List<Versao> listVersao = new ArrayList();
        try {
            listVersao = serialize.DeSerialize(file);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(VersaoDAOSer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listVersao;
    }

    @Override
    public Versao Retrieve(int id) {
        List<Versao> listVersao;
        listVersao = Retrieve();
        if (listVersao.get(id - 1).getId() == id) {
            return listVersao.get(id - 1);
        } else {
            return null;
        }
    }

    @Override
    public void Update(int id, Versao versao) {
        List<Versao> listVersao = Retrieve();
        Serialize<Modelo> serialize = new Serialize();
        for (int i = 0; i < listVersao.size(); i++) {
            if (listVersao.get(i).getId() == id) {
                listVersao.set(id - 1, versao);
                break;
            }
        }
        try {
            serialize.Serialize(file, (ArrayList) listVersao);
        } catch (IOException ex) {
            System.err.println("Erro: " + ex.getMessage());
        }
    }
}
