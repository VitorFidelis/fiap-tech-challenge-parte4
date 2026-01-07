# Sistema de Avaliação (Feedback)

Sistema de gerenciamento de feedbacks desenvolvido com Quarkus, um framework Java otimizado para Kubernetes.

## 🚀 Tecnologias Utilizadas

- **Quarkus 3.2.9** - Framework Java para aplicações nativas na nuvem
- **Java 17** - Versão LTS do Java
- **PostgreSQL** - Banco de dados relacional
- **AWS SQS** - Fila de mensagens para processamento assíncrono
- **Flyway** - Controle de versão de banco de dados
- **Maven** - Gerenciamento de dependências

## 📋 Pré-requisitos

- Java 17 ou superior
- Maven 3.9.0 ou superior
- Docker e Docker Compose (opcional, para execução em containers)
- Conta AWS (apenas se for usar SQS na nuvem)

## 🛠️ Configuração do Ambiente

1. **Banco de Dados**
   - Instale o PostgreSQL ou utilize o Docker Compose fornecido
   - Crie um banco de dados chamado `feedback`

2. **Variáveis de Ambiente**
   Crie um arquivo `.env` na raiz do projeto com as seguintes variáveis:
   ```
   QUARKUS_DATASOURCE_JDBC_URL=jdbc:postgresql://localhost:5432/feedback
   QUARKUS_DATASOURCE_USERNAME=seu_usuario
   QUARKUS_DATASOURCE_PASSWORD=sua_senha
   SQS_QUEUE_URL=sua_url_da_fila_sqs
   ```

## 🚀 Executando a Aplicação

### Modo Desenvolvimento

```bash
./mvnw quarkus:dev
```

A aplicação estará disponível em: http://localhost:8081

### Usando Docker Compose

```bash
docker-compose up -d
```

### Construindo o Projeto

```bash
# Empacotar a aplicação
./mvnw package

# Construir imagem Docker
./mvnw package -Dquarkus.container-image.build=true
```

## 📚 Documentação e Testes - Postman

Todos os endpoints da API estão documentados seguindo boas práticas REST.
Arquivos de coleções do Postman estão disponíveis para testar os endpoints.

## 🌐 Endpoints da API

### Feedbacks

#### Criar um novo feedback
- **Método**: `POST`
- **Endpoint**: `/feedbacks`
- **Content-Type**: `application/json`
- **Exemplo de Request Body**:
  ```json
  {
      "descricao": "Ótimo, adorei!",
      "nota": 9.0
  }
  ```
- **Respostas**:
  - `201 Created`: Feedback criado com sucesso
  - `400 Bad Request`: Dados inválidos
  - `500 Internal Server Error`: Erro interno do servidor

### Hello World

#### Verificar se a API está online
- **Método**: `GET`
- **Endpoint**: `/hello`
- **Resposta**:
  - `200 OK`: "Hello from Quarkus REST"


### A documentação adicional via Quarkus Dev UI está disponível em [Quarkus Dev UI](http://localhost:8081/q/dev-ui/welcome)

## 🏗️ Estrutura do Projeto

```
src/
├── main/
│   ├── docker/           # Arquivos Docker
│   ├── java/
│   │   └── br/feedback/
│   │       ├── dto/      # Objetos de Transferência de Dados
│   │       ├── entity/   # Entidades JPA
│   │       ├── repository/ # Repositórios de acesso a dados
│   │       ├── resource/ # Controladores REST
│   │       └── service/  # Lógica de negócios
│   └── resources/
│       ├── db/migration/ # Scripts do Flyway
│       └── application.properties
└── test/                 # Testes automatizados
```

## 🤝 Contribuição

1. Faça um Fork do projeto
2. Crie uma Branch para sua Feature (`git checkout -b feature/AmazingFeature`)
3. Adicione suas mudanças (`git add .`)
4. Comite suas mudanças (`git commit -m 'Adicionando uma incrível feature'`)
5. Faça o Push da Branch (`git push origin feature/AmazingFeature`)
6. Abra um Pull Request

## 📄 Licença

Este projeto é parte de um desafio educacional da FIAP. Uso livre para fins acadêmicos. Ver MIT License para demais finalidades.

## ✨ Agradecimentos

- Equipe FIAP pelo desafio
- Comunidade Quarkus
- Toda a equipe que colaborou no desenvolvimento.
