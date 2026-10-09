% ---------- HECHOS ----------
% candidato(Id, Nombre, AniosExp, Estudios, NotaEntrevista)
candidato(c1, ana,   5, licenciado, 90).
candidato(c2, luis,  2, tecnico,    70).
candidato(c3, marta, 4, licenciado, 85).
candidato(c4, jose,  1, bachiller,  60).
candidato(c5, carla, 6, maestria,   78).

% habilidad(IdCandidato, Habilidad)
habilidad(c1, scala).   habilidad(c1, prolog). habilidad(c1, sql).
habilidad(c2, scala).   habilidad(c2, git).
habilidad(c3, python).  habilidad(c3, sql).    habilidad(c3, scala).
habilidad(c4, html).
habilidad(c5, scala).   habilidad(c5, prolog). habilidad(c5, python).

% puesto(Id, Nombre, ExpMin, EstudiosMin, NotaMin)
puesto(p1, desarrollador_backend, 3, licenciado, 75).
puesto(p2, analista_datos,        2, bachiller,  70).

% requiere(IdPuesto, Habilidad)
requiere(p1, scala). requiere(p1, sql).
requiere(p2, python). requiere(p2, sql).

% nivel(Estudios, Valor)
nivel(tecnico, 1).
nivel(bachiller, 2).
nivel(licenciado, 3).
nivel(maestria, 4).

% ---------- REGLAS ----------
cumple_experiencia(C, P) :-
    candidato(C, _, Exp, _, _), puesto(P, _, ExpMin, _, _), Exp >= ExpMin.

cumple_estudios(C, P) :-
    candidato(C, _, _, E, _), puesto(P, _, _, Emin, _),
    nivel(E, N1), nivel(Emin, N2), N1 >= N2.

cumple_nota(C, P) :-
    candidato(C, _, _, _, Nota), puesto(P, _, _, _, NotaMin), Nota >= NotaMin.

cumple_habilidades(C, P) :-
    candidato(C, _, _, _, _), puesto(P, _, _, _, _),
    forall(requiere(P, H), habilidad(C, H)).

apto(C, P) :-
    cumple_experiencia(C, P), cumple_estudios(C, P),
    cumple_nota(C, P), cumple_habilidades(C, P).

recomendado(C, P) :-
    apto(C, P), candidato(C, _, _, _, Nota), Nota >= 85.

en_evaluacion(C, P) :-
    cumple_experiencia(C, P), cumple_estudios(C, P), \+ apto(C, P).

no_apto(C, P) :-
    candidato(C, _, _, _, _), puesto(P, _, _, _, _),
    \+ apto(C, P), \+ en_evaluacion(C, P).