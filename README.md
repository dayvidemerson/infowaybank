# Infoway Bank

Desafio para a vaga de Desenvolvedor Back-end Java na Infoway

## Descrição da Solução Proposta

Por ser um sistema de testes e por ter pouco tempo para implementar não pude elaborar uma solução mais robusta,
contudo eu desenvolvi uma API para o cadastro de Bancos, Agências Bancárias, Contas Bancárias e o Registro de
Movimentação de cada conta bancária.

Eu não criei ambiente administrativo tornando público o cadastro de Bancos, Agências Bancárias e para abertura
de uma Conta Bancária, contudo para acessar a conta bancária é necessário efetuar o login selecionando o banco
que deseja entrar, a agência bancária e preencher com os dados da sua conta e por fim colocar a senha definida
de acesso a conta, podendo o usuário efetuar login em várias contas simultâneas de forma isolada sem interferir
no acesso de cada uma.

Para segurança e isolamento do acesso dos dados de cada usuário, apesar de solicitado pelo desafio que a autenticação
fosse feita via oAuth2, infelizmente me deparei com muitos problemas e dificuldades que podem ser vistos no histórico
do controle de versão, por este motivo achei melhor seguir por outro caminho visto que eu poderia perder muito tempo
e não concluir as outras tarefas, por este motivo optei por desenvolver utilizando autenticação via JWT, que difere em
alguns poucos aspectos do oAuth2, mas atende a demanda e preenche o requisito para um token temporário e isolar as
requisições sem afetar outras contas que estejam logadas.

## Tecnologias Utilizadas

- Java v11.0.1
- Spring v2.1.1
- MySQL v.8.0.13

### Configuração do Banco de Dados

**Usuário:** root

**Senha:** rootroot

### Interface de Acesso a API

Enviarei os arquivos separados para acesso via web, esta sendo desenvolvido em Angular, porém como não serei avaliado
pelo frontend, ele estará disponível apenas na conclusão do desafio para não misturar os arquivos no GIT e dificultar
a avaliação.