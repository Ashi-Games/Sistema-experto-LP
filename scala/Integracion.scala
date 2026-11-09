import scala.io.Source

object Integracion:

  private def parseEstudios(s: String): Estudios =
    s.trim.toLowerCase match
      case "tecnico"    => Estudios.Tecnico
      case "bachiller"  => Estudios.Bachiller
      case "licenciado" => Estudios.Licenciado
      case "maestria"   => Estudios.Maestria
      case otro         => throw new IllegalArgumentException(s"Estudios desconocidos: $otro")

  private def lista(s: String): List[String] =
    s.split(",").map(_.trim.toLowerCase).filter(_.nonEmpty).toList

  def leerEntrada(lineas: List[String]): (List[Candidato], List[Puesto]) =
    val campos = lineas.map(_.trim).filter(_.nonEmpty).map(_.split("\\|", -1).toList)
    val candidatos = campos.collect {
      case List("C", id, nombre, exp, est, nota, habs) =>
        Candidato(id, nombre, exp.toInt, parseEstudios(est), nota.toInt, lista(habs))
    }
    val puestos = campos.collect {
      case List("P", id, nombre, exp, est, nota, reqs) =>
        Puesto(id, nombre, exp.toInt, parseEstudios(est), nota.toInt, lista(reqs))
    }
    (candidatos, puestos)

  def correr(): Unit =
    val src = Source.fromInputStream(System.in, "UTF-8")
    val lineas = try src.getLines().toList finally src.close()
    val (candidatos, puestos) = leerEntrada(lineas)
    for p <- puestos; r <- Funciones.evaluar(candidatos, p) do
      println(s"R|${p.id}|${r.candidato.id}|${r.puntaje}|${r.clasificacion}")