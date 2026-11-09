import shutil
import subprocess

from integracion.datos import RAIZ


def _serializar(candidatos, puestos):
    lineas = []
    for p in puestos:
        lineas.append("|".join([
            "P", p["id"], p["nombre"], str(p["exp_min"]),
            p["estudios_min"], str(p["nota_min"]), ",".join(p["requisitos"]),
        ]))
    for c in candidatos:
        lineas.append("|".join([
            "C", c["id"], c["nombre"], str(c["anios_exp"]),
            c["estudios"], str(c["nota"]), ",".join(c["habilidades"]),
        ]))
    return "\n".join(lineas) + "\n"


def ejecutar_scala(candidatos, puestos):
    exe = shutil.which("scala-cli")
    if exe is None:
        raise RuntimeError("No se encontró scala-cli en el PATH")

    resultado = subprocess.run(
        [exe, "run", "scala/", "--", "--stdin"],
        input=_serializar(candidatos, puestos),
        capture_output=True,
        text=True,
        encoding="utf-8",
        cwd=RAIZ,
    )
    if resultado.returncode != 0:
        raise RuntimeError(f"Scala falló:\n{resultado.stderr}")

    salida = []
    for linea in resultado.stdout.splitlines():
        partes = linea.strip().split("|")
        if len(partes) == 5 and partes[0] == "R":
            salida.append({
                "puesto": partes[1],
                "candidato": partes[2],
                "puntaje": int(partes[3]),
                "clasificacion": partes[4],
            })
    return salida