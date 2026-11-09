import shutil
import subprocess
from pathlib import Path

from integracion.datos import RAIZ
from integracion.generador import generar_hechos

REGLAS = "prolog/conocimiento.pl"
RELACIONES = ["recomendado", "en_evaluacion", "no_apto"]


def _buscar_swipl():
    exe = shutil.which("swipl")
    if exe:
        return exe
    por_defecto = Path(r"C:\Program Files\swipl\bin\swipl.exe")
    if por_defecto.exists():
        return str(por_defecto)
    raise RuntimeError("No se encontró swipl (SWI-Prolog) en el PATH")


def consultar_prolog(candidatos, puestos):
    """Genera los hechos desde los datos y devuelve {id_puesto: {relacion: [ids]}}."""
    generar_hechos(candidatos, puestos)

    meta = (
        "forall(("
        f"member(R,[{','.join(RELACIONES)}]), puesto(P,_,_,_,_), call(R,C,P)),"
        "(write(R),write('|'),write(P),write('|'),write(C),nl))"
    )
    resultado = subprocess.run(
        [_buscar_swipl(), "-q", "-s", REGLAS, "-g", meta, "-t", "halt"],
        capture_output=True,
        text=True,
        encoding="utf-8",
        cwd=RAIZ,
    )
    if resultado.returncode != 0:
        raise RuntimeError(f"Prolog falló:\n{resultado.stderr}")

    salida = {p["id"]: {r: [] for r in RELACIONES} for p in puestos}
    for linea in resultado.stdout.splitlines():
        partes = linea.strip().split("|")
        if len(partes) == 3 and partes[0] in RELACIONES and partes[1] in salida:
            salida[partes[1]][partes[0]].append(partes[2])
    return salida