import scala.io.Source
import scala.util.Try

object Prolog:
  private val archivo = "prolog/conocimiento.pl"

  def consultar(relacion: String, idPuesto: String): Either[String, List[String]] =
    Try {
      val meta = s"forall($relacion(C,$idPuesto),(write(C),nl))"
      val pb = new ProcessBuilder("swipl", "-q", "-s", archivo, "-g", meta, "-t", "halt")
      pb.redirectError(ProcessBuilder.Redirect.INHERIT)
      val proc = pb.start()
      val fuente = Source.fromInputStream(proc.getInputStream)
      val salida = try fuente.getLines().map(_.trim).filter(_.nonEmpty).toList finally fuente.close()
      proc.waitFor()
      salida
    }.toEither.left.map(e => s"No se pudo ejecutar swipl: ${e.getMessage}")

  def candidatos(repo: Repositorio, relacion: String, idPuesto: String): Either[String, List[Candidato]] =
    consultar(relacion, idPuesto).map(ids => ids.flatMap(repo.buscarCandidato))

  def demo(repo: Repositorio): Unit =
    println("\n--- Consulta a Prolog ---")
    for p <- repo.puestos; rel <- List("recomendado", "en_evaluacion", "no_apto") do
      candidatos(repo, rel, p.id) match
        case Right(cs) => println(s"${p.nombre} | $rel: ${cs.map(_.nombre).mkString(", ")}")
        case Left(e)   => println(s"Error: $e")