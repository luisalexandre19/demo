# demo
Projeto Demo

Este arquivo demonstra o passo a passo para executar e executar o projeto Demo.

Utilizado no projeto:

Java (Linguagem de desenvolvimento)
SpringBoot (Criação de serviço RestFull)
Maven (Geração do pacote (jar) para execução da Api)
Docker for MacOs Community Edition (CE) 17.12.0-ce stable (Ambiente de execução com containers)
Docker-compose (Configuração e execução de múltiplos containers)
Docker-swarm (Cluster) - Necessário instalar a VirtualBox (oracle) https://www.virtualbox.org/wiki/Downloads

Obs.: Considera-se que já tenha a versão do docker instalado, edição utilizada: Community Edition (CE) mais recente - stable.

Passo a passo:

Criar e acessar uma pasta: $ mkdir demo_test && cd demo_test

Fazer o clone deste projeto: $ git clone https://github.com/luisalexandre19/demo.git . && cd Demo

Executar o maven para criar a api restfull com springboot: $ ./mvnw clean package -Dmaven.test.skip=true

Executar o build do composer para gerar a imagem: $ docker-compose build

P.S. Para executar e visualizar a Api funcionando executando dentro de containers, basta executar o comando $ docker-compose up -d, e invocar as funcionalidades descrito abaixo usando localhost ou 127.0.0.1.

Agora vamos iniciar a rotina de deploy em cluster, com services e depois com swarms:

Iniciando o swarm: $ docker swarm init

Realizando o deploy: $ docker stack deploy --compose-file docker-stack.yml demoStack 

Até aqui já permite escalonar facilmente o ambiente aumentando os números de réplicas determinada no arquivo docker-stack.yml. 

Criando maquina virtual 1 para deploy em cluster: $ docker-machine create --driver virtualbox myvm1

Criando maquina virtual 2 para deploy em cluster: $ docker-machine create --driver virtualbox myvm2

Vericando se as maquinas foram criadas e pegando o IP da myvm1, ela será a manager: $ docker-machine ls 
Resultado obtido para verificação do ip:
NAME ACTIVE DRIVER STATE URL SWARM DOCKER ERRORS 
myvm1 - virtualbox Running tcp://192.168.99.100:2376 v17.12.1-ce 
myvm2 - virtualbox Running tcp://192.168.99.101:2376 v17.12.1-ce

Transformando a myvm1 em manager (usa o ip da maquina myvm1 obtido no passo acima, neste exemplo: 192.168.99.102): $ docker-machine ssh myvm1 "docker swarm init --advertise-addr 192.168.99.100" 

Executa o resultado (token) obtido na myvm2: $ docker-machine ssh myvm2 "docker swarm join --token SWMTKN-1-4c4e581po36q98u1o4us4offew88q0a7qpazxc010rlhgbirpi-amaduc1rsfiav87fty65j0np0 192.168.99.100:2377"

Obtendo configurção para usar o shell da myvm1: $ docker-machine env myvm1

Configurando o shell da myvm1: $ eval $(docker-machine env myvm1)

Executar o build do composer para gerar a imagem dentro do myvm1: $ docker-compose build

Realizando o deploy: $ docker stack deploy --compose-file docker-stack.yml demoStack

Funcionalidades da Api: 

Testando a aplicação em cluster (node myvw1): curl http://192.168.99.100:8080/demo/healthcheck

Testando a aplicação em cluster (node myvw2): curl http://192.168.99.101:8080/demo/healthcheck

Processando uma stream em cluster (node myvw1): curl http://192.168.99.100:8080/demo/process/aAbBABacafe

Processando uma stream em cluster (node myvw2): curl http://192.168.99.101:8080/demo/process/aAbBABacafe

Obtendo os processamentos realizados em cluster (node myvw1): curl http://192.168.99.100:8080/demo/findAll

Obtendo os processamentos realizados em cluster (node myvw2): curl http://192.168.99.101:8080/demo/findAll

Verificando o healthcheck da Api:

- Lista os containers em execução: $ docker ps

Ex. do resultado:

CONTAINER ID        IMAGE               COMMAND                  CREATED             STATUS                             PORTS                    NAMES
984b9d2a72cc        demo_api            "java -Djava.securit…"   56 seconds ago      Up 24 seconds (health: starting)   0.0.0.0:8080->8080/tcp   demo_api_1
b2036f425288        mysql               "docker-entrypoint.s…"   57 seconds ago      Up 25 seconds                      0.0.0.0:3306->3306/tcp   demo_db_1

- Executar o comando para obter o retorno do healthcheck, utilizando o Container ID como parametro: $ docker inspect --format='{{json .State.Health}}' 984b9d2a72cc 

Ex.:{"Status":"starting","FailingStreak":0,"Log":[]} = Ainda não executou a rotina, no nosso arquivo está configurada para executar a cada 1 minuto e 30 segundos.

Ex2.: {"Status":"healthy","FailingStreak":0,"Log":[{"Start":"2018-03-05T22:33:55.745823184Z","End":"2018-03-05T22:33:56.110767451Z","ExitCode":0,"Output":"OK"}]} = Sucesso na validação da Api



