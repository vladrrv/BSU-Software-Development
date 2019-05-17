SELECT
  l.id id, email, password, type,
  CASE type WHEN 'student' THEN s.name
            WHEN 'professor' THEN p.name
  END name
FROM logins l
LEFT JOIN professors p ON l.id = p.login_id
LEFT JOIN students s ON l.id = s.login_id