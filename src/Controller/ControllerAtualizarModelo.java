package Controller;

import Model.bo.Marca;
import Model.bo.Modelo;
import Service.ServiceMarca;
import Service.ServiceModelo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import view.TelaAtualizarModelo;

public class ControllerAtualizarModelo implements ActionListener {

    private final TelaAtualizarModelo telaAtualizarModelo;

    public ControllerAtualizarModelo(TelaAtualizarModelo telaAtualizarModelo) {
        this.telaAtualizarModelo = telaAtualizarModelo;
        this.telaAtualizarModelo.getjButtonClose().addActionListener(this);
        this.telaAtualizarModelo.getjButtonSave().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.telaAtualizarModelo.getjButtonClose()) {
            this.telaAtualizarModelo.dispose();
        } else if (e.getSource() == this.telaAtualizarModelo.getjButtonSave()) {
            if (!this.telaAtualizarModelo.getjTextFieldModel().getText().isEmpty()) {
                int idModelo = Integer.parseInt(this.telaAtualizarModelo.getjTextFieldId().getText());
                String nomeModelo = this.telaAtualizarModelo.getjTextFieldModel().getText();
                Marca marcaUpdate = null;
                Modelo modeloUpdate;
                nomeModelo = nomeModelo.substring(0, 1).toUpperCase().concat(nomeModelo.substring(1, nomeModelo.length()).toLowerCase());
                if (!RetornaModelo(idModelo, nomeModelo)) {
                    for (Marca marcaAtual : ServiceMarca.Search()) {
                        if (marcaAtual.getName().equalsIgnoreCase(this.telaAtualizarModelo.getjComboBoxMarca().getSelectedItem().toString())) {
                            marcaUpdate = marcaAtual;
                            break;
                        }
                    }
                    if (marcaUpdate != null) {
                        modeloUpdate = new Modelo(idModelo, nomeModelo, marcaUpdate);
                        ServiceModelo.Change(idModelo, modeloUpdate);
                        JOptionPane.showMessageDialog(null, "Modelo Atualizado", "Ok",
                                JOptionPane.INFORMATION_MESSAGE);
                        this.telaAtualizarModelo.dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Marca inválida", "Erro!",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "O modelo já está cadastrado", "Aviso!",
                            JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "É necessário preencher todos os campos!", "Aviso!",
                        JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    private boolean RetornaModelo(int idModelo, String modelo) {
        for (Modelo atual : ServiceModelo.Search()) {
            if (idModelo != atual.getId() && modelo.equalsIgnoreCase(atual.getModel())) {
                return true;
            }
        }
        return false;
    }
}
