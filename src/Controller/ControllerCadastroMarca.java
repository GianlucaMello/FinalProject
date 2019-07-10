package Controller;

import Model.bo.Marca;
import Service.ServiceMarca;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import view.TelaCadastroMarca;
import view.TelaPesquisaMarca;
public class ControllerCadastroMarca implements ActionListener {

    private final view.TelaCadastroMarca telaCadastroMarca;

    public ControllerCadastroMarca(TelaCadastroMarca telaCadastroMarca) {
        this.telaCadastroMarca = telaCadastroMarca;
        this.telaCadastroMarca.getjButtonCancel().addActionListener(this);
        this.telaCadastroMarca.getjButtonClose().addActionListener(this);
        this.telaCadastroMarca.getjButtonNew().addActionListener(this);
        this.telaCadastroMarca.getjButtonSave().addActionListener(this);
        this.telaCadastroMarca.getjButtonSearch().addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.telaCadastroMarca.getjButtonCancel()) {
            AtivaDesativaBotoes(true);
            LimpaCampos();
        } else if (e.getSource() == this.telaCadastroMarca.getjButtonClose()) {
            this.telaCadastroMarca.dispose();
        } else if (e.getSource() == this.telaCadastroMarca.getjButtonNew()) {
            AtivaDesativaBotoes(false);
            LimpaCampos();
        } else if (e.getSource() == this.telaCadastroMarca.getjButtonSave()) {
            if (!this.telaCadastroMarca.getjTextFieldName().getText().isEmpty()) {
                if (!RetornaMarca(this.telaCadastroMarca.getjTextFieldName().getText())) {
                    Marca marca = new Marca();
                    int tamList;
                    tamList = ServiceMarca.Search().size();
                    String nome = this.telaCadastroMarca.getjTextFieldName().getText();
                    if (tamList != 0) {
                        marca.setId(ServiceMarca.Search().get(tamList - 1).getId() + 1);
                    } else {
                        marca.setId(1);
                    }
                    nome = nome.substring(0, 1).toUpperCase().concat(nome.substring(1, nome.length()).toLowerCase());
                    marca.setName(nome);
                    ServiceMarca.Include(marca);
                    JOptionPane.showMessageDialog(null, "Marca Cadastrada", "Ok",
                            JOptionPane.INFORMATION_MESSAGE);
                    LimpaCampos();
                    AtivaDesativaBotoes(true);
                } else {
                    JOptionPane.showMessageDialog(null, "A Marca já está cadastrada", "Aviso",
                            JOptionPane.ERROR_MESSAGE);
                }
            } else{
                JOptionPane.showMessageDialog(null, "É necessário preencher todos os campos!", "Aviso!",
                            JOptionPane.WARNING_MESSAGE);
            }
        } else if (e.getSource() == this.telaCadastroMarca.getjButtonSearch()) {
            TelaPesquisaMarca telaPesquisaMarca = new TelaPesquisaMarca(null, true);
            ControllerPesquisaMarca controllerSearchMarca = new ControllerPesquisaMarca(telaPesquisaMarca);
            telaPesquisaMarca.setVisible(true);
            LimpaCampos();
        }
    }

    private void AtivaDesativaBotoes(boolean estadoDoBotao) {
        this.telaCadastroMarca.getjButtonCancel().setEnabled(!estadoDoBotao);
        this.telaCadastroMarca.getjButtonClose().setEnabled(estadoDoBotao);
        this.telaCadastroMarca.getjButtonNew().setEnabled(estadoDoBotao);
        this.telaCadastroMarca.getjButtonSave().setEnabled(!estadoDoBotao);
        this.telaCadastroMarca.getjButtonSearch().setEnabled(estadoDoBotao);
        ligaDesligaCampos(!estadoDoBotao);
    }

    private void LimpaCampos() {
        this.telaCadastroMarca.getjTextFieldName().setText("");
    }

    private void ligaDesligaCampos(boolean estadoDoCampo) {
        Component[] components = this.telaCadastroMarca.getjPanelCollectionField().getComponents();
        for (Component atualComponent : components) {
            if (atualComponent instanceof JTextField) {
                atualComponent.setEnabled(estadoDoCampo);
            }
        }
    }

    private boolean RetornaMarca(String marca) {
        return ServiceMarca.Search().stream().anyMatch((atual) -> (atual.getName().equalsIgnoreCase(marca)));
    }
}