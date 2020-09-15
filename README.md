# desafio-samba-tech

> Status do Projeto: Em desenvolvimento :warning:

## Configuração

Para executar o projeto, será necessário instalar os seguintes programas:

- [JDK 11: Necessário para executar o projeto Java](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
- [Maven 3.5.4: Necessário para realizar o build do projeto Java](https://maven.apache.org/docs/3.5.4/release-notes.html)
- [Spring Tools 4 for Eclipse: Para desenvolvimento do projeto](https://spring.io/tools)
- [H2: Conexão com o banco de dados em memória para testes](https://www.h2database.com/html/main.html)
- [PostgreSql: Conexão com o banco de dados físico](https://www.postgresql.org/download)
- [Docker: Necessário para gerar e rodar a imagem do projeto (Opcional)](https://docs.docker.com/get-docker/)

Para executar o projeto, é necessário utilizar o Spring Tools 4 for Eclipse, para que o mesmo identifique as dependências necessárias para a execução no repositório .m2 do Maven. Uma vez importado o projeto, será criado um arquivo *.classpath* que irá informar qual a classe principal para a execução.

### Gerenciamento do properties do projeto

- spring.profiles.active=
  
  Valores permitidos: production, development ou testH2

- samba-tech.storage.type-storage=
  
  Valores permitidos: local ou s3
  
### Conexao com o banco de dados

Ex:

spring.datasource.url= jdbc:postgresql://localhost:5433/samba-tech

spring.datasource.username=postgres

spring.datasource.password=123456

### Configuracao para rodar no S3

samba-tech.storage.s3.id-chave-acesso= (chave de acesso gerado do usuário)

samba-tech.storage.s3.chave-acesso-secreta= (chave secreta informada no IAM da AWS)

samba-tech.storage.s3.bucket=samba-tech-test

samba-tech.storage.s3.regiao=us-east-1

samba-tech.storage.s3.diretorio-media=medias

Obs: Para funcionar no s3 é importante os dados solicitados acima, os outros valores pré definidos são necessários para ele gerenciar os arquivos em transacoes

## Teste da documentação

Por fim, basta acessar a url: http://localhost:8080/swagger-ui.html#/ na máquina que esteja executando o projeto, Terá a documentacao da api pelo Swagger.

![documentacao-sambatech](https://user-images.githubusercontent.com/23174611/93091012-f0cf2280-f673-11ea-84fa-ae93fbcc08b3.png)

### Comandos para rodar no Docker
1. Gerar Imagem da aplicacao: docker build -t desafio-samba-tech-app .
2. Executar imagem: docker run -p 8080:8080 desafio-samba-tech-app

Obs1: É necessário instalar o docker

Obs2: Esses comandos devem ser executados pelo terminal no S.O na raiz do projeto para que ele execute com sucesso
