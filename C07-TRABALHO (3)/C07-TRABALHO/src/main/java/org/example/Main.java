package org.example;

import biblioteca.dao.*;
import biblioteca.model.*;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Date lerData(Scanner scanner, String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String data = scanner.nextLine();
            try {
                return Date.valueOf(data);
            } catch (IllegalArgumentException e) {
                System.out.println("Data invalida. Use o formato AAAA-MM-DD. Exemplo: 2005-08-24");
            }
        }
    }

    private static String lerStatusExemplar(Scanner scanner, String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String status = scanner.nextLine();
            if (status.equals("Disponivel") || status.equals("Emprestado") ||
                    status.equals("Reservado") || status.equals("Manutencao")) {
                return status;
            }
            System.out.println("Status invalido. Use: Disponivel, Emprestado, Reservado ou Manutencao.");
        }
    }

    public static void main(String[] eloquence) {
        Scanner scanner = new Scanner(System.in);

        // Instanciação de todos os DAOs necessários
        LivroDAO livroDAO = new LivroDAO();
        ClienteDAO clienteDAO = new ClienteDAO();
        CarteirinhaDAO carteirinhaDAO = new CarteirinhaDAO();
        CategoriaDAO categoriaDAO = new CategoriaDAO();
        AutorDAO autorDAO = new AutorDAO();
        EmprestimoDAO emprestimoDAO = new EmprestimoDAO();
        EdicaoDAO edicaoDAO = new EdicaoDAO();
        ExemplarDAO exemplarDAO = new ExemplarDAO();

        int opcaoPrincipal = -1;

        while (opcaoPrincipal != 0) {
            System.out.println("\n========= SISTEMA DA BIBLIOTECA =========");
            System.out.println("1 - Gerenciar Livros");
            System.out.println("2 - Gerenciar Clientes");
            System.out.println("3 - Gerenciar Categorias");
            System.out.println("4 - Gerenciar Autores");
            System.out.println("5 - Gerenciar Empréstimos");
            System.out.println("6 - Gerenciar Edição");
            System.out.println("7 - Gerenciar Exemplar");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");

            opcaoPrincipal = scanner.nextInt();
            scanner.nextLine();

            switch (opcaoPrincipal) {
                case 1:
                    int opLivro = -1;
                    while (opLivro != 0) {
                        System.out.println("\n--- GERENCIAR LIVROS ---");
                        System.out.println("1 - Inserir livros");
                        System.out.println("2 - Alterar livros");
                        System.out.println("3 - Deletar livros");
                        System.out.println("4 - Ver todos os livros (mostrando a categoria e autor)");
                        System.out.println("5 - Ver livro por id");
                        System.out.println("6 - Vincular autor ao livro");
                        System.out.println("7 - Remover autor do livro");
                        System.out.println("8 - Vincular categoria ao livro");
                        System.out.println("9 - Remover categoria do livro");
                        System.out.println("0 - Voltar");
                        System.out.print("Escolha: ");
                        opLivro = scanner.nextInt();
                        scanner.nextLine();

                        switch (opLivro) {
                            case 1:
                                System.out.print("Título: "); String titulo = scanner.nextLine();
                                System.out.print("ISBN: "); String isbn = scanner.nextLine();
                                System.out.print("Sinopse: "); String sinopse = scanner.nextLine();
                                System.out.print("Ano: "); int ano = scanner.nextInt(); scanner.nextLine();
                                if (livroDAO.insertLivro(new Livro(titulo, isbn, sinopse, ano))) {
                                    System.out.println("Livro inserido!");
                                } else {
                                    System.out.println("Livro nao foi inserido.");
                                }
                                break;
                            case 2:
                                System.out.print("ID do Livro para alterar: "); int idL = scanner.nextInt(); scanner.nextLine();
                                System.out.print("Novo Título: "); String nTitulo = scanner.nextLine();
                                System.out.print("Novo ISBN: "); String nIsbn = scanner.nextLine();
                                System.out.print("Nova Sinopse: "); String nSinopse = scanner.nextLine();
                                System.out.print("Novo Ano: "); int nAno = scanner.nextInt(); scanner.nextLine();
                                Livro lAtualizado = new Livro(nTitulo, nIsbn, nSinopse, nAno);
                                lAtualizado.setIdLivro(idL);
                                if (livroDAO.updateLivro(lAtualizado)) {
                                    System.out.println("Livro atualizado!");
                                } else {
                                    System.out.println("Livro nao foi atualizado.");
                                }
                                break;
                            case 3:
                                System.out.print("ID do Livro para deletar: "); int idDel = scanner.nextInt(); scanner.nextLine();
                                if (livroDAO.deleteLivro(idDel)) {
                                    System.out.println("Livro deletado!");
                                }
                                break;
                            case 4:
                                System.out.println("\n=== LISTA COMPLETA DE LIVROS ===");
                                livroDAO.selectLivrosComAutoresECategorias();
                                break;
                            case 5:
                                System.out.print("ID do Livro: "); int idBusca = scanner.nextInt(); scanner.nextLine();
                                Livro enc = livroDAO.selectLivroPorId(idBusca);
                                if(enc != null) System.out.println(enc.toString());
                                else System.out.println("Livro não encontrado.");
                                break;
                            case 6:
                                System.out.print("ID do Livro: "); int idLivroAutor = scanner.nextInt();
                                System.out.print("ID do Autor: "); int idAutorLivro = scanner.nextInt(); scanner.nextLine();
                                if (livroDAO.vincularAutor(idLivroAutor, idAutorLivro)) {
                                    System.out.println("Autor vinculado ao livro!");
                                } else {
                                    System.out.println("Autor nao foi vinculado ao livro.");
                                }
                                break;
                            case 7:
                                System.out.print("ID do Livro: "); int idLivroRemAutor = scanner.nextInt();
                                System.out.print("ID do Autor: "); int idAutorRemLivro = scanner.nextInt(); scanner.nextLine();
                                if (livroDAO.removerAutor(idLivroRemAutor, idAutorRemLivro)) {
                                    System.out.println("Autor removido do livro!");
                                } else {
                                    System.out.println("Autor nao foi removido do livro.");
                                }
                                break;
                            case 8:
                                System.out.print("ID do Livro: "); int idLivroCategoria = scanner.nextInt();
                                System.out.print("ID da Categoria: "); int idCategoriaLivro = scanner.nextInt(); scanner.nextLine();
                                if (livroDAO.vincularCategoria(idLivroCategoria, idCategoriaLivro)) {
                                    System.out.println("Categoria vinculada ao livro!");
                                } else {
                                    System.out.println("Categoria nao foi vinculada ao livro.");
                                }
                                break;
                            case 9:
                                System.out.print("ID do Livro: "); int idLivroRemCategoria = scanner.nextInt();
                                System.out.print("ID da Categoria: "); int idCategoriaRemLivro = scanner.nextInt(); scanner.nextLine();
                                if (livroDAO.removerCategoria(idLivroRemCategoria, idCategoriaRemLivro)) {
                                    System.out.println("Categoria removida do livro!");
                                } else {
                                    System.out.println("Categoria nao foi removida do livro.");
                                }
                                break;
                        }
                    }
                    break;

                case 2:
                    int opCliente = -1;
                    while (opCliente != 0) {
                        System.out.println("\n--- GERENCIAR CLIENTES ---");
                        System.out.println("1 - Inserir clientes");
                        System.out.println("2 - Alterar clientes");
                        System.out.println("3 - Deletar clientes");
                        System.out.println("4 - Inserir carteirinha");
                        System.out.println("5 - Alterar carteirinha");
                        System.out.println("6 - Deletar carteirinha");
                        System.out.println("7 - Listar todos os clientes");
                        System.out.println("8 - Listar todas as carteirinhas informando os seus clientes");
                        System.out.println("9 - Listar todos os empréstimos desse cliente");
                        System.out.println("0 - Voltar");
                        System.out.print("Escolha: ");
                        opCliente = scanner.nextInt();
                        scanner.nextLine();

                        switch (opCliente) {
                            case 1:
                                System.out.print("Nome: "); String nomeC = scanner.nextLine();
                                System.out.print("CPF: "); String cpf = scanner.nextLine();
                                System.out.print("Email: "); String email = scanner.nextLine();
                                Date data = lerData(scanner, "Data Nasc (AAAA-MM-DD): ");
                                if (clienteDAO.insertCliente(new Cliente(nomeC, cpf, email, data))) {
                                    System.out.println("Cliente inserido!");
                                } else {
                                    System.out.println("Cliente nao foi inserido.");
                                }
                                break;
                            case 2:
                                System.out.print("ID do Cliente para alterar: "); int idUp = scanner.nextInt(); scanner.nextLine();
                                System.out.print("Novo Nome: "); String nNome = scanner.nextLine();
                                System.out.print("Novo CPF: "); String nCpf = scanner.nextLine();
                                System.out.print("Novo Email: "); String nEmail = scanner.nextLine();
                                Date nData = lerData(scanner, "Nova Data Nasc (AAAA-MM-DD): ");
                                Cliente cAtualizado = new Cliente(nNome, nCpf, nEmail, nData);
                                cAtualizado.setIdCliente(idUp);
                                if (clienteDAO.updateCliente(cAtualizado)) {
                                    System.out.println("Cliente atualizado!");
                                } else {
                                    System.out.println("Cliente nao foi atualizado.");
                                }
                                break;
                            case 3:
                                System.out.print("ID para deletar: "); int idD = scanner.nextInt(); scanner.nextLine();
                                if (clienteDAO.deleteCliente(idD)) {
                                    System.out.println("Cliente deletado!");
                                } else {
                                    System.out.println("Cliente nao foi deletado.");
                                }
                                break;
                            case 4:
                                System.out.print("ID Cliente: "); int idC = scanner.nextInt(); scanner.nextLine();
                                System.out.print("Código: "); String cod = scanner.nextLine();
                                Date emi = lerData(scanner, "Emissao (AAAA-MM-DD): ");
                                Date val = lerData(scanner, "Validade (AAAA-MM-DD): ");
                                if (carteirinhaDAO.insertCarteirinha(new Carteirinha(idC, cod, emi, val))) {
                                    System.out.println("Carteirinha inserida!");
                                } else {
                                    System.out.println("Carteirinha nao foi inserida.");
                                }
                                break;
                            case 5:
                                System.out.print("ID da Carteirinha para alterar: "); int idCartUp = scanner.nextInt(); scanner.nextLine();
                                System.out.print("Novo Código: "); String nCod = scanner.nextLine();
                                Date nEmi = lerData(scanner, "Nova Emissao (AAAA-MM-DD): ");
                                Date nVal = lerData(scanner, "Nova Validade (AAAA-MM-DD): ");
                                Carteirinha cartAtualizada = new Carteirinha(0, nCod, nEmi, nVal);
                                cartAtualizada.setIdCarteirinha(idCartUp);
                                if (carteirinhaDAO.updateCarteirinha(cartAtualizada)) {
                                    System.out.println("Carteirinha atualizada!");
                                } else {
                                    System.out.println("Carteirinha nao foi atualizada.");
                                }
                                break;
                            case 6:
                                System.out.print("ID Carteirinha para deletar: "); int idCartD = scanner.nextInt(); scanner.nextLine();
                                if (carteirinhaDAO.deleteCarteirinha(idCartD)) {
                                    System.out.println("Carteirinha deletada!");
                                } else {
                                    System.out.println("Carteirinha nao foi deletada.");
                                }
                                break;
                            case 7:
                                List<Cliente> clientes = clienteDAO.selectAllClientes();
                                if(clientes.isEmpty()) System.out.println("Nenhum cliente cadastrado.");
                                for(Cliente c : clientes) System.out.println("ID: " + c.getIdCliente() + " | Nome: " + c.getNome() + " | CPF: " + c.getCpf() + " | Nascimento: " + c.getDataNascimento() + " | Ativo: " + c.isAtivo());
                                break;
                            case 8:
                                List<Carteirinha> carts = carteirinhaDAO.selectAllCarteirinhasComCliente();
                                if(carts.isEmpty()) System.out.println("Nenhuma carteirinha cadastrada.");
                                for(Carteirinha c : carts) {
                                    System.out.println("Cód: " + c.getCodigo() + " | Emissão: " + c.getDataEmissao() + " | Dono: " + c.getCliente().getNome() + " (CPF: " + c.getCliente().getCpf() + ")");
                                }
                                break;
                            case 9:
                                System.out.print("ID do Cliente: "); int idCliEmp = scanner.nextInt(); scanner.nextLine();
                                List<Emprestimo> empCli = emprestimoDAO.selectEmprestimosPorCliente(idCliEmp);
                                if(empCli.isEmpty()) {
                                    System.out.println("Nenhum empréstimo encontrado para este cliente.");
                                } else {
                                    for(Emprestimo e : empCli) {
                                        System.out.println("ID Empréstimo: " + e.getIdEmprestimo() + " | Tombo do Livro: " + e.getExemplar().getTombo() + " | Data Retirada: " + e.getDataEmprestimo());
                                    }
                                }
                                break;
                        }
                    }
                    break;

                case 3:
                    int opCat = -1;
                    while (opCat != 0) {
                        System.out.println("\n--- GERENCIAR CATEGORIAS ---");
                        System.out.println("1 - Adicionar categorias");
                        System.out.println("2 - Alterar categorias");
                        System.out.println("3 - Deletar categorias");
                        System.out.println("4 - Ver todas as categorias");
                        System.out.println("5 - Ver todos os livros de determinada categoria");
                        System.out.println("0 - Voltar");
                        System.out.print("Escolha: ");
                        opCat = scanner.nextInt(); scanner.nextLine();

                        switch (opCat) {
                            case 1:
                                System.out.print("Nome: "); String nomeCat = scanner.nextLine();
                                System.out.print("Descricao: "); String descCat = scanner.nextLine();
                                if (categoriaDAO.insertCategoria(new Categoria(nomeCat, descCat))) {
                                    System.out.println("Categoria inserida!");
                                } else {
                                    System.out.println("Categoria nao foi inserida.");
                                }
                                break;
                            case 2:
                                System.out.print("ID da Categoria: "); int idCatUp = scanner.nextInt(); scanner.nextLine();
                                System.out.print("Novo Nome: "); String nNomeCat = scanner.nextLine();
                                System.out.print("Nova Descrição: "); String nDescCat = scanner.nextLine();
                                Categoria catUp = new Categoria(nNomeCat, nDescCat);
                                catUp.setIdCategoria(idCatUp);
                                if (categoriaDAO.updateCategoria(catUp)) {
                                    System.out.println("Categoria atualizada!");
                                } else {
                                    System.out.println("Categoria nao foi atualizada.");
                                }
                                break;
                            case 3:
                                System.out.print("ID da Categoria para deletar: "); int idCatDel = scanner.nextInt(); scanner.nextLine();
                                if (categoriaDAO.deleteCategoria(idCatDel)) {
                                    System.out.println("Categoria deletada!");
                                } else {
                                    System.out.println("Categoria nao foi deletada.");
                                }
                                break;
                            case 4:
                                List<Categoria> categorias = categoriaDAO.selectAllCategorias();
                                for(Categoria c : categorias) System.out.println("ID: " + c.getIdCategoria() + " | Nome: " + c.getNome());
                                break;
                            case 5:
                                System.out.print("ID da Categoria: "); int idCat = scanner.nextInt(); scanner.nextLine();
                                List<Livro> livrosCat = livroDAO.selectLivrosPorCategoria(idCat);
                                if(livrosCat.isEmpty()) System.out.println("Nenhum livro vinculado a esta categoria.");
                                for(Livro l : livrosCat) {
                                    System.out.println("ID: " + l.getIdLivro() + " | Título: " + l.getTitulo() + " | ISBN: " + l.getIsbn());
                                }
                                break;
                        }
                    }
                    break;

                case 4:
                    int opAutor = -1;
                    while (opAutor != 0) {
                        System.out.println("\n--- GERENCIAR AUTORES ---");
                        System.out.println("1 - Adicionar autor");
                        System.out.println("2 - Alterar autor");
                        System.out.println("3 - Deletar autor");
                        System.out.println("4 - Ver todos os autores");
                        System.out.println("5 - Ver todos os livros de determinado autor");
                        System.out.println("0 - Voltar");
                        System.out.print("Escolha: ");
                        opAutor = scanner.nextInt(); scanner.nextLine();

                        switch (opAutor) {
                            case 1:
                                System.out.print("Nome: "); String nAut = scanner.nextLine();
                                System.out.print("Nacionalidade: "); String nacAut = scanner.nextLine();
                                if (autorDAO.insertAutor(new Autor(nAut, nacAut))) {
                                    System.out.println("Autor inserido!");
                                } else {
                                    System.out.println("Autor nao foi inserido.");
                                }
                                break;
                            case 2:
                                System.out.print("ID do Autor: "); int idAutUp = scanner.nextInt(); scanner.nextLine();
                                System.out.print("Novo Nome: "); String nNomeAut = scanner.nextLine();
                                System.out.print("Nova Nacionalidade: "); String nNacAut = scanner.nextLine();
                                Autor autUp = new Autor(nNomeAut, nNacAut);
                                autUp.setIdAutor(idAutUp);
                                if (autorDAO.updateAutor(autUp)) {
                                    System.out.println("Autor atualizado!");
                                } else {
                                    System.out.println("Autor nao foi atualizado.");
                                }
                                break;
                            case 3:
                                System.out.print("ID do Autor para deletar: "); int idAutDel = scanner.nextInt(); scanner.nextLine();
                                if (autorDAO.deleteAutor(idAutDel)) {
                                    System.out.println("Autor deletado!");
                                } else {
                                    System.out.println("Autor nao foi deletado.");
                                }
                                break;
                            case 4:
                                List<Autor> autores = autorDAO.selectAllAutores();
                                for(Autor a : autores) System.out.println("ID: " + a.getIdAutor() + " | Nome: " + a.getNome() + " | Nac: " + a.getNacionalidade());
                                break;
                            case 5:
                                System.out.print("ID do Autor: "); int idAut = scanner.nextInt(); scanner.nextLine();
                                List<Livro> livrosAut = livroDAO.selectLivrosPorAutor(idAut);
                                if(livrosAut.isEmpty()) System.out.println("Nenhum livro vinculado a este autor.");
                                for(Livro l : livrosAut) {
                                    System.out.println("ID: " + l.getIdLivro() + " | Título: " + l.getTitulo() + " | Ano: " + l.getAnoPublicacao());
                                }
                                break;
                        }
                    }
                    break;

                case 5:
                    int opEmp = -1;
                    while (opEmp != 0) {
                        System.out.println("\n--- GERENCIAR EMPRÉSTIMOS ---");
                        System.out.println("1 - Adicionar empréstimo");
                        System.out.println("2 - Alterar empréstimo (Registrar Devolução)");
                        System.out.println("3 - Deletar empréstimo");
                        System.out.println("4 - Ver todos os empréstimos detalhados");
                        System.out.println("5 - Ver todos os empréstimos por cliente");
                        System.out.println("0 - Voltar");
                        System.out.print("Escolha: ");
                        opEmp = scanner.nextInt(); scanner.nextLine();

                        switch (opEmp) {
                            case 1:
                                System.out.print("ID Cliente: "); int idCE = scanner.nextInt();
                                System.out.print("ID Exemplar: "); int idEx = scanner.nextInt(); scanner.nextLine();
                                Date dt = lerData(scanner, "Data Prevista (AAAA-MM-DD): ");
                                if (emprestimoDAO.insertEmprestimo(new Emprestimo(idCE, idEx, dt))) {
                                    System.out.println("Emprestimo inserido!");
                                } else {
                                    System.out.println("Emprestimo nao foi inserido.");
                                }
                                break;
                            case 2:
                                System.out.print("ID Empréstimo: "); int idEmpUp = scanner.nextInt(); scanner.nextLine();
                                Date dtDev = lerData(scanner, "Data Devolucao (AAAA-MM-DD): ");
                                if (emprestimoDAO.registrarDevolucao(idEmpUp, dtDev)) {
                                    System.out.println("Devolucao registrada. A multa e o status do exemplar sao atualizados pelo banco.");
                                } else {
                                    System.out.println("Devolucao nao foi registrada.");
                                }
                                break;
                            case 3:
                                System.out.print("ID Emprestimo para deletar: "); int idEmpDel = scanner.nextInt(); scanner.nextLine();
                                if (emprestimoDAO.deleteEmprestimo(idEmpDel)) {
                                    System.out.println("Emprestimo deletado!");
                                } else {
                                    System.out.println("Emprestimo nao foi deletado.");
                                }
                                break;
                            case 4:
                                List<Emprestimo> relatorio = emprestimoDAO.selectAllEmprestimosDetalhados();
                                if(relatorio.isEmpty()) System.out.println("Nenhum empréstimo cadastrado.");
                                for (Emprestimo e : relatorio) {
                                    String dataDevolucaoTexto = (e.getDataDevolucao() == null) ? "Em aberto" : e.getDataDevolucao().toString();
                                    System.out.println("ID: " + e.getIdEmprestimo() + " | Cliente: " + e.getCliente().getNome() + " | Tombo: " + e.getExemplar().getTombo() + " | Data prevista de devolução: " + e.getDataPrevistaDevolucao() + " | Data de devolução: " + dataDevolucaoTexto + " | Multa: R$" + e.getMulta());
                                }
                                break;
                            case 5:
                                System.out.print("ID do Cliente: "); int idCliBusca = scanner.nextInt(); scanner.nextLine();
                                List<Emprestimo> empCliBusca = emprestimoDAO.selectEmprestimosPorCliente(idCliBusca);
                                if(empCliBusca.isEmpty()) System.out.println("Nenhum empréstimo cadastrado para este cliente.");
                                for(Emprestimo e : empCliBusca) {
                                    System.out.println("ID Empréstimo: " + e.getIdEmprestimo() + " | Exemplar: " + e.getExemplar().getTombo() + " | Previsto para: " + e.getDataPrevistaDevolucao());
                                }
                                break;
                        }
                    }
                    break;

                case 6:
                    int opEdicao = -1;
                    while (opEdicao != 0) {
                        System.out.println("\n--- GERENCIAR EDIÇÃO ---");
                        System.out.println("1 - Adicionar edição");
                        System.out.println("2 - Alterar edição");
                        System.out.println("3 - Deletar edição");
                        System.out.println("4 - Ver todas as edições (detalhadas) de determinado livro");
                        System.out.println("0 - Voltar");
                        System.out.print("Escolha: ");
                        opEdicao = scanner.nextInt(); scanner.nextLine();

                        switch (opEdicao) {
                            case 1:
                                System.out.print("ID Livro: "); int idLivEd = scanner.nextInt();
                                System.out.print("Nº Edição: "); int nEdi = scanner.nextInt(); scanner.nextLine();
                                System.out.print("Editora: "); String edi = scanner.nextLine();
                                System.out.print("Preço de Reposição (Ex: 49.90): "); double preco = scanner.nextDouble(); scanner.nextLine();
                                if (edicaoDAO.insertEdicao(new Edicao(idLivEd, nEdi, edi, preco))) {
                                    System.out.println("Edicao inserida!");
                                } else {
                                    System.out.println("Edicao nao foi inserida.");
                                }
                                break;
                            case 2:
                                System.out.print("ID da Edição para alterar: "); int idEdUp = scanner.nextInt();
                                System.out.print("Novo Nº Edição: "); int nNEdi = scanner.nextInt(); scanner.nextLine();
                                System.out.print("Nova Editora: "); String nEdiStr = scanner.nextLine();
                                System.out.print("Novo Preço: "); double nPreco = scanner.nextDouble(); scanner.nextLine();
                                Edicao edUp = new Edicao(0, nNEdi, nEdiStr, nPreco);
                                edUp.setIdEdicao(idEdUp);
                                if (edicaoDAO.updateEdicao(edUp)) {
                                    System.out.println("Edicao atualizada!");
                                } else {
                                    System.out.println("Edicao nao foi atualizada.");
                                }
                                break;
                            case 3:
                                System.out.print("ID da Edição para deletar: "); int idEdDel = scanner.nextInt(); scanner.nextLine();
                                if (edicaoDAO.deleteEdicao(idEdDel)) {
                                    System.out.println("Edicao deletada!");
                                } else {
                                    System.out.println("Edicao nao foi deletada.");
                                }
                                break;
                            case 4:
                                System.out.print("ID do Livro: "); int idLiv = scanner.nextInt(); scanner.nextLine();
                                List<Edicao> edicoesLiv = edicaoDAO.selectEdicoesPorLivro(idLiv);
                                if(edicoesLiv.isEmpty()) System.out.println("Nenhuma edição cadastrada para este livro.");
                                for (Edicao e : edicoesLiv) {
                                    System.out.println("ID Edição: " + e.getIdEdicao() + " | Nº: " + e.getNumeroEdicao() + " | Editora: " + e.getEditora() + " | Título Original: " + e.getLivro().getTitulo());
                                }
                                break;
                        }
                    }
                    break;

                case 7:
                    int opExemplar = -1;
                    while (opExemplar != 0) {
                        System.out.println("\n--- GERENCIAR EXEMPLAR ---");
                        System.out.println("1 - Adicionar exemplar");
                        System.out.println("2 - Alterar exemplar (Status)");
                        System.out.println("3 - Deletar exemplar");
                        System.out.println("4 - Ver a quantidade de exemplares de determinado livro e um detalhamento");
                        System.out.println("0 - Voltar");
                        System.out.print("Escolha: ");
                        opExemplar = scanner.nextInt(); scanner.nextLine();

                        switch (opExemplar) {
                            case 1:
                                System.out.print("ID da Edição: "); int idEdEx = scanner.nextInt(); scanner.nextLine();
                                System.out.print("Tombo: "); String tombo = scanner.nextLine();
                                String stEx = lerStatusExemplar(scanner, "Status Inicial (Disponivel/Emprestado/Reservado/Manutencao): ");
                                Date dtAq = lerData(scanner, "Data Aquisicao (AAAA-MM-DD): ");
                                if (exemplarDAO.insertExemplar(new Exemplar(idEdEx, tombo, stEx, dtAq))) {
                                    System.out.println("Exemplar inserido!");
                                } else {
                                    System.out.println("Exemplar nao foi inserido.");
                                }
                                break;
                            case 2:
                                System.out.print("ID do Exemplar: "); int idExUp = scanner.nextInt(); scanner.nextLine();
                                String nStatus = lerStatusExemplar(scanner, "Novo Status (Disponivel/Emprestado/Reservado/Manutencao): ");
                                if (exemplarDAO.updateStatus(idExUp, nStatus)) {
                                    System.out.println("Status atualizado!");
                                } else {
                                    System.out.println("Status nao foi atualizado.");
                                }
                                break;
                            case 3:
                                System.out.print("ID do Exemplar para deletar: "); int idExDel = scanner.nextInt(); scanner.nextLine();
                                if (exemplarDAO.deleteExemplar(idExDel)) {
                                    System.out.println("Exemplar deletado!");
                                } else {
                                    System.out.println("Exemplar nao foi deletado.");
                                }
                                break;
                            case 4:
                                System.out.print("ID do Livro: "); int idL = scanner.nextInt(); scanner.nextLine();
                                List<Exemplar> exempLiv = exemplarDAO.selectDetalhesExemplaresPorLivro(idL);
                                if(exempLiv.isEmpty()) {
                                    System.out.println("Nenhum volume/exemplar físico encontrado para este livro.");
                                } else {
                                    System.out.println("=> Total de Volumes na estante: " + exempLiv.size());
                                    for(Exemplar ex : exempLiv) {
                                        System.out.println("ID: " + ex.getIdExemplar() +" | Tombo: " + ex.getTombo() + " | Status: " + ex.getStatusExemplar() + " | Edição nº: " + ex.getEdicao().getNumeroEdicao() + " (" + ex.getEdicao().getEditora() + ")");
                                    }
                                }
                                break;
                        }
                    }
                    break;
            }
        }
        System.out.println("Sistema Finalizado.");
        scanner.close();
    }
}
