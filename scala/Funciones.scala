object Funciones:

  private def prop(valor: Int, req: Int, max: Int): Int =
    if req <= 0 then max else math.min(valor * max / req, max)

  def puntaje(c: Candidato, p: Puesto): Int =
    val exp   = prop(c.aniosExp, p.expMin, 25)
    val est   = prop(c.estudios.nivel, p.estudiosMin.nivel, 25)
    val tiene = p.requisitos.count(c.habilidades.contains)
    val hab   = prop(tiene, p.requisitos.size, 25)
    val nota  = c.nota * 25 / 100
    exp + est + hab + nota

  def cumpleRequisitos(c: Candidato, p: Puesto): Boolean =
    c.aniosExp >= p.expMin &&
    c.estudios.nivel >= p.estudiosMin.nivel &&
    c.nota >= p.notaMin &&
    p.requisitos.forall(c.habilidades.contains)

  def aptos(cs: List[Candidato], p: Puesto): List[Candidato] =
    cs.filter(c => cumpleRequisitos(c, p))

  def ranking(cs: List[Candidato], p: Puesto): List[(Candidato, Int)] =
    cs.map(c => (c, puntaje(c, p))).sortBy(r => -r._2)

    
  def clasificar(c: Candidato, p: Puesto): Clasificacion =
    val basico = c.aniosExp >= p.expMin && c.estudios.nivel >= p.estudiosMin.nivel
    (cumpleRequisitos(c, p), basico) match
      case (true, _)      => Clasificacion.Apto
      case (false, true)  => Clasificacion.EnEvaluacion
      case (false, false) => Clasificacion.NoApto

  def evaluar(cs: List[Candidato], p: Puesto): List[Resultado] =
    cs.map(c => Resultado(c, p, puntaje(c, p), clasificar(c, p)))
      .sortBy(r => -r.puntaje)