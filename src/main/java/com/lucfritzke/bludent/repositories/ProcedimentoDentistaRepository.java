package com.lucfritzke.bludent.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lucfritzke.bludent.domain.Dentista;
import com.lucfritzke.bludent.domain.ProcedimentoDentista;
import com.lucfritzke.bludent.domain.ProcedimentoDentistaId;

@Repository
public interface ProcedimentoDentistaRepository extends JpaRepository<ProcedimentoDentista, ProcedimentoDentistaId> {

    @Query(value = "select  distinct p.cd_procedimento, p.ds_procedimento from procedimento p inner join procedimento_dentista pd on p.cd_procedimento = pd.cd_procedimento;", nativeQuery = true)
    List<Object[]> findProcedimentos();

    @Query(value = "select p.cd_procedimento as 'id', p.ds_procedimento as 'descricao' " +
            "from procedimento p, procedimento_dentista pd " +
            "where p.cd_procedimento = pd.cd_procedimento " +
            "and pd.cd_dentista = :dentistaId", nativeQuery = true)
    List<Object[]> findProcedimentoByDentista(@Param("dentistaId") Long id);

    @Query(value = " ", nativeQuery = true)
    
    List<ProcedimentoDentista> findByDentista(Dentista dentista);
}
