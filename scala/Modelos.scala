enum Estudios(val nivel: Int):
  case Tecnico    extends Estudios(1)
  case Bachiller  extends Estudios(2)
  case Licenciado extends Estudios(3)
  case Maestria   extends Estudios(4)

enum Clasificacion:
  case Apto, EnEvaluacion, NoApto

case class Candidato(
  id: String,
  nombre: String,
  aniosExp: Int,
  estudios: Estudios,
  nota: Int,
  habilidades: List[String]
)

case class Puesto(
  id: String,
  nombre: String,
  expMin: Int,
  estudiosMin: Estudios,
  notaMin: Int,
  requisitos: List[String]
)

case class Resultado(
  candidato: Candidato,
  puesto: Puesto,
  puntaje: Int,
  clasificacion: Clasificacion
)