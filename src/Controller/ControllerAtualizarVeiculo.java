package Controller;

import Model.bo.PessoaFisica;
import Model.bo.PessoaJuridica;
import Model.bo.Veiculo;
import Service.ServicePessoaFisica;
import Service.ServicePessoaJuridica;
import Service.ServiceVeiculo;
import Service.ServiceVersao;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import view.TelaAtualizarVeiculo;

public class ControllerAtualizarVeiculo implements ActionListener {

    private final TelaAtualizarVeiculo telaAtualizarVeiculo;

    public ControllerAtualizarVeiculo(TelaAtualizarVeiculo telaAtualizarVeiculo) {
        this.telaAtualizarVeiculo = telaAtualizarVeiculo;
        this.telaAtualizarVeiculo.getjButtonCancel().addActionListener(this);
        this.telaAtualizarVeiculo.getjButtonSave().addActionListener(this);
        this.telaAtualizarVeiculo.getjButtonValidateCpfCnpj().addActionListener(this);
        this.telaAtualizarVeiculo.getjComboBoxTipoVeiculo().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.telaAtualizarVeiculo.getjComboBoxTipoVeiculo()) {
            PreencherModelos();
        } else if (e.getSource() == this.telaAtualizarVeiculo.getjButtonValidateCpfCnpj()) {
            if (ValidarCPF_CNPJ() > 0) {
                JOptionPane.showMessageDialog(null, "Documento Cadastrado!");
            } else {
                JOptionPane.showMessageDialog(null, "Documento Não Cadastrado!");
            }
        } else if (e.getSource() == this.telaAtualizarVeiculo.getjButtonCancel()) {
            this.telaAtualizarVeiculo.dispose();
        } else if (e.getSource() == this.telaAtualizarVeiculo.getjButtonSave()) {
            int idUpdate = Integer.parseInt(this.telaAtualizarVeiculo.getjTextFieldId().getText());
            int idPessoa = ValidarCPF_CNPJ();
            Veiculo veiculoUpdate;
            if (this.telaAtualizarVeiculo.getjTextFieldProprietario().getText().equals("Pessoa Física")) {
                PessoaFisica pessoaFisica = new PessoaFisica();

                if (idPessoa > 0) {
                    pessoaFisica = ServicePessoaFisica.Search(idPessoa);
                } else {
                    JOptionPane.showMessageDialog(null, "Pessoa Física não cadastrada");
                }

                veiculoUpdate = new Veiculo(idUpdate, this.telaAtualizarVeiculo.getjComboBoxTipoVeiculo().getSelectedItem().toString(),
                        this.telaAtualizarVeiculo.getjFormattedTextFieldPlaca().getText(),
                        Integer.parseInt(this.telaAtualizarVeiculo.getjTextFieldAnoVeiculo().getText()),
                        pessoaFisica, this.telaAtualizarVeiculo.getjTextFieldCor().getText(),
                        ServiceVersao.Search(Integer.parseInt("" + this.telaAtualizarVeiculo.getjComboBoxVersaoVeiculo().getSelectedItem().toString().charAt(0)))
                );
                ServiceVeiculo.Change(idUpdate, veiculoUpdate);
                this.telaAtualizarVeiculo.dispose();
            } else {
                PessoaJuridica pessoaJuridica = new PessoaJuridica();
                if (idPessoa > 0) {
                    pessoaJuridica = ServicePessoaJuridica.Search(idPessoa);
                } else {
                    JOptionPane.showMessageDialog(null, "Pessoa Jurídica não cadastrada");
                }
                veiculoUpdate = new Veiculo(idUpdate, this.telaAtualizarVeiculo.getjComboBoxTipoVeiculo().getSelectedItem().toString(),
                        this.telaAtualizarVeiculo.getjFormattedTextFieldPlaca().getText(),
                        Integer.parseInt(this.telaAtualizarVeiculo.getjTextFieldAnoVeiculo().getText()),
                        pessoaJuridica, this.telaAtualizarVeiculo.getjTextFieldCor().getText(),
                        ServiceVersao.Search(Integer.parseInt("" + this.telaAtualizarVeiculo.getjComboBoxVersaoVeiculo().getSelectedItem().toString().charAt(0)))
                );
                ServiceVeiculo.Change(idUpdate, veiculoUpdate);
                this.telaAtualizarVeiculo.dispose();
            }
        }
    }

    private int ValidarCPF_CNPJ() {
        if (this.telaAtualizarVeiculo.getjTextFieldProprietario().getText().equals("Pessoa Física")) {
            for (PessoaFisica atual : ServicePessoaFisica.Search()) {
                if (atual.getCpf().equals(this.telaAtualizarVeiculo.getjFormattedTextFieldCpf_CnpjProp().getText())) {
                    return atual.getId();
                }
            }
        } else {
            for (PessoaJuridica atual : ServicePessoaJuridica.Search()) {
                if (atual.getCnpj().equals(this.telaAtualizarVeiculo.getjFormattedTextFieldCpf_CnpjProp().getText())) {
                    return atual.getId();
                }
            }
        }
        return 0;
    }

    private void PreencherModelos() {
        telaAtualizarVeiculo.getjComboBoxVersaoVeiculo().removeAllItems();
        ServiceVersao.Search().stream().filter((atual) -> (telaAtualizarVeiculo.getjComboBoxTipoVeiculo().getSelectedItem().toString().equals(atual.getTipo()))).forEachOrdered((atual) -> {
            telaAtualizarVeiculo.getjComboBoxVersaoVeiculo().addItem(atual.getId() + " " + atual.getModelo().getModel() + " " + atual.getVersao());
        });
    }
}
