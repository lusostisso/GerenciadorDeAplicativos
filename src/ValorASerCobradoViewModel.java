import javax.swing.table.AbstractTableModel;
public class ValorASerCobradoViewModel extends AbstractTableModel {
    private GerenciadorDeAssinaturas assinaturasCobradas;
    private final String[] nomesDasColunas = {
            "Nome",
            "Email",
            "Valor Mensalidade"
    };
    public ValorASerCobradoViewModel(GerenciadorDeAssinaturas ga){
        this.assinaturasCobradas = new GerenciadorDeAssinaturas();
        for (AplicativosDeUmCliente app: ga.getAssinaturas()){
            if(app.getValorDevedor()>0){
                assinaturasCobradas.add(app);
            }
        }
    }

    public String getColumnName(int col) {
        return nomesDasColunas[col];
    }

    public int getRowCount() {
        return assinaturasCobradas.getSize();
    }

    public int getColumnCount() {
        return nomesDasColunas.length;
    }

    public Object getValueAt(int linha, int col) {
        AplicativosDeUmCliente aplicativo = assinaturasCobradas.getAplicativoDeUmClienteNaLinha(linha);
        switch (col) {
            case 0:
                return (Object) (aplicativo.getNome());
            case 1:
                return (Object) (aplicativo.getEmail());
            case 2:
                return (Object) (aplicativo.getValorDevedor());
            default:
                return (Object) "none";
        }
    }


    public boolean isCellEditable(int row, int col)
    {
        return false;
    }

    public void setValueAt(Object value, int row, int col) {
        // Não será acionado porque isCellEditable retorna false para todas as celulas
        // Se possível é necessário colocar o dado no catálogo de prdutos
        // Indicar a alteração acionando: fireTableCellUpdated(row, col);
    }
}
