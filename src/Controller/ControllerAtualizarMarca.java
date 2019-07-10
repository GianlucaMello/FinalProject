package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import view.TelaAtualizarMarca;
import Model.bo.Marca;
import Service.ServiceMarca;
import javax.swing.JOptionPane;

public class ControllerAtualizarMarca implements ActionListener {

    private final TelaAtualizarMarca telaAtualizarMarca;

    public ControllerAtualizarMarca(TelaAtualizarMarca telaAtualizarMarca) {
        this.telaAtualizarMarca = telaAtualizarMarca;
        this.telaAtualizarMarca.getjButtonClose().addActionListener(this);
        this.telaAtualizarMarca.getjButtonSave().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.telaAtualizarMarca.getjButtonClose()) {
            this.telaAtualizarMarca.dispose();
        } else if (e.getSource() == this.telaAtualizarMarca.getjButtonSave()) {
            if (!this.telaAtualizarMarca.getjTextFieldName().getText().isEmpty()) {
                Marca marcaUpdate;
                int idMarca = Integer.parseInt(this.telaAtualizarMarca.getjTextFieldId().getText());
                String nomeMarca = this.telaAtualizarMarca.getjTextFieldName().getText();
                if (!VerificaMarca(idMarca, nomeMarca)) {
                    nomeMarca = nomeMarca.substring(0, 1).toUpperCase().concat(nomeMarca.substring(1, nomeMarca.length()).toLowerCase());
                    marcaUpdate = new Marca(idMarca, nomeMarca);
                    ServiceMarca.Change(idMarca, marcaUpdate);
                    JOptionPane.showMessageDialog(null, "Marca Atualizada", "Ok",
                            JOptionPane.INFORMATION_MESSAGE);
                    this.telaAtualizarMarca.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "A marca já está cadastrada", "Aviso!",
                            JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "É necessário preencher todos os campos!", "Aviso!",
                        JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    private boolean VerificaMarca(int idMarca, String marca) {
        return ServiceMarca.Search().stream().anyMatch((atual) -> (idMarca != atual.getId() && atual.getName().equalsIgnoreCase(marca)));
    }
}
