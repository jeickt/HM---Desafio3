Orientações para executar a aplicação:
 - realize o clone do projeto disponível em "https://bitbucket.org/JeisonRu/desafio_southsystem_3/src/master/" e entre na branch develop;
 - abra o terminal na pasta principal do projeto;
 - execute os comandos "mvn clean package" e "docker-compose up -d --build".

O banco de dados possui dois usuários cadastrados com as seguintes identificações:
 - username: user, password: 123456;
 - username: admin, password: 654321.

O phpMyAdmin está disponível em "http://localhost:8082", sob Utilizador: root, Palavra-passe: 3698741.

A URI base disponível é "http://localhost:8080".

A aplicação conta com autenticação via token JWT, que pode ser obtido via POST em "http://localhost:8080/login", a partir de arquivo json com os dados de usuário informados acima.

A documentação de acesso ao servidor web encontra-se em "http://localhost:8080/swagger-ui.html".

Apenas o usuário "admin" possui permissão de deletar produtos e categorias.
