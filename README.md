# Status de Desenvolvimento 11/06/2024 🟢 
- concluído. Este repositório possui duas versões para o mesmo projeto, LATEST (SEM MENSSAGERIA) ULTIMATE(COM MENSSAGERIA) 

### Confira a proposta do desafio original acessando o seguinte link: 👉🏻 <a href="https://github.com/PicPay/picpay-desafio-backend">Desafio - PicPay</a>.

<br>

# Objetivos ao Desenvolver Este Desafio 🏋🏻‍♀️
Meu objetivo principal é aplicar meus conhecimentos em arquitetura e incorporar as melhores práticas de desenvolvimento.
Estou igualmente empenhado em aprimorar minhas habilidades em gerenciamento de versionamento de código, utilizando o GitHub.
Esse esforço envolve trabalhar eficientemente com branches e pull requests para assegurar uma organização eficaz do código.<BR>
📌OBS: Alguns comentários foram mantidos para fins de estudo e consultas futuras.Os comentários são discretos e não comprometem a visibilidade.

<br><br>

 ▶ Clique na imagem abaixo para assistir ao vídeo de demonstração: CONSUMINDO E EXPLICANDO A API !

# VÍDEO PARA VERSÃO ULTIMATE (COM MENSSAGERIA)
 ### Em breve... 

# VÍDEO PARA VERSÃO LATEST (SEM MENSSAGERIA)
[![Assista ao meu vídeo no YouTube](https://img.youtube.com/vi/dx0jznpEJ28/0.jpg
)](https://www.youtube.com/watch?v=dx0jznpEJ28)

<br><br>

# Fluxograma(ULTIMATE)
![microservices (1)](https://github.com/im2back/BankChallenger/assets/117541466/33c2a339-76a3-4d21-bdbb-ce1d48bcfad7)




# Diagrama de classes
![Diagrama API HERANÇA](https://github.com/im2back/BankChallenger/assets/117541466/36b4277b-c295-42bc-bec5-bc56d9893fe8)

# Documentação 
### Arquivo da documentação utilizando Postman 👉🏻 <a href="https://drive.google.com/file/d/19tYC01DhH4lczly90nR0rixRVF2arAMO/view?usp=sharing">Documentação (LATEST) - Postman</a>

### Arquivo da documentação utilizando Postman 👉🏻 <a href="https://drive.google.com/file/d/12_ULlSj2lvBvYUPfyl6FzJ9hB21mn_x1/view?usp=sharing">Documentação (ULTIMATE)- Postman</a>



<br><br>
### Prévia da Documentação do Microsserviço de Usuário
![userms-documentation](https://github.com/im2back/BankChallenger/assets/117541466/018cebdc-a9ba-4324-8bd3-03913ee82e7e)
<br><br>
### Prévia da Documentação do Microsserviço de Transferência
![transferms-documentation](https://github.com/im2back/BankChallenger/assets/117541466/31164fb0-61bd-4841-bef3-db9c55b3c6e9)

<br><br>


# Testes Unitarios
## Resultado das métricas utilizando Jacoco & Sonarqube
<br><br>
### Transfer-Microservice
![jacoco-trasnferms](https://github.com/im2back/BankChallenger/assets/117541466/3b6bc634-f931-4d99-be80-d50fcb78f137)
![sonar-transferms](https://github.com/im2back/BankChallenger/assets/117541466/774b8450-7d5c-475b-a138-d6c1a70eb1c0)

<br><br>
### User-Microservice
![sonar-userms](https://github.com/im2back/BankChallenger/assets/117541466/2f747ff8-6d2a-4b48-a7ee-d43ff976037c)
![jacoco-userms](https://github.com/im2back/BankChallenger/assets/117541466/79b6ec6d-87ca-4d35-8c0e-751e66d25c28)


# Tecnologias utilizadas
## Back end
- Java 17
- Spring Boot
- JPA / Hibernate
- Maven
- Spring cloud
- H2 Database
- Docker
- RabbitMq

<br><br>

# Docker
Usou de docker para facilitar a execução
![docker](https://github.com/im2back/BankChallenger/assets/117541466/3f5533ce-6bcf-48d0-84fc-16d2c713f1aa)

<br>

# Como executar o projeto VERSÃO LATEST

## Utilizando dependencias locais
Pré-requisitos: Java 17

```bash
# Clonar repositório
git clone git@github.com:im2back/BankChallenger.git

#Entrar na pasta do projeto
- abrir o terminal git
- navegar para o commit que corresponde a versão latest
git checkout b16e1f48a197cbc113cf847f5ab0b68371854a00 

# executar o projeto
- Entrar na pasta de cada microsserviço :
./mvnw spring-boot:run

#Observações : ATENTAR-SE PARA AS PORTAS QUE ESTÃO SENDO USADAS NO PROJETO !!! VERIFICAR DISPONIBILIDADE DAS PORTAS !!!
```
## Utilizando o docker
```
# Baixar as imagens
docker pull im2back/usermicroservice:latest
docker pull im2back/transfermicroservice:latest


# Criar uma network em comum
docker network create bank


# Subir os containers
docker run -d --name transfer-microservice -p 8081:8080 -e MOCK_AUTHORIZE=https://util.devi.tools/api/v2/authorize -e  USER_MS_URL=http://user-microservice:8080 --network bank im2back/transfermicroservice:latest

docker run -d --name user-microservice -p 8080:8080 -e URL_NOTIFY=https://util.devi.tools/api/v1/notify --network bank im2back/usermicroservice:latest

# Ser feliz e consumir a api via postman através da documentação
```

# Como executar o projeto VERSÃO ULTIMATE

## Utilizando dependencias locais
Pré-requisitos: Java 17

```bash
# Clonar repositório
git clone git@github.com:im2back/BankChallenger.git

# executar o projeto
- Entrar na pasta de cada microsserviço :
./mvnw spring-boot:run

#Observações : ATENTAR-SE PARA AS PORTAS QUE ESTÃO SENDO USADAS NO PROJETO !!! VERIFICAR DISPONIBILIDADE DAS PORTAS !!!
```
## Utilizando o docker
```
# Baixar as imagens
docker pull im2back/transfermicroservice:ultimate
docker pull im2back/usermicroservice:ultimate

# Criar uma network em comum
docker network create bank

# Rodar uma instância do RabbitMq
docker run -it --rm --name rabbitmq --network bank -p 5672:5672 -p 15672:15672 rabbitmq:3.12-management


# Subir os containers
docker run -d --name transfer-microservice -p 8081:8080 -e RABBIT_MQ=rabbitmq -e MOCK_AUTHORIZE=https://util.devi.tools/api/v2/authorize -e  USER_MS_URL=http://user-microservice:8080 --network bank im2back/transfermicroservice:utimate


docker run -d --name user-microservice -p 8080:8080 -e RABBIT_MQ=rabbitmq -e URL_NOTIFY=https://util.devi.tools/api/v1/notify --network bank im2back/usermicroservice:utimate

# Ser feliz e consumir a api via postman através da documentação
```


# Autor

Jefferson Richards Sena de Souza

<a href="https://www.linkedin.com/in/jefferson-richards-sena-de-souza-4110a3222/" target="_blank"><img loading="lazy" src="https://img.shields.io/badge/-LinkedIn-%230077B5?style=flat&logo=linkedin&logoColor=white" target="_blank"></a>
