package biblioteca.model;

import java.sql.Date;

public class Exemplar {
    private int idExemplar; //PK
    private int idEdicao; // FK de Edicao(idEdicao)
    private String tombo;
    private String statusExemplar; // TEM QUE SER 'Disponivel', 'Emprestado', 'Reservado' OU 'Manutencao'
    private Date dataAquisicao;

    private Edicao edicao;

    public Exemplar() {}

    public Exemplar(int idEdicao, String tombo, String statusExemplar, Date dataAquisicao) {
        this.idEdicao = idEdicao;
        this.tombo = tombo;
        setStatusExemplar(statusExemplar); //SETTER VE SE É UMA DAS OPÇÕES DISPONIVEIS
        this.dataAquisicao = dataAquisicao;
    }

    public int getIdExemplar() { return idExemplar; }
    public void setIdExemplar(int idExemplar) { this.idExemplar = idExemplar; }
    public int getIdEdicao() { return idEdicao; }
    public void setIdEdicao(int idEdicao) { this.idEdicao = idEdicao; }
    public String getTombo() { return tombo; }
    public void setTombo(String tombo) { this.tombo = tombo; }
    public String getStatusExemplar() { return statusExemplar; }

    public void setStatusExemplar(String statusExemplar) {
        if (!statusExemplar.equals("Disponivel") && !statusExemplar.equals("Emprestado") &&
                !statusExemplar.equals("Reservado") && !statusExemplar.equals("Manutencao")) {
            throw new IllegalArgumentException("Status inválido para o exemplar.");
        }
        this.statusExemplar = statusExemplar;
    }

    public Date getDataAquisicao() { return dataAquisicao; }
    public void setDataAquisicao(Date dataAquisicao) { this.dataAquisicao = dataAquisicao; }
    public Edicao getEdicao() { return edicao; }
    public void setEdicao(Edicao edicao) { this.edicao = edicao; }

    @Override
    public String toString() {
        return "Exemplar [Tombo=" + tombo + ", Status=" + statusExemplar + "]";
    }
}
