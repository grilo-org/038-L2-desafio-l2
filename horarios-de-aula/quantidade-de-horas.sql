SELECT
    p.id AS professor_id,
    SUM(TIMESTAMPDIFF(MINUTE, cs.start_time, cs.end_time)) / 60 AS total_horas,
    COUNT(cs.id) AS total_aulas
FROM
    PROFESSOR p
JOIN
    PROFESSOR_SUBJECT ps ON ps.professor_id = p.id
JOIN
    CLASS c ON c.subject_id = ps.subject_id
JOIN
    CLASS_SCHEDULE cs ON cs.class_id = c.id
GROUP BY
    p.id;