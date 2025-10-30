package com.login.exemplo.repository;

import com.login.exemplo.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Interface de repositório para gerenciar operações de banco de dados de Usuários.
 * Estende JpaRepository que já fornece métodos prontos como:
 * - save() : para salvar um usuário
 * - findAll() : para buscar todos usuários
 * - findById() : para buscar um usuário pelo ID
 * - delete() : para excluir um usuário
 *
 * @Repository marca esta interface como um componente de repositório do Spring
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    /**
     * Método personalizado que busca um usuário pelo email.
     * O Spring Data JPA cria automaticamente a implementação baseada no nome do método.
     *
     * @param email email do usuário que será buscado no banco de dados
     * @return objeto Usuario se encontrado, ou null se não existir
     */
    Usuario findByEmail(String email);
    Usuario findById(int id);

//    Iterable<Integer> id(int id);



}
