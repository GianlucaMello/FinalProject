package Model.DAOSer;

import Model.bo.Pessoa;
import Model.bo.PessoaFisica;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PessoaFisicaDAOSer implements InterfaceDAO<PessoaFisica> {

    private final File file = new File(".\\src\\Serialização\\SerializacaoFinalPessoaFisica.txt");

    @Override
    public void Create(PessoaFisica usuario) {
        Serialize<PessoaFisica> serialize = new Serialize();
        List<PessoaFisica> listPessoa;
        listPessoa = Retrieve();
        listPessoa.add(usuario);
        try {
            serialize.Serialize(file, (ArrayList) listPessoa);
        } catch (IOException ex) {
            System.err.print("Erro: " + ex.getMessage());
        }
    }

    @Override
    public void Delete(int id) {
        Serialize<Pessoa> serialize = new Serialize();
        List<PessoaFisica> listPessoa;
        listPessoa = Retrieve();
        for (int i = 0; i < listPessoa.size(); i++) {
            if (listPessoa.get(i).getId() == id) {
                listPessoa.remove(i);
                break;
            }
        }
        for (int i = 0; i < listPessoa.size(); i++) {
            listPessoa.get(i).setId(i + 1);
        }
        try {
            serialize.Serialize(file, (ArrayList) listPessoa);
        } catch (IOException ex) {
            System.err.println("Erro: " + ex.getMessage());
        }
    }

    @Override
    public List<PessoaFisica> Retrieve() {
        Serialize serialize = new Serialize();
        List<PessoaFisica> listUsuario = new ArrayList<>();
        try {
            listUsuario = serialize.DeSerialize(file);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PessoaFisicaDAOSer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listUsuario;
    }

    @Override
    public PessoaFisica Retrieve(int id) {
        List<PessoaFisica> listPessoaFisica;
        listPessoaFisica = Retrieve();
        if (listPessoaFisica.get(id - 1).getId() == id) {
            return listPessoaFisica.get(id - 1);
        } else {
            return null;
        }
    }

    @Override
    public void Update(int id, PessoaFisica usuario) {
        List<PessoaFisica> listUsuario = Retrieve();
        Serialize<PessoaFisica> serialize = new Serialize();
        for (int i = 0; i < listUsuario.size(); i++) {
            if (listUsuario.get(i).getId() == id) {
                listUsuario.set(id - 1, usuario);
                break;
            }
        }
        try {
            serialize.Serialize(file, (ArrayList) listUsuario);
        } catch (IOException ex) {
            System.err.println("Erro: " + ex.getMessage());
        }
    }
}