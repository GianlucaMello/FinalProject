/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.DAOSer;

import Model.bo.Modelo;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gianl
 */
public class ModeloDAOSer implements InterfaceDAO<Modelo> {

    private final File file = new File(".\\src\\Serialização\\SerializacaoFinalModelo.txt");

    //private final String file = ("C:\\Users\\gianl\\Documents\\NetBeansProjects\\FinalProject\\src\\SerializacaoModelo");
    @Override
    public void Create(Modelo modelo) {
        Serialize<Modelo> serialize = new Serialize();
        List<Modelo> listModelo;
        listModelo = Retrieve();
        listModelo.add(modelo);
        try {
            serialize.Serialize(file, (ArrayList) listModelo);
        } catch (IOException ex) {
            System.err.println("Erro: " + ex.getMessage());
        }
    }

    @Override
    public void Delete(int id) {

        Serialize<Modelo> serialize = new Serialize();
        List<Modelo> listModelo;
        listModelo = Retrieve();
        for (int i = 0; i < listModelo.size(); i++) {
            if (listModelo.get(i).getId() == id) {
                listModelo.remove(i);
                break;
            }
        }
        for (int i = 0; i < listModelo.size(); i++) {
            listModelo.get(i).setId(i + 1);
        }
        try {
            serialize.Serialize(file, (ArrayList) listModelo);
        } catch (IOException ex) {
            System.err.println("Erro: " + ex.getMessage());
        }
    }

    @Override
    public List<Modelo> Retrieve() {
        Serialize<Modelo> serialize = new Serialize();
        List<Modelo> listModelo = new ArrayList<>();
        try {
            listModelo = serialize.DeSerialize(file);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MarcaDAOSer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listModelo;
    }

    @Override
    public Modelo Retrieve(int id) {
        List<Modelo> listModelo;
        listModelo = Retrieve();
        if (listModelo.get(id - 1).getId() == id) {
            return listModelo.get(id - 1);
        } else {
            return null;
        }
    }

    @Override
    public void Update(int id, Modelo object) {
        Serialize<Modelo> serialize = new Serialize();
        List<Modelo> listModelo;
        listModelo = Retrieve();
        for (int i = 0; i < listModelo.size(); i++) {
            if (listModelo.get(i).getId() == id) {
                listModelo.set(id - 1, object);
                break;
            }
        }
        try {
            serialize.Serialize(file, (ArrayList) listModelo);
        } catch (IOException ex) {
            System.err.println("Erro: " + ex.getMessage());
        }
    }
}