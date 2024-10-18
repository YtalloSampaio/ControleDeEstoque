package org.exemplo.controle_estoque.controller;

import org.exemplo.controle_estoque.model.Produto;
import org.exemplo.controle_estoque.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @PostMapping
    public ResponseEntity<Produto> registrarProduto(@RequestBody Produto produto) {
        return ResponseEntity.ok( produtoService.registrarProduto( produto ) );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produto> atualizarProduto(@PathVariable Long id, @RequestBody Produto produto) {
        return ResponseEntity.ok( produtoService.atualizarProduto( id, produto ) );
    }

    @PostMapping("/{id}/baixa")
    public ResponseEntity<?> darBaixaProduto(@PathVariable Long id, @RequestParam Integer quantidade) {
        produtoService.darBaixaProduto( id, quantidade );
        return ResponseEntity.ok().build();
    }
}