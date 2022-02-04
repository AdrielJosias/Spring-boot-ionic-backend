package com.adrieljosias.cursomc.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adrieljosias.cursomc.domain.ItemPedido;
import com.adrieljosias.cursomc.domain.PagamentoComBoleto;
import com.adrieljosias.cursomc.domain.Pedido;
import com.adrieljosias.cursomc.domain.enums.EstadoPagamento;
import com.adrieljosias.cursomc.repositories.ItemPedidoRepository;
import com.adrieljosias.cursomc.repositories.PagamentoRepository;
import com.adrieljosias.cursomc.repositories.PedidoRepository;
import com.adrieljosias.cursomc.services.exceptions.ObjectNotFoundException;

import ch.qos.logback.core.net.SyslogOutputStream;

@Service
public class PedidoService {

	//essa classe esta acessando a classe PedidoResource que é Objeto acessa aos dados
	@Autowired   //Intancia automaticamente
	private PedidoRepository repo;
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	private ProdutoService produtoService;
		
	@Autowired
	private ClienteService clienteService;
	
	//operacao que busca uma categoria por codigo
	public Pedido find(Integer id) {
		 Optional<Pedido> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}
	
	@Transactional
	public Pedido insert(Pedido obj) {
		obj.setId(null);//inserindo novo pedido
		obj.setInstante(new Date());//cria nova data com instante atual
		obj.setCliente(clienteService.find(obj.getCliente().getId()));
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);//pedido recem add ainda esta pendente
		obj.getPagamento().setPedido(obj); //o pagamento tem que conhecer o pedido
		if (obj.getPagamento() instanceof PagamentoComBoleto) {//criar boleto com data de vencimento BoletoService
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
		}
		obj = repo.save(obj);//salvar o pedido no banco
		pagamentoRepository.save(obj.getPagamento());//salvar o pagamento no banco
		for (ItemPedido ip : obj.getItens()) {//for percorrer a todo os itens de pedido associado ao obj.getitens, salvar os itens de pedido
			ip.setDesconto(0.0);//desconto 0
			ip.setProduto(produtoService.find(ip.getProduto().getId()));
			ip.setPreco(ip.getProduto().getPreco());//pegando o preço do produto
			ip.setPedido(obj);
		}
		itemPedidoRepository.saveAll(obj.getItens());//salvar itens no banco
		System.out.println(obj);
		return obj;
	}
}
