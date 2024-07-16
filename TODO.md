### Geral

- [ ] Executar a aplicação spring no docker
- [ ] CRUD Conta
- [ ] Implementar mecanismo para importação de contas a pagar via arquivo csv
- [ ] Implementar testes unitários.

### Conta

- [ ] **`POST`** Cadastrar conta
    - Só posso cadastrar uma conta com data de vencimento maior que a data atual se a conta ainda não foi paga.
    - Só posso cadastrar uma conta que já foi paga com a data de pagamento preenchida.
    - Só posso cadastrar uma conta que ainda não foi paga sem a data de pagamento preenchida.
    - Só posso cadastrar uma conta com valor maior que zero.
- [ ] **`POST`** Pagar conta
    - Só posso pagar uma conta que ainda não foi paga (status: AWAITING_PAYMENT).
    - Valor pago deve ser igual ao valor da conta.
    - Data de pagamento deve ser preenchida.
    - Data de pagamento deve ser maior ou igual a data de vencimento.
    - Só posso pagar uma conta que pertença ao usuário logado.
- [ ] **`PUT`** Atualizar conta
    - Só posso atualizar uma conta que ainda não foi paga (status: AWAITING_PAYMENT).
    - Só posso atualizar uma conta que pertença ao usuário logado.
- [ ] **`DEL`** Deletar conta
    - Só posso deletar uma conta que ainda não foi paga (status: AWAITING_PAYMENT).]
    - Só posso deletar uma conta que pertença ao usuário logado.
- [ ] **`GET`** Filtrar por id
    - Só posso visualizar uma conta que pertença ao usuário logado.
- [ ] **`POST`** Contas a pagar com filtros
    - Só posso visualizar contas que pertençam ao usuário logado.
- [ ] **`POST`** Valor total pago por período
    - Só posso visualizar contas que pertençam ao usuário logado.

### Histórico de importação de contas

- [ ] **`POST` Importar contas a pagar
    - Arquivo deve ser um csv.
    - Deve alterar o status da importação para IMPORTED após a finalização da importação.
    - Deve alterar o status da importação para ERROR caso ocorra algum erro na importação.
    - Deve alterar o status da importação para CANCELED caso a importação seja cancelada.
    - Deve alterar o status da importação para PROCESSING durante a importação.
    - Só posso importar contas que pertençam ao usuário logado.
    - Só posso importar contas que ainda não foram importadas.
- [ ] **`POST` Cancelar importação
    - Só posso cancelar uma importação que ainda não foi finalizada.
    - Só posso cancelar uma importação que pertença ao usuário logado.
    - Deve alterar o status da importação para CANCELED.
    - Deve alterar o status das contas importadas para CANCELED.
- [ ] **`POST` Obter Status importação
    - Só posso visualizar o status de uma importação que pertença ao usuário logado.
- [ ] **`POST` Listar contas
    - Só posso visualizar contas que pertençam ao usuário logado.
- [ ] **`GET` Filtrar por id
    - Só posso visualizar uma importação que pertença ao usuário logado.
