## Introdução e Objetivo Geral
- O objetivo deste projeto consistiu em praticar técnicas e princípios de programação Object-Oriented lecionados na disciplina de OOP.
- O trabalho centrou-se numa aplicação que é um gestor e reprodutor de músicas em suporte digital. A aplicação
foi desenvolvida incrementalmente e iterativamente. O primeiro incremento focou-se em funcionalidades
básicas e músicas no formato mp3, considerando um conjunto básico de casos de uso em torno
de bibliotecas de músicas e de playlists. Sucintamente, a aplicação permite carregar músicas para uma biblioteca e a partir da biblioteca, pesquisar, selecionar e pôr a tocar músicas individuais. Existem várias smart playlists (listas
de músicas geridas de forma automática) pré-definidas mas é também possível criar playlists manualmente. É possível gerir as
músicas contidas numa playlist manual e pôr a tocar as músicas de uma qualquer playlist.

## Organização geral da solução de desenho fornecida
- **ui:** classes responsáveis exclusivamente pela interface da aplicação
- **domain:** classes cuja responsabilidade se prende apenas com o domínio da aplicação
- **services:** classes cuja responsabilidade se prende com a utilização de serviços externos à aplicação
- **util:** classes que foram concebidas especificamente para a aplicação, mas potencialmente reutilizáveis noutros contextos

- Adicionalmente, tomámos ainda algumas decisões de implementação mais específicas, no que toca à arquitetura da aplicação concebida:
	- De modo a facilitar as interações entre o leitor de música e as respetivas classes que contém a implementação das operações sobre as músicas, decidimos fazer a comunicação direta com o Player, tanto na Playlist como na MusicLibrary. Deste modo cada um dos objetos estão independente na forma como cada respetivo controlador invoca os seus métodos para tocar as músicas, nomeadamente o método 'play()'), o que nos permitiu resolver alguns conflitos durante o desenvolvimento do projeto.
	- Em relação à classe `AbsPlaylist`, utilizámos a **Factory** do `Player` para obter a sua instância, implementar as operações realizadas sobre as músicas e realizar a alteração de propriedades face à mudança de estados do `Player` (consoante o contexto); colocámos a `MusicLibrary` como **Listener** do `Player`.
---
### Notas adicionais
- Relativamente à leitura dos meta-dados das músicas a partir de ficheiros mp3, recorremos à utilização da seguinte biblioteca open source https://github.com/mpatric/mp3agic.
- Uma vez que o desenho da interface da aplicação não é o foco desta disciplina, foi nos fornecido pelos docentes uma implementação rudimentar de uma interface gráfica (implementado com o swt do Eclipse).
- Adicionalmente, implementámos também testes unitários JUnit para as classes `ArrayQListWithSelection` e `Song`.
- Todas as classes foram devidamente documentadas com JavaDoc.

