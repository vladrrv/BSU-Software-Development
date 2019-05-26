SELECT description course_description, p.name professors_name, is_alt, s.name student_name
from student_course_offerings sco
JOIN students s ON s.id = sco.student_id
JOIN course_offerings co ON co.id = sco.course_offering_id
JOIN courses c ON c.id = co.course_id
JOIN professors p ON p.id = co.professor_id
WHERE student_id = 1