package biblioteca.dao;

import biblioteca.conexao.ConnectionDAO;
import biblioteca.model.Autor;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AutorDAO extends ConnectionDAO {

    public boolean insertAutor(Autor autor) {
        connectToDb();
        String sql = "INSERT INTO autor (nome, nacionalidade) VALUES (?, ?);";
        try {
            pst = connection.prepareStatement(sql);
            pst.setString(1, autor.getNome());
            pst.setString(2, autor.getNacionalidade());
            pst.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao inserir autor: " + e.getMessage());
            return false;
        } finally {
            fecharRecursos();
        }
    }

    public List<Autor> selectAllAutores() {
        connectToDb();
        List<Autor> autores = new ArrayList<>();
        String sql = "SELECT * FROM autor;";
        try {
            st = connection.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                Autor a = new Autor();
                a.setIdAutor(rs.getInt("id_autor"));
                a.setNome(rs.getString("nome"));
                a.setNacionalidade(rs.getString("nacionalidade"));
                autores.add(a);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar autores: " + e.getMessage());
        } finally {
            fecharRecursos();
        }
        return autores;
    }

    public List<Autor> selectAutorPorNome(String nome) {
        connectToDb();
        List<Autor> autores = new ArrayList<>();
        String sql = "SELECT * FROM autor WHERE nome LIKE ?;";
        try {
            pst = connection.prepareStatement(sql);
            pst.setString(1, "%" + nome + "%"); // Busca por aproximação
            rs = pst.executeQuery();
            while (rs.next()) {
                Autor a = new Autor();
                a.setIdAutor(rs.getInt("id_autor"));
                a.setNome(rs.getString("nome"));
                a.setNacionalidade(rs.getString("nacionalidade"));
                autores.add(a);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar autor por nome: " + e.getMessage());
        } finally {
            fecharRecursos();
        }
        return autores;
    }

    public boolean updateAutor(Autor autor) {
        connectToDb();
        String sql = "UPDATE autor SET nome = ?, nacionalidade = ? WHERE id_autor = ?;";
        try {
            pst = connection.prepareStatement(sql);
            pst.setString(1, autor.getNome());
            pst.setString(2, autor.getNacionalidade());
            pst.setInt(3, autor.getIdAutor());
            pst.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar autor: " + e.getMessage());
            return false;
        } finally {
            fecharRecursos();
        }
    }

    public boolean deleteAutor(int idAutor) {
        connectToDb();
        String sql = "DELETE FROM autor WHERE id_autor = ?;";
        try {
            pst = connection.prepareStatement(sql);
            pst.setInt(1, idAutor);
            pst.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao deletar autor: " + e.getMessage());
            return false;
        } finally {
            fecharRecursos();
        }
    }
}