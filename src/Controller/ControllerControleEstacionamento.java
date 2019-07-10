/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.bo.ControleEstacionamento;
import Model.bo.Veiculo;
import Service.ServiceControleEstacionamento;
import Service.ServiceVeiculo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import view.TelaControleEstacionamento;

/**
 *
 * @author gianl
 */
public class ControllerControleEstacionamento implements ActionListener {

    private final TelaControleEstacionamento telaControleEstacionamento;

    public ControllerControleEstacionamento(TelaControleEstacionamento telaControleEstacionamento) {
        this.telaControleEstacionamento = telaControleEstacionamento;
        this.telaControleEstacionamento.getjButtonApplyFilter().addActionListener(this);
        this.telaControleEstacionamento.getjButtonClean().addActionListener(this);
        this.telaControleEstacionamento.getjButtonCleanParking().addActionListener(this);
        this.telaControleEstacionamento.getjButtonIn().addActionListener(this);
        this.telaControleEstacionamento.getjButtonOut().addActionListener(this);
        PreencherTabela();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.telaControleEstacionamento.getjButtonApplyFilter()) {
            AplicarFiltro();
        } else if (e.getSource() == this.telaControleEstacionamento.getjButtonClean()) {
            this.telaControleEstacionamento.getjTextFieldFilter().setText("");
            AplicarFiltro();
        } else if (e.getSource() == this.telaControleEstacionamento.getjButtonCleanParking()) {
            ArrayList<ControleEstacionamento> listVeiculos;
            listVeiculos = (ArrayList<ControleEstacionamento>) ServiceControleEstacionamento.Search();
            for (int i = listVeiculos.size() - 1; i >= 0; i--) {
                ServiceControleEstacionamento.Delete(listVeiculos.get(i).getVeiculo().getPlaca());
            }
            LimparTabela();
            JOptionPane.showMessageDialog(null, "Todos os veículos do estacionamento foram removidos");
        } else if (e.getSource() == this.telaControleEstacionamento.getjButtonIn()) {
            ControleEstacionamento veiculoIn;
            Date dateIn = new Date();
            int id = VerificaPlaca(this.telaControleEstacionamento.getjFormattedTextFieldPlaca().getText());
            if (!RetornaPlaca(this.telaControleEstacionamento.getjFormattedTextFieldPlaca().getText())) {
                if (id > 0) {
                    Veiculo veiculo = ServiceVeiculo.Search(id);
                    veiculoIn = new ControleEstacionamento(dateIn, veiculo);
                    ServiceControleEstacionamento.Include(veiculoIn);
                    PreencherTabela();
                    JOptionPane.showMessageDialog(null, "Veiculo entrou no estacionamento", "Aviso!",
                            JOptionPane.INFORMATION_MESSAGE);
                    this.telaControleEstacionamento.getjFormattedTextFieldPlaca().setValue(null);
                } else {
                    JFrame aviso = new JFrame("Aviso");
                    JOptionPane.showMessageDialog(aviso, "Veiculo não encontrado", "Aviso!",
                            JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JFrame aviso = new JFrame("Aviso");
                JOptionPane.showMessageDialog(aviso, "Veículo já está no estacionamento", "Erro!",
                        JOptionPane.ERROR_MESSAGE);
            }

        } else if (e.getSource() == this.telaControleEstacionamento.getjButtonOut()) {
            if (RetornaPlaca(this.telaControleEstacionamento.getjFormattedTextFieldPlaca().getText())) {
                ServiceControleEstacionamento.Delete(this.telaControleEstacionamento.getjFormattedTextFieldPlaca().getText());
                JOptionPane.showMessageDialog(null, "Veiculo saiu do estacionamento", "Aviso!",
                        JOptionPane.INFORMATION_MESSAGE);
                this.telaControleEstacionamento.getjFormattedTextFieldPlaca().setText("");
            } else {
                JFrame aviso = new JFrame("Aviso");
                JOptionPane.showMessageDialog(aviso, "Veiculo não se encontra no estacionamento", "Aviso!",
                        JOptionPane.ERROR_MESSAGE);
            }
            PreencherTabela();
        }
    }

    private void AplicarFiltro() {
        TableRowSorter<DefaultTableModel> filter = new TableRowSorter<>(
                (DefaultTableModel) this.telaControleEstacionamento.getjTableParking().getModel()
        );
        filter.setRowFilter(RowFilter.regexFilter(this.telaControleEstacionamento.getjTextFieldFilter().getText()));
        this.telaControleEstacionamento.getjTableParking().setRowSorter(filter);
    }

    private void PreencherTabela() {
        LimparTabela();
        DefaultTableModel table = (DefaultTableModel) this.telaControleEstacionamento.getjTableParking().getModel();
        ServiceControleEstacionamento.Search().forEach((atual) -> {
            if (atual.getVeiculo().getPessoaFisica() != null) {
                table.addRow(new Object[]{atual.getVeiculo().getPessoaFisica().getNome(),
                    atual.getVeiculo().getVersao().getModelo().getModel(), atual.getVeiculo().getPlaca(),
                     atual.getVeiculo().getVersao().getVersao(), atual.getVeiculo().getVersao().getCategoria(),
                    atual.getVeiculo().getVersao().getMotor(), atual.getVeiculo().getCor(), atual.getVeiculo().getTipo(),
                    FormataData(atual.getData())
                });
            } else {
                table.addRow(new Object[]{atual.getVeiculo().getPessoaJuridica().getNome(),
                    atual.getVeiculo().getVersao().getModelo().getModel(), atual.getVeiculo().getPlaca(),
                    atual.getVeiculo().getVersao().getVersao(), atual.getVeiculo().getVersao().getCategoria(),
                    atual.getVeiculo().getVersao().getMotor(), atual.getVeiculo().getCor(), atual.getVeiculo().getTipo(),
                    FormataData(atual.getData())
                });
            }
        });
    }

    private int VerificaPlaca(String placa) {
        for (Veiculo atual : ServiceVeiculo.Search()) {
            if (atual.getPlaca().equalsIgnoreCase(placa)) {
                return atual.getId();
            }
        }
        return 0;
    }

    private String FormataData(Date data) {
        DateFormat formatoDataHora;
        String formatacao = "dd/MM/yyy HH:mm";
        formatoDataHora = new SimpleDateFormat(formatacao);
        return formatoDataHora.format(data);
    }

    private boolean RetornaPlaca(String placa) {
        return ServiceControleEstacionamento.Search().stream().anyMatch((atual) -> (atual.getVeiculo().getPlaca().equalsIgnoreCase(placa)));
    }

    private void LimparTabela() {
        DefaultTableModel table = (DefaultTableModel) this.telaControleEstacionamento.getjTableParking().getModel();
        table.setRowCount(0);
    }
}
