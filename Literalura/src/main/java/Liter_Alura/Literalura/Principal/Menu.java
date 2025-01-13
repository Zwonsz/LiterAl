package Liter_Alura.Literalura.Principal;

import Liter_Alura.Literalura.Model.Autor;
import Liter_Alura.Literalura.Model.Livro;
import Liter_Alura.Literalura.Repository.AutorRepositorio;
import Liter_Alura.Literalura.Repository.LivroRepositorio;
import Liter_Alura.Literalura.Service.ConsumoAPI;
import Liter_Alura.Literalura.Service.Desserealiza;
import Liter_Alura.Literalura.Model.DadosLivro;
import Liter_Alura.Literalura.Service.LivroService;
import Liter_Alura.Literalura.Service.RespostaLivro;
import jakarta.persistence.Column;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

@Service
@Component
public class Menu {
    private final String ENDERECO = "https://gutendex.com/books/?search=";
    Scanner scanner = new Scanner(System.in);
    ConsumoAPI x = new ConsumoAPI();
    Desserealiza desserealiza = new Desserealiza();
    private LivroService livroService;


    private final LivroRepositorio repositorio;
    private final AutorRepositorio repositorioAutor;

    @Autowired
    public Menu(LivroRepositorio repositorio, AutorRepositorio repositorioAutor) {
        this.repositorio = repositorio;
        this.repositorioAutor = repositorioAutor;
    }


    public void exibeMenu() {

        int opcaoWhile = -1;
        while (opcaoWhile != 0) {

            System.out.println("""
                    -----------------------------------
                    Escolha sua opção: 
                    1- buscar livros pelo título
                    2- listar livros registrados
                    3- listar autores registrados
                    4- listar autores vivos em determinado ano
                    5- listar livros em determinado idioma
                    0- sair
                    """);

            int opcao = scanner.nextInt();
            scanner.nextLine();
            switch (opcao) {
                case 1:
                    System.out.println("Digite o nome do livro a ser buscado: ");
                    String titulo = scanner.nextLine();
                    String json = x.obterDados(ENDERECO + titulo.replace(" ", "%20"));

                    RespostaLivro respostaLivros = desserealiza.desserealizar(json, RespostaLivro.class);
                    List<DadosLivro> livros = respostaLivros.getResults();

                    if (livros != null && !livros.isEmpty()) {
                        for (DadosLivro dadosLivro : livros) {
                            livroService.processarLivro(dadosLivro);
                        }
                    } else {
                        System.out.println("Nenhum livro encontrado com esse título.");
                    }
                    break;
                case 2:
                    List<Livro> todosLivros = repositorio.findAll();


                    Set<String> titulosUnicos = new HashSet<>();


                    for (Livro livro : todosLivros) {
                        if (titulosUnicos.add(livro.getTitulo())) {
                            System.out.println(livro);
                        }
                    }


                    if (titulosUnicos.isEmpty()) {
                        System.out.println("Nenhum livro encontrado no banco de dados.");
                    }
                    break;
                case 3:
                    listarAutorRegistrado();
                case 4:
                    listarAutoresVivos();
                    break;
                case 5:
                    listarLivrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida! Escolha novamente uma das opções válidas");
            }
        }

        }
        public void listarAutorRegistrado () {
            List<Autor> autores = repositorioAutor.findAll();
            for (Autor autor : autores) {
                System.out.println("Nome do Autor: " + autor.getNome());
                System.out.println("Data de Nascimento: " + autor.getAnoNascimento());
                System.out.println("Data de Falecimento: " + autor.getAnoFalecimento());
                System.out.println("Livros Escritos:");


                List<Livro> livros = autor.getLivros();
                if (livros.isEmpty()) {
                    System.out.println("  Nenhum livro cadastrado.");
                } else {
                    for (Livro livro : livros) {
                        System.out.println("  - " + livro.getTitulo());
                    }
                }
                System.out.println("-------------------------");
            }
        }
        public void listarAutoresVivos () {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Digite o ano para buscar autores vivos nesse período: ");
            int ano = scanner.nextInt();

            List<Autor> listaAutores = repositorioAutor.buscarAutoresPorAno((Integer) ano);

            if (listaAutores.isEmpty()) {
                System.out.println("Nenhum autor encontrado vivo no ano " + ano);
            } else {
                System.out.println("Autores vivos no ano " + ano + ":");
                for (Autor autor : listaAutores) {
                    System.out.println("Nome: " + autor.getNome());
                    System.out.println("Data de Nascimento: " + autor.getAnoNascimento());
                    System.out.println("Data de Falecimento: " + (autor.getAnoNascimento() != null ? autor.getAnoFalecimento() : "Ainda vivo"));
                    System.out.println("-----");
                }
            }
        }

        public void listarLivrosPorIdioma () {


            Scanner leitura = new Scanner(System.in);

            System.out.println("Selecione o idioma:");
            System.out.println("1 - Espanhol (es)");
            System.out.println("2 - Inglês (en)");
            System.out.println("3 - Francês (fr)");
            System.out.println("4 - Português (pt)");
            System.out.print("Escolha uma opção: ");

            int opcao = leitura.nextInt();
            String idioma = leitura.nextLine();

            switch (opcao) {
                case 1:
                    idioma = "es";
                    break;
                case 2:
                    idioma = "en";
                    break;
                case 3:
                    idioma = "fr";
                    break;
                case 4:
                    idioma = "pt";
                    break;
                default:
                    System.out.println("Opção inválida.");
                    return;
            }

//            List<Livro> livros = livroService.buscarLivrosPorIdioma(idioma);
//
//            if (livros.isEmpty()) {
//                System.out.println("Nenhum livro encontrado no idioma selecionado (" + idioma + ").");
//            } else {
//                System.out.println("Livros no idioma " + idioma + ":");
//                for (Livro livro : livros) {
//                    System.out.println("Título: " + livro.getTitulo());
//                    System.out.println("Autor: " + livro.getAutor().getNome());
//                    System.out.println("Idioma: " + livro.getIdioma());
//                    System.out.println("Número de Downloads: " + livro.getNumeroDownloads());
//                    System.out.println("");
//
//                }
            }
        }













