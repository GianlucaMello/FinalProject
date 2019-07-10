/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.bo.Veiculo;
import Service.ServiceVeiculo;
import Service.ServiceVersao;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import view.TelaAtualizarVeiculo;
import view.TelaPesquisaVeiculo;

/**
 *
 * @author gianl
 */
public class ControllerPesquisaVeiculo implements ActionListener {

    private final TelaPesquisaVeiculo telaPesquisaVeiculo;

    public ControllerPesquisaVeiculo(TelaPesquisaVeiculo telaPesquisaVeiculo) {
        this.telaPesquisaVeiculo = telaPesquisaVeiculo;
        this.telaPesquisaVeiculo.getjButtonApplyFilter().addActionListener(this);
        this.telaPesquisaVeiculo.getjButtonClean().addActionListener(this);
        this.telaPesquisaVeiculo.getjButtonClose().addActionListener(this);
        this.telaPesquisaVeiculo.getjButtonDelete().addActionListener(this);
        this.telaPesquisaVeiculo.getjButtonEdit().addActionListener(this);
        PreencherTabela();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.telaPesquisaVeiculo.getjButtonApplyFilter()) {
            AplicarFiltro();
        } else if (e.getSource() == this.telaPesquisaVeiculo.getjButtonClean()) {
            this.telaPesquisaVeiculo.getjTextFieldFilter().setText("");
            AplicarFiltro();
        } else if (e.getSource() == this.telaPesquisaVeiculo.getjButtonClose()) {
            this.telaPesquisaVeiculo.dispose();
        } else if (e.getSource() == this.telaPesquisaVeiculo.getjButtonDelete()) {
            int row = this.telaPesquisaVeiculo.getjTableVehicle().getSelectedRow();
            if (row < 0) {
                JFrame aviso = new JFrame("Aviso");
                JOptionPane.showMessageDialog(aviso, "Selecione uma linha para excluir", "Aviso!",
                        JOptionPane.WARNING_MESSAGE);
            } else {
                int idVeiculo = (int) this.telaPesquisaVeiculo.getjTableVehicle().getValueAt(row, 0);
                ServiceVeiculo.Delete(idVeiculo);
                PreencherTabela();
            }
        } else if (e.getSource() == this.telaPesquisaVeiculo.getjButtonEdit()) {
            int row = this.telaPesquisaVeiculo.getjTableVehicle().getSelectedRow();
            if (row < 0) {
                JFrame aviso = new JFrame("Aviso");
                JOptionPane.showMessageDialog(aviso, "Selecione uma linha para editar", "Aviso!",
                        JOptionPane.WARNING_MESSAGE);
            } else {
                TelaAtualizarVeiculo telaAtualizarVeiculo = new TelaAtualizarVeiculo(null, true);
                ControllerAtualizarVeiculo controllerAtualizarVeiculo = new ControllerAtualizarVeiculo(telaAtualizarVeiculo);
                int idUpdate = Integer.parseInt("" + this.telaPesquisaVeiculo.getjTableVehicle().getValueAt(row, 0));
                Veiculo veiculo = ServiceVeiculo.Search(idUpdate);
                telaAtualizarVeiculo.getjComboBoxTipoVeiculo().setSelectedItem(veiculo.getTipo());
                PreencherTipos(telaAtualizarVeiculo);
                telaAtualizarVeiculo.getjComboBoxTipoVeiculo().setSelectedItem(veiculo.getTipo());
                telaAtualizarVeiculo.getjComboBoxVersaoVeiculo().setSelectedItem(veiculo.getVersao().getVersao());
                telaAtualizarVeiculo.getjTextFieldAnoVeiculo().setText("" + veiculo.getAnoModelo());
                telaAtualizarVeiculo.getjTextFieldCor().setText(veiculo.getCor());
                telaAtualizarVeiculo.getjTextFieldId().setText("" + veiculo.getId());
                telaAtualizarVeiculo.getjFormattedTextFieldPlaca().setText(veiculo.getPlaca());
                if (ServiceVeiculo.Search(idUpdate).getPessoaFisica() != null) {
                    telaAtualizarVeiculo.getjTextFieldProprietario().setText("Pessoa Física");
                    telaAtualizarVeiculo.getjFormattedTextFieldCpf_CnpjProp().setText(veiculo.getPessoaFisica().getCpf());

                } else {
                    try {
                        MaskFormatter mascaraCNPJ = new MaskFormatter("##.###.###/####-##");
                        telaAtualizarVeiculo.getjFormattedTextFieldCpf_CnpjProp().setValue(null);
                        telaAtualizarVeiculo.getjLabelCpf_Cnpj().setText("CNPJ:");
                        telaAtualizarVeiculo.getjFormattedTextFieldCpf_CnpjProp().setFormatterFactory(
                                new DefaultFormatterFactory(mascaraCNPJ));
                    } catch (ParseException ex) {
                        Logger.getLogger(ControllerPesquisaVeiculo.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    telaAtualizarVeiculo.getjTextFieldProprietario().setText("Pessoa Jurídica");
                    telaAtualizarVeiculo.getjFormattedTextFieldCpf_CnpjProp().setText(veiculo.getPessoaJuridica().getCnpj());
                }
                telaAtualizarVeiculo.setLocationRelativeTo(null);
                telaAtualizarVeiculo.setVisible(true);
            }
            PreencherTabela();
        }
    }

    private void PreencherTabela() {
        LimparTabela();
        DefaultTableModel table = (DefaultTableModel) this.telaPesquisaVeiculo.getjTableVehicle().getModel();
        ServiceVeiculo.Search().forEach((Veiculo veiculoAtual) -> {
            table.addRow(new Object[]{veiculoAtual.getId(), RetornaNome(veiculoAtual.getId()), RetornaCPF_CNPJ(veiculoAtual.getId()),
                veiculoAtual.getPlaca(), "" + veiculoAtual.getId() + " " + veiculoAtual.getVersao().getModelo().getModel() + " " + 
                        veiculoAtual.getVersao().getVersao() + " " + veiculoAtual.getVersao().getCategoria() + " " + veiculoAtual.getVersao().getMotor(),
                veiculoAtual.getCor(), veiculoAtual.getTipo(), veiculoAtual.getAnoModelo(),});
        });
    }

    private void AplicarFiltro() {
        TableRowSorter<DefaultTableModel> filter = new TableRowSorter<>(
                (DefaultTableModel) this.telaPesquisaVeiculo.getjTableVehicle().getModel()
        );
        filter.setRowFilter(RowFilter.regexFilter(this.telaPesquisaVeiculo.getjTextFieldFilter().getText()));
        this.telaPesquisaVeiculo.getjTableVehicle().setRowSorter(filter);
    }

    private void LimparTabela() {
        DefaultTableModel table = (DefaultTableModel) this.telaPesquisaVeiculo.getjTableVehicle().getModel();
        table.setRowCount(0);
    }

    private String RetornaCPF_CNPJ(int id) {
        if (ServiceVeiculo.Search(id).getPessoaFisica() != null) {
            return ServiceVeiculo.Search(id).getPessoaFisica().getCpf();
        } else {
            return ServiceVeiculo.Search(id).getPessoaJuridica().getCnpj();
        }
    }

    private String RetornaNome(int id) {
        if (ServiceVeiculo.Search(id).getPessoaFisica() != null) {
            return ServiceVeiculo.Search(id).getPessoaFisica().getNome();
        } else {
            return ServiceVeiculo.Search(id).getPessoaJuridica().getNome();
        }
    }

    private void PreencherModelos(TelaAtualizarVeiculo telaAtualizarVeiculo) {
        telaAtualizarVeiculo.getjComboBoxVersaoVeiculo().removeAllItems();
            ServiceVersao.Search().stream().filter((atual) -> (telaAtualizarVeiculo.getjComboBoxTipoVeiculo().getSelectedItem().toString().equals(atual.getTipo()))).forEachOrdered((atual) -> {
                telaAtualizarVeiculo.getjComboBoxVersaoVeiculo().addItem("" + atual.getId() + " " + 
                        atual.getModelo().getModel() + " " + atual.getVersao() + " " + atual.getCategoria() + " " + atual.getMotor());
            });
    }
    
    private void PreencherTipos(TelaAtualizarVeiculo telaAtualizarVeiculo) {
        Set<String> arrayItens = new HashSet<>();
        ServiceVersao.Search().forEach((atual) -> {
            arrayItens.add(atual.getTipo());
        });
        if (telaAtualizarVeiculo.getjComboBoxTipoVeiculo().getItemCount() < arrayItens.size()) {
            telaAtualizarVeiculo.getjComboBoxTipoVeiculo().addItem("");
            arrayItens.forEach((arrayIten) -> {
                telaAtualizarVeiculo.getjComboBoxTipoVeiculo().addItem(arrayIten);
            });
        }
        PreencherModelos(telaAtualizarVeiculo);
    }
}
