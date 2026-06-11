package biblioteca.model;

import java.sql.Date;
import java.sql.Timestamp;

public class Emprestimo {
    private int idEmprestimo; //PK
    private int idCliente; //FK de Cliente(idCliente)
    private int idExemplar; //FK de Exemplar(idExemplar)
    private Timestamp dataEmprestimo;
    private Date dataPrevistaDevolucao;
    private Date dataDevolucao;
    private double multa; //TEM QUESER MAIOR QUE 0

    private Cliente cliente;
    private Exemplar exemplar;

    public Emprestimo() {}

    public Emprestimo(int idCliente, int idExemplar, Date dataPrevistaDevolucao) {
        this.idCliente = idCliente;
        this.idExemplar = idExemplar;
        this.dataPrevistaDevolucao = dataPrevistaDevolucao;
    }

    public int getIdEmprestimo() { return idEmprestimo; }
    public void setIdEmprestimo(int idEmprestimo) { this.idEmprestimo = idEmprestimo; }
    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }
    public int getIdExemplar() { return idExemplar; }
    public void setIdExemplar(int idExemplar) { this.idExemplar = idExemplar; }
    public Timestamp getDataEmprestimo() { return dataEmprestimo; }
    public void setDataEmprestimo(Timestamp dataEmprestimo) { this.dataEmprestimo = dataEmprestimo; }
    public Date getDataPrevistaDevolucao() { return dataPrevistaDevolucao; }
    public void setDataPrevistaDevolucao(Date dataPrevistaDevolucao) { this.dataPrevistaDevolucao = dataPrevistaDevolucao; }
    public Date getDataDevolucao() { return dataDevolucao; }
    public void setDataDevolucao(Date dataDevolucao) { this.dataDevolucao = dataDevolucao; }
    public double getMulta() { return multa; }

    public void setMulta(double multa) {
        if (multa < 0) throw new IllegalArgumentException("A multa não pode ser negativa.");
        this.multa = multa;
    }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    public Exemplar getExemplar() { return exemplar; }
    public void setExemplar(Exemplar exemplar) { this.exemplar = exemplar; }

    @Override
    public String toString() {
        return "Emprestimo [ID=" + idEmprestimo + ", Cliente ID=" + idCliente +
                ", Tombo=" + (exemplar != null ? exemplar.getTombo() : idExemplar) +
                ", Multa=R$ " + multa + "]";
    }
}
