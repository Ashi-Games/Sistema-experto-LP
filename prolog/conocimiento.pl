% Los hechos (candidato, habilidad, puesto, requiere) los genera Python
% desde data/*.json y quedan en prolog/hechos.pl
:- include('hechos.pl').

% ---------- HECHOS FIJOS ----------
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