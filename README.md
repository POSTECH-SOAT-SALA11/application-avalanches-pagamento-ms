[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=POSTECH-SOAT-SALA11_application-avalanches-pagamento-ms&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=POSTECH-SOAT-SALA11_application-avalanches-pagamento-ms)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=POSTECH-SOAT-SALA11_application-avalanches-pagamento-ms&metric=bugs)](https://sonarcloud.io/summary/new_code?id=POSTECH-SOAT-SALA11_application-avalanches-pagamento-ms)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=POSTECH-SOAT-SALA11_application-avalanches-pagamento-ms&metric=code_smells)](https://sonarcloud.io/summary/new_code?id=POSTECH-SOAT-SALA11_application-avalanches-pagamento-ms)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=POSTECH-SOAT-SALA11_application-avalanches-pagamento-ms&metric=coverage)](https://sonarcloud.io/summary/new_code?id=POSTECH-SOAT-SALA11_application-avalanches-pagamento-ms)


## Funcionalidades Principais 

### Internas
- **Integração de pagamento**: Integração com API de pagamentos(mock webhook).
- **Status de pagamento**: Acompanhe o status dos pagamentos.

## Tecnologias Utilizadas

- Java 18
- Spring Boot 3.2.5
- Docker
- Kubernetes (Minikube)
- Redis
- Amazon Elastic Container Registry (ECR)
- Amazon Elastic Kubernetes Service (EKS)

## Arquitetura AWS(Cloud)
[Ilustração na Wiki](https://github.com/POSTECH-SOAT-SALA11/application-avalanches-aws/wiki/Arquitetura-AWS)

## Esteiras CI/CD
[Ilustração na Wiki](https://github.com/POSTECH-SOAT-SALA11/application-avalanches-aws/wiki/Esteiras-CI-CD)

## Banco de dados
- Redis: Serve como cache de alta performance, armazenando dados temporários e reduzindo a carga no PostgreSQL. Ele oferece respostas rápidas para consultas frequentes, como o status dos pedidos.

## Estrutura do Projeto

O projeto segue os princípios de Domain-Driven Design (DDD) e clean architecture, com as seguintes camadas:

- **Frameworks and Drivers**: Contém a web api e as configurações de banco de dados.

- **Interface Adapters**: Contém os gateways que garantem a comunicação com o mundo externo,
e os adaptadores que ajudam a camada de apresentação a exibir resultados.

- **Application Business Rules**:  Encapsula e implementa as regras de negócio através de casos de uso.

- **Enterprise Business Rules**:  Representa a camada de entidades e suas regras de negócio.

Desenho da arquitetura: https://github.com/POSTECH-SOAT-SALA11/Avalanches/wiki/Desenho-da-arquitetura

## Execução do Projeto em Kubernetes

### Requisitos tecnológicos:
- **[Docker](https://www.docker.com/)**: para a criação de imagens de contêineres.
- **[Minikube](https://minikube.sigs.k8s.io/docs/start/?arch=%2Fwindows%2Fx86-64%2Fstable%2F.exe+download)**: para a execução de um cluster Kubernetes local.

Para executar o projeto em Kubernetes, siga estas etapas:

1. Clone o repositório.
   ```bash
   git clone https://github.com/POSTECH-SOAT-SALA11/application-avalanches-pagamento-ms.git
   ```

2. Acesse o repositório.
   ```bash
   cd application-avalanches-pagamento-ms
   ```

3. Execute o script que inicializará o projeto automaticamente.
   ```bash
   docker compose up --build
    ```

4. Acesse o Swagger da aplicação em:
   ```
   http://localhost:8282/swagger-ui/index.html#/
   ```

## Autores

- [Hennan Cesar Alves Gadelha de Freitas](https://github.com/HennanGadelha)
  (hennangadelhafreitas@gmail.com)

- [Adinelson da Silva Bruhmuller Júnior](https://github.com/Doomwhite)
  (adinelsonsbruhmuller@gmail.com)

- [RAUL DE SOUZA](https://github.com/raulsouza-rm355416)
  (dev.raulsouza@outlook.com)

- [Raphael Soares Teodoro](https://github.com/raphasteodoro)
  (raphael.s.teodoro@outlook.com)
