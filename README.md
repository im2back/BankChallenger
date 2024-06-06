# Status de Desenvolvimento 04/06/2024 🟢 

-  EM REFATORAMENTO DA ARQUITETURA ! A aplicação ja havia sido concluída no entando foi observado pontos de melhoria e agora vou refatora-la.
-  Motivo do refatoramento : Testando outro tipo de arquitetura utilizando Herança
  
### Confira a proposta do desafio original acessando o seguinte link: 👉🏻 <a href="https://github.com/PicPay/picpay-desafio-backend">Desafio - PicPay</a>.
<br><br>
 ▶ Clique na imagem abaixo para assistir ao vídeo de demonstração: CONSUMINDO E EXPLICANDO A API !

[![Watch the video](https://img.youtube.com/vi/K3YI8UU0_g8/0.jpg)](https://youtu.be/K3YI8UU0_g8)

# Objetivos ao Desenvolver Este Desafio 🏋🏻‍♀️
Meu objetivo principal é aplicar meus conhecimentos em arquitetura e incorporar as melhores práticas de desenvolvimento.
Estou igualmente empenhado em aprimorar minhas habilidades em gerenciamento de versionamento de código, utilizando o GitHub.
Esse esforço envolve trabalhar eficientemente com branches e pull requests para assegurar uma organização eficaz do código.
<br><br>

### 📌OBS: Alguns comentários foram mantidos para fins de estudo e consultas futuras.Os comentários são discretos e não comprometem a visibilidade.

# Documentação
### Arquivo da documentação utilizando Postman 👉🏻 <a href="https://drive.google.com/file/d/19tYC01DhH4lczly90nR0rixRVF2arAMO/view?usp=sharing">Documentação - Postman</a>
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

<br><br>
# Dockerização
### Como opção adicional para executar o projeto, realizei a sua dockerização, permitindo que ele seja executado em containers. Na seção abaixo, você encontrará um tutorial com instruções detalhadas sobre como rodar o projeto dessa forma.
![image](https://github.com/im2back/BankChallenger/assets/117541466/65f83705-0041-4027-8650-a110cc948631)



# Tecnologias utilizadas
## Back end
- Java 17
- Spring Boot
- JPA / Hibernate
- Maven
- Spring cloud
- H2 Database
- Docker

# Como executar o projeto

## Back end
Pré-requisitos: Java 17

```bash
# Clonar repositório
git clone git@github.com:im2back/BankChallenger.git

# executar o projeto
- Entrar na pasta de cada microsserviço :
./mvnw spring-boot:run

#Observações : ATENTAR-SE PARA AS PORTAS QUE ESTÃO SENDO USADAS NO PROJETO !!! VERIFICAR DISPONIBILIDADE DAS PORTAS !!!
```
## Utilizando Docker
Pré-requisitos: Docker
```bash
#Inicializar o docker

#Criar uma network
docker network create bank

#Baixar a imagem do microsserviço de usuários e rodar um container apartir dela

Baixar a imagem 
👉🏻 docker pull im2back/usermicroservice:latest

Inicializar o Container 
👉🏻 docker run -d --name user-microservice -p 8080:8080 --network bank im2back/usermicroservice:latest



#Baixar a imagem do microsserviço de transferencia e rodar um container apartir dela

Baixar a imagem 
👉🏻 docker pull im2back/transfermicroservice:latest

Inicializar o Container 
👉🏻 docker run -d --name transfer-microservice -p 8081:8080 -e MOCK_AUTHORIZE=https://util.devi.tools/api/v2/authorize -e MOCK_NOTIFICATION=https://util.devi.tools/api/v1/notify -e USER_MS_URL=http://user-microservice:8080 --network bank im2back/transfermicroservice

#Observações : ATENTAR-SE PARA AS PORTAS QUE ESTÃO SENDO USADAS NO PROJETO !!! VERIFICAR DISPONIBILIDADE DAS PORTAS !!!
```

# Autor

Jefferson Richards Sena de Souza

<a href="https://www.linkedin.com/in/jefferson-richards-sena-de-souza-4110a3222/" target="_blank"><img loading="lazy" src="https://img.shields.io/badge/-LinkedIn-%230077B5?style=flat&logo=linkedin&logoColor=white" target="_blank"></a>
