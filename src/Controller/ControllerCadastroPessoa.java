package Controller;

import Model.bo.PessoaFisica;
import Model.bo.PessoaJuridica;
import Service.ServicePessoaFisica;
import Service.ServicePessoaJuridica;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import view.TelaCadastroPessoa;
import view.TelaPesquisaPessoa;

public class ControllerCadastroPessoa implements ActionListener {

    private final TelaCadastroPessoa telaCadastroUsuario;

    public ControllerCadastroPessoa(TelaCadastroPessoa telaCadastroUsuario) {
        this.telaCadastroUsuario = telaCadastroUsuario;
        this.telaCadastroUsuario.getjButtonCancel().addActionListener(this);
        this.telaCadastroUsuario.getjButtonClose().addActionListener(this);
        this.telaCadastroUsuario.getjButtonNew().addActionListener(this);
        this.telaCadastroUsuario.getjButtonSave().addActionListener(this);
        this.telaCadastroUsuario.getjButtonSearch().addActionListener(this);
        this.telaCadastroUsuario.getjComboBoxOpcao().addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == this.telaCadastroUsuario.getjComboBoxOpcao()) {
            try {
                MaskFormatter mascaraCPF = new MaskFormatter("###.###.###-##");
                MaskFormatter mascaraCNPJ = new MaskFormatter("##.###.###/####-##");
                this.telaCadastroUsuario.getjFormattedTextFieldCPFCNPJ().setValue(null);
                if (this.telaCadastroUsuario.getjComboBoxOpcao().getSelectedIndex() == 0) {
                    this.telaCadastroUsuario.getjLabelCPFCNPJ().setText("CPF:");
                    this.telaCadastroUsuario.getjLabelRg().setVisible(true);
                    this.telaCadastroUsuario.getjTextFieldRg().setVisible(true);
                    this.telaCadastroUsuario.getjFormattedTextFieldCPFCNPJ().setFormatterFactory(
                            new DefaultFormatterFactory(mascaraCPF));
                } else {
                    this.telaCadastroUsuario.getjLabelCPFCNPJ().setText("CNPJ:");
                    this.telaCadastroUsuario.getjLabelRg().setVisible(false);
                    this.telaCadastroUsuario.getjTextFieldRg().setVisible(false);
                    this.telaCadastroUsuario.getjFormattedTextFieldCPFCNPJ().setFormatterFactory(
                            new DefaultFormatterFactory(mascaraCNPJ));
                }
            } catch (ParseException ex) {
                Logger.getLogger(ControllerCadastroPessoa.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (e.getSource() == this.telaCadastroUsuario.getjButtonCancel()) {
            LimpaCampos();
            AtivaDesativaBotoes(true);
        } else if (e.getSource() == this.telaCadastroUsuario.getjButtonClose()) {
            this.telaCadastroUsuario.dispose();
        } else if (e.getSource() == this.telaCadastroUsuario.getjButtonNew()) {
            LimpaCampos();
            AtivaDesativaBotoes(false);
        } else if (e.getSource() == this.telaCadastroUsuario.getjButtonSave()) {
            char opcao = this.telaCadastroUsuario.getjComboBoxOpcao().getSelectedItem().toString().charAt(0);
            if (opcao == 'F') {
                if (!this.telaCadastroUsuario.getjTextFieldNome().getText().isEmpty()
                        && !this.telaCadastroUsuario.getjTextAreaObs().getText().isEmpty()
                        && !this.telaCadastroUsuario.getjTextFieldEmail().getText().isEmpty()
                        && !this.telaCadastroUsuario.getjTextFieldEndereço().getText().isEmpty()
                        && !this.telaCadastroUsuario.getjTextFieldRg().getText().isEmpty()
                        && !"   .   .   -  ".equalsIgnoreCase(this.telaCadastroUsuario.getjFormattedTextFieldCPFCNPJ().getText())
                        && !"(  )     -    ".equalsIgnoreCase(this.telaCadastroUsuario.getjFormattedTextFieldCelular().getText())
                        && !"(  )    -    ".equalsIgnoreCase(this.telaCadastroUsuario.getjFormattedTextFieldFone().getText())) {
                    String rg = this.telaCadastroUsuario.getjTextFieldRg().getText(),
                            cpf = this.telaCadastroUsuario.getjFormattedTextFieldCPFCNPJ().getText();
                    if (RetornaCPF(cpf, rg)) {
                        PessoaFisica usuario = new PessoaFisica();
                        int tamList;
                        tamList = ServicePessoaFisica.Search().size();
                        if (tamList != 0) {
                            usuario.setId(ServicePessoaFisica.Search().get(tamList - 1).getId() + 1);
                        } else {
                            usuario.setId(1);
                        }
                        usuario.setRg(rg);
                        usuario.setNome(this.telaCadastroUsuario.getjTextFieldNome().getText());
                        usuario.setFone(this.telaCadastroUsuario.getjFormattedTextFieldFone().getText());
                        usuario.setCelular(this.telaCadastroUsuario.getjFormattedTextFieldCelular().getText());
                        usuario.setCpf(cpf);
                        usuario.setAddress(this.telaCadastroUsuario.getjTextFieldEndereço().getText());
                        usuario.setEmail(this.telaCadastroUsuario.getjTextFieldEmail().getText());
                        usuario.setObs(this.telaCadastroUsuario.getjTextAreaObs().getText());
                        usuario.setStatus(this.telaCadastroUsuario.getjComboBoxStatus().getSelectedItem().toString());
                        usuario.setTipo(opcao);
                        ServicePessoaFisica.Include(usuario);
                        JOptionPane.showMessageDialog(null, "Pessoa Física cadastrada!", "Ok!", JOptionPane.INFORMATION_MESSAGE);
                        AtivaDesativaBotoes(true);
                        LimpaCampos();
                    } else {
                        JOptionPane.showMessageDialog(null, "CPF e/ou RG já cadastrados!", "Erro!", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "É necessário preencher todos os campos!", "Aviso!", JOptionPane.WARNING_MESSAGE);
                }
            } else if (opcao == 'J') {
                if (!"  .   .   /    -  ".equalsIgnoreCase(this.telaCadastroUsuario.getjFormattedTextFieldCPFCNPJ().getText())
                        && !"(  )     -    ".equalsIgnoreCase(this.telaCadastroUsuario.getjFormattedTextFieldCelular().getText())
                        && !"(  )    -    ".equalsIgnoreCase(this.telaCadastroUsuario.getjFormattedTextFieldFone().getText())
                        && !this.telaCadastroUsuario.getjTextAreaObs().getText().isEmpty()
                        && !this.telaCadastroUsuario.getjTextFieldEmail().getText().isEmpty()
                        && !this.telaCadastroUsuario.getjTextFieldEndereço().getText().isEmpty()
                        && !this.telaCadastroUsuario.getjTextFieldNome().getText().isEmpty()) {
                    String cnpj = this.telaCadastroUsuario.getjFormattedTextFieldCPFCNPJ().getText();

                    if (RetornaCNPJ(cnpj)) {
                        PessoaJuridica usuario = new PessoaJuridica();
                        int tamList;
                        tamList = ServicePessoaJuridica.Search().size();
                        if (tamList != 0) {
                            usuario.setId(ServicePessoaJuridica.Search().get(tamList - 1).getId() + 1);
                        } else {
                            usuario.setId(1);
                        }
                        usuario.setNome(this.telaCadastroUsuario.getjTextFieldNome().getText());
                        usuario.setFone(this.telaCadastroUsuario.getjFormattedTextFieldFone().getText());
                        usuario.setCelular(this.telaCadastroUsuario.getjFormattedTextFieldCelular().getText());
                        usuario.setCnpj(cnpj);
                        usuario.setAddress(this.telaCadastroUsuario.getjTextFieldEndereço().getText());
                        usuario.setEmail(this.telaCadastroUsuario.getjTextFieldEmail().getText());
                        usuario.setObs(this.telaCadastroUsuario.getjTextAreaObs().getText());
                        usuario.setStatus(this.telaCadastroUsuario.getjComboBoxStatus().getSelectedItem().toString());
                        usuario.setTipo(opcao);
                        ServicePessoaJuridica.Include(usuario);
                        JOptionPane.showMessageDialog(null, "Pessoa Jurídica cadastrada!", "Ok!", JOptionPane.INFORMATION_MESSAGE);
                        AtivaDesativaBotoes(true);
                        LimpaCampos();
                    } else {
                        JOptionPane.showMessageDialog(null, "CNPJ já cadastrado!", "Erro!", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "É necessário preencher todos os campos!", "Aviso!", JOptionPane.WARNING_MESSAGE);
                }
            }

        } else if (e.getSource() == this.telaCadastroUsuario.getjButtonSearch()) {
            TelaPesquisaPessoa telaPesquisaUsuario = new TelaPesquisaPessoa(null, true);
            ControllerPesquisaPessoa controllerPesquisaUsuario = new ControllerPesquisaPessoa(telaPesquisaUsuario);
            telaPesquisaUsuario.setLocationRelativeTo(null);
            telaPesquisaUsuario.setVisible(true);
        }

    }

    private void LimpaCampos() {
        this.telaCadastroUsuario.getjTextAreaObs().setText("");
        this.telaCadastroUsuario.getjTextFieldEmail().setText("");
        this.telaCadastroUsuario.getjTextFieldEndereço().setText("Rua, Numero, Cidade-Estado");
        this.telaCadastroUsuario.getjTextFieldNome().setText("");
        this.telaCadastroUsuario.getjTextFieldRg().setText("");
        this.telaCadastroUsuario.getjFormattedTextFieldCPFCNPJ().setText("");
        this.telaCadastroUsuario.getjFormattedTextFieldCelular().setText("");
        this.telaCadastroUsuario.getjFormattedTextFieldFone().setText("");
        this.telaCadastroUsuario.getjComboBoxOpcao().setSelectedIndex(this.telaCadastroUsuario.getjComboBoxOpcao().getSelectedIndex());
        this.telaCadastroUsuario.getjComboBoxStatus().setSelectedIndex(this.telaCadastroUsuario.getjComboBoxStatus().getSelectedIndex());
    }

    private void AtivaDesativaBotoes(boolean estadoDoBotao) {
        this.telaCadastroUsuario.getjButtonCancel().setEnabled(!estadoDoBotao);
        this.telaCadastroUsuario.getjButtonClose().setEnabled(estadoDoBotao);
        this.telaCadastroUsuario.getjButtonNew().setEnabled(estadoDoBotao);
        this.telaCadastroUsuario.getjButtonSave().setEnabled(!estadoDoBotao);
        this.telaCadastroUsuario.getjButtonSearch().setEnabled(estadoDoBotao);
        ligaDesligaCampos(!estadoDoBotao);
    }

    private void ligaDesligaCampos(boolean estadoDoCampo) {
        Component[] components = this.telaCadastroUsuario.getjPanelData().getComponents();
        for (Component atualComponent : components) {
            if (atualComponent instanceof JTextField) {
                atualComponent.setEnabled(estadoDoCampo);
            }
            if (atualComponent instanceof JComboBox) {
                atualComponent.setEnabled(estadoDoCampo);
            }
            if (atualComponent instanceof JFormattedTextField) {
                atualComponent.setEnabled(estadoDoCampo);
            }
            this.telaCadastroUsuario.getjTextAreaObs().setEnabled(estadoDoCampo);
        }
    }

    private boolean RetornaCPF(String cpf, String rg) {
        return ServicePessoaFisica.Search().stream().noneMatch((atual) -> (atual.getCpf().equalsIgnoreCase(cpf) || atual.getRg().equalsIgnoreCase(rg)));
    }

    private boolean RetornaCNPJ(String cnpj) {
        return ServicePessoaJuridica.Search().stream().noneMatch((atual) -> (atual.getCnpj().equalsIgnoreCase(cnpj)));
    }

}
