package br.inatel.labs.lab.rest.server;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import br.inatel.labs.lab.rest.server.model.Produto;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProdutoControllerTest {

	@Autowired
	private WebTestClient webTestClient;
	
	@Test
	void dadoProdutoIdInvalido_quandoDeleteProdutoPeloId_entaoRespondeComStatusNotFound() {
		Long produtoIdInvalido = 99L;
		
		webTestClient.delete()
			.uri("/produto/" + produtoIdInvalido)
			.exchange()
			.expectStatus()
				.isNotFound();dd
	}
	
	
	@Test
	void dadoProdutoIdValido_quandoDeleteProdutoPeloId_entaoRespondeComStatusNoContent() {
		Long produtoValido = 2L;
		
		webTestClient.delete()
			.uri("/produto/" + produtoValido)
			.exchange()
			.expectStatus()
				.isNoContent();
	}
	
	@Test
	void dadoProdutoExistente_quandoPutProduto_entaoRespondeComStatusNoContent() {
		Produto produtoExistente = new Produto();
		produtoExistente.setId(1L);
		produtoExistente.setDescricao("Furadeira a bateria");
		produtoExistente.setPreco(new BigDecimal(800.00));
		
		webTestClient.put()
			.uri("/produto")
			.bodyValue(produtoExistente)
			.exchange()
			.expectStatus()
				.isNoContent();
	}
	
	@Test
	void dadoNovoProduto_quandoPostProduto_entaoRespondeComStatusCreatedEProdutoValido() {
		Produto novoProduto = new Produto();
		novoProduto.setDescricao("Tupia de Mesa");
		novoProduto.setPreco(new BigDecimal(9000.00));
		
		Produto produtoRespondido = webTestClient.post()
				.uri("/produto")
				.bodyValue(novoProduto)
				.exchange()
				.expectStatus()
					.isCreated()
				.expectBody(Produto.class)
					.returnResult()
					.getResponseBody();
		
		assertThat(produtoRespondido).isNotNull();
		assertThat(produtoRespondido.getId()).isNotNull();
	}
	
	
	
	@Test
	void dadoProdutoIdInvalido_quandoGetProdutoPeloId_entaoRespondeComStatutsNotFound() {
		Long idInvalido = 99L;
		
		webTestClient.get()
			.uri("/produto/" + idInvalido)
			.exchange()
			.expectStatus().isNotFound();
	}
	
	
	@Test
	void dadoProdutoIdValido_quandoGetProdutoPeloId_entaoRespondeComProdutoValido() {
		Long produtoIdValido = 1L;
		
		Produto produtoRespondido = webTestClient.get()
				.uri("/produto/" + produtoIdValido)
				.exchange()
				.expectStatus().isOk()
				.expectBody(Produto.class)
					.returnResult()
					.getResponseBody();
		
		assertNotNull(produtoRespondido);
		assertEquals(produtoRespondido.getId(), produtoIdValido);
		
	}
	
	
	@Test
	void deveListarProdutos() {
		webTestClient.get()
			.uri("/produto")
			.exchange()
			.expectStatus()
				.isOk()
			.expectBody()
				.returnResult()
			;
	}

}
