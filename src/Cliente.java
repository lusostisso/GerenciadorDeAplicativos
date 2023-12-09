
public class Cliente {
    private String email;
    private String nome;
    private String cpf;

    public Cliente(String nome, String cpf, String email) {
        this.email = email;
        this.nome = nome;
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String toLineFile(){
        return nome+","+cpf+","+email;
    }

    public static Cliente fromLineFile(String line){
        String[] tokens = line.split(",");
        String nome= tokens[0];
        String cpf = tokens[1];
        String email = tokens[2];
        return new Cliente(nome,cpf, email);
    }

    @Override
    public String toString() {
        return "nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", email='" + email + '\'';

    }
}
