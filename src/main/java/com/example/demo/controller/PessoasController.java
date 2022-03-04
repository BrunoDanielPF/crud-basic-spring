package com.example.demo.controller;

import com.example.demo.model.PessoasModel;
import com.example.demo.service.PessoasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("v1")
public class PessoasController {

    @Autowired
    PessoasService pessoasService;
    @GetMapping("/read")
    public ResponseEntity<Iterable<PessoasModel>> retornaTodosDados(){
        return ResponseEntity.ok(pessoasService.findAll());
    }
    @GetMapping("/{id}")
    public PessoasModel returnId(@PathVariable int id){
        return pessoasService.findById(id).orElseThrow(RuntimeException::new);
    }
    @PostMapping("/update")
    public ResponseEntity updatePessoas(@RequestBody PessoasModel param) throws URISyntaxException {
       PessoasModel dadoSalvo = pessoasService.save(param);
       return ResponseEntity.created(new URI("/clients" + dadoSalvo.getId())).body(dadoSalvo);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deletePessoa(@PathVariable int id){
        pessoasService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
