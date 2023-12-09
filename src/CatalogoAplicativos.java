import javax.swing.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

public class CatalogoAplicativos {
    private List<Aplicativo> aplicativos;

    public CatalogoAplicativos() {
        aplicativos = new ArrayList<>();
    }

    public void cadastra(Aplicativo p) {
        aplicativos.add(p);
    }

    public Aplicativo getProdutoNaLinha(int linha) {
        if (linha >= aplicativos.size()) {
            return null;
        }
        return aplicativos.get(linha);
    }

    public List<Aplicativo> getAplicativos() {
        return aplicativos;
    }

    public int indexOf (Aplicativo app){
        int i=0;
        for (Aplicativo a: aplicativos){
            if (a==app){
                return i;
            }
            i++;
        }
        return -1;
    }
    public Aplicativo getAplicativo(int codigo) {
        return aplicativos.stream()
                .filter(app -> app.getCodigo() == codigo)
                .findFirst()
                .orElse(null);
    }

    public int getQtdade() {
        return aplicativos.size();
    }

    public void setNovoPreco (int linha, double preco){
        getProdutoNaLinha(linha).setPreco(preco);
    }

    public void setNovoNome (int linha, String nome){
        getProdutoNaLinha(linha).setNome(nome);
    }
    public void loadDataFromFile() {
        Path appsFilePath = Paths.get("apps.dat");
        try (Stream<String> appsStream = Files.lines(appsFilePath)) {
            appsStream.forEach(str -> aplicativos.add(Aplicativo.fromLineFile(str)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveDataToFile() {
        Path appsFilePath = Paths.get("apps.dat");
        try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(appsFilePath, StandardCharsets.UTF_8))) {
            for (Aplicativo app : aplicativos) {
                String line = app.toLineFile();
                if (!line.isEmpty()) {
                    writer.println(line);
                }
            }
            JOptionPane.showMessageDialog(null, "Aplicativo(s) salvo(s)!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar aplicativo: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
