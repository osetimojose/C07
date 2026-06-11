package biblioteca.dao;

import biblioteca.conexao.ConnectionDAO;
import biblioteca.model.Carteirinha;
import biblioteca.model.Cliente;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarteirinhaDAO extends ConnectionDAO {

    public boolean insertCarteirinha(Carteirinha carteirinha) {
        connectToDb();
        String sql = "INSERT INTO carteirinha (id_cliente, codigo, data_emissao, validade) VALUES (?, ?, ?, ?);";
        try {
            pst = connection.prepareStatement(sql);
            pst.setInt(1, carteirinha.getIdCliente());
            pst.setString(2, carteirinha.getCodigo());
            pst.setDate(3, carteirinha.getDataEmissao());
            pst.setDate(4, carteirinha.getValidade());
            pst.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao emitir carteirinha: " + e.getMessage());
            return false;
        } finally {
            fecharRecursos();
        }
    }

    public List<Carteirinha> selectAllCarteirinhasComCliente() {
        connectToDb();
        List<Carteirinha> lista = new ArrayList<>();
        String sql = "SELECT cart.*, cli.nome, cli.cpf FROM carteirinha cart " +
                "INNER JOIN cliente cli ON cart.id_cliente = cli.id_cliente;";
        try {
            st = connection.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                Carteirinha cart = new Carteirinha();
                cart.setIdCarteirinha(rs.getInt("id_carteirinha"));
                cart.setIdCliente(rs.getInt("id_cliente"));
                cart.setCodigo(rs.getString("codigo"));
                cart.setDataEmissao(rs.getDate("data_emissao"));
                cart.setValidade(rs.getDate("validade"));

                Cliente cli = new Cliente();
                cli.setIdCliente(rs.getInt("id_cliente"));
                cli.setNome(rs.getString("nome"));
                cli.setCpf(rs.getString("cpf"));

                cart.setCliente(cli);
                lista.add(cart);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar carteirinhas: " + e.getMessage());
        } finally {
            fecharRecursos();
        }
        return lista;
    }

    public boolean updateCarteirinha(Carteirinha carteirinha) {
        connectToDb();
        String sql = "UPDATE carteirinha SET codigo=?, data_emissao=?, validade=? WHERE id_carteirinha=?;";
        try {
            pst = connection.prepareStatement(sql);
            pst.setString(1, carteirinha.getCodigo());
            pst.setDate(2, carteirinha.getDataEmissao());
            pst.setDate(3, carteirinha.getValidade());
            pst.setInt(4, carteirinha.getIdCarteirinha());
            pst.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar carteirinha: " + e.getMessage());
            return false;
        } finally {
            fecharRecursos();
        }
    }

    public boolean deleteCarteirinha(int idCarteirinha) {
        connectToDb();
        String sql = "DELETE FROM carteirinha WHERE id_carteirinha = ?;";
        try {
            pst = connection.prepareStatement(sql);
            pst.setInt(1, idCarteirinha);
            pst.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao deletar carteirinha: " + e.getMessage());
            return false;
        } finally {
            fecharRecursos();
        }
    }
}