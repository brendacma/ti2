package app;

import java.util.List;
import java.util.Scanner;

import dao.UsuarioDAO;
import model.Usuario;

public class Aplicacao {
    
    private static final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private static final Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) throws Exception {
        int opcao;
        
        do {
            System.out.println("\n\n===== Menu =====");
            System.out.println("1. Listar usuários");
            System.out.println("2. Inserir usuário");
            System.out.println("3. Atualizar usuário");
            System.out.println("4. Excluir usuário");
            System.out.println("5. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            
            switch (opcao) {
                case 1:
                    listarUsuarios();
                    break;
                case 2:
                    inserirUsuario();
                    break;
                case 3:
                    atualizarUsuario();
                    break;
                case 4:
                    excluirUsuario();
                    break;
                case 5:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 5);
        
        scanner.close();
    }
    
    private static void listarUsuarios() {
        List<Usuario> usuarios = usuarioDAO.getAll();
        for (Usuario u : usuarios) {
            System.out.println(u.toString());
        }
    }
    
    private static void inserirUsuario() {
        System.out.println("\n==== Inserir usuário ====");
        System.out.print("Informe o código: ");
        int codigo = scanner.nextInt();
        scanner.nextLine(); // Consume newline left-over
        System.out.print("Informe o login: ");
        String login = scanner.nextLine();
        System.out.print("Informe a senha: ");
        String senha = scanner.nextLine();
        System.out.print("Informe o sexo (M/F): ");
        char sexo = scanner.next().charAt(0);
        
        Usuario usuario = new Usuario(codigo, login, senha, sexo);
        if (usuarioDAO.insert(usuario)) {
            System.out.println("Inserção com sucesso -> " + usuario.toString());
        } else {
            System.out.println("Falha ao inserir usuário.");
        }
    }
    
    private static void atualizarUsuario() {
        System.out.println("\n==== Atualizar usuário ====");
        System.out.print("Informe o código do usuário a ser atualizado: ");
        int codigo = scanner.nextInt();
        scanner.nextLine(); // Consume newline left-over
        Usuario usuario = usuarioDAO.getByCodigo(codigo);
        if (usuario != null) {
            System.out.print("Informe o novo login: ");
            usuario.setLogin(scanner.nextLine());
            System.out.print("Informe a nova senha: ");
            usuario.setSenha(scanner.nextLine());
            System.out.print("Informe o novo sexo (M/F): ");
            usuario.setSexo(scanner.next().charAt(0));
            
            if (usuarioDAO.update(usuario)) {
                System.out.println("Usuário atualizado com sucesso.");
            } else {
                System.out.println("Falha ao atualizar usuário.");
            }
        } else {
            System.out.println("Usuário não encontrado.");
        }
    }
    
    private static void excluirUsuario() {
        System.out.println("\n==== Excluir usuário ====");
        System.out.print("Informe o código do usuário a ser excluído: ");
        int codigo = scanner.nextInt();
        if (usuarioDAO.delete(codigo)) {
            System.out.println("Usuário excluído com sucesso.");
        } else {
            System.out.println("Falha ao excluir usuário.");
        }
    }
}
