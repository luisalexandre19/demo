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

Iniciando o swarm: $ docker swarm init

Realizando o deploy: $ docker stack deploy --compose-file docker-stack.yml demoStack 

Até aqui já permite escalonar o ambiente aumentando os números de réplicas determinada no arquivo docker-stack.yml, mas o objetivo é implantar o projeto em um cluster utilizando o swarms, portanto, devemos executar os passos abaixo.

Criando maquina virtual 1 para deploy em cluster: $ docker-machine create --driver virtualbox myvm1

Criando maquina virtual 2 para deploy em cluster: $ docker-machine create --driver virtualbox myvm2

Vericando se as maquinas foram criadas e pegando o IP da myvm1, ela será a manager: $ docker-machine ls 
Resultado obtido para verificação do ip:
NAME ACTIVE DRIVER STATE URL SWARM DOCKER ERRORS 
myvm1 - virtualbox Running tcp://192.168.99.102:2376 v17.12.1-ce 
myvm2 - virtualbox Running tcp://192.168.99.103:2376 v17.12.1-ce

Transformando a myvm1 em manager (usa o ip da maquina myvm1 obtido no passo acima, neste exemplo: 192.168.99.102): $ docker-machine ssh myvm1 "docker swarm init --advertise-addr 192.168.99.102" 

Executa o resultado (token) obtido na myvm2: $ docker-machine ssh myvm2 "docker swarm join 
--token SWMTKN-1-549tnng4hz9v3llrkgops07ut79ko57b59vnaj6mazzyavfarn-1wopf0hrhj5b4mrh9flogpxkk 
192.168.99.102:2377"

Obtendo configurção para usar o shell da myvm1: $ docker-machine env myvm1

Configurando o shell da myvm1: $ eval $(docker-machine env myvm1)

Realizando o deploy: $ docker stack deploy --compose-file docker-stack.yml demoStack

Testando a aplicação em cluster (node myvw1): curl http://192.168.99.102:8080/demo/healthcheck
Testando a aplicação em cluster (node myvw2): curl http://192.168.99.103:8080/demo/healthcheck
Processando uma stream em cluster (node myvw1): curl http://192.168.99.102:8080/demo/process/aAbBABacafe
Processando uma stream em cluster (node myvw2): curl http://192.168.99.103:8080/demo/process/aAbBABacafe
Obtendo os processamentos realizados em cluster (node myvw1): curl http://192.168.99.102:8080/demo/findAll
Obtendo os processamentos realizados em cluster (node myvw2): curl http://192.168.99.103:8080/demo/findAll
