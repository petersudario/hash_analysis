package com.example;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

class User {
    private String nome;
    private String senha;
    private static final String FILENAME = "usuarios.json";

    public User(String nome, String senha) {
        this.nome = nome;
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public String getSenha() {
        return senha;
    }

    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("nome", nome);
        jsonObject.put("senha", hashSenhaComSalt(senha));
        return jsonObject;
    }

    private static String generateSalt() {
        SecureRandom sr = new SecureRandom();
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    private static String hashSenhaComSalt(String senha) {
        try {
            String salt = generateSalt();
            String saltedSenha = salt + senha;
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(saltedSenha.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return salt + ":" + hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String hashSenhaComSalt(String senha, String salt) {
        try {
            String saltedSenha = salt + senha;
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(saltedSenha.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void cadastrarUsuario(User usuario) {
        JSONArray jsonArray = lerUsuarios();

        jsonArray.add(usuario.toJSON());

        salvarUsuarios(jsonArray);

        System.out.println("Usuário cadastrado com sucesso.");
    }

    public static boolean autenticarUsuario(String nome, String senha) {
        JSONArray jsonArray = lerUsuarios();

        for (Object obj : jsonArray) {
            JSONObject jsonObject = (JSONObject) obj;
            String jsonNome = (String) jsonObject.get("nome");
            String storedHashedSenha = (String) jsonObject.get("senha");

            if (jsonNome.equals(nome)) {
                String[] parts = storedHashedSenha.split(":");
                String salt = parts[0];
                String storedHash = parts[1];
                if (storedHash.equals(hashSenhaComSalt(senha, salt))) {
                    return true;
                }
            }
        }

        return false;
    }

    private static JSONArray lerUsuarios() {
        JSONArray jsonArray = new JSONArray();
        JSONParser parser = new JSONParser();

        try (FileReader reader = new FileReader(FILENAME)) {
            Object obj = parser.parse(reader);
            if (obj instanceof JSONArray) {
                jsonArray = (JSONArray) obj;
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return jsonArray;
    }

    private static void salvarUsuarios(JSONArray jsonArray) {
        try (FileWriter file = new FileWriter(FILENAME)) {
            file.write(jsonArray.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}