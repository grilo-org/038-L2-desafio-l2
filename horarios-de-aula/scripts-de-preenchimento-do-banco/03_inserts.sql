USE desafio_l2;

-- DEPARTMENT
INSERT INTO DEPARTMENT (id, name) VALUES
(1, 'Engenharia'),
(2, 'Matemática'),
(3, 'Computação'),
(4, 'Física');

-- TITLE
INSERT INTO TITLE (id, name) VALUES
(1, 'Professor Titular'),
(2, 'Professor Associado'),
(3, 'Professor Assistente'),
(4, 'Professor Substituto');

-- PROFESSOR
INSERT INTO PROFESSOR (id, department_id, title_id) VALUES
(1, 1, 1),
(2, 1, 2),
(3, 2, 3),
(4, 3, 1),
(5, 3, 4),
(6, 4, 2);

-- BUILDING
INSERT INTO BUILDING (id, name) VALUES
(1, 'Prédio Central'),
(2, 'Anexo Norte'),
(3, 'Laboratórios'),
(4, 'Bloco Físico');

-- ROOM
INSERT INTO ROOM (id, building_id) VALUES
(1, 1),
(2, 1),
(3, 2),
(4, 3),
(5, 4);

-- SUBJECT
INSERT INTO SUBJECT (id, subject_id, code, name) VALUES
(1, NULL, 'ENG101', 'Cálculo I'),
(2, NULL, 'ENG102', 'Física I'),
(3, NULL, 'MAT201', 'Álgebra Linear'),
(4, NULL, 'COMP101', 'Introdução à Programação'),
(5, NULL, 'COMP201', 'Estruturas de Dados'),
(6, NULL, 'FIS301', 'Mecânica Quântica'),
(7, NULL, 'COMP301', 'Banco de Dados'),
(8, NULL, 'MAT301', 'Estatística'),
(9, NULL, 'ENG201', 'Cálculo II');

-- PROFESSOR_SUBJECT
INSERT INTO PROFESSOR_SUBJECT (id, professor_id, subject_id) VALUES
(1, 1, 1), -- Prof 1 ministra Cálculo I
(2, 2, 2), -- Prof 2 ministra Física I
(3, 3, 3), -- Prof 3 ministra Álgebra Linear
(4, 4, 4), -- Prof 4 ministra Introdução à Programação
(5, 5, 5), -- Prof 5 ministra Estruturas de Dados
(6, 4, 7), -- Prof 4 ministra Banco de Dados
(7, 3, 8), -- Prof 3 ministra Estatística
(8, 1, 9), -- Prof 1 ministra Cálculo II
(9, 6, 6); -- Prof 6 ministra Mecânica Quântica

-- SUBJECT_PREREQUISITE
INSERT INTO SUBJECT_PREREQUISITE (id, subject_id, prerequisiteid) VALUES
(1, 2, 1),    -- Física I exige Cálculo I
(2, 5, 4),    -- Estruturas de Dados exige Introdução à Programação
(3, 7, 5),    -- Banco de Dados exige Estruturas de Dados
(4, 9, 1),    -- Cálculo II exige Cálculo I
(5, 6, 2);    -- Mecânica Quântica exige Física I

-- CLASS
INSERT INTO CLASS (id, subject_id, year, semester, code) VALUES
(1, 1, 2025, 1, 'A'),
(2, 2, 2025, 1, 'A'),
(3, 3, 2025, 1, 'B'),
(4, 4, 2025, 2, 'A'),
(5, 5, 2025, 2, 'B'),
(6, 6, 2025, 2, 'A'),
(7, 7, 2025, 2, 'A'),
(8, 8, 2025, 1, 'A'),
(9, 9, 2025, 2, 'A');

-- CLASS_SCHEDULE
INSERT INTO CLASS_SCHEDULE (id, class_id, room_id, day_of_week, start_time, end_time) VALUES
(1, 1, 1, 'Segunda', '08:00', '10:00'),
(2, 2, 2, 'Terça', '10:00', '12:00'),
(3, 3, 3, 'Quarta', '14:00', '16:00'),
(4, 4, 4, 'Quinta', '08:00', '10:00'),
(5, 5, 1, 'Sexta', '10:00', '12:00'),
(6, 6, 5, 'Segunda', '14:00', '16:00'),
(7, 7, 4, 'Terça', '16:00', '18:00'),
(8, 8, 3, 'Quarta', '08:00', '10:00'),
(9, 9, 2, 'Quinta', '10:00', '12:00');