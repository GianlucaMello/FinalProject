package Controller;

import Model.bo.Modelo;
import Model.bo.Versao;
import Service.ServiceModelo;
import Service.ServiceVersao;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import view.TelaCadastroVersao;
import view.TelaPesquisaVersao;

public class ControllerCadastroVersao implements ActionListener {

    private final TelaCadastroVersao telaCadastroVersao;

    public ControllerCadastroVersao(TelaCadastroVersao telaCadastroVersao) {
        this.telaCadastroVersao = telaCadastroVersao;
        this.telaCadastroVersao.getjButtonCancel().addActionListener(this);
        this.telaCadastroVersao.getjButtonClose().addActionListener(this);
        this.telaCadastroVersao.getjButtonNew().addActionListener(this);
        this.telaCadastroVersao.getjButtonSave().addActionListener(this);
        this.telaCadastroVersao.getjButtonSearch().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.telaCadastroVersao.getjButtonCancel()) {
            LimpaCampos();
            AtivaDesativaBotoes(true);
        } else if (e.getSource() == this.telaCadastroVersao.getjButtonClose()) {
            this.telaCadastroVersao.dispose();
        } else if (e.getSource() == this.telaCadastroVersao.getjButtonNew()) {
            AtivaDesativaBotoes(false);
            PreencherModelos();
        } else if (e.getSource() == this.telaCadastroVersao.getjButtonSave()) {
            if (!this.telaCadastroVersao.getjTextFieldCategoria().getText().isEmpty()
                    && !this.telaCadastroVersao.getjTextFieldMotor().getText().isEmpty()
                    && !this.telaCadastroVersao.getjTextFieldTipo().getText().isEmpty()
                    && !this.telaCadastroVersao.getjTextFieldVersao().getText().isEmpty()) {
                float motor = Float.parseFloat(this.telaCadastroVersao.getjTextFieldMotor().getText());
                String nome = this.telaCadastroVersao.getjTextFieldVersao().getText().toUpperCase(),
                        categoria = this.telaCadastroVersao.getjTextFieldCategoria().getText(),
                        tipo = this.telaCadastroVersao.getjTextFieldTipo().getText();
                categoria = categoria.substring(0, 1).toUpperCase().concat(categoria.substring(1, categoria.length()).toLowerCase());
                tipo = tipo.substring(0, 1).toUpperCase().concat(tipo.substring(1, tipo.length()).toLowerCase());
                if (!RetornaModelo(nome, categoria, motor, this.telaCadastroVersao.getjComboBoxModelo().getSelectedItem().toString())) {
                    Versao versao = new Versao();
                    int tamList;
                    tamList = ServiceVersao.Search().size();
                    if (tamList != 0) {
                        versao.setId(ServiceVersao.Search().get(tamList - 1).getId() + 1);
                    } else {
                        versao.setId(1);
                    }
                    versao.setVersao(nome);
                    versao.setMotor(motor);
                    versao.setCategoria(categoria);
                    versao.setTipo(tipo);
                    for (Modelo atual : ServiceModelo.Search()) {
                        if (this.telaCadastroVersao.getjComboBoxModelo().getSelectedItem().toString().equals(atual.getModel())) {
                            versao.setModelo(atual);
                            break;
                        }
                    }
                    if (versao.getModelo() != null) {
                        ServiceVersao.Include(versao);
                        AtivaDesativaBotoes(true);
                        LimpaCampos();
                    } else {
                        JOptionPane.showMessageDialog(null, "Modelo inválido", "Erro!",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "A Versão já está cadastrada", "Erro!",
                            JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "É necessário preencher todos os campos!", "Aviso!",
                        JOptionPane.WARNING_MESSAGE);
            }
        } else if (e.getSource() == this.telaCadastroVersao.getjButtonSearch()) {
            TelaPesquisaVersao telaPesquisaVersao = new TelaPesquisaVersao(null, true);
            ControllerPesquisaVersao controllerPesquisaVersao = new ControllerPesquisaVersao(telaPesquisaVersao);
            telaPesquisaVersao.setLocationRelativeTo(null);
            telaPesquisaVersao.setVisible(true);
        }
    }

    private void LimpaCampos() {
        this.telaCadastroVersao.getjTextFieldCategoria().setText("");
        this.telaCadastroVersao.getjTextFieldMotor().setText("");
        this.telaCadastroVersao.getjTextFieldVersao().setText("");
        this.telaCadastroVersao.getjTextFieldTipo().setText("");
        this.telaCadastroVersao.getjComboBoxModelo().setSelectedItem(null);
    }

    private void AtivaDesativaBotoes(boolean estadoDoBotao) {
        this.telaCadastroVersao.getjButtonCancel().setEnabled(!estadoDoBotao);
        this.telaCadastroVersao.getjButtonClose().setEnabled(estadoDoBotao);
        this.telaCadastroVersao.getjButtonNew().setEnabled(estadoDoBotao);
        this.telaCadastroVersao.getjButtonSave().setEnabled(!estadoDoBotao);
        this.telaCadastroVersao.getjButtonSearch().setEnabled(estadoDoBotao);
        ligaDesligaCampos(!estadoDoBotao);
    }

    private void PreencherModelos() {
        telaCadastroVersao.getjComboBoxModelo().removeAllItems();
        ArrayList<Modelo> listModelo = new ArrayList<>(ServiceModelo.Search());

        listModelo.forEach((modelo) -> {
            telaCadastroVersao.getjComboBoxModelo().addItem(modelo.getModel());
        }
        );
    }

    private void ligaDesligaCampos(boolean estadoDoCampo) {
        Component[] components = this.telaCadastroVersao.getjPanelData().getComponents();

        for (Component atualComponent : components) {
            if (atualComponent instanceof JTextField) {
                atualComponent.setEnabled(estadoDoCampo);
            }
            if (atualComponent instanceof JComboBox) {
                atualComponent.setEnabled(estadoDoCampo);
            }
        }
    }

    private boolean RetornaModelo(String nome, String categoria, float motor, String modelo) {
        for (Versao atual : ServiceVersao.Search()) {
            if (atual.getVersao().equalsIgnoreCase(nome) && atual.getCategoria().equalsIgnoreCase(categoria)
                    && atual.getMotor() == motor && atual.getModelo().getModel().equalsIgnoreCase(modelo)) {
                return true;
            }
        }
        return false;
    }
}
