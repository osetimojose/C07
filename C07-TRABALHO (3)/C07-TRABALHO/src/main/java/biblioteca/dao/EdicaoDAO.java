package biblioteca.dao;

import biblioteca.conexao.ConnectionDAO;
import biblioteca.model.Edicao;
import biblioteca.model.Livro;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EdicaoDAO extends ConnectionDAO {

    public boolean insertEdicao(Edicao edicao) {
        connectToDb();
        String sql = "INSERT INTO edicao (id_livro, numero_edicao, editora, preco_reposicao) VALUES (?, ?, ?, ?);";
        try {
            pst = connection.prepareStatement(sql);
            pst.setInt(1, edicao.getIdLivro());
            pst.setInt(2, edicao.getNumeroEdicao());
            pst.setString(3, edicao.getEditora());
            pst.setDouble(4, edicao.getPrecoReposicao());
            pst.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao inserir edição: " + e.getMessage());
            return false;
        } finally {
            fecharRecursos();
        }
    }

    public List<Edicao> selectAllEdicoes() {
        connectToDb();
        List<Edicao> edicoes = new ArrayList<>();
        String sql = "SELECT * FROM edicao;";
        try {
            st = connection.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                Edicao e = new Edicao();
                e.setIdEdicao(rs.getInt("id_edicao"));
                e.setIdLivro(rs.getInt("id_livro"));
                e.setNumeroEdicao(rs.getInt("numero_edicao"));
                e.setEditora(rs.getString("editora"));
                e.setPrecoReposicao(rs.getDouble("preco_reposicao"));
                edicoes.add(e);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar edições: " + e.getMessage());
        } finally {
            fecharRecursos();
        }
        return edicoes;
    }

    public List<Edicao> selectEdicoesPorLivro(int idLivro) {
        connectToDb();
        List<Edicao> edicoes = new ArrayList<>();
        String sql = "SELECT ed.*, l.titulo FROM edicao ed INNER JOIN livro l ON ed.id_livro = l.id_livro WHERE ed.id_livro = ?;";
        try {
            pst = connection.prepareStatement(sql);
            pst.setInt(1, idLivro);
            rs = pst.executeQuery();
            while (rs.next()) {
                Edicao e = new Edicao();
                e.setIdEdicao(rs.getInt("id_edicao"));
                e.setIdLivro(rs.getInt("id_livro"));
                e.setNumeroEdicao(rs.getInt("numero_edicao"));
                e.setEditora(rs.getString("editora"));
                e.setPrecoReposicao(rs.getDouble("preco_reposicao"));

                Livro l = new Livro();
                l.setTitulo(rs.getString("titulo"));
                e.setLivro(l);

                edicoes.add(e);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar edições por livro: " + e.getMessage());
        } finally {
            fecharRecursos();
        }
        return edicoes;
    }

    public List<Edicao> selectEdicoesPorEditora(String editora) {
        connectToDb();
        List<Edicao> edicoes = new ArrayList<>();
        String sql = "SELECT * FROM edicao WHERE editora LIKE ?;";
        try {
            pst = connection.prepareStatement(sql);
            pst.setString(1, "%" + editora + "%");
            rs = pst.executeQuery();
            while (rs.next()) {
                Edicao e = new Edicao();
                e.setIdEdicao(rs.getInt("id_edicao"));
                e.setIdLivro(rs.getInt("id_livro"));
                e.setNumeroEdicao(rs.getInt("numero_edicao"));
                e.setEditora(rs.getString("editora"));
                e.setPrecoReposicao(rs.getDouble("preco_reposicao"));
                edicoes.add(e);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar edições por editora: " + e.getMessage());
        } finally {
            fecharRecursos();
        }
        return edicoes;
    }

    public boolean updateEdicao(Edicao edicao) {
        connectToDb();
        String sql = "UPDATE edicao SET numero_edicao = ?, editora = ?, preco_reposicao = ? WHERE id_edicao = ?;";
        try {
            pst = connection.prepareStatement(sql);
            pst.setInt(1, edicao.getNumeroEdicao());
            pst.setString(2, edicao.getEditora());
            pst.setDouble(3, edicao.getPrecoReposicao());
            pst.setInt(4, edicao.getIdEdicao());
            pst.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar edição: " + e.getMessage());
            return false;
        } finally {
            fecharRecursos();
        }
    }

    public boolean deleteEdicao(int idEdicao) {
        connectToDb();
        String sql = "DELETE FROM edicao WHERE id_edicao = ?;";
        try {
            pst = connection.prepareStatement(sql);
            pst.setInt(1, idEdicao);
            pst.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao deletar edição: " + e.getMessage());
            return false;
        } finally {
            fecharRecursos();
        }
    }
}