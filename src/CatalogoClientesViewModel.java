import javax.swing.table.AbstractTableModel;

public class CatalogoClientesViewModel extends AbstractTableModel {
    private CatalogoCliente dados;
    private final String[] nomesDasColunas = {
            "Nome",
            "Cpf",
            "Email",
            "Assinaturas"
    };
    public CatalogoClientesViewModel(CatalogoCliente cliente){
        this.dados = cliente;
    }

    public String getColumnName(int col) {
        return nomesDasColunas[col];
    }

    public int getRowCount() {
        return dados.getQtdade();
    }

    public int getColumnCount() {
        return nomesDasColunas.length;
    }

    public Object getValueAt(int row, int col) {
        Cliente cliente = dados.getProdutoNaLinha(row);

        switch (col) {
            case 0 : return (Object)(cliente.getNome());
            case 1 : return (Object)(cliente.getCpf());
            case 2 : return (Object)(cliente.getEmail());
            case 3: return (Object)"Ver Assinaturas";
            default: return (Object)"none";
        }
    }

    public boolean isCellEditable(int row, int col)
    {
        if(col==0 || col==2){
            return true;
        }
        return false;
    }

    public void setValueAt(Object value, int row, int col) {
        // Não será acionado porque isCellEditable retorna false para todas as celulas
        // Se possível é necessário colocar o dado no catálogo de prdutos
        // Indicar a alteração acionando: fireTableCellUpdated(row, col);
        if (col==0){
            //nome
            dados.setNome(row, String.valueOf(value));
        }
        if(col==2){
            //email
            dados.setEmail(row, String.valueOf(value));
        }
        fireTableCellUpdated(row,col);
    }
}
