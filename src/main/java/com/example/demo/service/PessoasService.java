package com.example.demo.service;

import com.example.demo.model.PessoasModel;
import org.springframework.data.repository.CrudRepository;

public interface PessoasService extends CrudRepository<PessoasModel, Integer> {
}
