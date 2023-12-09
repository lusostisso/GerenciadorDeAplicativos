import javax.swing.table.AbstractTableModel;

public class GerenciadorDeAssinaturasViewModelMostraApps extends AbstractTableModel {
    private AplicativosDeUmCliente assinaturas;
    private final String[] nomesDasColunas = {
            "COD ASS",
            "Nome APP",
            "Preco",
            "SO",
            "Data",
            "Estado",
            "Remover",
    };
    public GerenciadorDeAssinaturasViewModelMostraApps(AplicativosDeUmCliente ga){
        this.assinaturas = ga;
    }

    public String getColumnName(int col) {
        return nomesDasColunas[col];
    }

    public int getRowCount() {
        return assinaturas.getSize();
    }

    public int getColumnCount() {
        return nomesDasColunas.length;
    }

    public Object getValueAt(int linha, int col) {

        Assinatura ass = assinaturas.getAssinaturaLinha(linha);
        switch (col) {
            case 0:
                return (Object) (ass.getCodigo());
            case 1:
                return (Object) (ass.getAplicativo().getNome());
            case 2:
                return (Object) (ass.getAplicativo().getPreco());
            case 3:
                return (Object) (ass.getAplicativo().getSo());
            case 4:
                return (Object) (ass.getData());
            case 5:
                return (Object)(ass.getStatusAssinatura());
            case 6:
                if(ass.getStatusAssinatura().getStatus().equals("ativo")){
                    return (Object) ("Cancelar Assinatura");
                } else {
                    return (Object) ("Ativar Assinatura");
                }
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
