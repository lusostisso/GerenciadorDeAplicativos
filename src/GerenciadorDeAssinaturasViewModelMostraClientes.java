import javax.swing.table.AbstractTableModel;
import java.util.LinkedList;
import java.util.List;

public class GerenciadorDeAssinaturasViewModelMostraClientes extends AbstractTableModel {
    private List<AplicativosDeUmCliente> clientes;
    private Aplicativo appPagina;
    private final String[] nomesDasColunas = {
            "Nome",
            "Email",
            "CPF",
            "COD Assinatura"
    };

    public GerenciadorDeAssinaturasViewModelMostraClientes(List<AplicativosDeUmCliente> clientes, Aplicativo appPagina) {
        this.clientes = new LinkedList<>();
        for (AplicativosDeUmCliente appCli: clientes){
            if(appCli.getStatusAssinatura(appPagina).equals(StatusAssinatura.ATIVO.getStatus())){
                this.clientes.add(appCli);
            }
        }
        this.appPagina = appPagina;
    }

    public String getColumnName(int col) {
        return nomesDasColunas[col];
    }

    public int getRowCount() {
        return clientes.size();
    }

    public int getColumnCount() {
        return nomesDasColunas.length;
    }

    public Object getValueAt(int linha, int col) {
        AplicativosDeUmCliente cliente = clientes.get(linha);
        Assinatura ass = null;
        for (Assinatura assinatura: cliente.getAssinaturas()){
            if(assinatura.getAplicativo()==appPagina){
                ass = assinatura;
                break;
            }
        }
        switch (col) {
            case 0:
                return cliente.getNome();
            case 1:
                return cliente.getEmail();
            case 2:
                return cliente.getCPF();
            case 3:
                return String.valueOf(ass.getCodigo());
            default:
                return "none";
        }
    }

    public boolean isCellEditable(int row, int col) {
        return false;
    }

    public void setValueAt(Object value, int row, int col) {
        // Não será acionado porque isCellEditable retorna false para todas as células
    }
}
