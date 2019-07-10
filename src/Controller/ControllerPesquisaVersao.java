package Controller;

import Model.bo.Modelo;
import Model.bo.Versao;
import Service.ServiceModelo;
import Service.ServiceVersao;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import view.TelaAtualizarVersao;
import view.TelaPesquisaVersao;

public class ControllerPesquisaVersao implements ActionListener {

    private final TelaPesquisaVersao telaPesquisaVersao;

    public ControllerPesquisaVersao(TelaPesquisaVersao telaPesquisaVersao) {
        this.telaPesquisaVersao = telaPesquisaVersao;
        this.telaPesquisaVersao.getjButtonApplyFilter().addActionListener(this);
        this.telaPesquisaVersao.getjButtonClean().addActionListener(this);
        this.telaPesquisaVersao.getjButtonClose().addActionListener(this);
        this.telaPesquisaVersao.getjButtonDelete().addActionListener(this);
        this.telaPesquisaVersao.getjButtonEdit().addActionListener(this);
        PreencherTabela();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.telaPesquisaVersao.getjButtonApplyFilter()) {
            AplicarFiltro();
        } else if (e.getSource() == this.telaPesquisaVersao.getjButtonClean()) {
            this.telaPesquisaVersao.getjTextFieldFilter().setText("");
            AplicarFiltro();
        } else if (e.getSource() == this.telaPesquisaVersao.getjButtonClose()) {
            this.telaPesquisaVersao.dispose();
        } else if (e.getSource() == this.telaPesquisaVersao.getjButtonDelete()) {
            int row = this.telaPesquisaVersao.getjTableVersao().getSelectedRow();
            if (row < 0) {
                JFrame aviso = new JFrame("Aviso");
                JOptionPane.showMessageDialog(aviso, "Selecione uma versão para excluir", "Aviso!",
                        JOptionPane.WARNING_MESSAGE);
            } else {
                int idVersao = (int) this.telaPesquisaVersao.getjTableVersao().getValueAt(row, 0);
                ServiceVersao.Delete(idVersao);
                JOptionPane.showMessageDialog(null, "Versão Excluída", "Ok!",
                        JOptionPane.INFORMATION_MESSAGE);
                PreencherTabela();
            }
        } else if (e.getSource() == this.telaPesquisaVersao.getjButtonEdit()) {
            int row = this.telaPesquisaVersao.getjTableVersao().getSelectedRow();
            if (row < 0) {
                JFrame aviso = new JFrame("Aviso");
                JOptionPane.showMessageDialog(aviso, "Selecione uma linha para editar.", "Aviso!",
                        JOptionPane.WARNING_MESSAGE);
            } else {
                TelaAtualizarVersao telaAtualizarVersao = new TelaAtualizarVersao(null, true);
                ControllerAtualizarVersao controllerAtualizarVersao = new ControllerAtualizarVersao(telaAtualizarVersao);
                int id = Integer.parseInt(this.telaPesquisaVersao.getjTableVersao().getValueAt(row, 0).toString());
                Versao versao = ServiceVersao.Search(id);
                telaAtualizarVersao.getjTextFieldId().setText(""+id);
                telaAtualizarVersao.getjTextFieldVersao().setText(versao.getVersao());
                telaAtualizarVersao.getjTextFieldTipo().setText(versao.getTipo());
                telaAtualizarVersao.getjTextFieldCategoria().setText(versao.getCategoria());
                telaAtualizarVersao.getjTextFieldMotor().setText(""+versao.getMotor());
                PreencherModelos(telaAtualizarVersao);
                telaAtualizarVersao.getjComboBoxModelo().setSelectedItem(versao.getModelo().getModel());
                telaAtualizarVersao.setLocationRelativeTo(null);
                telaAtualizarVersao.setVisible(true);
            }
            PreencherTabela();
        }
    }

    private void PreencherTabela() {
        LimparTabela();
        DefaultTableModel table = (DefaultTableModel) this.telaPesquisaVersao.getjTableVersao().getModel();
        ArrayList<Versao> listVersao;
        listVersao = (ArrayList) ServiceVersao.Search();
        listVersao.forEach((versaoAtual) -> {
            table.addRow(new Object[]{versaoAtual.getId(), versaoAtual.getModelo().getModel(),
                versaoAtual.getModelo().getMarca().getName(), versaoAtual.getVersao(), versaoAtual.getTipo(),
                versaoAtual.getCategoria(), versaoAtual.getMotor()

            });
        });
    }

    private void LimparTabela() {
        DefaultTableModel table = (DefaultTableModel) this.telaPesquisaVersao.getjTableVersao().getModel();
        table.setRowCount(0);
    }

    private void AplicarFiltro() {
        TableRowSorter<DefaultTableModel> filter = new TableRowSorter<>(
                (DefaultTableModel) this.telaPesquisaVersao.getjTableVersao().getModel()
        );
        filter.setRowFilter(RowFilter.regexFilter(this.telaPesquisaVersao.getjTextFieldFilter().getText()));
        this.telaPesquisaVersao.getjTableVersao().setRowSorter(filter);
    }

    private void PreencherModelos(TelaAtualizarVersao tela) {
        tela.getjComboBoxModelo().removeAllItems();
        ArrayList<Modelo> listModelos = (ArrayList<Modelo>) ServiceModelo.Search();
        listModelos.forEach((Modelo modelo) -> {
            tela.getjComboBoxModelo().addItem(modelo.getModel());
        });
    }
}
