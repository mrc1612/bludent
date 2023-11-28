

/* select * from cidade; */
/*select * from procedimento;*/

select * from dentista;
select * from procedimento;
select * from paciente;
select * from procedimento_dentista;
/*sds*/
select  distinct p.cd_procedimento, p.ds_procedimento from procedimento p inner join procedimento_dentista pd on p.cd_procedimento = pd.cd_procedimento;




select p.cd_procedimento as 'id' , p.ds_procedimento as 'descricao'
from procedimento p, procedimento_dentista pd
where p.cd_procedimento = pd.cd_procedimento
and pd.cd_dentista = 5;

select * from procedimento_dentista;
select * from procedimento;

select * from procedimento_dentista pd where pd.cd_dentista = 2;
select * from consulta;
select * from paciente;
select * from users;
select * from dentista;

select c.cd_consulta as 'id', c.dt_consulta as 'data', c.hr_consulta as 'hora', 
			d.nm_dentista as 'nomeDentista', p.ds_procedimento as 'descricao', pa.nm_paciente as 'paciente', pd.vl_procedimento as 'valor'
from dentista d, procedimento p, procedimento_dentista pd, consulta c, paciente pa
where d.cd_dentista = pd.cd_dentista 
	AND pd.cd_procedimento = p.cd_procedimento
    AND c.procedimento_dentista_cd_dentista = d.cd_dentista
    AND c.procedimento_dentista_cd_procedimento = p.cd_procedimento
    AND c.cd_paciente = pa.cd_paciente
    AND c.dt_consulta >= "2023-11-23"
    AND c.procedimento_dentista_cd_dentista = 3