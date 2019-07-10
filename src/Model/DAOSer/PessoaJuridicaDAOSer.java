package Model.DAOSer;

import Model.bo.PessoaJuridica;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PessoaJuridicaDAOSer implements InterfaceDAO<PessoaJuridica> {

    private final File file = new File(".\\src\\Serialização\\SerializacaoFinalPessoaJuridica.txt");

    @Override
    public void Create(PessoaJuridica usuario) {
        Serialize<PessoaJuridica> serialize = new Serialize();
        List<PessoaJuridica> listPessoaJuridica;
        listPessoaJuridica = Retrieve();
        try {
            listPessoaJuridica.add(usuario);
            serialize.Serialize(file, (ArrayList) listPessoaJuridica);
        } catch (IOException ex) {
            System.err.print("Erro: " + ex.getMessage());
        }
    }

    @Override
    public void Delete(int id) {
        Serialize<PessoaJuridica> serialize = new Serialize();
        List<PessoaJuridica> listPessoaJuridica;
        listPessoaJuridica = Retrieve();
        for (int i = 0; i < listPessoaJuridica.size(); i++) {
            if (listPessoaJuridica.get(i).getId() == id) {
                listPessoaJuridica.remove(i);
                break;
            }
        }
        for (int i = 0; i < listPessoaJuridica.size(); i++) {
            listPessoaJuridica.get(i).setId(i + 1);
        }
        try {
            serialize.Serialize(file, (ArrayList) listPessoaJuridica);
        } catch (IOException ex) {
            System.err.println("Erro: " + ex.getMessage());
        }
    }

    @Override
    public List<PessoaJuridica> Retrieve() {
        Serialize serialize = new Serialize();
        List<PessoaJuridica> listPessoaJuridica = new ArrayList<>();
        try {
            listPessoaJuridica = serialize.DeSerialize(file);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PessoaJuridicaDAOSer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listPessoaJuridica;
    }

    @Override
    public PessoaJuridica Retrieve(int id) {
        List<PessoaJuridica> listPessoaJuridica;
        listPessoaJuridica = Retrieve();
        if (listPessoaJuridica.get(id - 1).getId() == id) {
            return listPessoaJuridica.get(id - 1);
        } else {
            return null;
        }
    }

    @Override
    public void Update(int id, PessoaJuridica usuario) {
        List<PessoaJuridica> listPessoaJuridica = Retrieve();
        Serialize<PessoaJuridica> serialize = new Serialize();
        for (int i = 0; i < listPessoaJuridica.size(); i++) {
            if (listPessoaJuridica.get(i).getId() == id) {
                listPessoaJuridica.set(id - 1, usuario);
                break;
            }
        }
        try {
            serialize.Serialize(file, (ArrayList) listPessoaJuridica);
        } catch (IOException ex) {
            System.err.println("Erro: " + ex.getMessage());
        }
    }
}
