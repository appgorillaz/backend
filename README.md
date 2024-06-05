# Gorillaz API

[![](https://skillicons.dev/icons?i=java,spring,postgresql,docker,docker-compose)](https://skillicons.dev)

Este projeto foi construído utilizando Java, Spring Boot e o banco de dados PostgreSQL que é servido através do docker.

## Uso

Para rodar a API você vai precisar do `maven`, a API vai estar disponível na porta :8081

Para rodar o PostgreSQL você vai precisar do `docker` e `docker-compose`

Com o docker e o docker-compose instalados, você deve rodar o comando abaixo para iniciar os containers do `postgres` e do `pgadmin`.
(caso não rode, insira `sudo` na frente)

```shell
    docker-compose up -d
```

O pgadmin vai estar disponível em `localhost:5050`.
Credenciais de acesso ao pgadmin: `gorillaz@fatec.com` e `gorillaz_adm`

Após isso, crie um novo server com o nome de `gorillaz`, host, user e password vão ser `postgres`.

Para verificar se tudo está funcionando faça um `POST` para a API no endpoint `/users` com o body:
```json
{
  "email": "teste@me.com",
  "password": "123",
  "gender": "Masculino",
  "name": "teste",
  "course": "DSM"
}
```