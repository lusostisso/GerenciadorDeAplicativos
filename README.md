# GerenciadorDeAplicativos
O objetivo deste trabalho é desenvolver um sistema aplicativo em Java explorando os conceitos estudados na disciplina de Programação Orientada a Objetos com destaque para: modelagem de objetos, herança, polimorfismo, interfaces, arquivos texto, API de coleções, funções lambdas, funções agregadoras e a API Swing.


Considere que uma startup usa SAS (Software As a Service) como modelo de negócio. Neste modelo o cliente pode baixar os aplicativos gratuitamente na loja, porém eles só funcionam se o cliente tiver uma assinatura paga. Em função disso, todo o mês, é necessário levantar a lista dos clientes que devem ser cobrados (a cobrança propriamente dita é feita em outro sistema).


Para fazer esta gestão, startups que trabalham nesse modelo de negócio necessitam de um software de apoio que tenha as seguintes funcionalidades:

    · Cadastrar/editar os aplicativos que disponibiliza
    
    · Cadastrar/editar a base de clientes
    
    · Gerenciar assinaturas (registrar a assinatura ou o cancelamento de assinatura de um cliente)
    
    · Listar a base de clientes
    
    · Listar a base de aplicativos
    
    · Listar as assinaturas de um cliente
    
    · Listar os assinantes de um produto
    
    · Gerar uma lista com os clientes que devem ser cobrados no mês corrente. Para cada cliente listar o nome, o email e o valor a ser cobrado (depende da quantidade de assinaturas do cliente)
    
    · Listar o faturamento com um determinado aplicativo no mês corrente (supondo que todos os que foram cobrados pagaram). Listar o total e por SO.

Para que a gestão do negócio seja possível, sabe-se, que devem ser armazenadas, no mínimo as seguintes informações:

  Para cada Aplicativo desenvolvido pela empresa:
  
      · Código
      
      · Nome
      
      · SO
      
      · Valor mensal da licença
      
  Para cada Cliente da empresa:
      
      · CPF
      
      · Nome
      
      · Email
  
  Para cada assinatura:
  
      · Código (número da assinatura)
      
      · Código do aplicativo
      
      · CPF do cliente
      
      · Mês e ano de início da vigência
