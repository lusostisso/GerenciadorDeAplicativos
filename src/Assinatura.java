public class Assinatura {
    private StatusAssinatura statusAssinatura;
    private Aplicativo aplicativo;
    private static int aux = 1;
    private int codigo;
    private String data;

    public Assinatura(StatusAssinatura statusAssinatura, Aplicativo aplicativo, String data, String codigo) {
        this.statusAssinatura = statusAssinatura;
        this.aplicativo = aplicativo;
        this.codigo = Integer.parseInt(codigo);
        this.aux = Integer.parseInt(codigo);
        this.data = data;
    }

    public Assinatura(StatusAssinatura statusAssinatura, Aplicativo aplicativo, String data) {
        this.statusAssinatura = statusAssinatura;
        this.aplicativo = aplicativo;
        aux++;
        this.codigo = aux;
        this.data = data;
    }

    public StatusAssinatura getStatusAssinatura() {
        return statusAssinatura;
    }

    public void inverterStatus () {
        statusAssinatura = statusAssinatura.inverterStatus();
    }

    public Aplicativo getAplicativo() {
        return aplicativo;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getData() {
        return data;
    }

    public void setStatusAssinatura(StatusAssinatura statusAssinatura) {
        this.statusAssinatura = statusAssinatura;
    }

    public void setAplicativo(Aplicativo aplicativo) {
        this.aplicativo = aplicativo;
    }



    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Assinatura{" +
                "statusAssinatura=" + statusAssinatura +
                ", aplicativo=" + aplicativo +
                ", data='" + data + '\'' +
                '}';
    }
}
