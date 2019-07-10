package Model.DAOSer;

import Model.bo.Marca;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MarcaDAOSer implements InterfaceDAO<Marca> {

    private final File file = new File(".\\src\\Serialização\\SerializacaoFinalMarca.txt");

    @Override
    public void Create(Marca object) {
        Serialize<Marca> serialize = new Serialize();
        List<Marca> listMarcas;
        listMarcas = Retrieve();
        listMarcas.add(object);
        try {
            serialize.Serialize(file, (ArrayList) listMarcas);
        } catch (IOException ex) {
            System.err.println("Erro: " + ex.getMessage());
        }
    }

    @Override
    public void Delete(int id) {
        Serialize<Marca> serialize = new Serialize();
        List<Marca> listMarcas;
        listMarcas = Retrieve();
        for (int i = 0; i < listMarcas.size(); i++) {
            if (listMarcas.get(i).getId() == id) {
                listMarcas.remove(i);
                break;
            }
        }
        for (int i = 0; i < listMarcas.size(); i++) {
            listMarcas.get(i).setId(i + 1);
        }
        try {
            serialize.Serialize(file, (ArrayList) listMarcas);
        } catch (IOException ex) {
            System.err.println("Erro: " + ex.getMessage());
        }

    }

    @Override
    public List<Marca> Retrieve() {
        Serialize<Marca> serialize = new Serialize();
        List<Marca> listMarcas = new ArrayList<>();
        try {
            listMarcas = serialize.DeSerialize(file);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MarcaDAOSer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listMarcas;
    }

    @Override
    public Marca Retrieve(int id) {
        List<Marca> listMarcas;
        listMarcas = Retrieve();
        if (listMarcas.get(id - 1).getId() == id) {
            return listMarcas.get(id - 1);
        } else {
            return null;
        }
    }

    @Override
    public void Update(int id, Marca object) {
        Serialize<Marca> serialize = new Serialize();
        List<Marca> listMarcas;
        listMarcas = Retrieve();
        for (int i = 0; i < listMarcas.size(); i++) {
            if (listMarcas.get(i).getId() == id) {
                listMarcas.set(id - 1, object);
                break;
            }
        }
        try {
            serialize.Serialize(file, (ArrayList) listMarcas);
        } catch (IOException ex) {
            System.err.println("Erro: " + ex.getMessage());
        }
    }
}