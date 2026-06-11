package biblioteca.model;

import java.util.ArrayList;
import java.util.List;

public class Livro {
    private int idLivro; //PK
    private String titulo;
    private String isbn;
    private String sinopse;
    private int anoPublicacao; //TEM QUE SER MAIOR QUE ZERO (FAZER ISSO NA MAIIN)

    private List<Autor> autores = new ArrayList<>();
    private List<Categoria> categorias = new ArrayList<>();

    public Livro() {}

    public Livro(String titulo, String isbn, String sinopse, int anoPublicacao) {
        this.titulo = titulo;
        this.isbn = isbn;
        this.sinopse = sinopse;
        setAnoPublicacao(anoPublicacao);
    }

    public int getIdLivro(){
        return idLivro;
    }
    public void setIdLivro(int idLivro) {
        this.idLivro = idLivro;
    }
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public String getIsbn() {
        return isbn;
    }
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    public String getSinopse() {
        return sinopse;
    }
    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }
    public int getAnoPublicacao() {
        return anoPublicacao;
    }
    public void setAnoPublicacao(int anoPublicacao) {
        this.anoPublicacao = anoPublicacao;
    }

    public List<Autor> getAutores() {
        return autores;
    }

    public void setAutores(List<Autor> autores) {
        this.autores = autores;
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }

    public void adicionarAutor(Autor autor) {
        this.autores.add(autor);
    }

    public void adicionarCategoria(Categoria categoria) {
        this.categorias.add(categoria);
    }

    @Override
    public String toString() {
        return "Livro [ID=" + idLivro + ", Titulo=" + titulo + ", ISBN=" + isbn + "]";
    }
}
