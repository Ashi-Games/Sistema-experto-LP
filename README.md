# Sistema Experto de Selección de Candidatos

Proyecto del curso Lenguajes de Programación. Sistema experto multilenguaje que evalúa candidatos para puestos de trabajo:

- **Python**: integrador. Lee los datos, se comunica con Scala y Prolog y muestra el resultado.
- **Scala**: lógica principal (puntaje, filtrado, ranking, pattern matching).
- **Prolog**: base de conocimientos y reglas de inferencia.

## Arquitectura

```
          USUARIO
             |
          Python (main.py)
         /           \
     Scala          Prolog
  (puntaje y      (reglas e
 clasificación)   inferencia)
```

Flujo: Python lee `data/*.json` → se los envía a Scala → recibe puntajes y clasificación → genera `prolog/hechos.pl` → consulta a Prolog → compara y muestra los resultados.

## Requisitos

| Herramienta | Versión probada | Verificar con |
| --- | --- | --- |
| JDK | 17 o superior (probado con 24) | `java -version` |
| [Scala CLI](https://scala-cli.virtuslab.org/install) | reciente | `scala-cli version` |
| [SWI-Prolog](https://www.swi-prolog.org/download/stable) | 10.1.7 | `swipl --version` |
| Python | 3.10 o superior (probado con 3.14) | `py --version` (Windows) o `python3 --version` |

Al instalar SWI-Prolog en Windows, marca **"Add swipl to the system PATH"**. Al instalar Python, marca **"Add python.exe to PATH"**.

No hay librerías de Python que instalar: solo se usa la biblioteca estándar.

## Extensiones recomendadas (VS Code)

- **Scala (Metals)**
- **Python** (Microsoft)
- **VSC-Prolog**

## Estructura

```
.
├── main.py                  # programa principal (integrador)
├── data/
│   ├── candidatos.json
│   └── puestos.json
├── integracion/
│   ├── datos.py             # lectura de los JSON
│   ├── scala_service.py     # Python -> Scala (subprocess)
│   ├── generador.py         # genera prolog/hechos.pl desde los datos
│   └── prolog_service.py    # Python -> Prolog (subprocess)
├── scala/
│   ├── Modelos.scala        # case class y enum
│   ├── Funciones.scala      # puntaje, ranking, pattern matching
│   ├── Repositorio.scala    # registrar y buscar
│   ├── Integracion.scala    # modo no interactivo (--stdin)
│   ├── Prolog.scala         # consulta Scala -> Prolog (menú)
│   ├── Menu.scala           # menú de consola
│   └── Main.scala
└── prolog/
    ├── conocimiento.pl      # reglas
    └── hechos.pl            # generado por Python
```

## Cómo ejecutar

Clona el repo y, **desde la carpeta raíz**:

```bash
git clone https://github.com/Ashi-Games/Sistema-experto-LP.git
cd Sistema-experto-LP
py main.py
```

La primera vez tarda unos segundos porque Scala compila.

### Solo Scala (menú interactivo)

```bash
scala-cli run scala/
```

### Solo Prolog

```bash
swipl prolog/conocimiento.pl
```

```prolog
?- findall(C, recomendado(C, p1), L).
```

Salir con `halt.`

## Datos

Para agregar candidatos o puestos, edita `data/candidatos.json` y `data/puestos.json`. Scala y Prolog toman los mismos datos desde Python.

## Autor

<Eidan Limaylla Santos> — <lenguajes de programacion / 8vo ciclo>