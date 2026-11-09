import time

from integracion.datos import cargar_candidatos, cargar_puestos
from integracion.prolog_service import consultar_prolog
from integracion.scala_service import ejecutar_scala


def veredicto_prolog(inferencia, id_candidato):
    if id_candidato in inferencia["recomendado"]:
        return "Recomendado"
    if id_candidato in inferencia["en_evaluacion"]:
        return "EnEvaluacion"
    if id_candidato in inferencia["no_apto"]:
        return "NoApto"
    return "Apto"


def coinciden(scala, prolog):
    return scala == ("Apto" if prolog == "Recomendado" else prolog)


def main():
    print("=" * 62)
    print("SISTEMA EXPERTO DE SELECCIÓN DE CANDIDATOS")
    print("Python + Scala + Prolog")
    print("=" * 62)

    print("\n[1/3] Python: leyendo datos...")
    candidatos = cargar_candidatos()
    puestos = cargar_puestos()
    nombres = {c["id"]: c["nombre"] for c in candidatos}
    print(f"      {len(candidatos)} candidatos, {len(puestos)} puestos")

    print("[2/3] Scala: calculando puntajes y clasificación (puede tardar unos segundos)...")
    t0 = time.time()
    resultados = ejecutar_scala(candidatos, puestos)
    print(f"      {len(resultados)} evaluaciones en {time.time() - t0:.1f} s")

    print("[3/3] Prolog: aplicando reglas de inferencia...")
    inferencia = consultar_prolog(candidatos, puestos)

    for p in puestos:
        print("\n" + "-" * 62)
        print(f"PUESTO: {p['nombre']} ({p['id']})")
        print("-" * 62)
        print(f"{'Candidato':<10}{'Pts':>5}  {'Scala':<13}{'Prolog':<14}OK")
        filas = sorted(
            (r for r in resultados if r["puesto"] == p["id"]),
            key=lambda r: -r["puntaje"],
        )
        for r in filas:
            prolog = veredicto_prolog(inferencia[p["id"]], r["candidato"])
            ok = "si" if coinciden(r["clasificacion"], prolog) else "NO"
            print(
                f"{nombres[r['candidato']]:<10}{r['puntaje']:>5}  "
                f"{r['clasificacion']:<13}{prolog:<14}{ok}"
            )

    print("\nProceso terminado.")


if __name__ == "__main__":
    main()