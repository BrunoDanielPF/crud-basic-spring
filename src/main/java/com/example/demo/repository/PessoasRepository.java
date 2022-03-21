package com.example.demo.repository;

import com.example.demo.model.PessoasModel;
import org.springframework.data.repository.CrudRepository;

public interface PessoasRepository extends CrudRepository<PessoasModel, Integer> {
}
