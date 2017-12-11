# RabbitMQ

## O que é o RabbitMQ?

RabbitMQ is a message-queueing software called a message broker or queue manager. Simply said; It is a software where queues can be defined, applications may connect to the queue and transfer a message onto it.

---

## Explique como o RabbitMQ funciona. Não esqueça de falar o que é e para que servem os seguintes elementos: exchange, binding, routing key e queue.

###### A seguir será mostrado um exemplo do funcionamento do RabbitMQ.

1. O producer (produtor) publica uma mensagem no exchange (contexto de troca de mensagens).

2. O exchange recebe a mensagem e agora é o responsável pelo routing (encaminhamento) da mensagem.

3. Um binding (conexões) deve ser configurada previamente entre a queue (fila) e o exchange. Neste caso, nos temos 2 bindings a diferentes queues do exchange. O exchange encaminha a mensagem as queues.

4. A mensagem fica na queue até que elas sejam utilizadas por um consumer (consumidor).

5. O consumer consome a mensagem.

###### Na imagem pode ser visualizado todo esse processo:

![](img/ebrq.png)

###### Outras informações:

- Exchange: É uma agente de roteamento, definido por host no RabbitMQ. Recebe mensagens dos produtores e envia as filas dependendo das regras definidas pelo tipo do contexto de troca.

- Binding: É um 'link' para vincular uma fila (queue) a um exchange.

- Routing Key: É um atributo de mensagem. O exchange pode observar esta chave ao decidir como enchaminhar a mensagem para filas (dependendo do tipo de exchange).

- Queue: Fila é uma estrutura de dados dinâmica sujeita a uma regra de operação: O primeiro a ser inserido será o primeiro a ser removido ou finalizado (FIFO). No RabbitMQ é utilizada como buffer para as mensagens.
