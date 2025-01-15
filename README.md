# The Movie DataBase

## This proyect 

Una app construida con **Kotlin** para consumir la Api de [The Movie DB](https://www.themoviedb.org/).  
En esta rama puede encontrar las UI construida con las View Clasicas generadas con XML.  
Pero si usted prefiere, al igual que yo, las  UI en **JetPack Compose**, lo puede encontrar en esta Branch [Compose](https://github.com/jorgesanme/the_movie_db/tree/ComposeUI) 

## Description
Esta app muestra las Películas más populares en la zona donde se encuentre el dispositivo. Para localizar el mismo se usa el ServiceLocator, el cual necesita que el usuario conceda los permisos de localización.  
Una vez descargada la lista de películas, se les muestra al usuario en un GripView. Desde aquí y con un click en cada imagen, se le presenta los detalles de la película donde se puede selecciónar las favoritas.  
Para conseguir este comportarmiento, se ha creado un **Single Source of Truth**. Descargando el listado desde la api y almacenandolo en una DDBB local (Room). Desde aquí es donde se inicia toda interacción con la UI, para mostar listado, detalles o marcar como favoritos.


## Project Description
Se desea demostrar los conocimientos en la creación de apps que consuman datos desde un backEnd.  
Para ello se ha diseñado una **Arquitectura Clean** usando los patrones **SOLID**.  
Con respecto al patron de presentación, se ha optado por el  **MVVM**.  

El proyecto se construye con una arquitectura por capas. Las mismas estan localizadas en diferentes modulos:
* App
* data
* domain
* useCases
  > Los más puristas prefieren 3 capas. Es mi preferencia separar los UseCases en un modulo 😏 😃

### Dependency Injection
  Se han realiado pruebas con las siguiente librerias de DI:
  * [Dagger2](https://github.com/jorgesanme/the_movie_db/tree/DI_5-5_dagger2)
  * [Koin](https://github.com/jorgesanme/the_movie_db/tree/DI_5-2_Koin)
  * Hilt.  --> Siendo esta última la que se mergea a Main

### Test
  Se ha generado una batería de test usando JUnit4 y Mockito para los test de UI.  
  Dado la división por modulos y la complejidad para testear, se han creado otros tres modulos con las siguientes funciones:
  * appTestShared: --> Helper & Fake de los repositorios tanto local como remoto, LocationServise y permissionChecker
  * buildSrc: --> Gestion de las dependencias y librerias. (obsoleto tras la creación de los libs.versions.toml)
  * testShared: --> Mock de un objeto domain

Quedando la siguiente estructura de modulos:

<img src="https://github.com/jorgesanme/the_movie_db/blob/main/images/modules.png" width="160" height="160" />
  
### Images
| Permisos | Cargo de Datos | Detalles |
| --- | --- | --- |
|<img src="https://github.com/jorgesanme/the_movie_db/blob/main/images/permission.gif" width="160" height="350" />|<img src="https://github.com/jorgesanme/the_movie_db/blob/main/images/start.gif" width="160" height="350" />|<img src="https://github.com/jorgesanme/the_movie_db/blob/main/images/details.gif" width="160" height="350" />|


### Librerias
|  **_Api call_** | **_DI_**| **_UI layer_**| **_Test_**|
|---|---|---|---|
| Coroutines | Hilt  | Room  | Junit4  |
| OkHttp3    |Dagger2| Glide | Espresso|
| Retrofit   | Koin  | Coil  | Mockito |
| Arrow      |       |       | Turbine |
| JavaX      |       |       |         |
