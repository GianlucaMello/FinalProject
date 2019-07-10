package Controller;

import Model.bo.PessoaFisica;
import Model.bo.PessoaJuridica;
import Model.bo.Veiculo;
import Service.ServicePessoaFisica;
import Service.ServicePessoaJuridica;
import Service.ServiceVeiculo;
import Service.ServiceVersao;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import view.TelaCadastroVeiculo;
import view.TelaPesquisaVeiculo;

public class ControllerCadastroVeiculo implements ActionListener {

    private final TelaCadastroVeiculo telaCadastroVeiculo;

    public ControllerCadastroVeiculo(TelaCadastroVeiculo telaCadastroVeiculo) {
        this.telaCadastroVeiculo = telaCadastroVeiculo;
        this.telaCadastroVeiculo.getjButtonCancel().addActionListener(this);
        this.telaCadastroVeiculo.getjButtonClose().addActionListener(this);
        this.telaCadastroVeiculo.getjButtonNew().addActionListener(this);
        this.telaCadastroVeiculo.getjButtonSave().addActionListener(this);
        this.telaCadastroVeiculo.getjButtonSearch().addActionListener(this);
        this.telaCadastroVeiculo.getjButtonValidateCpfCnpj().addActionListener(this);
        this.telaCadastroVeiculo.getjComboBoxOpcaoProprietario().addActionListener(this);
        this.telaCadastroVeiculo.getjComboBoxTipoVeiculo().addActionListener(this);
        PreencherTipos();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.telaCadastroVeiculo.getjComboBoxOpcaoProprietario()) {
            try {
                MaskFormatter mascaraCPF = new MaskFormatter("###.###.###-##");
                MaskFormatter mascaraCNPJ = new MaskFormatter("##.###.###/####-##");
                this.telaCadastroVeiculo.getjFormattedTextFieldCpf_CnpjProp().setValue(null);
                if (this.telaCadastroVeiculo.getjComboBoxOpcaoProprietario().getSelectedIndex() == 0) {
                    this.telaCadastroVeiculo.getjLabelCpf_Cnpj().setText("CPF:");
                    this.telaCadastroVeiculo.getjFormattedTextFieldCpf_CnpjProp().setFormatterFactory(
                            new DefaultFormatterFactory(mascaraCPF));
                } else {
                    this.telaCadastroVeiculo.getjLabelCpf_Cnpj().setText("CNPJ:");
                    this.telaCadastroVeiculo.getjFormattedTextFieldCpf_CnpjProp().setFormatterFactory(
                            new DefaultFormatterFactory(mascaraCNPJ));
                }
            } catch (ParseException ex) {
                Logger.getLogger(ControllerCadastroPessoa.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (e.getSource() == this.telaCadastroVeiculo.getjComboBoxTipoVeiculo()) {
            PreencherModelos();
        } else if (e.getSource() == this.telaCadastroVeiculo.getjButtonValidateCpfCnpj()) {
            if (ValidarCPF_CNPJ() > 0) {
                JOptionPane.showMessageDialog(null, "Documento Cadastrado!");
            } else {
                JOptionPane.showMessageDialog(null, "Documento Não Cadastrado!");
            }
        } else if (e.getSource() == this.telaCadastroVeiculo.getjButtonCancel()) {
            LimpaCampos();
            AtivaDesativaBotoes(true);
        } else if (e.getSource() == this.telaCadastroVeiculo.getjButtonClose()) {
            LimpaCampos();
            this.telaCadastroVeiculo.dispose();
        } else if (e.getSource() == this.telaCadastroVeiculo.getjButtonNew()) {
            LimpaCampos();
            AtivaDesativaBotoes(false);
        } else if (e.getSource() == this.telaCadastroVeiculo.getjButtonSave()) {
            Veiculo veiculo;
            int id, tamList = ServiceVeiculo.Search().size(), idPessoa = ValidarCPF_CNPJ();
            if (tamList != 0) {
                id = ServiceVeiculo.Search().get(tamList - 1).getId() + 1;
            } else {
                id = 1;
            }
            if (this.telaCadastroVeiculo.getjComboBoxOpcaoProprietario().getSelectedIndex() == 0) {
                if (!"   .   .   -  ".equalsIgnoreCase(this.telaCadastroVeiculo.getjFormattedTextFieldCpf_CnpjProp().getText())
                        && !"   -    ".equalsIgnoreCase(this.telaCadastroVeiculo.getjFormattedTextFieldPlaca().getText())
                        && this.telaCadastroVeiculo.getjComboBoxTipoVeiculo().getSelectedItem() != null
                        && this.telaCadastroVeiculo.getjComboBoxVersaoVeiculo().getSelectedItem() != null
                        && !this.telaCadastroVeiculo.getjTextFieldAnoVeiculo().getText().equalsIgnoreCase("")
                        && !this.telaCadastroVeiculo.getjTextFieldCor().getText().equalsIgnoreCase("")) {
                    PessoaFisica pessoaFisica;

                    if (idPessoa > 0) {
                        if (!VerificaPlaca(this.telaCadastroVeiculo.getjFormattedTextFieldPlaca().getText())) {
                            pessoaFisica = ServicePessoaFisica.Search(idPessoa);
                            veiculo = new Veiculo(id, this.telaCadastroVeiculo.getjComboBoxTipoVeiculo().getSelectedItem().toString(),
                                    this.telaCadastroVeiculo.getjFormattedTextFieldPlaca().getText(),
                                    Integer.parseInt(this.telaCadastroVeiculo.getjTextFieldAnoVeiculo().getText()),
                                    pessoaFisica, this.telaCadastroVeiculo.getjTextFieldCor().getText(),
                                    ServiceVersao.Search(Integer.parseInt("" + this.telaCadastroVeiculo.getjComboBoxVersaoVeiculo().getSelectedItem().toString().charAt(0)))
                            );
                            System.out.println(ServiceVersao.Search(Integer.parseInt("" + this.telaCadastroVeiculo.getjComboBoxVersaoVeiculo().getSelectedItem().toString().charAt(0))).getModelo().getModel());
                            ServiceVeiculo.Include(veiculo);
                            JOptionPane.showMessageDialog(null, "Veículo Cadastrado!", "Ok!", JOptionPane.INFORMATION_MESSAGE);
                            LimpaCampos();
                            AtivaDesativaBotoes(true);
                        } else {
                            JOptionPane.showMessageDialog(null, "Um veiculo com a placa informada já foi cadastrado!", "Erro!", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Pessoa Física não cadastrada");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "É necessário preencher todos os campos", "Aviso!", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                if (!"  .   .   /    -  ".equalsIgnoreCase(this.telaCadastroVeiculo.getjFormattedTextFieldCpf_CnpjProp().getText())
                        && !"   -    ".equalsIgnoreCase(this.telaCadastroVeiculo.getjFormattedTextFieldPlaca().getText())
                        && this.telaCadastroVeiculo.getjComboBoxTipoVeiculo().getSelectedItem() != null
                        && this.telaCadastroVeiculo.getjComboBoxVersaoVeiculo().getSelectedItem() != null
                        && !this.telaCadastroVeiculo.getjTextFieldAnoVeiculo().getText().equalsIgnoreCase("")
                        && !this.telaCadastroVeiculo.getjTextFieldCor().getText().equalsIgnoreCase("")) {
                    PessoaJuridica pessoaJuridica;
                    if (idPessoa > 0) {
                        if (!VerificaPlaca(this.telaCadastroVeiculo.getjFormattedTextFieldPlaca().getText())) {
                            pessoaJuridica = ServicePessoaJuridica.Search(idPessoa);

                            veiculo = new Veiculo(id, this.telaCadastroVeiculo.getjComboBoxTipoVeiculo().getSelectedItem().toString(),
                                    this.telaCadastroVeiculo.getjFormattedTextFieldPlaca().getText(),
                                    Integer.parseInt(this.telaCadastroVeiculo.getjTextFieldAnoVeiculo().getText()),
                                    pessoaJuridica, this.telaCadastroVeiculo.getjTextFieldCor().getText(),
                                    ServiceVersao.Search(Integer.parseInt("" + this.telaCadastroVeiculo.getjComboBoxVersaoVeiculo().getSelectedItem().toString().charAt(0)))
                            );
                            ServiceVeiculo.Include(veiculo);
                            JOptionPane.showMessageDialog(null, "Veículo Cadastrado", "Ok!", JOptionPane.INFORMATION_MESSAGE);
                            LimpaCampos();
                            AtivaDesativaBotoes(true);
                        } else {
                            JOptionPane.showMessageDialog(null, "Um veículo com a placa informada já foi cadastrado!", "Erro!", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Pessoa Jurídica não cadastrada");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "É necessário preencher todos os campos!", "Aviso!", JOptionPane.WARNING_MESSAGE);
                }
            }
        } else if (e.getSource() == this.telaCadastroVeiculo.getjButtonSearch()) {
            TelaPesquisaVeiculo telaPesquisaVeiculo = new TelaPesquisaVeiculo(null, true);
            ControllerPesquisaVeiculo controllerPesquisaVeiculo = new ControllerPesquisaVeiculo(telaPesquisaVeiculo);
            telaPesquisaVeiculo.setLocationRelativeTo(null);
            telaPesquisaVeiculo.setVisible(true);
        }
    }

    private void AtivaDesativaBotoes(boolean estadoDoBotao) {
        this.telaCadastroVeiculo.getjButtonCancel().setEnabled(!estadoDoBotao);
        this.telaCadastroVeiculo.getjButtonClose().setEnabled(estadoDoBotao);
        this.telaCadastroVeiculo.getjButtonNew().setEnabled(estadoDoBotao);
        this.telaCadastroVeiculo.getjButtonSave().setEnabled(!estadoDoBotao);
        this.telaCadastroVeiculo.getjButtonSearch().setEnabled(estadoDoBotao);
        ligaDesligaCampos(!estadoDoBotao);
    }

    private void LimpaCampos() {
        this.telaCadastroVeiculo.getjComboBoxOpcaoProprietario().setSelectedIndex(0);
        this.telaCadastroVeiculo.getjComboBoxTipoVeiculo().setSelectedIndex(0);
        this.telaCadastroVeiculo.getjComboBoxVersaoVeiculo().setSelectedItem(null);
        this.telaCadastroVeiculo.getjFormattedTextFieldCpf_CnpjProp().setValue(null);
        this.telaCadastroVeiculo.getjTextFieldCor().setText("");
        this.telaCadastroVeiculo.getjTextFieldAnoVeiculo().setText("");
        this.telaCadastroVeiculo.getjFormattedTextFieldPlaca().setValue(null);
    }

    private void ligaDesligaCampos(boolean estadoDoCampo) {
        Component[] components = this.telaCadastroVeiculo.getjPanelData().getComponents();

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
            if (atualComponent instanceof JButton) {
                atualComponent.setEnabled(estadoDoCampo);
            }
        }
    }

    private int ValidarCPF_CNPJ() {
        if (this.telaCadastroVeiculo.getjComboBoxOpcaoProprietario().getSelectedIndex() == 0) {
            for (PessoaFisica atual : ServicePessoaFisica.Search()) {
                if (atual.getCpf().equals(this.telaCadastroVeiculo.getjFormattedTextFieldCpf_CnpjProp().getText())) {
                    return atual.getId();
                }
            }
        } else {
            for (PessoaJuridica atual : ServicePessoaJuridica.Search()) {
                if (atual.getCnpj().equals(this.telaCadastroVeiculo.getjFormattedTextFieldCpf_CnpjProp().getText())) {
                    return atual.getId();
                }
            }
        }
        return 0;
    }

    private void PreencherModelos() {
        this.telaCadastroVeiculo.getjComboBoxVersaoVeiculo().removeAllItems();
        ServiceVersao.Search().stream().filter((atual) -> (this.telaCadastroVeiculo.getjComboBoxTipoVeiculo().getSelectedItem().toString().equals(atual.getTipo()))).forEachOrdered((atual) -> {
            this.telaCadastroVeiculo.getjComboBoxVersaoVeiculo().addItem("" + atual.getId() + " " + atual.getModelo().getModel() + " " + atual.getVersao() + " " + atual.getCategoria() + " " + atual.getMotor());
        });
    }

    private void PreencherTipos() {
        Set<String> arrayItens = new HashSet<>();
        ServiceVersao.Search().forEach((atual) -> {
            arrayItens.add(atual.getTipo());
        });
        if (this.telaCadastroVeiculo.getjComboBoxTipoVeiculo().getItemCount() < arrayItens.size()) {
            this.telaCadastroVeiculo.getjComboBoxTipoVeiculo().addItem("");
            arrayItens.forEach((arrayIten) -> {
                this.telaCadastroVeiculo.getjComboBoxTipoVeiculo().addItem(arrayIten);
            });
        }
        PreencherModelos();
    }

    private boolean VerificaPlaca(String placa) {
        return ServiceVeiculo.Search().stream().anyMatch((atual) -> (atual.getPlaca().equalsIgnoreCase(placa)));
    }

}
