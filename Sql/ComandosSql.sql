

/* select * from cidade; */
/*select * from procedimento;*/

select * from dentista;
select * from procedimento;
select * from paciente;
select * from procedimento_dentista;
/*sds*/
select  distinct p.cd_procedimento, p.ds_procedimento from procedimento p inner join procedimento_dentista pd on p.cd_procedimento = pd.cd_procedimento;