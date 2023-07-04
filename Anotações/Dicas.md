### Links!

#### Mysql server: https://dev.mysql.com/downloads/file/?id=518834
#### Workbench: https://dev.mysql.com/downloads/workbench/

### Como identificar se ele está instalado e em execução

#### janela de pesquisa --> serviços --> busca por mysql : se não tiver em execução clica em cima do campo mysql e clica no botão de executar (play)

## Disponibilizar a aplicação para o cliente

### Build JAR file
### clica com o botão direito em cima do projeto --> export --> JavaRunnable JAR file --> next
### escolhe um destino para inserir o arquivo.
### Em lunch configuration seleciona a classe principal do projeto (main).
### Em library handling escolhe a terceira opção, copy required libraries ...

## Ferramentas para instalar na  máquina do cliente

### O arquivo exportado .JAR
### O arquivo db.properties
### O MYSQL Connector
### O JavaFX SDK com o sistema operacional da máquina cliente link (javaFX SDK https://gluonhq.com/products/javafx/)
### O Java JDK (https://www.oracle.com/br/java/technologies/downloads/#jdk20-windows e https://jdk.java.net/archive/) -->
### Escolher sempre as versões Versão de suporte de longo períodoLTS

## Passo a passo para instalação na máquina do cliente

### Instale o Java o JDk da oracle (clica 2 vezes no executável --> next) + o open jdk (para não pagar e não ter problemas com licenças).

### 1° Descompacta o arquivo openJdk na pasta C/arquivos de programas
### 2° Cria a variável de ambiente JAVA_HOME caso não exista, se existir insere o caminho do JDK. Edita a variável path inserindo o caminho do OpenJDK até a pasta bin. 

### Extrai a pasta Javafx na pasta de sua preferencia. foi descompactado na pasta c/javalibs/
### Cria uma variável de ambiente para o Javafx com o nome: PATH_TO_FX e insere o caminho
### Comando para executar o programa: java --module-path %PATH_TO_FX% --add-modules javafx.controls,javafx.fxml -cp projetoJavaFX-JDBC.jar application.Main
### É preciso instalar o banco de dados e popular ou inserir os dados do banco de dados remoto no arquivo db.properties. verificar se o serviço do mysql está rodando e abrir o SGBD