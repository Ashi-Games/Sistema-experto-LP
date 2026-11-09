from integracion.datos import RAIZ

HECHOS = RAIZ / "prolog" / "hechos.pl"


def _atomo(texto):
    """Convierte un texto en átomo Prolog entre comillas simples."""
    limpio = str(texto).replace("\\", "\\\\").replace("'", "\\'")
    return f"'{limpio}'"


def generar_hechos(candidatos, puestos):
    lineas = ["% Archivo generado por Python (integracion/generador.py). No editar a mano.", ""]

    lineas.append("% candidato(Id, Nombre, AniosExp, Estudios, Nota)")
    for c in candidatos:
        lineas.append(
            f"candidato({_atomo(c['id'])}, {_atomo(c['nombre'])}, {int(c['anios_exp'])}, "
            f"{_atomo(c['estudios'])}, {int(c['nota'])})."
        )

    lineas += ["", "% habilidad(IdCandidato, Habilidad)"]
    for c in candidatos:
        for h in c["habilidades"]:
            lineas.append(f"habilidad({_atomo(c['id'])}, {_atomo(h)}).")

    lineas += ["", "% puesto(Id, Nombre, ExpMin, EstudiosMin, NotaMin)"]
    for p in puestos:
        lineas.append(
            f"puesto({_atomo(p['id'])}, {_atomo(p['nombre'])}, {int(p['exp_min'])}, "
            f"{_atomo(p['estudios_min'])}, {int(p['nota_min'])})."
        )

    lineas += ["", "% requiere(IdPuesto, Habilidad)"]
    for p in puestos:
        for r in p["requisitos"]:
            lineas.append(f"requiere({_atomo(p['id'])}, {_atomo(r)}).")

    HECHOS.write_text("\n".join(lineas) + "\n", encoding="utf-8")
    return HECHOS