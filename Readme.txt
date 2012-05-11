-Práctica de Tecnologías de pregunta y respuesta:

		Realizada por Carlos Crespo González-Calero

_____                 . . . . . o o o o o
  __|[_]|__ ___________ _______    ____      o
 |[] [] []| [] [] [] [] [_____(__  ][]]_n_n__][.
_|________|_[_________]_[________]_|__|________)<
  oo    oo 'oo      oo ' oo    oo 'oo 0000---oo\_
 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


----Práctica básica:

Para completar la práctica básica se ha implementado una grámatica básica suficiente para que
 Nico pueda mostrar el resultado de una búsqueda del tipo:
	"Quiero un viaje para 3 personas de madrid a barcelona el 01 del 07 para 2012"
Imprimiendo en la extensión de chrome uno de los viajes encontrados en formato enriquecido. El proyecto entregado actualmente está configurado para buscar vuelos.

Se han modificado las siguientes clases:

travel.grf : para implementar un grafo sencillo que permita una respuesta 

userAgent.asl : recibe los mensajes de la extensión chrome y los manda al nluAgent.asl
	Para que funcione en la versión de trenes descomentar la parte comentada y comentar la función equivalente para aviones


nluAgent.asl : recibe los mensajes de userAgent y los transforma en viajes
	Para que funcione en la versión de trenes descomentar la línea que manda el viaje a travelAgent y comentar la equivalente de flightAgent

travelAgent.asl : se encarga de buscar los trenes y mandarselos de vuelta a userAgent para que los muestre Nico desde su extensión.



   	                            ___
   		                          \\ \
   		                           \\ `\
               ___               \\  \
              |    \              \\  `\
              |     \              \ \\ \
              |      \              \ (*)`\
              | =(*)= \              \  \\ \        _//
              |      __\__------------`-----`------(__)----._
            __|---~~~       ____          ______    __    [][\___
         --|___             |//| =(*)=  /~      )  |\\|          \__
               ~~~---..._______________/      ,/_________________/
                              (__)    /      /  =         (__)= : : : : : : : :
                              //     /     ,/  =
                             ..     /  // /    =
                            ..     / (*),/    =
                           ..     / // /       =
                          ..     //  ,/       =
                         ..     //  /         =
                        ..     // ,/         \
                       ..     //_/           \
                      ..                    \
                     ..                       \
                    ..                       \
                   ..                        \
                  ..                        \

----Práctica opcional: scrapping de vuelos
	

"Quiero el viaje más barato para 3 personas de madrid a copenhagen el 01 del 07 para 2012"
"Quiero el viaje más barato para 3 personas de madrid a berlin el 12 del 08 para 2012"

Clases modificadas:

Agentes: userAgent, nluAgent y flightAgent (nuevo)
Implementación de la función findFlight en flightAgent
Declaración de la función findFlight en SOEnvironment
Implementación de la función findFlight en TEPRModel
Adaptación de VuelosBaratosScrapper a interfaz WebServiceConnector
Implementación de VuelosBaratosConvenion
Implementación de JourneyFlight en sojason.beans
Adaptación de JourneyFlight a interfaz perceptable. Función unfoldPercepts()



La lectura de la parte que viene a continuación es opcional y se recomienda únicamente si el lector quiere llegar a compartir los sentimientos del desarrollador causados por el desarrollo de la práctica.

Se ha realizado un scrapping de la página de vuelosBaratos.es y como resultado se ha llegado a un servicio capaz de comparar entre más de 400 vuelos seleccionando únicamente el más barato, libre de publicidad, estrategias de márketing u otros factores que puedan perjudicar tu búsqueda por la verdadera ganga, ese vuelo que una vez lo reservas correrás a twittear lo barato que te ha salido y lo útil que te ha sido VuelosBaratosScrapper.java.
A pesar de ser una versión beta con una gigantesca ToDo list, cumple su cometido, te encuentra "el vuelo".

Para completar las tareas encomendadas se siguió el siguiente procedimiento:

-------Parte I: "VuelosBaratosScrapping: from novice to ninja"---------
El nuevo proyecto se llamaría VuelosBaratosScrapping en honor a su función: hacer scrapping de la página VuelosBaratos.es. La elección de esta página fue simplemente la virtud de que la información de tu búsqueda se pasaba en la URL, simple, llano y sencillo.
La cosa se complicó cuando al buscar la información en el div donde debería estar no se obtenía nada, ni por JSoup ni por JavaScrappe ni por nodeJS ni por ninguna tecnología existente.
Finalmente, con la ayuda inestimable de Miguel Coronado, doctorando del grupo de sistemas inteligentes y programador muy diestro en estos temas, se encontró la información de los vuelos escondida en unos JSON en unas funciones dentro de unos scripts al final de la respesta HTTP a la petición GET.
Ya era cuestión de tiempo, los patrones y expresiones regulares hicieron su función, así como el conocimiento adquirido durante mi periodo de becario en el GSI y en cuestión de horas el módulo java era capaz de parsear cerca de 500 vuelos y compararlos todos simultáneamente para encontrar "el vuelo".

-------Parte II: "Integrando en el proyecto"----------
Una vez desarrollado el sistema en los .java corrientes y amigables que me habían acompañado hasta el momento, tocaba la integración con el proyecto Jason. Ya no existía eclipse, sólo el bloc de notas, como los hombres. Simplemente empecé a tirar del hilo, el resultado fue que para implementar nuestro módulo debía cambiar gran parte de lo escrito hasta el momento, a parte de tocar unas cuantas clases que ya estaban cuando yo llegué. El resultado fue lo que acompaña este readme, un proyecto capaz de encontrar "el vuelo"

-------Parte III: "Queda mucho por hacer"---------
- Implementar todos los códigos de aeropuertos restantes
- Implementar los códigos de compañías
- Casos de vuelos con escalas
- Otras opciones de búsqueda a parte del vuelo más barato
- Unas gramáticas más sofisticadas para mejorar la interacción con Nico
- Manejo de excepciones
- Vestir a Nico de azafata
- Negociaciones entre agentes
- ...





