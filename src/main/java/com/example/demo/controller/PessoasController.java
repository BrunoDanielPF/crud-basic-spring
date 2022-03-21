package com.example.demo.controller;

import com.example.demo.model.PessoasModel;
import com.example.demo.repository.PessoasRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("v1")
@Api(value = "api rest Pessoas")
public class PessoasController {
    @Autowired
    PessoasRepository pessoasRepository;

    @ApiOperation(value = "retorna lista de pessoas")
    @GetMapping
    public ResponseEntity<Iterable<PessoasModel>> returnAllPessoas(){
        try{
        return ResponseEntity.ok(pessoasRepository.findAll());
        }catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @ApiOperation(value = "retorna uma pessoa")
    @GetMapping("/{id}")
    public ResponseEntity returnId(@PathVariable int id){
        return ResponseEntity.ok(pessoasRepository.findById(id).orElseThrow(RuntimeException::new));
    }
    @ApiOperation(value = "Atualiza uma pessoa")
    @PutMapping("/{id}")
    public ResponseEntity updatePessoa(@PathVariable int id, @Validated @RequestBody PessoasModel param) {
       return pessoasRepository.findById(id).map(pessoa -> {
            pessoa.setNome(param.getNome());
            PessoasModel updatePessoa = pessoasRepository.save(pessoa);
            return ResponseEntity.ok().body(updatePessoa);
        }).orElseThrow(RuntimeException::new);
    }
    @ApiOperation(value = "Salva uma pessoa")
    @PostMapping
    public ResponseEntity newPessoa(@RequestBody PessoasModel param) {
        try {
            PessoasModel dadoSalvo = pessoasRepository.save(param);
            return ResponseEntity.ok().body(dadoSalvo);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
    @ApiOperation(value = "deleta uma pessoa")
    @DeleteMapping("/{id}")
    public ResponseEntity deletePessoa(@PathVariable int id){
        try{
            pessoasRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
