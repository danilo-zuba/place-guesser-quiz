# Contextualização do Projeto: Region Quiz

## 1. Visão geral

O **Region Quiz** é um aplicativo Android de quiz voltado para conhecimentos geográficos sobre o Brasil, com foco na identificação de cidades, monumentos, paisagens, pontos turísticos e outros lugares brasileiros. O objetivo do jogador é responder corretamente às perguntas, acumular pontos e avançar durante a partida.

O projeto está sendo desenvolvido como uma aplicação educacional, com foco na prática de desenvolvimento mobile, construção de interfaces, organização do código e futura integração com um banco de dados.

## 2. Tecnologias e ferramentas

As tecnologias definidas para o desenvolvimento são:

- **Kotlin:** linguagem utilizada para implementar a lógica do aplicativo.
- **XML Views:** tecnologia utilizada para construir e organizar as interfaces gráficas.
- **Android Studio:** ambiente de desenvolvimento.
- **Activities e Intents:** mecanismos utilizados para organizar as telas e realizar a navegação entre elas.
- **Supabase:** serviço planejado para uma futura etapa, destinado ao armazenamento e à recuperação de perguntas e imagens.

O projeto utiliza **XML Views, e não Jetpack Compose**, para a construção das interfaces. Portanto, novas telas e componentes visuais devem seguir o padrão XML, com sua lógica correspondente implementada em Kotlin.

## 3. Funcionalidades previstas

O aplicativo contará com três tipos principais de desafios:

1. **IMAGE_CHOICE:** apresentação de quatro imagens, entre as quais o jogador deverá identificar o lugar correspondente à pergunta.
2. **IMAGE_MULTIPLE_CHOICE:** apresentação de uma imagem acompanhada de alternativas para responder à pergunta.
3. **TRUE_FALSE:** apresentação de uma afirmação sobre um lugar brasileiro, que deverá ser classificada como verdadeira ou falsa.

A partida deverá conter 20 perguntas selecionadas aleatoriamente, sem repetição dentro da mesma partida. O sistema também deverá contabilizar a pontuação e fornecer feedback ao jogador após suas respostas.

As perguntas e imagens deverão ser obtidas dinamicamente, evitando que o conteúdo do quiz fique limitado a informações fixas diretamente no código-fonte.

## 4. Progresso atual do desenvolvimento

O projeto já possui uma estrutura inicial funcional, com as primeiras interfaces desenvolvidas utilizando XML Views.

### Tela principal

Foi criada a tela inicial do aplicativo no arquivo `activity_main.xml`, com os seguintes elementos:

- Título do aplicativo.
- Imagem de fundo.
- Botão **Jogar**.
- Botão **Instruções**.
- Botão **Créditos**.

A tela é controlada pela classe `MainActivity.kt`. A navegação de um botão para uma segunda Activity já foi implementada e testada.

### Tela de instruções

Foi iniciada a implementação da tela de instruções, composta por:

- Título da tela.
- Texto explicativo sobre o funcionamento do jogo.
- Área de texto rolável, utilizando `ScrollView`, para acomodar o conteúdo.
- Botão **Voltar** para retornar à tela anterior.

A interface está sendo desenvolvida em `activity_instrucoes.xml`, enquanto a lógica de navegação fica em `Instrucoes.kt`. O layout e o comportamento dessa tela ainda podem passar por ajustes.

### Estado das funcionalidades

Até o momento, o trabalho está concentrado na estrutura inicial e na navegação entre telas. Os três tipos de desafios, o sistema de pontuação, a seleção de perguntas e a integração com o Supabase fazem parte do escopo planejado, mas ainda não devem ser considerados implementados.

## 5. Arquitetura e organização futuras

O desenvolvimento deverá evoluir de maneira incremental, começando pela conclusão das interfaces e da navegação básica, seguida pela implementação da lógica do quiz.

Em etapas posteriores, pretende-se organizar o acesso aos dados de forma separada das interfaces, utilizando uma estrutura que permita introduzir componentes como **Repository** e **ViewModel**, caso sejam adequados à evolução do projeto.

O Supabase deverá ser integrado futuramente para permitir o armazenamento e a consulta de perguntas e alternativas, além do gerenciamento das imagens utilizadas nos desafios.

A implementação dessas etapas deverá preservar a separação entre interface, lógica do jogo e acesso aos dados, facilitando a manutenção e a expansão do aplicativo.

## 6. Próximas etapas

As principais etapas previstas são:

1. Finalizar as telas de instruções e créditos.
2. Implementar a navegação entre as telas principais.
3. Desenvolver a tela e a lógica das partidas.
4. Implementar os três tipos de desafios.
5. Criar a seleção aleatória de 20 perguntas sem repetição.
6. Implementar pontuação e feedback das respostas.
7. Integrar o Supabase para obtenção dinâmica de perguntas e imagens.
8. Testar o funcionamento e a adaptação das interfaces em diferentes tamanhos de tela.

## 7. Objetivo final

O objetivo é entregar um aplicativo Android funcional, organizado e expansível, capaz de oferecer partidas de quiz sobre a geografia brasileira. O projeto também busca consolidar conhecimentos de Kotlin, XML Views, navegação entre Activities, manipulação de dados e integração com serviços externos.

Toda nova implementação deverá respeitar as tecnologias escolhidas, especialmente o uso de **Kotlin + XML Views**, e considerar o estágio atual do projeto para evitar tratar funcionalidades planejadas como se já estivessem concluídas.