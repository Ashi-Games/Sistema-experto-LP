# Sistema Experto de Selección de Candidatos

Avance 1 del curso Lenguajes de Programación. Sistema experto que evalúa candidatos para puestos de trabajo usando **Scala** (lógica, datos y menú) y **Prolog** (base de conocimientos y reglas de inferencia).

## Requisitos

| Herramienta | Versión probada | Verificar con |
| --- | --- | --- |
| JDK | 17 o superior (probado con 24) | `java -version` |
| [Scala CLI](https://scala-cli.virtuslab.org/install) | cualquiera reciente | `scala-cli version` |
| [SWI-Prolog](https://www.swi-prolog.org/download/stable) | 10.1.7 | `swipl --version` |

Al instalar SWI-Prolog en Windows, marca la opción **"Add swipl to the system PATH"**.

## Extensiones recomendadas (VS Code)

- **Scala (Metals)** — scalameta
- **Scala Syntax (official)** — scala-lang
- **VSC-Prolog**

Si VSC-Prolog muestra el aviso *"The Prolog executable was not found"*, agrega en `settings.json`:

```json
{
  "prolog.executablePath": "C:\\Program Files\\swipl\\bin\\swipl.exe"
}
```

## Estructura

```
.
├── scala/
│   ├── Modelos.scala       # case class y enum (Candidato, Puesto, Resultado)
│   ├── Funciones.scala     # puntaje, filtrado, ranking, pattern matching
│   ├── Repositorio.scala   # registrar y buscar candidatos/puestos
│   ├── Prolog.scala        # conexión Scala -> Prolog (ProcessBuilder)
│   ├── Menu.scala          # menú de consola
│   └── Main.scala          # punto de entrada
└── prolog/
    └── conocimiento.pl     # hechos y reglas (apto, recomendado, en_evaluacion, no_apto)
```

## Cómo ejecutar

Clona el repositorio y, **desde la carpeta raíz** (importante: Scala lee `prolog/conocimiento.pl` con ruta relativa):

```bash
git clone https://github.com/<usuario>/<repo>.git
cd <repo>
scala-cli run scala/
```

La primera vez tarda porque descarga el compilador.

### Probar solo Prolog

```bash
swipl prolog/conocimiento.pl
```

```prolog
?- findall(C, apto(C, p1), L).
?- findall(C, recomendado(C, p1), L).
?- findall(C, en_evaluacion(C, p1), L).
?- findall(C, no_apto(C, p1), L).
```

Salir con `halt.`

## Menú

```
1. Listar candidatos
2. Listar puestos
3. Registrar candidato
4. Buscar candidato por nombre
5. Evaluar candidatos para un puesto (Scala)
6. Consultar recomendaciones (Prolog)
0. Salir
```

## Limitaciones conocidas

Los candidatos registrados desde el menú existen solo en Scala; los hechos de Prolog están escritos a mano en `conocimiento.pl`, por lo que la opción 6 no los ve.

## Autor

<Eidan Limaylla Santos> — <Leanguajes de Programacion / 8to ciclo>