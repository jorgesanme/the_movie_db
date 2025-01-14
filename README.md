# The Movie DataBase

## This proyect 

Una app construida con **Kotlin** para consumir la Api de [The Movie DB](https://www.themoviedb.org/).  
En esta rama puede encontrar las UI construida con las View Clasicas construidas con XML.  
Pero si usted prefiere, al igual que yo, las  UI en **JetPack Compose**, lo puede encontrar en esta Branch [Compose](https://github.com/jorgesanme/the_movie_db/tree/ComposeUI) 

## DESCRIPTION
Esta app muestra las Películas más populares en la zona donde se encuentre el dispositivo. Para localizar el mismo se usa el ServiceLocator, el cual necesita que el usuario conceda los permisos de localización.  
Una vez descarga la lista de películas, se les muestra al usuario en un GripView. Desde aquí y con un click en cada imagen, se le presenta los detalles de la película donde se puede selecciónar las favoritas.  
Para conseguir este comportarmiento, se ha creado un **Single Source of Truth**. Descargando el listado desde la api y almacenandolo en una DDBB local (Room). Desde aquí es donde se inicia toda interacción con la UI, para mostar listado, detalles o marcar como favoritos.


## PROYECT DESCRIPTION
Se desea demostrar los conocimientos en la creación de apps que consuman datos desde un backEnd.  
Para ello se ha diseñado una **Arquitectura Clean** usando los patrones **SOLID**.  
Con respecto al patron de presentación, se ha optado por el  **MVVM**
El proyecto se construye con una arquitectura por capas. Las mismas estan localizadas en 

## IMAGEN
<img src="https://github.com/jorgesanme/Compose_Marvel_Api/blob/main/images/op.gif" width="160" height="350" />|
<img src="https://github.com/jorgesanme/Compose_Marvel_Api/blob/main/images/segund.gif" width="160" height="350" />|
<img src="https://github.com/jorgesanme/Compose_Marvel_Api/blob/main/images/detall.gif" width="160" height="350" />|

mucho bla bla bla
