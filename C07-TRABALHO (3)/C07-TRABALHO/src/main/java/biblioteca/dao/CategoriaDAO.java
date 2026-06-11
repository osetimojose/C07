package biblioteca.dao;

import biblioteca.conexao.ConnectionDAO;
import biblioteca.model.Categoria;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO extends ConnectionDAO {

    public boolean insertCategoria(Categoria categoria) {
        connectToDb();
        String sql = "INSERT INTO categoria (nome, descricao) VALUES (?, ?);";
        try {
            pst = connection.prepareStatement(sql);
            pst.setString(1, categoria.getNome());
            pst.setString(2, categoria.getDescricao());
            pst.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao inserir categoria: " + e.getMessage());
            return false;
        } finally {
            fecharRecursos();
        }
    }

    public List<Categoria> selectAllCategorias() {
        connectToDb();
        List<Categoria> categorias = new ArrayList<>();
        String sql = "SELECT * FROM categoria;";
        try {
            st = connection.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                Categoria c = new Categoria();
                c.setIdCategoria(rs.getInt("id_categoria"));
                c.setNome(rs.getString("nome"));
                c.setDescricao(rs.getString("descricao"));
                categorias.add(c);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar categorias: " + e.getMessage());
        } finally {
            fecharRecursos();
        }
        return categorias;
    }

    public Categoria selectCategoriaPorNome(String nome) {
        connectToDb();
        Categoria c = null;
        String sql = "SELECT * FROM categoria WHERE nome = ?;";
        try {
            pst = connection.prepareStatement(sql);
            pst.setString(1, nome);
            rs = pst.executeQuery();
            if (rs.next()) {
                c = new Categoria();
                c.setIdCategoria(rs.getInt("id_categoria"));
                c.setNome(rs.getString("nome"));
                c.setDescricao(rs.getString("descricao"));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar categoria: " + e.getMessage());
        } finally {
            fecharRecursos();
        }
        return c;
    }


    public boolean updateCategoria(Categoria categoria) {
        connectToDb();
        String sql = "UPDATE categoria SET nome = ?, descricao = ? WHERE id_categoria = ?;";
        try {
            pst = connection.prepareStatement(sql);
            pst.setString(1, categoria.getNome());
            pst.setString(2, categoria.getDescricao());
            pst.setInt(3, categoria.getIdCategoria());
            pst.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar categoria: " + e.getMessage());
            return false;
        } finally {
            fecharRecursos();
        }
    }

    public boolean deleteCategoria(int idCategoria) {
        connectToDb();
        String sql = "DELETE FROM categoria WHERE id_categoria = ?;";
        try {
            pst = connection.prepareStatement(sql);
            pst.setInt(1, idCategoria);
            pst.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao deletar categoria: " + e.getMessage());
            return false;
        } finally {
            fecharRecursos();
        }
    }
}