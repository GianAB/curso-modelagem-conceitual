package com.gianprog.cursomc;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.gianprog.cursomc.domain.Categoria;
import com.gianprog.cursomc.domain.Cidade;
import com.gianprog.cursomc.domain.Cliente;
import com.gianprog.cursomc.domain.Endereco;
import com.gianprog.cursomc.domain.Estado;
import com.gianprog.cursomc.domain.ItemPedido;
import com.gianprog.cursomc.domain.Pagamento;
import com.gianprog.cursomc.domain.PagamentoComBoleto;
import com.gianprog.cursomc.domain.PagamentoComCartao;
import com.gianprog.cursomc.domain.Pedido;
import com.gianprog.cursomc.domain.Produto;
import com.gianprog.cursomc.domain.enums.EstadoPagamento;
import com.gianprog.cursomc.domain.enums.TipoCliente;
import com.gianprog.cursomc.repositories.CategoriaRepository;
import com.gianprog.cursomc.repositories.CidadeRepository;
import com.gianprog.cursomc.repositories.ClienteRepository;
import com.gianprog.cursomc.repositories.EnderecoRepository;
import com.gianprog.cursomc.repositories.EstadoRepository;
import com.gianprog.cursomc.repositories.ItemPedidoRepository;
import com.gianprog.cursomc.repositories.PagamentoRepository;
import com.gianprog.cursomc.repositories.PedidoRepository;
import com.gianprog.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner{
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;	

	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
			
	}

	@Override
	public void run(String... args) throws Exception {
		Categoria cat1 = new Categoria(null, "informática");
		Categoria cat2 = new Categoria(null, "escritório");
		
		Produto p1 = new Produto(null, "Computador", 2000.0);
		Produto p2 = new Produto(null, "Impressora", 800.0);
		Produto p3 = new Produto(null, "Mouse", 80.0);
		
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().add(cat1);
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().add(cat1);
		
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));
		
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		
		Cidade cid1 = new Cidade(null, "Uberlândia", est1);
		Cidade cid2 = new Cidade(null, "São Paulo", est2);
		Cidade cid3 = new Cidade(null, "Campinas", est2);
		
		est1.getCidades().add(cid1);
		est2.getCidades().addAll(Arrays.asList(cid2, cid3));
		
		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(cid1, cid2, cid3));
		
		Cliente cli1 = new Cliente(null, "gian", "gian@gmail.com", "03100066697", TipoCliente.PESSOA_FISICA);
		Cliente cli2 = new Cliente(null, "jade", "jade@gmail.com", "03166666697", TipoCliente.PESSOA_FISICA);
		Cliente cli3 = new Cliente(null, "maique", "maique@gmail.com", "69800066697", TipoCliente.PESSOA_JURIDICA);
		
		Endereco end1 = new Endereco(null, "Rua q", "100", "casa 2", "Centro", "39855-0000", cli2, cid1);
		Endereco end2 = new Endereco(null, "Rua zero", "2", null, "Centro", "39855-0000", cli1, cid1);
		Endereco end3 = new Endereco(null, "Av. rio", "12", null, "Centro", "39855-0000", cli1, cid2);
		Endereco end4 = new Endereco(null, "Rua asd", "9", "Ap. 2", "Centro", "01512-000", cli3, cid2);
		
		cli1.getEnderecos().addAll(Arrays.asList(end2, end3));
		cli2.getEnderecos().addAll(Arrays.asList(end1));
		cli3.getEnderecos().addAll(Arrays.asList(end4));
		
		cli1.getTelefones().add("(11) 9 9743-5750");
		cli2.getTelefones().add("(33) 9 8857-1665");
		cli3.getTelefones().add("(33) 9 8852-6233");
		cli1.getTelefones().add("(33) 9 8833-6545");
		
		clienteRepository.saveAll(Arrays.asList(cli1, cli2, cli3));
		enderecoRepository.saveAll(Arrays.asList(end1, end2, end3, end4));
		
		Pedido ped1 = new Pedido(null, Instant.parse("2017-09-30T10:32:00Z"), end2, cli1);
		cli1.getPedidos().add(ped1);
		Pedido ped2 = new Pedido(null, Instant.parse("2017-10-10T19:35:00Z"), end1, cli2);
		cli2.getPedidos().add(ped2);
		Pedido ped3 = new Pedido(null, Instant.parse("2018-07-10T10:00:00Z"), end4, cli3);
		cli3.getPedidos().add(ped3);
		
		Pagamento pag1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, 6);
		ped1.setPagamento(pag1);
		Pagamento pag2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, LocalDate.parse("2020-07-12"), null);
		ped2.setPagamento(pag2);
		Pagamento pag3 = new PagamentoComBoleto(null, EstadoPagamento.CANCELADO, LocalDate.parse("2020-08-01"), null);
		ped3.setPagamento(pag3);
		
		pag1.setPedido(ped1);
		pag2.setPedido(ped2);
		pag3.setPedido(ped3);
		
		pedidoRepository.saveAll(Arrays.asList(ped1, ped2, ped3));
		pagamentoRepository.saveAll(Arrays.asList(pag1, pag2, pag3));
		
		ItemPedido ip1 = new ItemPedido(p1, ped1, 00.0, 1, 2000.0);
		ItemPedido ip2 = new ItemPedido(p3, ped1, 00.0, 2, 80.0);
		ItemPedido ip3 = new ItemPedido(p2, ped2, 00.0, 2, 200.0);
		ItemPedido ip4 = new ItemPedido(p1, ped3, 00.0, 2, 4000.0);
		
		ped1.getItens().addAll(Arrays.asList(ip1, ip2));
		ped2.getItens().add(ip3);
		ped3.getItens().add(ip4);
		
		p1.getItens().addAll(Arrays.asList(ip1, ip4));
		p2.getItens().add(ip3);
		p3.getItens().add(ip2);
		
		itemPedidoRepository.saveAll(Arrays.asList(ip1, ip2, ip3, ip4));
//		pedidoRepository.saveAll(Arrays.asList(ped1, ped2, ped3));
//		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));
	}

}
