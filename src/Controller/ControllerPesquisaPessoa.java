package Controller;

import Model.bo.PessoaFisica;
import Model.bo.PessoaJuridica;
import Service.ServicePessoaFisica;
import Service.ServicePessoaJuridica;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import view.TelaAtualizarPessoa;
import view.TelaPesquisaPessoa;

public class ControllerPesquisaPessoa implements ActionListener {

    private final TelaPesquisaPessoa telaPesquisaUsuario;
    private int opcao;

    public ControllerPesquisaPessoa(TelaPesquisaPessoa telaPesquisaUsuario) {

        this.telaPesquisaUsuario = telaPesquisaUsuario;
        this.telaPesquisaUsuario.getjButtonApplyFilter().addActionListener(this);
        this.telaPesquisaUsuario.getjButtonClean().addActionListener(this);
        this.telaPesquisaUsuario.getjButtonClose().addActionListener(this);
        this.telaPesquisaUsuario.getjButtonDelete().addActionListener(this);
        this.telaPesquisaUsuario.getjButtonEdit().addActionListener(this);
        this.telaPesquisaUsuario.getjComboBoxOpcao().addActionListener(this);
        PreencherTabela(0);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.telaPesquisaUsuario.getjComboBoxOpcao()) {
            opcao = this.telaPesquisaUsuario.getjComboBoxOpcao().getSelectedIndex();
            switch (this.telaPesquisaUsuario.getjComboBoxOpcao().getSelectedIndex()) {
                case 0:
                    PreencherTabela(opcao);
                    break;
                case 1:
                    PreencherTabela(opcao);
                    break;
                default:
                    PreencherTabela(opcao);
                    break;
            }
        }

        if (e.getSource() == this.telaPesquisaUsuario.getjButtonApplyFilter()) {
            AplicarFiltro();
        } else if (e.getSource() == this.telaPesquisaUsuario.getjButtonClean()) {
            this.telaPesquisaUsuario.getjTextFieldFilter().setText("");
            AplicarFiltro();
        } else if (e.getSource() == this.telaPesquisaUsuario.getjButtonClose()) {
            this.telaPesquisaUsuario.dispose();
        } else if (e.getSource() == this.telaPesquisaUsuario.getjButtonDelete()) {
            int row = this.telaPesquisaUsuario.getjTableUser().getSelectedRow();
            if (row < 0) {
                JFrame aviso = new JFrame("Aviso");
                JOptionPane.showMessageDialog(aviso, "Selecione uma linha para excluir", "Aviso!",
                        JOptionPane.WARNING_MESSAGE);
            } else if (row >= 0 && (char) (this.telaPesquisaUsuario.getjTableUser().getValueAt(row, 10)) == 'F') {
                int idUser = (int) this.telaPesquisaUsuario.getjTableUser().getValueAt(row, 0);
                ServicePessoaFisica.Delete(idUser);
                JOptionPane.showMessageDialog(null, "Pessoa Física deletada!", "Ok!",
                        JOptionPane.INFORMATION_MESSAGE);
                PreencherTabela(opcao);
            } else if (row >= 0 && (char) (this.telaPesquisaUsuario.getjTableUser().getValueAt(row, 10)) == 'J') {
                int idUser = (int) this.telaPesquisaUsuario.getjTableUser().getValueAt(row, 0);
                ServicePessoaJuridica.Delete(idUser);
                JOptionPane.showMessageDialog(null, "Pessoa Jurídica deletada!", "Ok!",
                        JOptionPane.INFORMATION_MESSAGE);
                PreencherTabela(opcao);
            }
        } else if (e.getSource() == this.telaPesquisaUsuario.getjButtonEdit()) {
            int row = this.telaPesquisaUsuario.getjTableUser().getSelectedRow();
            if (row < 0) {
                JFrame aviso = new JFrame("Aviso");
                JOptionPane.showMessageDialog(aviso, "Selecione uma linha para editar.", "Aviso!",
                        JOptionPane.WARNING_MESSAGE);
            } else {
                TelaAtualizarPessoa telaAtualizarUsuario = new TelaAtualizarPessoa(null, true);
                ControllerAtualizarPessoa controllerAtualizarUsuario = new ControllerAtualizarPessoa(telaAtualizarUsuario);
                if ('F' == (char) this.telaPesquisaUsuario.getjTableUser().getValueAt(row, 10)) {
                    int updateUser = (int) (this.telaPesquisaUsuario.getjTableUser().getValueAt(row, 0));
                    PessoaFisica pessoaUpdate;
                    pessoaUpdate = ServicePessoaFisica.Search(updateUser);
                    telaAtualizarUsuario.getjFormattedTextFieldCPFCNPJ().setText(pessoaUpdate.getCpf());
                    telaAtualizarUsuario.getjFormattedTextFieldCelular().setText(pessoaUpdate.getCelular());
                    telaAtualizarUsuario.getjFormattedTextFieldFone().setText(pessoaUpdate.getFone());
                    telaAtualizarUsuario.getjTextAreaObs().setText(pessoaUpdate.getObs());
                    telaAtualizarUsuario.getjTextFieldEmail().setText(pessoaUpdate.getEmail());
                    telaAtualizarUsuario.getjTextFieldEndereço().setText(pessoaUpdate.getAddress());
                    telaAtualizarUsuario.getjTextFieldId().setText("" + pessoaUpdate.getId());
                    telaAtualizarUsuario.getjTextFieldNome().setText(pessoaUpdate.getNome());
                    telaAtualizarUsuario.getjTextFieldRg().setText(pessoaUpdate.getRg());
                    telaAtualizarUsuario.getjTextFieldTipo().setText("" + pessoaUpdate.getTipo());
                    telaAtualizarUsuario.getjComboBoxStatus().setSelectedItem(pessoaUpdate.getStatus());
                } else {
                    try {
                        MaskFormatter mascaraCNPJ = new MaskFormatter("##.###.###/####-##");
                        telaAtualizarUsuario.getjFormattedTextFieldCPFCNPJ().setValue(null);
                        telaAtualizarUsuario.getjLabelCPFCNPJ().setText("CNPJ:");
                        telaAtualizarUsuario.getjLabelRg().setVisible(false);
                        telaAtualizarUsuario.getjTextFieldRg().setVisible(false);
                        telaAtualizarUsuario.getjFormattedTextFieldCPFCNPJ().setFormatterFactory(
                                new DefaultFormatterFactory(mascaraCNPJ));
                    } catch (ParseException ex) {
                        Logger.getLogger(ControllerCadastroPessoa.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    int updateUser = (int) (this.telaPesquisaUsuario.getjTableUser().getValueAt(row, 0));
                    PessoaJuridica pessoaUpdate;
                    pessoaUpdate = ServicePessoaJuridica.Search(updateUser);
                    telaAtualizarUsuario.getjFormattedTextFieldCPFCNPJ().setText("" + pessoaUpdate.getCnpj());
                    telaAtualizarUsuario.getjFormattedTextFieldCelular().setText(pessoaUpdate.getCelular());
                    telaAtualizarUsuario.getjFormattedTextFieldFone().setText(pessoaUpdate.getFone());
                    telaAtualizarUsuario.getjTextAreaObs().setText(pessoaUpdate.getObs());
                    telaAtualizarUsuario.getjTextFieldEmail().setText(pessoaUpdate.getEmail());
                    telaAtualizarUsuario.getjTextFieldEndereço().setText(pessoaUpdate.getAddress());
                    telaAtualizarUsuario.getjTextFieldId().setText("" + pessoaUpdate.getId());
                    telaAtualizarUsuario.getjTextFieldNome().setText(pessoaUpdate.getNome());
                    telaAtualizarUsuario.getjTextFieldTipo().setText("" + pessoaUpdate.getTipo());
                    telaAtualizarUsuario.getjComboBoxStatus().setSelectedItem(pessoaUpdate.getStatus());
                }
                telaAtualizarUsuario.setLocationRelativeTo(null);
                telaAtualizarUsuario.setVisible(true);
            }
            PreencherTabela(opcao);
        }
    }

    private void PreencherTabela(int opcao) {
        LimparTabela();
        DefaultTableModel table = (DefaultTableModel) this.telaPesquisaUsuario.getjTableUser().getModel();
        ArrayList<PessoaFisica> listaFisica;
        ArrayList<PessoaJuridica> listaJuridica;
        listaFisica = (ArrayList) ServicePessoaFisica.Search();
        listaJuridica = (ArrayList) ServicePessoaJuridica.Search();
        switch (opcao) {
            case 0:
                listaFisica.forEach((atual) -> {
                    table.addRow(new Object[]{atual.getId(), atual.getStatus(), atual.getNome(), atual.getCpf(),
                        atual.getRg(), atual.getFone(), atual.getCelular(), atual.getAddress(), atual.getEmail(),
                        atual.getObs(), atual.getTipo()
                    });
                });
                listaJuridica.forEach((atual) -> {
                    table.addRow(new Object[]{atual.getId(), atual.getStatus(), atual.getNome(), atual.getCnpj(), "-",
                        atual.getFone(), atual.getCelular(), atual.getAddress(), atual.getEmail(), atual.getObs(),
                        atual.getTipo()
                    });
                });
                break;

            case 1:
                listaFisica.forEach((atual) -> {
                    table.addRow(new Object[]{atual.getId(), atual.getStatus(), atual.getNome(), atual.getCpf(),
                        atual.getRg(), atual.getFone(), atual.getCelular(), atual.getAddress(), atual.getEmail(),
                        atual.getObs(), atual.getTipo()
                    });
                });
                break;
            case 2:
                listaJuridica.forEach((atual) -> {
                    table.addRow(new Object[]{atual.getId(), atual.getStatus(), atual.getNome(), atual.getCnpj(), "-",
                        atual.getFone(), atual.getCelular(), atual.getAddress(), atual.getEmail(), atual.getObs(), atual.getTipo()
                    });
                });
                break;
            default:
                break;
        }
    }

    private void LimparTabela() {
        DefaultTableModel table = (DefaultTableModel) this.telaPesquisaUsuario.getjTableUser().getModel();
        table.setRowCount(0);
    }

    private void AplicarFiltro() {
        TableRowSorter<DefaultTableModel> filter = new TableRowSorter<>(
                (DefaultTableModel) this.telaPesquisaUsuario.getjTableUser().getModel()
        );
        filter.setRowFilter(RowFilter.regexFilter(this.telaPesquisaUsuario.getjTextFieldFilter().getText()));
        this.telaPesquisaUsuario.getjTableUser().setRowSorter(filter);
    }

}
