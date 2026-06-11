package biblioteca.dao;

import biblioteca.conexao.ConnectionDAO;
import biblioteca.model.Emprestimo;
import biblioteca.model.Cliente;
import biblioteca.model.Exemplar;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmprestimoDAO extends ConnectionDAO {

    public boolean insertEmprestimo(Emprestimo emp) {
        connectToDb();

        String sql = "CALL registrar_emprestimo(?, ?, ?);";
        try {
            pst = connection.prepareStatement(sql);
            pst.setInt(1, emp.getIdCliente());
            pst.setInt(2, emp.getIdExemplar());
            pst.setDate(3, emp.getDataPrevistaDevolucao());
            pst.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao registrar empréstimo: " + e.getMessage());
            return false;
        } finally {
            fecharRecursos();
        }
    }

    public boolean deleteEmprestimo(int idEmprestimo) {
        connectToDb();
        String sql = "DELETE FROM emprestimo WHERE id_emprestimo = ?;";
        try {
            pst = connection.prepareStatement(sql);
            pst.setInt(1, idEmprestimo);
            pst.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao deletar emprestimo: " + e.getMessage());
            return false;
        } finally {
            fecharRecursos();
        }
    }

    public List<Emprestimo> selectAllEmprestimosDetalhados() {
        connectToDb();
        List<Emprestimo> lista = new ArrayList<>();
        String sql = "SELECT emp.*, cli.nome AS nome_cliente, ex.tombo FROM emprestimo emp " +
                "INNER JOIN cliente cli ON emp.id_cliente = cli.id_cliente " +
                "INNER JOIN exemplar ex ON emp.id_exemplar = ex.id_exemplar;";
        try {
            st = connection.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                Emprestimo emp = new Emprestimo();
                emp.setIdEmprestimo(rs.getInt("id_emprestimo"));
                emp.setIdCliente(rs.getInt("id_cliente"));
                emp.setIdExemplar(rs.getInt("id_exemplar"));
                emp.setDataEmprestimo(rs.getTimestamp("data_emprestimo"));
                emp.setDataPrevistaDevolucao(rs.getDate("data_prevista_devolucao"));
                emp.setDataDevolucao(rs.getDate("data_devolucao"));
                emp.setMulta(rs.getDouble("multa"));

                Cliente cli = new Cliente();
                cli.setNome(rs.getString("nome_cliente"));
                emp.setCliente(cli);

                Exemplar ex = new Exemplar();
                ex.setTombo(rs.getString("tombo"));
                emp.setExemplar(ex);

                lista.add(emp);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar relatórios de empréstimo: " + e.getMessage());
        } finally {
            fecharRecursos();
        }
        return lista;
    }

    public List<Emprestimo> selectEmprestimosPorCliente(int idCliente) {
        connectToDb();
        List<Emprestimo> lista = new ArrayList<>();

        String sql = "SELECT * FROM vw_emprestimos_detalhados WHERE data_devolucao IS NULL;";

        try {
            pst = connection.prepareStatement(sql);
            pst.setInt(1, idCliente);
            rs = pst.executeQuery();
            while (rs.next()) {
                Emprestimo emp = new Emprestimo();
                emp.setIdEmprestimo(rs.getInt("id_emprestimo"));
                emp.setIdCliente(rs.getInt("id_cliente"));
                emp.setIdExemplar(rs.getInt("id_exemplar"));
                emp.setDataEmprestimo(rs.getTimestamp("data_emprestimo"));
                emp.setDataPrevistaDevolucao(rs.getDate("data_prevista_devolucao"));
                emp.setDataDevolucao(rs.getDate("data_devolucao"));
                emp.setMulta(rs.getDouble("multa"));

                Exemplar ex = new Exemplar();
                ex.setTombo(rs.getString("tombo"));
                emp.setExemplar(ex);

                lista.add(emp);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar empréstimos do cliente: " + e.getMessage());
        } finally {
            fecharRecursos();
        }
        return lista;
    }

    public boolean registrarDevolucao(int idEmprestimo, java.sql.Date dataDevolucao) {
        connectToDb();
        String sql = "UPDATE emprestimo SET data_devolucao = ? WHERE id_emprestimo = ?;";
        try {
            pst = connection.prepareStatement(sql);
            pst.setDate(1, dataDevolucao);
            pst.setInt(2, idEmprestimo);
            pst.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao registrar devolução: " + e.getMessage());
            return false;
        } finally {
            fecharRecursos();
        }
    }
}
