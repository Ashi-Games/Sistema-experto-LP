@main def main(): Unit =
  val candidatos = List(
    Candidato("c1", "Ana",   5, Estudios.Licenciado, 90, List("scala", "prolog", "sql")),
    Candidato("c2", "Luis",  2, Estudios.Tecnico,    70, List("scala", "git")),
    Candidato("c3", "Marta", 4, Estudios.Licenciado, 85, List("python", "sql", "scala")),
    Candidato("c4", "Jose",  1, Estudios.Bachiller,  60, List("html")),
    Candidato("c5", "Carla", 6, Estudios.Maestria,   78, List("scala", "prolog", "python"))
  )

  val puestos = List(
    Puesto("p1", "Desarrollador Backend", 3, Estudios.Licenciado, 75, List("scala", "sql")),
    Puesto("p2", "Analista de Datos",     2, Estudios.Bachiller,  70, List("python", "sql"))
  )

  Menu.correr(Repositorio(candidatos, puestos))