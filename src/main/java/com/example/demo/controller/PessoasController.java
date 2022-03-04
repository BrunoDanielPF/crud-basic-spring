package com.example.demo.controller;

import com.example.demo.model.PessoasModel;
import com.example.demo.service.PessoasService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("v1")
public class PessoasController {
    @Autowired
    PessoasService pessoasService;
    @GetMapping
    public ResponseEntity<Iterable<PessoasModel>> returnAllPessoas(){
        return ResponseEntity.ok(pessoasService.findAll());
    }
    @GetMapping("/{id}")
    public PessoasModel returnId(@PathVariable int id){
        return pessoasService.findById(id).orElseThrow(RuntimeException::new);
    }
    @PutMapping("/{id}")
    public ResponseEntity<PessoasModel> updatePessoa(@PathVariable int id, @Validated @RequestBody PessoasModel param) {
        PessoasModel pessoa = pessoasService.findById(id).orElseThrow(RuntimeException::new);
        pessoa.setNome(param.getNome());
        final PessoasModel updatepessoa = pessoasService.save(pessoa);
        return ResponseEntity.ok(updatepessoa);
    }
    @PostMapping
    public ResponseEntity newPessoa(@RequestBody PessoasModel param) throws URISyntaxException {
       PessoasModel dadoSalvo = pessoasService.save(param);
       return ResponseEntity.created(new URI("/clients" + dadoSalvo.getId())).body(dadoSalvo);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity deletePessoa(@PathVariable int id){
        pessoasService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
