package biblioteca.dao;

import biblioteca.conexao.ConnectionDAO;
import biblioteca.model.Livro;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LivroDAO extends ConnectionDAO {

    public boolean insertLivro(Livro livro) {

        connectToDb();
        String comando = "INSERT INTO livro (titulo, isbn, sinopse, ano_publicacao) VALUES (?, ?, ?, ?)";

        try {
            pst = connection.prepareStatement(comando);
            pst.setString(1, livro.getTitulo());
            pst.setString(2, livro.getIsbn());
            pst.setString(3, livro.getSinopse());
            pst.setInt(4, livro.getAnoPublicacao());
            pst.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao inserir esse livro: " + e.getMessage());
            return false;
        } finally {
            fecharRecursos();
        }
    }

    public boolean updateLivro(Livro livro) {

        connectToDb();
        String sql = "UPDATE livro SET titulo=?, isbn=?, sinopse=?, ano_publicacao=? WHERE id_livro=?;";

        try {
            pst = connection.prepareStatement(sql);
            pst.setString(1, livro.getTitulo());
            pst.setString(2, livro.getIsbn());
            pst.setString(3, livro.getSinopse());
            pst.setInt(4, livro.getAnoPublicacao());
            pst.setInt(5, livro.getIdLivro());
            pst.execute();
            return true;
        }
        catch (SQLException e){
            System.out.println("Erro ao atualizar Livro: " + e.getMessage());
            return false;
        }
        finally{
            fecharRecursos();
        }
    }
    public boolean deleteLivro(int idLivro) {

        connectToDb();
        String sql = "DELETE FROM livro WHERE id_livro = ?;";

        try {
            pst = connection.prepareStatement(sql);
            pst.setInt(1, idLivro);
            pst.execute();
            return true;
        }
        catch (SQLException e) {
            System.out.println("Erro ao deletar Livro: " + e.getMessage());
            return false;
        }
        finally{
            fecharRecursos();
        }
    }

    public boolean vincularAutor(int idLivro, int idAutor) {
        connectToDb();
        String sql = "INSERT INTO livro_autor (id_livro, id_autor) VALUES (?, ?);";
        try {
            pst = connection.prepareStatement(sql);
            pst.setInt(1, idLivro);
            pst.setInt(2, idAutor);
            pst.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao vincular autor ao livro: " + e.getMessage());
            return false;
        } finally {
            fecharRecursos();
        }
    }

    public boolean removerAutor(int idLivro, int idAutor) {
        connectToDb();
        String sql = "DELETE FROM livro_autor WHERE id_livro = ? AND id_autor = ?;";
        try {
            pst = connection.prepareStatement(sql);
            pst.setInt(1, idLivro);
            pst.setInt(2, idAutor);
            pst.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao remover autor do livro: " + e.getMessage());
            return false;
        } finally {
            fecharRecursos();
        }
    }

    public boolean vincularCategoria(int idLivro, int idCategoria) {
        connectToDb();
        String sql = "INSERT INTO livro_categoria (id_livro, id_categoria) VALUES (?, ?);";
        try {
            pst = connection.prepareStatement(sql);
            pst.setInt(1, idLivro);
            pst.setInt(2, idCategoria);
            pst.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao vincular categoria ao livro: " + e.getMessage());
            return false;
        } finally {
            fecharRecursos();
        }
    }

    public boolean removerCategoria(int idLivro, int idCategoria) {
        connectToDb();
        String sql = "DELETE FROM livro_categoria WHERE id_livro = ? AND id_categoria = ?;";
        try {
            pst = connection.prepareStatement(sql);
            pst.setInt(1, idLivro);
            pst.setInt(2, idCategoria);
            pst.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao remover categoria do livro: " + e.getMessage());
            return false;
        } finally {
            fecharRecursos();
        }
    }

    public List<Livro> selectLivrosPorCategoria(int idCategoria) {
        connectToDb();
        List<Livro> livros = new ArrayList<>();
        String sql = "SELECT l.* FROM livro l INNER JOIN livro_categoria lc ON l.id_livro = lc.id_livro WHERE lc.id_categoria = ?;";
        try {
            pst = connection.prepareStatement(sql);
            pst.setInt(1, idCategoria);
            rs = pst.executeQuery();
            while (rs.next()) {
                Livro livro = new Livro();
                livro.setIdLivro(rs.getInt("id_livro"));
                livro.setTitulo(rs.getString("titulo"));
                livro.setIsbn(rs.getString("isbn"));
                livro.setSinopse(rs.getString("sinopse"));
                livro.setAnoPublicacao(rs.getInt("ano_publicacao"));
                livros.add(livro);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar livros por categoria: " + e.getMessage());
        } finally {
            fecharRecursos();
        }
        return livros;
    }

    public List<Livro> selectLivrosPorAutor(int idAutor) {
        connectToDb();
        List<Livro> livros = new ArrayList<>();
        String sql = "SELECT l.* FROM livro l INNER JOIN livro_autor la ON l.id_livro = la.id_livro WHERE la.id_autor = ?;";
        try {
            pst = connection.prepareStatement(sql);
            pst.setInt(1, idAutor);
            rs = pst.executeQuery();
            while (rs.next()) {
                Livro livro = new Livro();
                livro.setIdLivro(rs.getInt("id_livro"));
                livro.setTitulo(rs.getString("titulo"));
                livro.setIsbn(rs.getString("isbn"));
                livro.setSinopse(rs.getString("sinopse"));
                livro.setAnoPublicacao(rs.getInt("ano_publicacao"));
                livros.add(livro);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar livros por autor: " + e.getMessage());
        } finally {
            fecharRecursos();
        }
        return livros;
    }

    public void selectLivrosComAutoresECategorias() {
        connectToDb();
        String sql = "SELECT l.id_livro, l.titulo, l.ano_publicacao, " +
                "GROUP_CONCAT(DISTINCT a.nome SEPARATOR ', ') AS autores, " +
                "GROUP_CONCAT(DISTINCT c.nome SEPARATOR ', ') AS categorias " +
                "FROM livro l " +
                "LEFT JOIN livro_autor la ON l.id_livro = la.id_livro " +
                "LEFT JOIN autor a ON la.id_autor = a.id_autor " +
                "LEFT JOIN livro_categoria lc ON l.id_livro = lc.id_livro " +
                "LEFT JOIN categoria c ON lc.id_categoria = c.id_categoria " +
                "GROUP BY l.id_livro;";
        try {
            st = connection.createStatement();
            rs = st.executeQuery(sql);
            boolean encontrou = false;
            while (rs.next()) {
                encontrou = true;
                System.out.println("ID: " + rs.getInt("id_livro") + " | Título: " + rs.getString("titulo") +
                        " (" + rs.getInt("ano_publicacao") + ")");
                System.out.println("  Autores: " + (rs.getString("autores") != null ? rs.getString("autores") : "Nenhum cadastrado"));
                System.out.println("  Categorias: " + (rs.getString("categorias") != null ? rs.getString("categorias") : "Nenhuma cadastrada"));
                System.out.println("-------------------------------------------------");
            }
            if (!encontrou) System.out.println("Nenhum livro encontrado no acervo.");
        } catch (SQLException e) {
            System.out.println("Erro ao buscar relatório completo de livros: " + e.getMessage());
        } finally {
            fecharRecursos();
        }
    }

    public Livro selectLivroPorId(int idLivro){
        connectToDb();
        Livro livroEncontrado = null;
        String sql = "SELECT * FROM livro WHERE id_livro = ?;";

        try {

            pst = connection.prepareStatement(sql);
            pst.setInt(1, idLivro);
            rs = pst.executeQuery();

            if (rs.next()){
                livroEncontrado = new Livro();
                livroEncontrado.setIdLivro(rs.getInt("id_livro"));
                livroEncontrado.setTitulo(rs.getString("titulo"));
                livroEncontrado.setIsbn(rs.getString("isbn"));
                livroEncontrado.setSinopse(rs.getString("sinopse"));
                livroEncontrado.setAnoPublicacao(rs.getInt("ano_publicacao"));
            }
        }
        catch (SQLException e){
            System.out.println("Erro ao buscar o livro por ID: " + e.getMessage());
        }
        finally{
            fecharRecursos();
        }
        return livroEncontrado;
    }
}


