
# DressMeApp

DressMeApp es una aplicación de Android que permite al usuario crear y visualizar un *armario virtual* con la ropa que tenga y, con las prendas que se registren, sugerir conjuntos de prendas para vestir en diferentes ocasiones.

El proyecto ha sido desarrollado utilizando Android Studio, Java y Gradle. La aplicación utiliza una base de datos SQLite almacenada localmente para registrar las prendas del usuario, sus propiedades y un historial de las sugerencias de conjuntos que han sido aceptadas por el usuario. También se pueden crear diferentes _perfiles_ para su uso por diferentes personas en el ámbito doméstico.

## Pantallas (Activities)

### Principal
 - ``PrimeraActivity``: Es la pantalla que se muestra cuando el usuario inicia la aplicación. Muestra el logo durante 1,5 segundos y después muestra la ``MainActivity``.
 - ``MainActivity``: Permite al usuario iniciar sesión con su perfil escribiendo su nombre y contraseña o, si no tiene, entrar a la ``RegistroActivity``.
 - ``RegistroActivity``: Permite crear un nuevo perfil o importar uno previamente exportado.
 - ``MenuPrincipalActivity``: Es lo que se abre una vez iniciado sesión de forma correcta. Permite acceder a las tres funcionalidades principales del programa: el vestuario, el recomendador y la configuración.
### Vestuario
 - ``VestuarioActivity``: Permite al usuario visualizar sus prendas de distintas formas: filtrando por palabras clave u ordenando/agrupando en base a diferentes criterios.
 - ``AniadirPrendaActivity``: Permite al usuario añadir una nueva prenda a su armario.
 - ``ModificarPrendaActivity``: Permite al usuario modificar los atributos de la prenda, adjuntarle una foto, enviarla a otro perfil o borrarla.
 -  ``FotosActivity``: Pide permiso para acceder a la cámara. Si lo consigue, la activa y permite al usuario tomar una foto de su prenda y guardarla para distinguir fácilmente su prenda.
### Recomendador
 - ``RecomendadorActivity``: Es el menú principal de la funcionalidad de recomendación, que permite solicitar una recomendación o visualizar el historial o los conjuntos favoritos.
 - ``AlgoritmoRecomendadorActivity``: Solicita al usuario la información contextual necesaria (formalidad, temperatura ambiente) para generar una sugerencia de vestimenta (conjunto).
 - ``ResultadoAlgoritmoActivity``: Muestra el conjunto generado. El usuario puede volver a solicitar una recomendación hasta que pulse "Guardar" o "Añadir a favoritos".
 -  ``HistorialActivity``: Muestra la lista de los conjuntos que ha guardado el usuario, junto con un nombre descriptivo del evento que el usuario ha introducido para que pueda reconocer para qué situación se ha sugerido el conjunto.
 -  ``ConjuntosFavoritosActivity``: Muestra la lista de los conjuntos que ha guardado el usuario como favoritos.
### Configuración
 - ``ConfiguracionActivity``: Es el menú principal de la configuración. Permite al usuario añadir tallas, colores o combinaciones de colores y gestionar su perfil.
 - ``CambioTallasActivity`` y ``AniadirTallaActivity``: Permite al usuario visualizar las tallas que se pueden asignar a las prendas y añadir nuevas (``AniadirTallaActivity``).
 - ``CambioColorActivity`` y ``NuevoColorActivity``: Permite al usuario visualizar los colores que se pueden asignar a las prendas y añadir nuevos (``AniadirColorActivity``).
 - ``AniadirCombinacionActivity``: Permite indicar que parejas de colores forman buenas combinaciones. Esta información es usada por el recomendador para evitar sugerir conjuntos con combinaciones de colores antiestéticas.
 - ``PerfilConfiguracionActivity``: Muestra distintas opciones para gestionar el perfil del usuario: mostrar su información, cambiar la contraseña, exportar sus datos o borrar el perfil.
 - ``MostrarDatosActivity``: Muestra el nombre del perfil y la contraseña (oculta por defecto).
 - ``ConfiguracionContraseniaActivity``: Permite al usuario cambiar su contraseña.
## Clases relacionadas con la base de datos
- ``BaseDatos``: Inicializa la base de datos con algunos valores por defecto.
- ``GestorBD``: Encapsula la funcionalidad global de la base de datos.
- ``GestorBDPerfil``: Encapsula la funcionalidad relacionada con los perfiles de la base de datos.
- ``GestorBDPrendas``: Encapsula la funcionalidad relacionada con la funcionalidad de vestuario de la base de datos.
- ``GestorBDFotos``: Encapsula la funcionalidad relacionada con la toma y guardado de fotos de prendas en la base de datos.
- ``GestorBDAlgoritmo``: Encapsula la funcionalidad relacionada con la funcionalidad del algoritmo vestidor de la base de datos y la gestión de conjuntos.
- ``ExportarBD``: Permite exportar la información de la base de datos en ficheros en un formato para posteriormente importarlos en otro dispositivo o para hacer una copia de seguridad.
- ``LibreriaBD``: Librería externa con utilidades para interactuar con la base de datos a bajo nivel.
## Clases del paquete "Objetos"
- ``Prenda``: Clase que describe una prenda.
- ``PrendaAdapter``: Clase que define cómo se muestra una lista de prendas por pantalla (usado en ``VestuarioActivity``, ``HistorialActivity``...)
- ``ColorPrenda``: Clase que describe un color asociado a una prenda.
- ``ColorAdapter``: Clase que define cómo se muestra un listado de colores (``CambioColorActivity``).
- ``ComboColorPrenda``: Clase que describe un par de colores que combinan bien.
- ``ComboColorAdapter``: Clase que define cómo se muestra un listado de objetos ``ComboColorPrenda`` (``AniadirCombinacionActivity``).
- ``TallaAdapter``: Clase que define cómo se muestra un listado de tallas (``CambioTallasActivity``)
- ``Conjunto``: Clase que representa un conjunto de prendas que sugiere el algoritmo recomendador.
- ``Exportador``: Clase cuyos métodos permiten exportar los datos de la aplicación en la carpeta de descargas.
- ``Importador``: Clase cuyos métodos importan datos de la aplicación previamente exportados desde la carpeta de descargas.
- ``RecyclerViewOnItemClickListener``: Interfaz que registra eventos de pulsación en los objetos ``RecyclerView`` usados para mostrar los listados.
- Clases para la exportación/importación: varias clases que representan el contenido de las tablas de la base de datos para facilitar su exportación/importación: ``ColorBD``, ``ComboColorBD``, ``ConjuntoBD``, ``FotoBD``, ``PerfilBD``, ``PrendaBD`` y ``TallaBD``.