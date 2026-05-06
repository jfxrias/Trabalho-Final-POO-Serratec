# 🏦 Sistema Bancário — Trabalho Final de POO

> Projeto desenvolvido como trabalho final da disciplina de **Programação Orientada a Objetos** na **Residência em TIC Software do Serratec**.

---

## 📋 Sobre o Projeto

Sistema bancário em linha de comando (CLI) desenvolvido em **Java**, com integração a banco de dados **PostgreSQL**. O sistema simula operações reais de um banco, com diferentes níveis de acesso para clientes e funcionários.

---

## ✨ Funcionalidades

### 👤 Cliente
- Login autenticado por CPF e senha
- Escolha entre **Conta Corrente** e **Conta Poupança**
- **Movimentações:** Saque, Depósito e Transferência (com taxas automáticas)
- **Relatórios gerados em `.txt`:**
  - Saldo atual
  - Tributações e taxas acumuladas
  - Simulação de rendimento (Poupança)
  - Contratação e consulta de Seguro de Vida

### 🏢 Funcionário (Gerente / Diretor / Presidente)
- Login com nível de acesso diferenciado por cargo
- Realiza as mesmas operações bancárias
- **Relatórios exclusivos por cargo:**
  - **Gerente:** Total de contas na agência
  - **Diretor:** Lista de todos os clientes do sistema
  - **Presidente:** Capital total armazenado no banco + todos os relatórios do Diretor

---

## 🧱 Conceitos de POO Aplicados

| Conceito | Aplicação |
|---|---|
| **Herança** | `ContaCorrente` e `ContaPoupanca` herdam de `Conta` (abstract); `Gerente`, `Diretor` e `Presidente` herdam de `Funcionario` (abstract) |
| **Abstração** | Classes `Conta` e `Funcionario` são abstratas, definindo contratos para as subclasses |
| **Encapsulamento** | Todos os atributos privados com getters e setters controlados |
| **Polimorfismo** | Métodos `sacar()`, `depositar()` e `transferir()` sobrescritos com comportamentos diferentes em `ContaCorrente` |
| **Interface** | `Controlador` implementada por `Menu`, padronizando o ponto de entrada do sistema |
| **Pacotes** | Organização em `cliente`, `funcionario`, `menu`, `main` e `pacoteConexao` |

---

## 🗂️ Estrutura do Projeto

```
src/
├── cliente/
│   ├── Cliente.java          # Entidade do cliente
│   ├── Conta.java            # Classe abstrata base
│   ├── ContaCorrente.java    # Com taxa por operação (R$0,10/R$0,20)
│   └── ContaPoupanca.java    # Com simulação de rendimento
├── funcionario/
│   ├── Funcionario.java      # Classe abstrata base
│   ├── Gerente.java          # Acesso por agência
│   ├── Diretor.java          # Acesso a dados de clientes
│   └── Presidente.java       # Acesso ao capital total do banco
├── menu/
│   ├── Controlador.java      # Interface do menu
│   └── Menu.java             # Lógica principal e fluxo da aplicação
├── main/
│   └── Main.java             # Ponto de entrada
└── pacoteConexao/
    └── Conexao.java          # Conexão JDBC com PostgreSQL
Sql/
├── ScriptBDPOO.sql           # Script de criação e população do banco
└── postgresql-42.7.9.jar     # Driver JDBC
```

---

## 🛠️ Tecnologias Utilizadas

- **Java** (POO / OOP)
- **PostgreSQL** (banco de dados relacional)
- **JDBC** com `PreparedStatement` (prevenção de SQL Injection)
- **Eclipse IDE**

---

## 🗄️ Modelagem do Banco de Dados

O banco possui 5 tabelas com relacionamentos via chaves estrangeiras:

```
clientes (cpf PK, nome, senha)
    ↓
contas (id PK, cpf_titular FK, saldo, agencia, tipo)
    ↓
operacoes (id PK, tipo, valor, cpf_origem FK, cpf_destino FK, data)

funcionarios (cpf PK, nome, senha, cargo, agencia)
seguro (id PK, cpf_cliente FK, valor)
```

Tipos enumerados (PostgreSQL `ENUM`): `cargo_func`, `conta_tipo`, `tipo_operacao`.

---

## ▶️ Como Executar

### Pré-requisitos
- Java 11+
- PostgreSQL instalado e rodando
- Eclipse IDE (ou outro de sua preferência)

### Passo a passo

1. **Clone o repositório**
   ```bash
   git clone https://github.com/jfxrias/Trabalho-Final-POO-Serratec.git
   ```

2. **Configure o banco de dados**
   - Crie o banco no PostgreSQL e execute o script:
   ```bash
   psql -U postgres -f Sql/ScriptBDPOO.sql
   ```

3. **Configure a conexão**
   - Edite `src/pacoteConexao/Conexao.java` e defina sua senha do PostgreSQL:
   ```java
   private static final String SENHA = "sua_senha_aqui";
   ```

4. **Adicione o driver JDBC ao classpath**
   - No Eclipse: `Botão direito no projeto → Build Path → Add External JARs → selecione o arquivo postgresql-42.7.9.jar`

5. **Execute `Main.java`**

### Usuários de teste (já inseridos pelo script)

| Tipo | CPF | Senha |
|---|---|---|
| Cliente | 11111111111 | 123 |
| Cliente | 22222222222 | 234 |
| Gerente | 44444444444 | 789 |
| Diretor | 55555555555 | 987 |
| Presidente | 66666666666 | 654 |

---

## 👥 Autores

Desenvolvido durante a **Residência em TIC Software — Serratec**, Turma 04.

| Nome |
|---|
| [João Gabriel Farias Machado](https://www.linkedin.com/in/jfxrias/) |
| Ana Paula Pimenta |
| [Leonardo De Mattos Veiga](https://www.linkedin.com/in/leonardo-de-mattos-veiga-736a30271/) |
| Daniel Valle |
| [Emily Neves](https://www.linkedin.com/in/emilyneves2002/) |

---

## 📄 Licença

Este projeto é de caráter acadêmico e está disponível para consulta e aprendizado.
