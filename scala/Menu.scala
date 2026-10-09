import scala.annotation.tailrec
import scala.io.StdIn.readLine
import scala.util.Try

object Menu:

  private val texto =
    """
      |===== SISTEMA EXPERTO DE SELECCIÓN =====
      |1. Listar candidatos
      |2. Listar puestos
      |3. Registrar candidato
      |4. Buscar candidato por nombre
      |5. Evaluar candidatos para un puesto (Scala)
      |6. Consultar recomendaciones (Prolog)
      |0. Salir""".stripMargin

  private def leer(msg: String): String =
    Option(readLine(msg)).getOrElse("").trim

  private def leerInt(msg: String): Option[Int] =
    Try(leer(msg).toInt).toOption

  private def parseEstudios(s: String): Option[Estudios] =
    s.toLowerCase match
      case "tecnico"    => Some(Estudios.Tecnico)
      case "bachiller"  => Some(Estudios.Bachiller)
      case "licenciado" => Some(Estudios.Licenciado)
      case "maestria"   => Some(Estudios.Maestria)
      case _            => None

  private def elegirPuesto(repo: Repositorio): Option[Puesto] =
    repo.listarPuestos()
    val p = repo.buscarPuesto(leer("Id del puesto: "))
    if p.isEmpty then println("Puesto no encontrado.")
    p

  private def registrar(repo: Repositorio): Repositorio =
    val id     = leer("Id (ej. c7): ")
    val nombre = leer("Nombre: ")
    val datos = for
      exp  <- leerInt("Años de experiencia: ").filter(_ >= 0)
      est  <- parseEstudios(leer("Estudios (tecnico/bachiller/licenciado/maestria): "))
      nota <- leerInt("Nota de entrevista (0-100): ").filter(n => n >= 0 && n <= 100)
    yield (exp, est, nota)

    datos match
      case Some((exp, est, nota)) if id.nonEmpty && nombre.nonEmpty =>
        val habs = leer("Habilidades separadas por coma: ")
          .split(",").map(_.trim.toLowerCase).filter(_.nonEmpty).toList
        repo.registrarCandidato(Candidato(id, nombre, exp, est, nota, habs)) match
          case Right(r) =>
            println(s"Registrado: $nombre")
            r
          case Left(e) =>
            println(s"Error: $e")
            repo
      case _ =>
        println("Datos inválidos, no se registró.")
        repo

  private def evaluarScala(repo: Repositorio, p: Puesto): Unit =
    println(s"\nEvaluación (Scala) para ${p.nombre}:")
    Funciones.evaluar(repo.candidatos, p).foreach { r =>
      println(s"  ${r.candidato.nombre}: ${r.puntaje} pts -> ${r.clasificacion}")
    }

  private def consultarProlog(repo: Repositorio, p: Puesto): Unit =
    println(s"\nConsulta (Prolog) para ${p.nombre}:")
    for rel <- List("recomendado", "en_evaluacion", "no_apto") do
      Prolog.candidatos(repo, rel, p.id) match
        case Right(cs) => println(s"  $rel: ${cs.map(_.nombre).mkString(", ")}")
        case Left(e)   => println(s"  Error: $e")

  private def ejecutar(op: String, repo: Repositorio): Repositorio =
    op match
      case "1" =>
        repo.listarCandidatos()
        repo
      case "2" =>
        repo.listarPuestos()
        repo
      case "3" =>
        registrar(repo)
      case "4" =>
        repo.buscarPorNombre(leer("Nombre a buscar: ")).foreach(c => println(s"  ${c.id} | ${c.nombre}"))
        repo
      case "5" =>
        elegirPuesto(repo).foreach(p => evaluarScala(repo, p))
        repo
      case "6" =>
        elegirPuesto(repo).foreach(p => consultarProlog(repo, p))
        repo
      case _ =>
        println("Opción no válida.")
        repo

  @tailrec
  def correr(repo: Repositorio): Unit =
    println(texto)
    leer("Opción: ") match
      case "0" => println("Hasta luego.")
      case op  => correr(ejecutar(op, repo))