package Controller;

import Model.bo.PessoaFisica;
import Model.bo.PessoaJuridica;
import Service.ServicePessoaFisica;
import Service.ServicePessoaJuridica;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import view.TelaAtualizarPessoa;

public class ControllerAtualizarPessoa implements ActionListener {

    private final TelaAtualizarPessoa telaAtualizarUsuario;

    ControllerAtualizarPessoa(TelaAtualizarPessoa telaAtualizarUsuario) {
        this.telaAtualizarUsuario = telaAtualizarUsuario;
        this.telaAtualizarUsuario.getjButtonClose().addActionListener(this);
        this.telaAtualizarUsuario.getjButtonSave().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.telaAtualizarUsuario.getjButtonClose()) {
            this.telaAtualizarUsuario.dispose();
        } else if (e.getSource() == this.telaAtualizarUsuario.getjButtonSave()) {
            int idUpdate = Integer.parseInt(this.telaAtualizarUsuario.getjTextFieldId().getText());
            if (this.telaAtualizarUsuario.getjTextFieldTipo().getText().equals("F")) {
                if (!this.telaAtualizarUsuario.getjTextFieldNome().getText().isEmpty()
                        && !this.telaAtualizarUsuario.getjTextAreaObs().getText().isEmpty()
                        && !this.telaAtualizarUsuario.getjTextFieldEmail().getText().isEmpty()
                        && !this.telaAtualizarUsuario.getjTextFieldEndereço().getText().isEmpty()
                        && !this.telaAtualizarUsuario.getjTextFieldRg().getText().isEmpty()
                        && !"   .   .   -  ".equalsIgnoreCase(this.telaAtualizarUsuario.getjFormattedTextFieldCPFCNPJ().getText())
                        && !"(  )     -    ".equalsIgnoreCase(this.telaAtualizarUsuario.getjFormattedTextFieldCelular().getText())
                        && !"(  )    -    ".equalsIgnoreCase(this.telaAtualizarUsuario.getjFormattedTextFieldFone().getText())) {
                    String cpf = this.telaAtualizarUsuario.getjFormattedTextFieldCPFCNPJ().getText(),
                            rg = this.telaAtualizarUsuario.getjTextFieldRg().getText();
                    if (RetornaCPF(idUpdate, cpf, rg)) {
                        PessoaFisica pessoaFisicaUpdate = new PessoaFisica();
                        pessoaFisicaUpdate.setAddress(this.telaAtualizarUsuario.getjTextFieldEndereço().getText());
                        pessoaFisicaUpdate.setCelular(this.telaAtualizarUsuario.getjFormattedTextFieldCelular().getText());
                        pessoaFisicaUpdate.setCpf(cpf);
                        pessoaFisicaUpdate.setEmail(this.telaAtualizarUsuario.getjTextFieldEmail().getText());
                        pessoaFisicaUpdate.setFone(this.telaAtualizarUsuario.getjFormattedTextFieldFone().getText());
                        pessoaFisicaUpdate.setId(idUpdate);
                        pessoaFisicaUpdate.setNome(this.telaAtualizarUsuario.getjTextFieldNome().getText());
                        pessoaFisicaUpdate.setObs(this.telaAtualizarUsuario.getjTextAreaObs().getText());
                        pessoaFisicaUpdate.setRg(rg);
                        pessoaFisicaUpdate.setStatus(this.telaAtualizarUsuario.getjComboBoxStatus().getSelectedItem().toString());
                        pessoaFisicaUpdate.setTipo(this.telaAtualizarUsuario.getjTextFieldTipo().getText().charAt(0));
                        ServicePessoaFisica.Change(idUpdate, pessoaFisicaUpdate);
                        JOptionPane.showMessageDialog(null, "Pessoa Física atualizada!", "Ok!", JOptionPane.INFORMATION_MESSAGE);
                        this.telaAtualizarUsuario.dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "CPF e/ou RG já cadastrados!", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "É necessário preencher todos os campos!", "Aviso!", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                if (!"  .   .   /    -  ".equalsIgnoreCase(this.telaAtualizarUsuario.getjFormattedTextFieldCPFCNPJ().getText())
                        && !"(  )     -    ".equalsIgnoreCase(this.telaAtualizarUsuario.getjFormattedTextFieldCelular().getText())
                        && !"(  )    -    ".equalsIgnoreCase(this.telaAtualizarUsuario.getjFormattedTextFieldFone().getText())
                        && !this.telaAtualizarUsuario.getjTextAreaObs().getText().isEmpty()
                        && !this.telaAtualizarUsuario.getjTextFieldEmail().getText().isEmpty()
                        && !this.telaAtualizarUsuario.getjTextFieldEndereço().getText().isEmpty()
                        && !this.telaAtualizarUsuario.getjTextFieldNome().getText().isEmpty()) {
                    String cnpj = this.telaAtualizarUsuario.getjFormattedTextFieldCPFCNPJ().getText();
                    if (RetornaCNPJ(idUpdate, cnpj)) {
                        PessoaJuridica pessoaJuridicaUpdate = new PessoaJuridica();
                        pessoaJuridicaUpdate.setAddress(this.telaAtualizarUsuario.getjTextFieldEndereço().getText());
                        pessoaJuridicaUpdate.setCelular(this.telaAtualizarUsuario.getjFormattedTextFieldCelular().getText());
                        pessoaJuridicaUpdate.setCnpj(cnpj);
                        pessoaJuridicaUpdate.setEmail(this.telaAtualizarUsuario.getjTextFieldEmail().getText());
                        pessoaJuridicaUpdate.setFone(this.telaAtualizarUsuario.getjFormattedTextFieldFone().getText());
                        pessoaJuridicaUpdate.setId(idUpdate);
                        pessoaJuridicaUpdate.setNome(this.telaAtualizarUsuario.getjTextFieldNome().getText());
                        pessoaJuridicaUpdate.setObs(this.telaAtualizarUsuario.getjTextAreaObs().getText());
                        pessoaJuridicaUpdate.setStatus(this.telaAtualizarUsuario.getjComboBoxStatus().getSelectedItem().toString());
                        pessoaJuridicaUpdate.setTipo(this.telaAtualizarUsuario.getjTextFieldTipo().getText().charAt(0));
                        ServicePessoaJuridica.Change(idUpdate, pessoaJuridicaUpdate);
                        JOptionPane.showMessageDialog(null, "Pessoa Jurídica atualizada!", "Ok!", JOptionPane.INFORMATION_MESSAGE);
                        this.telaAtualizarUsuario.dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "CNPJ já cadastrado!", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "É necessário preencher todos os campos!", "Aviso!", JOptionPane.WARNING_MESSAGE);
                }
            }
        }
    }

    private boolean RetornaCPF(int idUpdate, String cpf, String rg) {
        return ServicePessoaFisica.Search().stream().noneMatch((atual) -> (atual.getId() != idUpdate && (atual.getCpf().equalsIgnoreCase(cpf) || atual.getRg().equalsIgnoreCase(rg))));
    }

    private boolean RetornaCNPJ(int idUpdate, String cnpj) {
        return ServicePessoaJuridica.Search().stream().noneMatch((atual) -> (atual.getId() != idUpdate && atual.getCnpj().equals(cnpj)));
    }
}
