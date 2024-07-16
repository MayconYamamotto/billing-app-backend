# billing-app-backend

---

[![CI](https://github.com/MayconYamamotto/asd/actions/workflows/ci.yml/badge.svg)](https://github.com/MayconYamamotto/asd/actions/workflows/ci.yml)

### Documentação

- [Collection do Postman](docs/Billing.postman_collection.json)
- [Imagem da modelagem do banco de dados](docs/entidades.png)

### Tecnologias

- Java 17
- Spring Boot
- PostgreSQL
- Flyway
- GitHub Actions
- Docker
- JWT
- Lombok

### Entidades

| [Conta](src/main/java/br/com/billing/domain/entity/BillEntity.java) |
|---------------------------------------------------------------------|
| id: UUID                                                            |
| importHistory: BillingImportHistoryEntity                           |
| expirationDate: LocalDate                                           |
| paymentDate: LocalDate                                              |
| description: String                                                 |
| amount: BigDecimal                                                  |
| sysuser: UserEntity                                                 |
| status: BillingStatus                                               |

| [Histórico de importação de estoque](src/main/java/br/com/billing/domain/entity/BillingImportHistoryEntity.java) |
|------------------------------------------------------------------------------------------------------------------|
| id: UUID                                                                                                         |
| importHistory: BillingImportHistoryEntity                                                                        |
| expirationDate: LocalDate                                                                                        |
| paymentDate: LocalDate                                                                                           |
| description: String                                                                                              |
| amount: BigDecimal                                                                                               |
| sysuser: UserEntity                                                                                              |
| status: BillingStatus                                                                                            |

### Autenticação

##### Endpoints

- [POST /user/login](src/main/java/br/com/billing/infrastructure/controller/UserController.java)
    - Autentica um usuário e retorna um token JWT
    - Body:
      ```json
      {
        "email": "string",
        "password": "string"
      }
      ```
    - Response:
      ```json
      {
        "token": "string"
      }
      ```
- [POST /user/signup](src/main/java/br/com/billing/infrastructure/controller/UserController.java)
    - Cria um novo usuário e retorna um token JWT
    - Body:
      ```json
      {
        "firstName": "string",
        "lastName": "string",
        "email": "string",
        "password": "string",
        "confirmPassword": "string",
        "active": true
      }
      ```
    - Response:
      ```json
      {
        "token": "string"
      }
      ```
- [POST /bill](src/main/java/br/com/billing/infrastructure/controller/BillController.java)

    - O valor da conta deve ser maior que zero.
    - A conta paga deve ter a data de pagamento preenchida.
    - Somente contas pagas devem ter a data de pagamento preenchida
    - Body:
      ```json
      {
        "expirationDate": "2024-07-20",
        "description": "Conta de luz",
        "amount": 10,
        "sysuser": {
          "id": "79f4cea5-b8f8-4350-aa18-c254b45c3703"
        },
        "status": "AWAITING_PAYMENT"
      }
      ```
    - Response: `Entidade de Conta`

- [POST /bill/pay](src/main/java/br/com/billing/infrastructure/controller/BillController.java)

    - É permitido pagar apenas contas com status 'Aguardando pagamento'.
    - A conta paga deve ter a data de pagamento preenchida
    - Body:
      ```json
      {
        "id": "31eddeee-a256-4154-a19d-f7cc46e4e641"
      }
      ```
    - Response: `Entidade de Conta`

- [POST /bill/payInBulk](src/main/java/br/com/billing/infrastructure/controller/BillController.java)
    - É permitido pagar apenas contas com status 'Aguardando pagamento'.
    - A conta paga deve ter a data de pagamento preenchida
    - exemplo de [arquivo CSV](docs/payInBulk.csv)
    - Body:
      ```json
      {
        "file": "aWQ7DQo4YjMwYmYwNS0yNjgwLTQ3NmItOTc4ZS05NGU5MmZmMjU1ZjkNCjc0OWIyZWFmLTgxNTgtNGYwMC05YjRlLTNkOWNmNTk5YjI3Yw=="
      }
      ```
    - Response:
    - Será retornado um [arquivo CSV](docs/feedback.csv) com o resultado do processamento com as seguintes colunas:
    - id, conta, status, mensagem de retorno
      ```json
      {
        "base64File": "aWQsY29udGEsc3RhdHVzLG1lbnNhZ2VtIGRlIHJldG9ybm8NCjhiMzBiZjA1LTI2ODAtNDc2Yi05NzhlLTk0ZTkyZmYyNTVmOSwsLENvbnRhIG7Do28gZW5jb250cmFkYS4NCjc0OWIyZWFmLTgxNTgtNGYwMC05YjRlLTNkOWNmNTk5YjI3YyxGZWlqw6NvLFBhZ28sw4kgcGVybWl0aWRvIHBhZ2FyIGFwZW5hcyBjb250YXMgY29tIHN0YXR1cyAnQWd1YXJkYW5kbyBwYWdhbWVudG8nLg0K"
      }
      ```
- [GET /bill/{id}](src/main/java/br/com/billing/infrastructure/controller/BillController.java)

    - Retorna uma conta pelo id
    - Será feita a validação se a conta pertence ao usuário logado
    - Response: `Entidade de Conta`

- [POST /bill/list](src/main/java/br/com/billing/infrastructure/controller/BillController.java)

    - Retorna todas as contas do usuário logado
    - Body:
      ```json
      {
        "customFilters": [
          {
            "field": "description",
            "operation": "EQUALS",
            "values": ["Conta de luz"]
          },
          {
            "field": "expirationDate",
            "operation": "EQUALS",
            "values": ["2024-07-20"]
          }
        ],
        "pageNumber": 0,
        "pageSize": 10
      }
      ```
    - Response:
      ```json
      {
        "content": [],
        "pageNumber": 0,
        "pageSize": 10,
        "totalElements": 0
      }
      ```

- [POST /bill/totalBillsPeriod](src/main/java/br/com/billing/infrastructure/controller/BillController.java)
    - Retorna o valor total das contas pagas em um período
    - Body:
      ```json
      {
        "initialDate": "2024-07-01",
        "finalDate": "2024-07-20"
      }
      ```
    - Response:
      ```json
      {
        "total": 0.0
      }
      ```
- [PUT /bill/{id}](src/main/java/br/com/billing/infrastructure/controller/BillController.java)

    - Atualiza uma conta
    - Será feita a validação se a conta pertence ao usuário logado
    - Body: `Entidade de Conta`
    - Response: `Entidade de Conta`

- [DELETE /bill/{id}](src/main/java/br/com/billing/infrastructure/controller/BillController.java)
    - Deleta uma conta
        - É permitido excluir apenas contas com status 'Aguardando pagamento'.
        - Response: `Entidade de Conta`
