# MUII-GIW
Asignatura de Gestión de Información en la Web (GIW) - Máster Profesional en Ingeniería Informática 2020/2021

<details open="open">
  <summary>Tabla de contenidos</summary>
  <ol>
    <li>
      <a href="#practica1">Práctica 1: Sistema de Recuperación de Información</a>
    </li>
    <li>
      <a href="#practica2">Práctica 2: Sistema de Recomendación basado en Filtrado Colaborativo</a>
    </li>
    <li>
      <a href="#practica3">Práctica 3: Análisis básico de una red social</a>
    </li>
    <li>
      <a href="#practica4">Práctica 4: Análisis y evaluación de redes en Twitter</a>
    </li>
  </ol>
      
<a name="practica1"></a>
## Práctica 1: Sistema de Recuperación de Información

En esta práctica se ha desarrollado un Sistema de Recuperación de Información que se compone de dos programas:

* **Indexador**: que recibe como parámetros la ruta de la colección documental a indexar, el fichero de palabras vacı́as a emplear y la ruta donde alojar los ı́ndices, y se encarga de crear los ı́ndices oportunos y ficheros auxiliares necesarios para la recuperación.
* **Motor de búsqueda**: que recibe como parámetro la ruta donde están alojados todos los ı́ndices y ofrece al usuario una interfaz gráfica en la que puede realizar una consulta de texto y obtener los documentos relevantes a dicha consulta

Toda la documentación de esta práctica se encuentra [aquí](practica1/P1_JuanManuelCastilloNievas.pdf)

### Manual de usuario

Lo primero que se debe hacer es ejecutar el parseador **Parser.java** porque todos los documentos están ubicados en un mismo archivo, con lo cual hay que crear un archivo de texto por cada documento. 

#### Línea de comandos

1. Ejecutar el jar **Indexer.jar** para crear los índices de los documentos usando la instrucción 
```
java -jar <directorio documentos> <fichero stopwords> <directorio indices>
```

<img src="https://github.com/Jumacasni/MUII-GIW/blob/main/img/p1-01.png" width="100%" height="">

2. Se ejecuta la interfaz gráfica mediante la línea de comandos usando la instrucción
```
java -jar Searcher.java <directorio indices>
```
<img src="https://github.com/Jumacasni/MUII-GIW/blob/main/img/p1-02.png" width="100%" height="">

3. Introducir búsqueda

<img src="https://github.com/Jumacasni/MUII-GIW/blob/main/img/p1-03.png" width="100%" height="">

4. Seleccionar documento a abrir

<img src="https://github.com/Jumacasni/MUII-GIW/blob/main/img/p1-04.png" width="100%" height="">

#### Interfaz web

La interfaz web se proporciona en el archivo [interfaz.zip](practica1/interfaz.zip). Este archivo es una aplicación web de Java y se debe importar a un IDE, en mi caso **Apache Netbeans 12.3**. Se debe ejecutar el proyecto web Java que abrirá en el navegador la URL [http://localhost:8080/Search/](http://localhost:8080/Search/)

1. Introducir la búsqueda

<img src="https://github.com/Jumacasni/MUII-GIW/blob/main/img/p1-05.png" width="100%" height="">

2. Ver documentos relevantes encontrados

<img src="https://github.com/Jumacasni/MUII-GIW/blob/main/img/p1-06.png" width="100%" height="">

3. Abrir un documento cualquiera

<img src="https://github.com/Jumacasni/MUII-GIW/blob/main/img/p1-07.png" width="100%" height="">

<a name="practica2"></a>
## Práctica 2: Sistema de Recomendación basado en Filtrado Colaborativo

Este sistema muestra inicialmente al usuario 20 pelı́culas aleatorias para que este las evalúe, asignando 1 estrella cuando no le gusta nada, hasta 5 estrellas indicando que es una de sus pelı́culas favoritas.

Una vez se obtienen las valoraciones del usuario, el sistema calculará la **similitud entre el usuario y el resto de usuarios**, obteniendo el conjunto de vecinos (usuarios) que más se parecen a él en cuanto a pelı́culas vistas y valoraciones.

El objetivo de esta aplicación es **predecir** la valoración de todas las pelı́culas que el usuario no ha visto pero que sı́ han visto sus vecinos más cercanos, mostrando aquellas pelı́culas que se predicen con una **valoración mayor o igual a 4 estrellas**.

Para el desarrollo de este sistema se ha trabajado con la colección **MovieLens**, compuesta por 100.000 valoraciones de 943 usuarios sobre 1682 pelı́culas.

Toda la documentación de esta práctica se encuentra [aquí](practica2/P2_JuanManuelCastilloNievas.pdf)

### Manual de usuario

Se debe ejecutar ```python3 main.py``` estando los archivos *u.data* y *u.item* en el mismo directorio.

1. Valorar las 20 películas iniciales

<img src="https://github.com/Jumacasni/MUII-GIW/blob/main/img/p2-01.png" width="100%" height="">

2. Esperar a que se calcule la similitud con el resto de usuarios

<img src="https://github.com/Jumacasni/MUII-GIW/blob/main/img/p2-02.png" width="100%" height="">

3. Indicar si se desean ver las recomendaciones encontradas

<img src="https://github.com/Jumacasni/MUII-GIW/blob/main/img/p2-03.png" width="100%" height="">

4. La salida final del programa es una lista ordenada de mayor valoración predecida a menor

<img src="https://github.com/Jumacasni/MUII-GIW/blob/main/img/p2-04.png" width="100%" height="">

<a name="practica3"></a>
## Práctica 3: Análisis básico de una red social

El objetivo de esta práctica es conocer y aprender a usar una herramienta de análisis y visualización de redes, ası́ como familiarizarse con los procedimientos de análisis de redes y sus medida. Como herramienta se ha usado **Gephi**.

Como conjunto de datos se ha utilizado el **GEMSEM Facebook** publicado en la página de **SNAP**, el cual es una colección de datos de las páginas de Facebook en noviembre de 2017. En los nodos se representan las páginas de Facebook verificadas y la unión de dos nodos indica que ambas páginas se han dado me gusta mutuamente, con lo cual estamos ante un **grafo no dirigido**. Este conjunto de datos incluye 8 categorı́as de páginas de Facebook, y para este documento se ha elegido concretamente la categorı́a de TV Shows, la cual está formada por **3.892 nodos** y **17.262 aristas**.

Toda la visualización, análisis y conclusiones se encuentran en la [documentación](practica3/P3_JuanManuelCastilloNievas.pdf)

<a name="practica4"></a>
## Práctica 4: Análisis y evaluación de redes en Twitter

El objetivo de esta práctica es formalizar todos los conocimientos adquiridos en el curso aplicándo- los a un caso real de análisis de una red social online generada a partir de Twitter. El análisis se ha hecho usando la herramienta **Gephi**.

Para la realización de esta práctica y aprovechando la celebración del concurso de Eurovisión y el gran impacto mediático que tiene (sobre todo en Twitter), se han planteado las siguientes preguntas de investigación:

* ¿Qué usuarios de Twitter tuvieron un número mayor de interacciones durante la segunda
semifinal de Eurovisión 2021?
* ¿Cuáles son las cuentas que se deben seguir si alguien está empezando a introducirse en el
mundo de Eurovisión?

El conjunto de datos se ha obtenido utilizando la herramienta **Twitter Streaming Importer** que es un plugin adicional de Gephi. La búsqueda que se ha hecho para obtener el conjunto de datos es muy sencilla y se compone exclusivamente de dos hashtags: *#Eurovision* y *#Eurovision2021*.

Esta búsqueda tuvo lugar durante el desarrollo de la segunda semifinal de Eurovisión el dı́a 20 de mayo de 2021 desde las 21:00 hasta las 23:00, aproximadamente. Cuando finalizó el programa, se habı́an recolectado un total de **4852 nodos** y **8783 aristas**.

Todo el análisis realizado se encuentra en la [documentación](practica4/P4_JuanManuelCastilloNievas.pdf)