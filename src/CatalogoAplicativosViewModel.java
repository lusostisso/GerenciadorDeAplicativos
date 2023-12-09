import javax.swing.table.AbstractTableModel;

public class CatalogoAplicativosViewModel extends AbstractTableModel {
    private CatalogoAplicativos aplicativos;
    private final String[] nomesDasColunas = {
        "Codigo",
        "Nome",
        "Preco",
        "Sist. Operacional",
            "Assinantes"
    };

    public CatalogoAplicativosViewModel(CatalogoAplicativos aplicativos){
        this.aplicativos = aplicativos;
    }
    public String getColumnName(int col) {
        return nomesDasColunas[col];
    }

    public int getRowCount() { 
        return aplicativos.getQtdade(); 
    }

    public int getColumnCount() { 
        return nomesDasColunas.length;
    }

    public Object getValueAt(int row, int col) {
        Aplicativo app = aplicativos.getProdutoNaLinha(row);
        switch (col) {
            case 0 : return (Object)(app.getCodigo());
            case 1 : return (Object)(app.getNome());
            case 2 : return (Object)(app.getPreco());
            case 3 : return (Object)(app.getSo());
            case 4: return (Object) ("Ver Assinantes");
            default: return (Object)"none";
        }
    }

    public boolean isCellEditable(int row, int col)
        {
            if (col==1  || col == 2) return true;
            return false;
        }

    public void setValueAt(Object value, int row, int col) {
        // Não será acionado porque isCellEditable retorna false para todas as celulas
        // Se possível é necessário colocar o dado no catálogo de prdutos
        // Indicar a alteração acionando: fireTableCellUpdated(row, col);

       if (col==2){
           //preco
           aplicativos.setNovoPreco(row, Double.valueOf((String) value));
       }
       if(col==1){
           //nome
           aplicativos.setNovoNome(row, String.valueOf(value));
       }
        fireTableCellUpdated(row, col);
    }    
}
