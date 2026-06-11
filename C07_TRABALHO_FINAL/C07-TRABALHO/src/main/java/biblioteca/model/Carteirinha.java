package biblioteca.model;

import java.sql.Date;

public class Carteirinha {
    private int idCarteirinha; //PK;
    private int idCliente; //FK Cliente(id)
    private String codigo;
    private Date dataEmissao;
    private Date validade; //VALIDADE TEM QUE SER MAIOR QUE DATAEMISSÃO
    private Cliente cliente;

    public Carteirinha() {}

    public Carteirinha(int idCliente, String codigo, Date dataEmissao, Date validade) {
        this.idCliente = idCliente;
        this.codigo = codigo;
        this.dataEmissao = dataEmissao;
        this.validade = validade;
    }

    public int getIdCarteirinha() {
        return idCarteirinha;
    }

    public void setIdCarteirinha(int idCarteirinha) {
        this.idCarteirinha = idCarteirinha;
    }

    public int getIdCliente() {
        return idCliente;
    }
    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }
    public String getCodigo() {
        return codigo;
    }
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    public Date getDataEmissao() {
        return dataEmissao;
    }
    public void setDataEmissao(Date dataEmissao) {
        this.dataEmissao = dataEmissao;
    }
    public Date getValidade() {
        return validade;
    }
    public void setValidade(Date validade) {
        this.validade = validade;
    }
    public Cliente getCliente() {
        return cliente;
    }
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public String toString() {
        return "Carteirinha [ID=" + idCarteirinha +
                ", ID Cliente=" + idCliente +
                ", Código='" + codigo + '\'' +
                ", Emissão=" + dataEmissao +
                ", Validade=" + validade + "]";
    }
}
