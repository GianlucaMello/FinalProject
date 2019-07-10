package Controller;

import Model.bo.Modelo;
import Model.bo.Versao;
import Service.ServiceModelo;
import Service.ServiceVersao;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import view.TelaAtualizarVersao;

public class ControllerAtualizarVersao implements ActionListener {

    private final TelaAtualizarVersao telaAtualizarVersao;

    public ControllerAtualizarVersao(TelaAtualizarVersao telaAtualizarVersao) {
        this.telaAtualizarVersao = telaAtualizarVersao;
        this.telaAtualizarVersao.getjButtonClose().addActionListener(this);
        this.telaAtualizarVersao.getjButtonSave().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.telaAtualizarVersao.getjButtonClose()) {
            this.telaAtualizarVersao.dispose();
        } else if (e.getSource() == this.telaAtualizarVersao.getjButtonSave()) {
            if (!this.telaAtualizarVersao.getjTextFieldCategoria().getText().isEmpty()
                    && !this.telaAtualizarVersao.getjTextFieldMotor().getText().isEmpty()
                    && !this.telaAtualizarVersao.getjTextFieldTipo().getText().isEmpty()
                    && !this.telaAtualizarVersao.getjTextFieldVersao().getText().isEmpty()) {
                int idUpdate = Integer.parseInt("" + this.telaAtualizarVersao.getjTextFieldId().getText());
                float motor = Float.parseFloat(this.telaAtualizarVersao.getjTextFieldMotor().getText());
                String nome = this.telaAtualizarVersao.getjTextFieldVersao().getText().toUpperCase(),
                        categoria = this.telaAtualizarVersao.getjTextFieldCategoria().getText(),
                        tipo = this.telaAtualizarVersao.getjTextFieldTipo().getText();
                categoria = categoria.substring(0, 1).toUpperCase().concat(categoria.substring(1, categoria.length()).toLowerCase());
                tipo = tipo.substring(0, 1).toUpperCase().concat(tipo.substring(1, tipo.length()).toLowerCase());
                if (!RetornaModelo(idUpdate, nome, categoria, motor, this.telaAtualizarVersao.getjComboBoxModelo().getSelectedItem().toString())) {
                    Versao versaoUpdate;
                    Modelo modelo = null;
                    for (Modelo modeloAtual : ServiceModelo.Search()) {
                        if (modeloAtual.getModel().equalsIgnoreCase(this.telaAtualizarVersao.getjComboBoxModelo().getSelectedItem().toString())) {
                            modelo = modeloAtual;
                            break;
                        }
                    }
                    if(modelo != null){
                        versaoUpdate = new Versao(idUpdate, nome, categoria, tipo, motor, modelo);
                        ServiceVersao.Change(idUpdate, versaoUpdate);
                        JOptionPane.showMessageDialog(null, "Versão Atualizada!");
                        this.telaAtualizarVersao.dispose();
                    } else{
                        JOptionPane.showMessageDialog(null, "Modelo Inválido!", "Erro!", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "A Versão já está cadastrada!", "Erro!", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "É necessário preencher todos os campos!", "Aviso!", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    private boolean RetornaModelo(int idUpdate, String nome, String categoria, float motor, String modelo) {
        return ServiceVersao.Search().stream().anyMatch((atual) -> (atual.getId() != idUpdate && atual.getVersao().equalsIgnoreCase(nome)
                && atual.getCategoria().equalsIgnoreCase(categoria) && atual.getMotor() == motor
                && atual.getModelo().getModel().equalsIgnoreCase(modelo)));
    }
}
