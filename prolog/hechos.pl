% Archivo generado por Python (integracion/generador.py). No editar a mano.

% candidato(Id, Nombre, AniosExp, Estudios, Nota)
candidato('c1', 'Ana', 5, 'licenciado', 90).
candidato('c2', 'Luis', 2, 'tecnico', 70).
candidato('c3', 'Marta', 4, 'licenciado', 85).
candidato('c4', 'Jose', 1, 'bachiller', 60).
candidato('c5', 'Carla', 6, 'maestria', 78).
candidato('c6', 'Diego', 3, 'licenciado', 80).

% habilidad(IdCandidato, Habilidad)
habilidad('c1', 'scala').
habilidad('c1', 'prolog').
habilidad('c1', 'sql').
habilidad('c2', 'scala').
habilidad('c2', 'git').
habilidad('c3', 'python').
habilidad('c3', 'sql').
habilidad('c3', 'scala').
habilidad('c4', 'html').
habilidad('c5', 'scala').
habilidad('c5', 'prolog').
habilidad('c5', 'python').
habilidad('c6', 'scala').
habilidad('c6', 'sql').

% puesto(Id, Nombre, ExpMin, EstudiosMin, NotaMin)
puesto('p1', 'Desarrollador Backend', 3, 'licenciado', 75).
puesto('p2', 'Analista de Datos', 2, 'bachiller', 70).

% requiere(IdPuesto, Habilidad)
requiere('p1', 'scala').
requiere('p1', 'sql').
requiere('p2', 'python').
requiere('p2', 'sql').
