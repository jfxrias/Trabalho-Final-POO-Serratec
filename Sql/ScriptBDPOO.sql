-- Criação da tabela de usuários (Clientes e Funcionários)
create type cargo_func as enum ('gerente', 'diretor','presidente');
create type conta_tipo as enum ('corrente','poupanca');
create type tipo_operacao as enum ('saque', 'deposito','transferencia');

create table if not exists clientes (
    cpf varchar(11) primary key,
    nome varchar(100) not null,
    senha varchar(20) not null
);
	
create table if not exists funcionarios (
	cpf varchar(11) primary key, 
	nome varchar(100) not null, 
	senha varchar(20) not null,
	cargo cargo_func not null, 
	agencia int
); 

create table if not exists contas (
	id serial primary key,
	cpf_titular varchar(11) not null,
	saldo numeric(10,2) not null default 0,
	agencia int not null, 
	tipo conta_tipo not null,
	
	constraint fk_conta_cliente
	foreign key (cpf_titular) references clientes(cpf)
);

create table if not exists operacoes (
	id serial primary key,
	tipo tipo_operacao not null,
	valor numeric(10,2) not null, 
	cpf_origem varchar(11),
	cpf_destino varchar(11), 
	data timestamp default current_timestamp, 
	
	constraint fk_origem
	foreign key (cpf_origem) references clientes(cpf),

	constraint fk_destino
	foreign key (cpf_destino) references clientes(cpf)
);

create table if not exists seguro (
	id serial primary key,
	cpf_cliente varchar(11) not null, 
	valor numeric(10,2) not null,

	constraint fk_seguro_cliente 
	foreign key (cpf_cliente) references clientes(cpf)		
);

-- inserts

insert into clientes (cpf, nome, senha) values
('11111111111', 'Daniel Valle', '123'),
('22222222222', 'Maria Silva', '234'),
('33333333333', 'Pedro Souza', '456')
;

-- FUNCIONARIOS
insert into funcionarios (cpf, nome, senha, cargo, agencia) values
('44444444444', 'Joao Gerente', '789', 'gerente', 1),
('55555555555', 'Ana Diretora', '987', 'diretor', null),
('66666666666', 'Carlos Presidente', '654', 'presidente', null)
;

-- CONTAS
insert into contas (cpf_titular, saldo, agencia, tipo) values
('11111111111', 1000.00, 1, 'corrente'),
('22222222222', 500.00, 1, 'poupanca'),
('33333333333', 200.00, 2, 'corrente')
;

-- OPERACOES
insert into operacoes (tipo, valor, cpf_origem, cpf_destino) values
('saque', 100.00, '11111111111', null),
('deposito', 200.00, null, '22222222222'),
('transferencia', 150.00, '11111111111', '22222222222'),
('transferencia', 50.00, '22222222222', '33333333333')
;

-- SEGURO
insert into seguro (cpf_cliente, valor) values
('11111111111', 10000.00),
('22222222222', 5000.00)
;

-- Detalhes completos:
select '--- Clientes ---'  as info;  
select * from clientes;
select '--- Contas ---'  as info;  
select * from contas;
select '--- Funcionários ---'  as info;
select * from funcionarios;





