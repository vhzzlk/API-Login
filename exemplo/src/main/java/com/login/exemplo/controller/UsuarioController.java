package com.login.exemplo.controller;

import com.login.exemplo.entity.Usuario;
import com.login.exemplo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;


@RestController
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class UsuarioController {

    private static final Logger log = LoggerFactory.getLogger(UsuarioController.class);

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping(value = "Vitinho")
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }


    @PostMapping(value = "/signup", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> signup(@RequestBody Usuario user) {
        Map<String, Object> resp = new HashMap<>();

        String email = user.getEmail() == null ? null : user.getEmail().trim().toLowerCase(Locale.ROOT);
        String password = user.getPassword() == null ? null : user.getPassword().trim();
        String name = user.getName() == null ? null : user.getName().trim();

        if (email == null || email.isBlank()
                || password == null || password.isBlank()
                || name == null || name.isBlank()) {
            resp.put("message", "Dados inválidos");
            return ResponseEntity.badRequest().body(resp);
        }

        Usuario existente = usuarioRepository.findByEmail(email);
        if (existente != null) {
            resp.put("message", "Email já cadastrado");
            return ResponseEntity.status(409).body(resp);
        }

        Usuario novo = new Usuario(name, email, password);
        usuarioRepository.save(novo);
        log.info("Usuário cadastrado: email={}, id={}", email, novo.getId());

        resp.put("message", "Cadastro realizado com sucesso!");
        resp.put("token", UUID.randomUUID().toString());
        return ResponseEntity.ok(resp);
    }

    @PostMapping(value = "/login", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> login(@RequestBody Usuario user) {
        Map<String, Object> resp = new HashMap<>();

        String email = user.getEmail() == null ? null : user.getEmail().trim().toLowerCase(Locale.ROOT);
        String password = user.getPassword() == null ? null : user.getPassword().trim();

        if (email == null || email.isBlank() || password == null || password.isBlank()) {
            resp.put("message", "Credenciais inválidas");
            return ResponseEntity.badRequest().body(resp);
        }

        Usuario encontrado = usuarioRepository.findByEmail(email);
        if (encontrado == null) {
            log.warn("Login falhou: usuário não encontrado para email={}", email);
            resp.put("message", "Usuário não encontrado");
            return ResponseEntity.status(401).body(resp);
        }

        // 1) Comparação direta
        if (password.equals(encontrado.getPassword())) {
            log.info("Login OK: email={}", email);
            resp.put("message", "Login realizado com sucesso!");
            resp.put("token", UUID.randomUUID().toString());
            return ResponseEntity.ok(resp);
        }

        // 2) Tolerância: muitos casos antigos salvaram a senha no campo 'name' por engano
        if (password.equals(encontrado.getName())) {
            log.warn("Detectado cadastro antigo com campos invertidos para email={}. Corrigindo senha no banco.", email);
            try {
                encontrado.setPassword(password);
                usuarioRepository.save(encontrado);
                resp.put("message", "Login realizado com sucesso!");
                resp.put("token", UUID.randomUUID().toString());
                return ResponseEntity.ok(resp);
            } catch (Exception e) {
                log.error("Falha ao corrigir senha para email={}", email, e);
            }
        }

        // 3) Tolerância: se a senha salva tiver espaços, tenta comparar com trim
        String stored = encontrado.getPassword();
        if (stored != null && password.equals(stored.trim())) {
            log.warn("Senha com espaços detectada para email={}. Normalizando no banco.", email);
            try {
                encontrado.setPassword(stored.trim());
                usuarioRepository.save(encontrado);
                resp.put("message", "Login realizado com sucesso!");
                resp.put("token", UUID.randomUUID().toString());
                return ResponseEntity.ok(resp);
            } catch (Exception e) {
                log.error("Falha ao normalizar senha para email={}", email, e);
            }
        }

        log.warn("Login falhou: senha incorreta para email={}", email);
        resp.put("message", "Credenciais inválidas. Tente novamente.");
        return ResponseEntity.status(401).body(resp);
    }

    @GetMapping(value = "/verify", produces = "application/json")
    public ResponseEntity<?> verify(@RequestHeader(value = "Authorization", required = false) String authorization) {
        Map<String, Object> resp = new HashMap<>();
        if (authorization != null && authorization.startsWith("Bearer ")) {
            resp.put("message", "Token válido");
            return ResponseEntity.ok(resp);
        }
        resp.put("message", "Token inválido");
        return ResponseEntity.status(401).body(resp);
    }

    @GetMapping(value = "{id}")
        public Usuario usuariosId(@PathVariable int id){
        Usuario findId = usuarioRepository.findById(id);
        return findId;
        }

     @DeleteMapping(value = "deletar/{id}")
    public ResponseEntity<?> deletar(@PathVariable int id){
        Usuario findId = usuarioRepository.findById(id);
        usuarioRepository.delete(findId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Deletado com sucesso");
     }

}