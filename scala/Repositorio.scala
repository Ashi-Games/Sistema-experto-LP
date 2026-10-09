case class Repositorio(candidatos: List[Candidato], puestos: List[Puesto]):

  def registrarCandidato(c: Candidato): Either[String, Repositorio] =
    if candidatos.exists(_.id == c.id) then Left(s"Ya existe un candidato con id ${c.id}")
    else Right(copy(candidatos = candidatos :+ c))

  def registrarPuesto(p: Puesto): Either[String, Repositorio] =
    if puestos.exists(_.id == p.id) then Left(s"Ya existe un puesto con id ${p.id}")
    else Right(copy(puestos = puestos :+ p))

  def buscarCandidato(id: String): Option[Candidato] =
    candidatos.find(_.id == id)

  def buscarPuesto(id: String): Option[Puesto] =
    puestos.find(_.id == id)

  def buscarPorNombre(texto: String): List[Candidato] =
    candidatos.filter(_.nombre.toLowerCase.contains(texto.toLowerCase))

  def listarCandidatos(): Unit =
    candidatos.foreach { c =>
      println(s"  ${c.id} | ${c.nombre} | ${c.aniosExp} años | ${c.estudios} | nota ${c.nota} | ${c.habilidades.mkString(", ")}")
    }

  def listarPuestos(): Unit =
    puestos.foreach { p =>
      println(s"  ${p.id} | ${p.nombre} | ${p.expMin} años | ${p.estudiosMin} | nota mín ${p.notaMin} | ${p.requisitos.mkString(", ")}")
    }