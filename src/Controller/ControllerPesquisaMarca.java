package Controller;
import Service.ServiceMarca;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import view.TelaAtualizarMarca;
import view.TelaPesquisaMarca;

public final class ControllerPesquisaMarca implements ActionListener {

    private final TelaPesquisaMarca telaPesquisaMarca;

    public ControllerPesquisaMarca(TelaPesquisaMarca telaPesquisaMarca) {
        this.telaPesquisaMarca = telaPesquisaMarca;
        this.telaPesquisaMarca.getjButtonApplyFilter().addActionListener(this);
        this.telaPesquisaMarca.getjButtonClean().addActionListener(this);
        this.telaPesquisaMarca.getjButtonClose().addActionListener(this);
        this.telaPesquisaMarca.getjButtonDelete().addActionListener(this);
        this.telaPesquisaMarca.getjButtonEdit().addActionListener(this);
        PreencherTabela();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.telaPesquisaMarca.getjButtonApplyFilter()) {
            AplicarFiltro();
        } else if (e.getSource() == this.telaPesquisaMarca.getjButtonClean()) {
            this.telaPesquisaMarca.getjTextFieldFilter().setText("");
            AplicarFiltro();
        } else if (e.getSource() == this.telaPesquisaMarca.getjButtonClose()) {
            this.telaPesquisaMarca.dispose();
        } else if (e.getSource() == this.telaPesquisaMarca.getjButtonDelete()) {
            int row = this.telaPesquisaMarca.getjTableMarca().getSelectedRow();
            if (row < 0) {
                JFrame aviso = new JFrame("Aviso");
                JOptionPane.showMessageDialog(aviso, "Selecione uma marca para excluir", "Aviso!",
                        JOptionPane.WARNING_MESSAGE);
            } else {
                int idMarca = (int) this.telaPesquisaMarca.getjTableMarca().getValueAt(row, 0);
                ServiceMarca.Delete(idMarca);
                PreencherTabela();
                JOptionPane.showMessageDialog(null, "Marca excluída", "Ok!",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } else if (e.getSource() == this.telaPesquisaMarca.getjButtonEdit()) {
            int row = this.telaPesquisaMarca.getjTableMarca().getSelectedRow();
            if (row < 0) {
                JFrame aviso = new JFrame("Aviso");
                JOptionPane.showMessageDialog(aviso, "Selecione uma linha para editar.", "Aviso!",
                        JOptionPane.WARNING_MESSAGE);
            } else {
                TelaAtualizarMarca telaAtualizarMarca = new TelaAtualizarMarca(null, true);
                ControllerAtualizarMarca controllerAtualizarMarca = new ControllerAtualizarMarca(telaAtualizarMarca);
                int idMarca = (int) this.telaPesquisaMarca.getjTableMarca().getValueAt(row, 0);
                String nameMarca = (String) this.telaPesquisaMarca.getjTableMarca().getValueAt(row, 1);
                telaAtualizarMarca.getjTextFieldId().setText("" + idMarca);
                telaAtualizarMarca.getjTextFieldName().setText(nameMarca);
                telaAtualizarMarca.setLocationRelativeTo(null);
                telaAtualizarMarca.setVisible(true);
            }
            PreencherTabela();
        }
    }

    private void PreencherTabela() {
        LimparTabela();
        DefaultTableModel table = (DefaultTableModel) this.telaPesquisaMarca.getjTableMarca().getModel();
        ServiceMarca.Search().forEach((marcaAtual) -> {
            table.addRow(new Object[]{marcaAtual.getId(), marcaAtual.getName()});
        });
    }

    private void LimparTabela() {
        DefaultTableModel table = (DefaultTableModel) this.telaPesquisaMarca.getjTableMarca().getModel();
        table.setRowCount(0);
    }

    private void AplicarFiltro() {
        TableRowSorter<DefaultTableModel> filter = new TableRowSorter<>(
                (DefaultTableModel) this.telaPesquisaMarca.getjTableMarca().getModel()
        );
        filter.setRowFilter(RowFilter.regexFilter(this.telaPesquisaMarca.getjTextFieldFilter().getText()));
        this.telaPesquisaMarca.getjTableMarca().setRowSorter(filter);
    }

}
