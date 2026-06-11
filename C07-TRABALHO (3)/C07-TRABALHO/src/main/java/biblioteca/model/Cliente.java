package biblioteca.model;
import java.sql.Date;

public class Cliente {
    private int idCliente; // PK
    private String nome;
    private String cpf;
    private String email;
    private Date dataNascimento;
    private boolean ativo;

    public Cliente() {}

    public Cliente(String nome, String cpf, String email, Date dataNascimento) {
        this.nome = nome; this.cpf = cpf; this.email = email; this.dataNascimento = dataNascimento;
    }

    public int getIdCliente(){return idCliente;}
    public void setIdCliente(int idCliente){ this.idCliente = idCliente;}
    public String getNome(){return nome;}
    public void setNome(String nome){this.nome = nome;}
    public String getCpf(){return cpf;}
    public void setCpf(String cpf){this.cpf = cpf;}
    public String getEmail(){return email;}
    public void setEmail(String email){this.email = email;}
    public Date getDataNascimento(){return dataNascimento;}
    public void setDataNascimento(Date dataNascimento){this.dataNascimento = dataNascimento;}
    public boolean isAtivo(){return ativo;}
    public void setAtivo(boolean ativo){ this.ativo = ativo;}

    @Override
    public String toString() {
        return "Cliente [ID=" + idCliente + ", Nome=" + nome + ", CPF=" + cpf + "]";
    }
}