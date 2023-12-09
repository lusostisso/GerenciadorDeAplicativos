import java.util.*;
import java.util.stream.Collectors;


public class AplicativosDeUmCliente {
    private List<Assinatura> assinaturas;
    private Cliente proprietario;

    public AplicativosDeUmCliente (Cliente proprietario, List <Assinatura> assinaturas){
        this.assinaturas = assinaturas;
        this.proprietario = proprietario;
    }

    public String getStatusAssinatura(Aplicativo app) {
        return assinaturas.stream()
                .filter(ass -> app.equals(ass.getAplicativo()))
                .map(ass -> ass.getStatusAssinatura().getStatus())
                .findFirst()
                .orElse(null);
    }

    public void trocaStatusAssinatura(Aplicativo app) {
        assinaturas.stream()
                .filter(ass -> app.equals(ass.getAplicativo()))
                .forEach(Assinatura::inverterStatus);
    }


    public void add (Assinatura assinatura) {
        assinaturas.add(assinatura);
    }

    public boolean containsApp(Aplicativo app) {
        return assinaturas.stream()
                .anyMatch(ass -> ass.getAplicativo().equals(app));
    }


    public int getSize(){
        return assinaturas.size();
    }

    public String getCPF() {
        return proprietario.getCpf();
    }

    public String getNome(){
        return proprietario.getNome();
    }

    public double getValorDevedor() {
        return assinaturas.stream()
                .filter(a -> a.getStatusAssinatura() == StatusAssinatura.ATIVO)
                .mapToDouble(a -> a.getAplicativo().getPreco())
                .sum();
    }

    public double getValorDevedorPorIOS() {
        return assinaturas.stream()
                .filter(a -> a.getAplicativo().getSo() == Aplicativo.SO.IOS)
                .filter(a -> a.getStatusAssinatura() == StatusAssinatura.ATIVO)
                .mapToDouble(a -> a.getAplicativo().getPreco())
                .sum();
    }

    public double getValorDevedorPorAndroid() {
        return assinaturas.stream()
                .filter(a -> a.getAplicativo().getSo() == Aplicativo.SO.Android && a.getStatusAssinatura() == StatusAssinatura.ATIVO)
                .mapToDouble(a -> a.getAplicativo().getPreco())
                .sum();
    }

    public String getEmail(){
        return proprietario.getEmail();
    }


    public Assinatura getAssinaturaLinha (int linha) {
        return assinaturas.get(linha);
    }


    public List<Aplicativo> getAplicativos() {
        return assinaturas.stream()
                .map(Assinatura::getAplicativo)
                .collect(Collectors.toList());
    }


    public boolean possuiAssinaturaDoAplicativoEspecifico(Aplicativo aplicativo) {
        return assinaturas.stream()
                .anyMatch(a -> a.getAplicativo().equals(aplicativo) && a.getStatusAssinatura() == StatusAssinatura.ATIVO);
    }


    public List<Assinatura> getAssinaturas() {
        return assinaturas;
    }

    public Cliente getProprietario() {
        return proprietario;
    }

    @Override
    public String toString() {
        return "assinaturas=" + assinaturas +
                ", proprietario=" + proprietario;
    }
}
