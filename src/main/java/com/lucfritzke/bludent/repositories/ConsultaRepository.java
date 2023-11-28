package com.lucfritzke.bludent.repositories;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lucfritzke.bludent.domain.Consulta;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Long>{

    @Query(value ="select " +
    "c.cd_consulta as 'id', " +
    "c.dt_consulta as 'data', " +
    "c.hr_consulta as 'hora', " +
    "d.nm_dentista as 'nomeDentista', " +
    "p.ds_procedimento as 'descricao', " +
    "pa.nm_paciente as 'paciente', " +
    "pd.vl_procedimento as 'valor' " +
    "from " +
    "dentista d, " +
    "procedimento p, " +
    "procedimento_dentista pd, " +
    "consulta c, " +
    "paciente pa " +
    "where " +
    "d.cd_dentista = pd.cd_dentista " +
    "AND pd.cd_procedimento = p.cd_procedimento " +
    "AND c.procedimento_dentista_cd_dentista = d.cd_dentista " +
    "AND c.procedimento_dentista_cd_procedimento = p.cd_procedimento " +
    "AND c.cd_paciente = pa.cd_paciente " +
    "AND c.dt_consulta >= :data " +
    "AND c.procedimento_dentista_cd_dentista = :idDentista", nativeQuery=true)
    List<Object[]> getConsultaParam(@Param("data") LocalDate data, @Param("idDentista") long idDentista);
    
}
