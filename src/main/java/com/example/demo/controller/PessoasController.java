package com.example.demo.controller;

import com.example.demo.dao.PessoasDao;
import com.example.demo.model.PessoasModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("v1")
public class PessoasController {
    @Autowired
    PessoasDao dao;
    @GetMapping
    public ResponseEntity<Iterable<PessoasModel>> returnAllPessoas(){
        try{
        return ResponseEntity.ok(dao.findAll());
        }catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity returnId(@PathVariable int id){
        return ResponseEntity.ok(dao.findById(id).orElseThrow(RuntimeException::new));
    }
    @PutMapping("/{id}")
    public ResponseEntity updatePessoa(@PathVariable int id, @Validated @RequestBody PessoasModel param) {
       return dao.findById(id).map(pessoa -> {
            pessoa.setNome(param.getNome());
            PessoasModel updatePessoa = dao.save(pessoa);
            return ResponseEntity.ok().body(updatePessoa);
        }).orElseThrow(RuntimeException::new);
    }
    @PostMapping
    public ResponseEntity newPessoa(@RequestBody PessoasModel param) {
        try {
            PessoasModel dadoSalvo = dao.save(param);
            return ResponseEntity.ok().body(dadoSalvo);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
    @DeleteMapping("/{id}")
    public ResponseEntity deletePessoa(@PathVariable int id){
        try{
            dao.deleteById(id);
            return ResponseEntity.ok().build();
        }catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
