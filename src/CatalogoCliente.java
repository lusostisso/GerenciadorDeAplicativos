import javax.swing.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class CatalogoCliente {
    private List<Cliente> clientes;

    public CatalogoCliente() {
        clientes = new ArrayList<>();
    }

    public void cadastra(Cliente p) {
        clientes.add(p);
    }

    public Cliente getProdutoNaLinha(int linha) {
        if (linha >= clientes.size()) {
            return null;
        }
        return clientes.get(linha);
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public int indexOf (Cliente cliente){
        int i=0;
        for (Cliente c: clientes){
            if (c==cliente){
                return i;
            }
            i++;
        }
        return -1;
    }

    public int getQtdade() {
        return clientes.size();
    }

    public void setNome (int linha, String nome) {
        getProdutoNaLinha(linha).setNome(nome);
    }

    public void setEmail (int linha, String nome) {
        getProdutoNaLinha(linha).setEmail(nome);
    }

    public Cliente getCliente(String cpf) {
        return clientes.stream()
                .filter(c -> c.getCpf().equals(cpf))
                .findFirst()
                .orElse(null);
    }
    public void loadDataFromFile() {
        Path appsFilePath = Paths.get("clientes.dat");
        try (Stream<String> appsStream = Files.lines(appsFilePath)) {
            appsStream.forEach(str -> clientes.add(Cliente.fromLineFile(str)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveDataToFile() {
        Path appsFilePath = Paths.get("clientes.dat");
        
        try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(appsFilePath, StandardCharsets.UTF_8))) {
            for (Cliente cliente : clientes) {
                String line = cliente.toLineFile();
                if (!line.isEmpty()) {
                    writer.println(line);
                }
            }
            JOptionPane.showMessageDialog(null, "Dados(s) salvo(s)!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar clientes: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
