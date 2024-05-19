package com.example;

import java.security.NoSuchAlgorithmException;
import java.util.*;

public class App {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Escolha uma opção:");
            System.out.println("1. Cadastrar usuário");
            System.out.println("2. Autenticar usuário");
            System.out.println("3. Sair");

            int opcao = scanner.nextInt();

            if (opcao == 1) {
                System.out.println("Digite o nome do usuário:");
                String nome = scanner.next();
                System.out.println("Digite a senha do usuário:");
                String senha = scanner.next();
                if (nome.length() == 4 && senha.length() == 4) {
                    User.cadastrarUsuario(new User(nome, senha));
                } else {
                    System.out.println("O nome e a senha devem ter 4 caracteres.");
                }
            } else if (opcao == 2) {
                System.out.println("Digite o nome do usuário:");
                String nome = scanner.next();
                System.out.println("Digite a senha do usuário:");
                String senha = scanner.next();
                if (User.autenticarUsuario(nome, senha)) {
                    System.out.println("Usuário autenticado com sucesso.");
                } else {
                    System.out.println("Usuário ou senha incorretos.");
                }
            } else if (opcao == 3) {
                System.out.println("Saindo...");
                break;
            } else if (opcao == 4) {
                // Caracteres permitidos na senha (alfanuméricos neste exemplo)
                String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

                // Tamanho da senha (a ser ajustado conforme necessário)
                int passwordLength = 4;
                BruteforceSHA256.bruteForce(characters, passwordLength,
                        "oEHHqkZhADmQBSVAg7aW3w==:d1a6bcd2602b37ec615f95907aa51b75e681f090784ecbc2114669f923bc4bbc");

            }
        }
    }
}
