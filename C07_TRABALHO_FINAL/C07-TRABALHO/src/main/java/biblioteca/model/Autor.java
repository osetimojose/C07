package biblioteca.model;

public class Autor {
    private int idAutor; //PK
    private String nome;
    private String nacionalidade;

    public Autor() {}

    public Autor(String nome, String nacionalidade) {
        this.nome = nome;
        this.nacionalidade = nacionalidade;
    }

    public int getIdAutor() { return idAutor; }
    public void setIdAutor(int idAutor) { this.idAutor = idAutor; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getNacionalidade() { return nacionalidade; }
    public void setNacionalidade(String nacionalidade) { this.nacionalidade = nacionalidade; }

    @Override
    public String toString() {
        return "Autor [ID=" + idAutor + ", Nome=" + nome + ", Nacionalidade=" + nacionalidade + "]";
    }
}