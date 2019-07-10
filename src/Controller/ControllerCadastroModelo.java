package Controller;

import Model.bo.Marca;
import Model.bo.Modelo;
import Service.ServiceMarca;
import Service.ServiceModelo;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import view.TelaCadastroModelo;
import view.TelaPesquisaModelo;

public class ControllerCadastroModelo implements ActionListener {

    private view.TelaCadastroModelo telaCadastroModelo;

    public ControllerCadastroModelo() {
    }

    public ControllerCadastroModelo(TelaCadastroModelo telaCadastroModelo) {
        this.telaCadastroModelo = telaCadastroModelo;
        this.telaCadastroModelo.getjButtonCancel().addActionListener(this);
        this.telaCadastroModelo.getjButtonClose().addActionListener(this);
        this.telaCadastroModelo.getjButtonNew().addActionListener(this);
        this.telaCadastroModelo.getjButtonSave().addActionListener(this);
        this.telaCadastroModelo.getjButtonSearch().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.telaCadastroModelo.getjButtonCancel()) {
            LimpaCampos();
            AtivaDesativaBotoes(true);
        } else if (e.getSource() == this.telaCadastroModelo.getjButtonClose()) {
            this.telaCadastroModelo.dispose();
        } else if (e.getSource() == this.telaCadastroModelo.getjButtonNew()) {
            LimpaCampos();
            AtivaDesativaBotoes(false);
            PreencherMarcas();
        } else if (e.getSource() == this.telaCadastroModelo.getjButtonSave()) {
            if (!this.telaCadastroModelo.getjTextFieldModel().getText().isEmpty()) {
                if (!RetornaModelo(this.telaCadastroModelo.getjTextFieldModel().getText())) {
                    Modelo modelo = new Modelo();
                    int tamList;
                    String nome = this.telaCadastroModelo.getjTextFieldModel().getText();
                    tamList = ServiceModelo.Search().size();
                    nome = nome.substring(0, 1).toUpperCase().concat(nome.substring(1, nome.length()).toLowerCase());
                    if (tamList != 0) {
                        modelo.setId(ServiceModelo.Search().get(tamList - 1).getId() + 1);
                    } else {
                        modelo.setId(1);
                    }
                    modelo.setModel(nome);
                    for (Marca marcaAtual : ServiceMarca.Search()) {
                        if (marcaAtual.getName().equalsIgnoreCase(this.telaCadastroModelo.getjComboBoxMarca().getSelectedItem().toString())) {
                            modelo.setMarca(marcaAtual);
                            break;
                        }
                    }
                    if (modelo.getMarca() != null) {
                        ServiceModelo.Include(modelo);
                        JOptionPane.showMessageDialog(null, "Modelo Cadastrado", "Ok!",
                                JOptionPane.INFORMATION_MESSAGE);
                        AtivaDesativaBotoes(true);
                        LimpaCampos();
                    } else{
                        JOptionPane.showMessageDialog(null, "Marca inválida", "Erro!",
                            JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "O Modelo já está cadastrado", "Aviso",
                            JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "É necessário preencher todos os campos!", "Aviso!",
                        JOptionPane.WARNING_MESSAGE);
            }
        } else if (e.getSource() == this.telaCadastroModelo.getjButtonSearch()) {
            TelaPesquisaModelo telaPesquisaModelo = new TelaPesquisaModelo(null, true);
            ControllerPesquisaModelo controllerPesquisaModelo = new ControllerPesquisaModelo(telaPesquisaModelo);
            telaPesquisaModelo.setLocationRelativeTo(null);
            telaPesquisaModelo.setVisible(true);
            LimpaCampos();
        }
    }

    private void PreencherMarcas() {
        telaCadastroModelo.getjComboBoxMarca().removeAllItems();
        ArrayList<Marca> listMarcas = (ArrayList<Marca>) ServiceMarca.Search();
        listMarcas.forEach((marca) -> {
            telaCadastroModelo.getjComboBoxMarca().addItem(marca.getName());
        });
    }

    private void LimpaCampos() {
        this.telaCadastroModelo.getjTextFieldModel().setText("");
        this.telaCadastroModelo.getjComboBoxMarca().setSelectedItem(null);
    }

    public void AtivaDesativaBotoes(boolean estadoDoBotao) {
        this.telaCadastroModelo.getjButtonCancel().setEnabled(!estadoDoBotao);
        this.telaCadastroModelo.getjButtonClose().setEnabled(estadoDoBotao);
        this.telaCadastroModelo.getjButtonNew().setEnabled(estadoDoBotao);
        this.telaCadastroModelo.getjButtonSave().setEnabled(!estadoDoBotao);
        this.telaCadastroModelo.getjButtonSearch().setEnabled(estadoDoBotao);
        ligaDesligaCampos(!estadoDoBotao);
    }

    public void ligaDesligaCampos(boolean estadoDoCampo) {
        Component[] components = this.telaCadastroModelo.getjPanelData().getComponents();
        for (Component atualComponent : components) {
            if (atualComponent instanceof JTextField) {
                atualComponent.setEnabled(estadoDoCampo);
            }
            if (atualComponent instanceof JComboBox) {
                atualComponent.setEnabled(estadoDoCampo);
            }
        }
    }

    private boolean RetornaModelo(String modelo) {
        return ServiceModelo.Search().stream().anyMatch((atual) -> (atual.getModel().equalsIgnoreCase(modelo)));
    }
}
