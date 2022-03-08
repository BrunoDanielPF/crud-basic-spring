package com.example.demo.dao;

import com.example.demo.model.PessoasModel;
import org.springframework.data.repository.CrudRepository;

public interface PessoasDao extends CrudRepository<PessoasModel, Integer> {
}
