import json
from pathlib import Path

RAIZ = Path(__file__).resolve().parent.parent
DATA = RAIZ / "data"


def _leer(nombre):
    with open(DATA / nombre, encoding="utf-8") as f:
        return json.load(f)


def cargar_candidatos():
    return _leer("candidatos.json")


def cargar_puestos():
    return _leer("puestos.json")