# **TesteAPI**


#**Automação de Testes de API - Desafio**
Este projeto realiza automação de testes de API utilizando Java e RestAssured. Os testes abrangem validações funcionais, de contrato e fluxos de autenticação na API DummyJSON.

#**Tecnologias Utilizadas**
- Java 8+
- RestAssured (Framework para testes de API)
- JUnit (Framework de testes)
- Maven (Gerenciador de dependências)


#**Pré-requisitos**
- Java instalado (versão 8 ou superior).
- Maven configurado e instalado.
- Um IDE de sua preferência, como IntelliJ IDEA ou Eclipse.


#**Configuração do Projeto**
#**1. Clonar o Repositório**
Clone este repositório para a sua máquina local:

`git clone https://gitlab.com/desafio7450604/testeapi.git`

#**2. Importar o Projeto**
Abra o projeto no IntelliJ IDEA, Eclipse ou outro IDE.
Certifique-se de que o Maven carregue todas as dependências do projeto.

#**3. Estrutura de Pastas**
src/test/java/com/desafio/api: Contém as classes de teste.
src/test/resources/schemas: Contém os arquivos de contrato JSON Schema.

Execução dos Testes

#**1. Via IDE**
Abra a classe DesafioTest.java.
Clique com o botão direito em qualquer teste (ou na classe) e selecione Run.

#**2. Via Maven**

`mvn clean test`

#**Cenários de Teste Implementados**

#**Funcionais**


- Verificar o status da aplicação:
Testa a rota /test para validar se a API está respondendo corretamente.

-Autenticação de Usuários:
	- Login com sucesso.
	- Login sem senha (erro esperado).

-Validação de Produtos:
	- Buscar lista de produtos.
	- Buscar produto por ID.
	- Criar novo produto com sucesso.

	
#**Validações de Contrato**

	- Valida as respostas das rotas /products/add, /users e /products/1 com JSON Schema.
	
#**Testes Negativos**
	- -Token inválido.
	- -Produto com ID inexistente.
	- -Login sem senha


#**Como Abrir o Relatório Allure**

#1.**Baixar o Allure**
Acesse o link oficial do Allure Framework:
[link](https://github.com/allure-framework/allure2/releases.)

#**2.Clique na versão mais recente (exemplo: 2.13.5).**
#**3.Faça o download do arquivo .zip na seção Assets.**

#**2. Configurar o Allure no Windows**

1.Extraia o conteúdo do arquivo .zip para uma pasta no seu computador (ex.: C:\allure).
2.Adicione o caminho da pasta bin do Allure à variável de ambiente PATH

	-Abra o Menu Iniciar e procure por "Editar variáveis de ambiente do sistema".
	-Clique em "Variáveis de Ambiente".
	-Na seção Variáveis do Sistema, selecione a variável Path e clique em Editar.
	-Clique em Novo e adicione o caminho completo até a pasta bin (ex.: C:\allure\bin).

	
3.Clique em OK para salvar.

#**3. Verificar se o Allure está Configurado**

1. Abra o Prompt de Comando (CMD).
2. Digite o comando: `allure --version`
3. Se o Allure estiver instalado corretamente, ele exibirá a versão instalada no terminal.


#**4. Executar os Testes com Maven**

1.Rode os testes automatizados e gere os relatórios com o Maven:
  
 	 `mvn surefire:test`
 
2.Certifique-se de que os resultados dos testes estão sendo gerados no diretório target/surefire-reports ou equivalente.


#**5. Gerar e Abrir o Relatório Allure**

1.Gere o relatório Allure apontando para os resultados do Maven: allure serve target/surefire-reports

Esse comando irá:
	-Gerar o relatório Allure com base nos resultados dos testes.
	-Abrir automaticamente o relatório no navegador padrão do Windows.


#**POSSÍVEIS BUGS/MELHORIAS**
1. Criação de Token: Alterar o status HTTP para 201 Created.
2. Autenticação na Criação de Produto: Exigir um token válido na rota POST /products/add.
3. Criação de Usuários com Permissões: Implementar uma rota para criar usuários com níveis de acesso diferentes.
4. Validação de Campos Numéricos: Garantir que price e discountPercentage aceitem apenas valores numéricos.

**OBS:**
 Essas melhorias aumentam a consistência, a segurança e a robustez da API, além de facilitar testes e  validações.