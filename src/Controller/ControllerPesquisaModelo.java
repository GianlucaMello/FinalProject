package Controller;
import Model.bo.Marca;
import Model.bo.Modelo;
import Service.ServiceMarca;
import Service.ServiceModelo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import view.TelaAtualizarModelo;
import view.TelaPesquisaModelo;

public class ControllerPesquisaModelo implements ActionListener {

    private final TelaPesquisaModelo telaPesquisaModelo;

    public ControllerPesquisaModelo(TelaPesquisaModelo telaPesquisaModelo) {
        this.telaPesquisaModelo = telaPesquisaModelo;
        this.telaPesquisaModelo.getjButtonApplyFilter().addActionListener(this);
        this.telaPesquisaModelo.getjButtonClean().addActionListener(this);
        this.telaPesquisaModelo.getjButtonClose().addActionListener(this);
        this.telaPesquisaModelo.getjButtonDelete().addActionListener(this);
        this.telaPesquisaModelo.getjButtonEdit().addActionListener(this);
        PreencherTabela();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.telaPesquisaModelo.getjButtonApplyFilter()) {
            AplicarFiltro();
        } else if (e.getSource() == this.telaPesquisaModelo.getjButtonClean()) {
            this.telaPesquisaModelo.getjTextFieldFilter().setText("");
            AplicarFiltro();
        } else if (e.getSource() == this.telaPesquisaModelo.getjButtonClose()) {
            this.telaPesquisaModelo.dispose();
        } else if (e.getSource() == this.telaPesquisaModelo.getjButtonDelete()) {
            int row = this.telaPesquisaModelo.getjTableModel().getSelectedRow();
            if (row < 0) {
                JFrame aviso = new JFrame("Aviso");
                JOptionPane.showMessageDialog(aviso, "Selecione uma marca para excluir", "Aviso!",
                        JOptionPane.WARNING_MESSAGE);
            } else {
                int idModelo = (int) this.telaPesquisaModelo.getjTableModel().getValueAt(row, 0);
                ServiceModelo.Delete(idModelo);
                JOptionPane.showMessageDialog(null, "Modelo Excluído", "Ok!",
                        JOptionPane.INFORMATION_MESSAGE);
                PreencherTabela();
            }
        } else if (e.getSource() == this.telaPesquisaModelo.getjButtonEdit()) {
            int row = this.telaPesquisaModelo.getjTableModel().getSelectedRow();
            if (row < 0) {
                JFrame aviso = new JFrame("Aviso");
                JOptionPane.showMessageDialog(aviso, "Selecione uma linha para editar.", "Aviso!",
                        JOptionPane.WARNING_MESSAGE);
            } else {
                TelaAtualizarModelo telaAtualizarModelo = new TelaAtualizarModelo(null, true);
                ControllerAtualizarModelo controllerAtualizarModelo = new ControllerAtualizarModelo(telaAtualizarModelo);
                telaAtualizarModelo.getjTextFieldId().setText((this.telaPesquisaModelo.getjTableModel().getValueAt(row, 0)).toString());
                telaAtualizarModelo.getjTextFieldModel().setText((String) this.telaPesquisaModelo.getjTableModel().getValueAt(row, 1));
                PreencherMarcas(telaAtualizarModelo);
                telaAtualizarModelo.getjComboBoxMarca().setSelectedItem(this.telaPesquisaModelo.getjTableModel().getValueAt(row, 2));
                telaAtualizarModelo.setLocationRelativeTo(null);
                telaAtualizarModelo.setVisible(true);
            }
        PreencherTabela();
        }
        
    }

    private void PreencherTabela() {
        LimparTabela();
        DefaultTableModel table = (DefaultTableModel) this.telaPesquisaModelo.getjTableModel().getModel();
        ArrayList<Modelo> listModelo = (ArrayList)ServiceModelo.Search();
        listModelo.forEach((modeloAtual) -> {
            table.addRow(new Object[]{ modeloAtual.getId(), modeloAtual.getModel(), modeloAtual.getMarca().getName()
                
            });
        });

    }

    private void LimparTabela() {
        DefaultTableModel table = (DefaultTableModel) this.telaPesquisaModelo.getjTableModel().getModel();
        table.setRowCount(0);
    }

    private void AplicarFiltro() {
        TableRowSorter<DefaultTableModel> filter = new TableRowSorter<>(
                (DefaultTableModel) this.telaPesquisaModelo.getjTableModel().getModel()
        );
        filter.setRowFilter(RowFilter.regexFilter(this.telaPesquisaModelo.getjTextFieldFilter().getText()));
        this.telaPesquisaModelo.getjTableModel().setRowSorter(filter);
    }

    private void PreencherMarcas(TelaAtualizarModelo tela) {
        tela.getjComboBoxMarca().removeAllItems();
        ArrayList<Marca> listMarcas = (ArrayList<Marca>) ServiceMarca.Search();
        listMarcas.forEach((Marca marca) -> {
            tela.getjComboBoxMarca().addItem(marca.getName());
        });
    }

}
