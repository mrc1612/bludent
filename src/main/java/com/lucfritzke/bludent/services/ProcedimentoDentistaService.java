package com.lucfritzke.bludent.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.lucfritzke.bludent.domain.Dentista;
import com.lucfritzke.bludent.domain.Procedimento;
import com.lucfritzke.bludent.domain.ProcedimentoDentista;
import com.lucfritzke.bludent.domain.ProcedimentoDentistaId;
import com.lucfritzke.bludent.dto.ProcedimentoPorDentistaDTO;
import com.lucfritzke.bludent.exceptions.NotFoundException;
import com.lucfritzke.bludent.repositories.DentistaRepository;
import com.lucfritzke.bludent.repositories.ProcedimentoDentistaRepository;

@Service
public class ProcedimentoDentistaService {


    @Autowired
    private ProcedimentoDentistaRepository repository;

    @Autowired
    private DentistaRepository dRepository;


    public JpaRepository<ProcedimentoDentista, ProcedimentoDentistaId> repository() {
        return this.repository;
    }

    public ProcedimentoDentista create(ProcedimentoDentista entity){
        return repository().save(entity);
    }

    public List<Procedimento> getProcedimento() {
        List<Object[]> lp = repository.findProcedimentos();
        List<Procedimento> procedimentos = new ArrayList<>();
        for (Object[] o : lp) {
            Long id = (Long) o[0]; // Ajuste o tipo de dado conforme necessário
            String descricao = (String) o[1];
            
            Procedimento p = new Procedimento();
            p.setId(id);
            p.setDescricao(descricao);
            
            procedimentos.add(p);
        }
        return procedimentos;
    }

    public ProcedimentoPorDentistaDTO listarProcedimentosPorDentista(Long id){
        List<Object[]> listaP = new ArrayList<>();
        List<Procedimento> lp = new ArrayList<>();
        Optional<Dentista> d = dRepository.findById(id);
        if(d.isPresent()){
            listaP = repository.findProcedimentoByDentista(id);

            for (Object[] o : listaP) {
                Procedimento p = new Procedimento();
                p.setDescricao((String) o[1]);
                p.setId((Long)o[0]);
                lp.add(p);
            }
        }
        ProcedimentoPorDentistaDTO pDTo = new ProcedimentoPorDentistaDTO();
        pDTo.setCd_dentista(id);
        pDTo.setNm_dentista(d.get().getNome());
        pDTo.setProcedimentos(lp);
        
        return pDTo;
    }

    public ProcedimentoDentista findById(ProcedimentoDentistaId id) {

        return repository().findById(id).orElseThrow(() ->  new NotFoundException("Entidade não encontrada"));

    }

    public ProcedimentoDentista update(ProcedimentoDentista entity) {
        return repository.save(entity);
    }

    public List<ProcedimentoDentista> findDentistaById(Dentista d) {
        return repository.findByDentista(d);
    }

    public void delete(ProcedimentoDentistaId id) {
        repository().delete(
                 repository().findById(id)
                         .orElseThrow(() -> new NotFoundException("{ \"status\" : \"ERRO\", \"mensagem\" : \"Entidade não existe\"}"))
         );
     }
}
