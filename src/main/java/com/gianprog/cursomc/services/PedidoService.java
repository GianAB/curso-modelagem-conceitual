package com.gianprog.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gianprog.cursomc.domain.Pedido;
import com.gianprog.cursomc.repositories.PedidoRepository;

import com.gianprog.cursomc.services.exception.ObjectNotFoundException;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository repository;
	
	public List<Pedido> findAll() {
		List<Pedido> lista = repository.findAll();
		return lista;
	}
	
	public Pedido findById(Integer id) throws ObjectNotFoundException{
		Optional<Pedido> obj = repository.findById(id);
		 return obj.orElseThrow(()-> new ObjectNotFoundException(
				 "Objeto não encontrado! Id: " + id + ", tipo: " + Pedido.class.getName()));
	}
}
