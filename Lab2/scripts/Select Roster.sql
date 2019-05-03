select name
from grades g
LEFT JOIN course_offerings co ON co.id = g.course_offering_id
LEFT JOIN students s ON s.id = g.student_id
