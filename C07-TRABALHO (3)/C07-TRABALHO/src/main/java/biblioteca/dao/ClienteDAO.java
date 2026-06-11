package biblioteca.dao;

import biblioteca.conexao.ConnectionDAO;
import biblioteca.model.Cliente;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO extends ConnectionDAO {

    public boolean insertCliente(Cliente cliente) {
        connectToDb();
        String sql = "INSERT INTO cliente (nome, cpf, email, data_nascimento) VALUES (?, ?, ?, ?);";
        try {
            pst = connection.prepareStatement(sql);
            pst.setString(1, cliente.getNome());
            pst.setString(2, cliente.getCpf());
            pst.setString(3, cliente.getEmail());
            pst.setDate(4, cliente.getDataNascimento());
            pst.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao inserir cliente: " + e.getMessage());
            return false;
        } finally {
            fecharRecursos();
        }
    }

    public List<Cliente> selectAllClientes() {
        connectToDb();
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM cliente;";
        try {
            st = connection.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                Cliente c = new Cliente();
                c.setIdCliente(rs.getInt("id_cliente"));
                c.setNome(rs.getString("nome"));
                c.setCpf(rs.getString("cpf"));
                c.setEmail(rs.getString("email"));
                c.setDataNascimento(rs.getDate("data_nascimento"));
                c.setAtivo(rs.getBoolean("ativo"));
                clientes.add(c);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar clientes: " + e.getMessage());
        } finally {
            fecharRecursos();
        }
        return clientes;
    }

    public Cliente selectClientePorCpf(String cpf) {
        connectToDb();
        Cliente c = null;
        String sql = "SELECT * FROM cliente WHERE cpf = ?;";
        try {
            pst = connection.prepareStatement(sql);
            pst.setString(1, cpf);
            rs = pst.executeQuery();
            if (rs.next()) {
                c = new Cliente();
                c.setIdCliente(rs.getInt("id_cliente"));
                c.setNome(rs.getString("nome"));
                c.setCpf(rs.getString("cpf"));
                c.setEmail(rs.getString("email"));
                c.setDataNascimento(rs.getDate("data_nascimento"));
                c.setAtivo(rs.getBoolean("ativo"));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar cliente por CPF: " + e.getMessage());
        } finally {
            fecharRecursos();
        }
        return c;
    }

    public boolean updateCliente(Cliente cliente) {
        connectToDb();
        String sql = "UPDATE cliente SET nome=?, cpf=?, email=?, data_nascimento=? WHERE id_cliente=?;";
        try {
            pst = connection.prepareStatement(sql);
            pst.setString(1, cliente.getNome());
            pst.setString(2, cliente.getCpf());
            pst.setString(3, cliente.getEmail());
            pst.setDate(4, cliente.getDataNascimento());
            pst.setInt(5, cliente.getIdCliente());
            pst.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar cliente: " + e.getMessage());
            return false;
        } finally {
            fecharRecursos();
        }
    }

    public boolean deleteCliente(int idCliente) {
        connectToDb();
        String sql = "DELETE FROM cliente WHERE id_cliente = ?;";
        try {
            pst = connection.prepareStatement(sql);
            pst.setInt(1, idCliente);
            pst.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao deletar cliente: " + e.getMessage());
            return false;
        } finally {
            fecharRecursos();
        }
    }
}
