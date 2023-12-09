import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class Aplicativo {
    public enum SO { Android, IOS }; 
    private int codigo;
    private String nome;
    private double preco;
    private SO so;
    
    public Aplicativo(int codigo, String nome, double preco, Aplicativo.SO so) {
        this.codigo = codigo;
        this.nome = nome;
        this.preco = preco;
        this.so = so;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public SO getSo() {
        return so;
    }

    public String toLineFile(){
        return codigo+","+nome+","+preco+","+so;
    }

    public static Aplicativo fromLineFile(String line){
        String[] tokens = line.split(",");
        int codigo = Integer.parseInt(tokens[0]);
        String nome = tokens[1];
        double preco = Double.parseDouble(tokens[2]);
        Aplicativo.SO so = Aplicativo.SO.valueOf(Aplicativo.SO.class, tokens[3]);
        return new Aplicativo(codigo,nome,preco,so);
    }

    @Override
    public String toString() {
        return "codigo=" + codigo +
                ", nome='" + nome + '\'' +
                ", preco=" + preco +
                ", so=" + so;
    }
}
