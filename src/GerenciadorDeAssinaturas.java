import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class GerenciadorDeAssinaturas{
    private List<AplicativosDeUmCliente> assinaturas;
    public GerenciadorDeAssinaturas() {
        this.assinaturas = new LinkedList<>();
    }

    public void saveDataToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("assinaturas.dat"))) {
            for (AplicativosDeUmCliente assinatura : assinaturas) {
                Cliente cliente = assinatura.getProprietario();
                writer.write(cliente.getCpf() + ":");
                for (Assinatura ass: assinatura.getAssinaturas()){
                    writer.write(ass.getAplicativo().getCodigo() + "," + ass.getStatusAssinatura().getStatus() +"," + ass.getData() + "," + ass.getCodigo() +",");
                }
                writer.write("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void loadDataFromFile(CatalogoCliente cc, CatalogoAplicativos ca) {
        try (BufferedReader reader = new BufferedReader(new FileReader("assinaturas.dat"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    try {
                        Cliente c = cc.getCliente(parts[0]);
                        String[] appStatusArray = parts[1].split(",");
                        List <Assinatura> listaAssinaturas = new LinkedList<>();
                        for (int i = 0; i < appStatusArray.length; i += 4) {
                            Aplicativo app = ca.getAplicativo(Integer.parseInt(appStatusArray[i]));
                            StatusAssinatura status = StatusAssinatura.fromString(appStatusArray[i + 1]);
                            String data = appStatusArray[i+2];
                            String cod = appStatusArray[i+3];
                            listaAssinaturas.add(new Assinatura(status, app, data, cod));
                        }
                        assinaturas.add(new AplicativosDeUmCliente(c,listaAssinaturas));
                    } catch (Exception e) {
                        System.out.println("Erro ao processar a linha: " + line);
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void add (Cliente cliente, Assinatura assinatura){
        List <Assinatura> ass = new LinkedList<>();
        ass.add(assinatura);
        assinaturas.add(new AplicativosDeUmCliente(cliente, ass));
    }

    public void add (AplicativosDeUmCliente aplicativosDeUmCliente){
        assinaturas.add(aplicativosDeUmCliente);
    }
    public List<AplicativosDeUmCliente> getClientesDoAplicativo(Aplicativo app) {
        return assinaturas.stream()
                .filter(assinatura -> assinatura.containsApp(app))
                .collect(Collectors.toList());
    }
    public AplicativosDeUmCliente getAplicativoDeUmClienteEspecifico(Cliente cliente) {
        return assinaturas.stream()
                .filter(prop -> prop.getCPF().equals(cliente.getCpf()))
                .findFirst()
                .orElse(null);
    }

    public void trocaStatusDeUmAplicativoDeUmCliente (Cliente cliente, Aplicativo aplicativo) {
        getAplicativoDeUmClienteEspecifico(cliente).trocaStatusAssinatura(aplicativo);
    }

    public LinkedList <Aplicativo> getListaDeAplicativosDeUmClienteEspecifico (Cliente cliente){
        return new LinkedList(getAplicativoDeUmClienteEspecifico(cliente).getAplicativos());
    }

    public AplicativosDeUmCliente getAplicativoDeUmClienteNaLinha(int linha){
        return assinaturas.get(linha);
    }
    public int getSize(){
        return assinaturas.size();
    }

    public List<AplicativosDeUmCliente> getAssinaturas() {
        return assinaturas;
    }
    public double getFaturamentoAplicativosIOS() {
        return assinaturas.stream()
                .mapToDouble(AplicativosDeUmCliente::getValorDevedorPorIOS)
                .sum();
    }
    public double getFaturamentoAplicativoEspecifico(Aplicativo app) {
        return assinaturas.stream()
                .filter(prop -> prop.possuiAssinaturaDoAplicativoEspecifico(app))
                .mapToDouble(assinatura -> app.getPreco())
                .sum();
    }

    public double getFaturamentoAplicativosAndroid() {
        return assinaturas.stream()
                .mapToDouble(AplicativosDeUmCliente::getValorDevedorPorAndroid)
                .sum();
    }

    @Override
    public String toString() {
        return "Assinaturas: " + assinaturas.toString();
    }
}



