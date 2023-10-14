package br.inatel.labs.labjpa.service;

import br.inatel.labs.labjpa.entity.NotaCompra;
import br.inatel.labs.labjpa.entity.NotaCompraItem;
import br.inatel.labs.labjpa.repository.NotaCompraItemRepository;
import br.inatel.labs.labjpa.repository.NotaCompraRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class NotaCompraService {

    @Autowired
    private NotaCompraRepository notaCompraRepository;

    @Autowired
    private NotaCompraItemRepository notaCompraItemRepository;

    public NotaCompra salvar(NotaCompra nc) {
        return notaCompraRepository.save(nc);
    }

    public Optional<NotaCompra> buscarNotaCompraPeloId(Long id) {
        return notaCompraRepository.findById(id);
    }

    public NotaCompra buscarNotaCompraPeloIdComListaItem(Long id) {
        Optional<NotaCompra> notaCompra = notaCompraRepository.findById(id);
        if (notaCompra.isPresent()) {
            NotaCompra nc = notaCompra.get();
            nc.getListaNotaCompraItem().size();
            return nc;
        } else {
            throw new RuntimeException("Nenhuma nota compra encontrada");
        }
    }

    public List<NotaCompra> listaNotaCompra() {
        return notaCompraRepository.findAll();
    }


    public NotaCompraItem salvar(NotaCompraItem item) {
        return notaCompraItemRepository.save(item);
    }

    public Optional<NotaCompraItem> buscarNotaCompraItemPeloId(Long id) {
        return notaCompraItemRepository.findById(id);
    }

    public List<NotaCompraItem> listaNotaCompraItem() {
        return notaCompraItemRepository.findAll();
    }
}
