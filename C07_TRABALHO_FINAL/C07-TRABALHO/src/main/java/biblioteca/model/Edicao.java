package biblioteca.model;

public class Edicao {
    private int idEdicao; //PK
    private int idLivro; // FK para Livro(idLivro)
    private int numeroEdicao; //TEM QUE SER MAIOR QUE ZERO
    private String editora;
    private double precoReposicao; //TEM QUE SER MAIOR QUE ZERO

    private Livro livro;

    public Edicao() {}

    public Edicao(int idLivro, int numeroEdicao, String editora, double precoReposicao) {
        this.idLivro = idLivro;
        setNumeroEdicao(numeroEdicao); //SETTER VERIFICA SE É MAIOR QUE 0
        this.editora = editora;
        setPrecoReposicao(precoReposicao); //SETTER VERIFICA SE É MAIOR QUE 0
    }

    public int getIdEdicao() { return idEdicao; }
    public void setIdEdicao(int idEdicao) { this.idEdicao = idEdicao; }
    public int getIdLivro() { return idLivro; }
    public void setIdLivro(int idLivro) { this.idLivro = idLivro; }
    public int getNumeroEdicao() { return numeroEdicao; }

    public void setNumeroEdicao(int numeroEdicao) {
        if (numeroEdicao <= 0) throw new IllegalArgumentException("O número da edição deve ser maior que zero.");
        this.numeroEdicao = numeroEdicao;
    }

    public String getEditora() { return editora; }
    public void setEditora(String editora) { this.editora = editora; }
    public double getPrecoReposicao() { return precoReposicao; }

    public void setPrecoReposicao(double precoReposicao) {
        if (precoReposicao <= 0) throw new IllegalArgumentException("O preço de reposição deve ser maior que zero.");
        this.precoReposicao = precoReposicao;
    }

    public Livro getLivro() { return livro; }
    public void setLivro(Livro livro) { this.livro = livro; }

    @Override
    public String toString() {
        return "Edicao [ID=" + idEdicao + ", Editora=" + editora + ", N.=" + numeroEdicao + ", Preço=" + precoReposicao + "]";
    }
}
