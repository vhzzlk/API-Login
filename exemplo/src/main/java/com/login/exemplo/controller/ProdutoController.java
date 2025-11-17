package com.login.exemplo.controller;


import com.login.exemplo.dto.ProdutoRequestDTO;
import com.login.exemplo.entity.Produto;
import com.login.exemplo.repository.ProdutoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("produto")
public class ProdutoController {

    @Autowired
    ProdutoRepository produtoRepository;
    //Busca por id
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarId(@PathVariable int id){
        Optional<Produto> produto = produtoRepository.findById(id);
        if (produto.isPresent()){
        return ResponseEntity.ok(produto);

        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario Não Encontrado");
        }

    }

    //Busca todos
    @GetMapping("/all")
    public ResponseEntity<?> buscarTodos(@Valid ProdutoRequestDTO produto){


        return ResponseEntity.ok(produtoRepository.findAll());
    }

    //criar produto
    @PostMapping("/criar")
    public ResponseEntity<?> criarProduto(@Valid @RequestBody ProdutoRequestDTO produto){
        Produto novo = new Produto(
                produto.getName(),produto.getPrice(),produto.getQuantity());

        return ResponseEntity.status(HttpStatus.CREATED).body(produtoRepository.save(novo));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable int id){
        boolean exists = produtoRepository.existsById(id);
        if(exists){
            produtoRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Deletado com sucesso");
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto Não Encontrado");
        }

    }
    @PutMapping("/{id}")
    public ResponseEntity<?> alterarProduto(@PathVariable int id, @RequestBody Produto produto){
        Optional<Produto> novo = produtoRepository.findById(id);
               if (novo.isPresent()){
                   Produto produto1 = novo.get();
                   produto1.setName(produto.getName());
                   produto1.setPrice(produto.getPrice());
                   produto1.setQuantity(produto.getQuantity());
                   produtoRepository.save(produto1);
                   return ResponseEntity.ok(produto1);
               }
               else{
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Este ID não existe");

               }
    }

}
