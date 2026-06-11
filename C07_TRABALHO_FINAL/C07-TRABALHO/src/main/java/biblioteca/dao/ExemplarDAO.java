package biblioteca.dao;

import biblioteca.conexao.ConnectionDAO;
import biblioteca.model.Exemplar;
import biblioteca.model.Edicao;
import biblioteca.model.Livro;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExemplarDAO extends ConnectionDAO {

    public boolean insertExemplar(Exemplar exemplar) {
        connectToDb();
        String sql = "INSERT INTO exemplar (id_edicao, tombo, status_exemplar, data_aquisicao) VALUES (?, ?, ?, ?);";
        try {
            pst = connection.prepareStatement(sql);
            pst.setInt(1, exemplar.getIdEdicao());
            pst.setString(2, exemplar.getTombo());
            pst.setString(3, exemplar.getStatusExemplar());
            pst.setDate(4, exemplar.getDataAquisicao());
            pst.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao inserir exemplar: " + e.getMessage());
            return false;
        } finally {
            fecharRecursos();
        }
    }

    public List<Exemplar> selectAllExemplaresComLivro() {
        connectToDb();
        List<Exemplar> lista = new ArrayList<>();
        String sql = "SELECT ex.*, ed.editora, liv.titulo FROM exemplar ex " +
                "INNER JOIN edicao ed ON ex.id_edicao = ed.id_edicao " +
                "INNER JOIN livro liv ON ed.id_livro = liv.id_livro;";
        try {
            st = connection.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                Exemplar ex = new Exemplar();
                ex.setIdExemplar(rs.getInt("id_exemplar"));
                ex.setIdEdicao(rs.getInt("id_edicao"));
                ex.setTombo(rs.getString("tombo"));
                ex.setStatusExemplar(rs.getString("status_exemplar"));
                ex.setDataAquisicao(rs.getDate("data_aquisicao"));

                Edicao ed = new Edicao();
                ed.setEditora(rs.getString("editora"));

                Livro liv = new Livro();
                liv.setTitulo(rs.getString("titulo"));

                ed.setLivro(liv);
                ex.setEdicao(ed);

                lista.add(ex);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar exemplares com livro: " + e.getMessage());
        } finally {
            fecharRecursos();
        }
        return lista;
    }

    public List<Exemplar> selectDetalhesExemplaresPorLivro(int idLivro) {
        connectToDb();
        List<Exemplar> lista = new ArrayList<>();
        String sql = "SELECT ex.*, ed.numero_edicao, ed.editora FROM exemplar ex " +
                "INNER JOIN edicao ed ON ex.id_edicao = ed.id_edicao " +
                "WHERE ed.id_livro = ?;";
        try {
            pst = connection.prepareStatement(sql);
            pst.setInt(1, idLivro);
            rs = pst.executeQuery();
            while (rs.next()) {
                Exemplar ex = new Exemplar();
                ex.setIdExemplar(rs.getInt("id_exemplar"));
                ex.setTombo(rs.getString("tombo"));
                ex.setStatusExemplar(rs.getString("status_exemplar"));

                Edicao ed = new Edicao();
                ed.setNumeroEdicao(rs.getInt("numero_edicao"));
                ed.setEditora(rs.getString("editora"));
                ex.setEdicao(ed);

                lista.add(ex);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar detalhes de exemplares: " + e.getMessage());
        } finally {
            fecharRecursos();
        }
        return lista;
    }

    public boolean updateStatus(int idExemplar, String novoStatus) {
        connectToDb();
        String sql = "UPDATE exemplar SET status_exemplar = ? WHERE id_exemplar = ?;";
        try {
            pst = connection.prepareStatement(sql);
            pst.setString(1, novoStatus);
            pst.setInt(2, idExemplar);
            pst.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao mudar status do exemplar: " + e.getMessage());
            return false;
        } finally {
            fecharRecursos();
        }
    }

    public boolean deleteExemplar(int idExemplar) {
        connectToDb();
        String sql = "DELETE FROM exemplar WHERE id_exemplar = ?;";
        try {
            pst = connection.prepareStatement(sql);
            pst.setInt(1, idExemplar);
            pst.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao deletar exemplar: " + e.getMessage());
            return false;
        } finally {
            fecharRecursos();
        }
    }
}