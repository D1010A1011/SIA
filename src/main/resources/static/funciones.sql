--Funcion para Obtener usuario para Transacciones del Back-end
CREATE OR REPLACE FUNCTION obtener_usuario(email_param VARCHAR)
    RETURNS TABLE (
                      nombre_usuario VARCHAR,
                      correo_institucional VARCHAR,
                      contrasena VARCHAR,
                      rol VARCHAR
                  ) AS $$
BEGIN
    RETURN QUERY
        SELECT
            u.nombres||' '||u.apellidos,
            u.correo_institucional,
            u.contrasena,
            CASE
                WHEN e.id_usuario IS NOT NULL AND p.id_usuario IS NOT NULL THEN 'PROF-EST'
                WHEN e.id_usuario IS NOT NULL THEN 'EST'
                WHEN p.id_usuario IS NOT NULL THEN 'PROF'
                ELSE NULL
            END AS rol
    FROM usuario u
    LEFT JOIN estudiante e ON u.id_usuario = e.id_usuario
    LEFT JOIN profesor p ON u.id_usuario = p.id_usuario
    WHERE u.correo_institucional = email_param;
END;
$$ LANGUAGE plpgsql;


-- Función para guardar usuarios en tabla
CREATE OR REPLACE FUNCTION insertar_usuario(
    p_nombre_usuario VARCHAR,
    p_contrasena VARCHAR,
    p_nombres VARCHAR,
    p_apellidos VARCHAR,
    p_telefono VARCHAR,
    p_tipo_identificacion VARCHAR,
    p_numero_identificacion VARCHAR,
    p_id_programa INT
) RETURNS VOID AS $$
DECLARE
    v_correo_final VARCHAR;
    v_contador INT := 1;
BEGIN
    -- Generar el correo base
    v_correo_final :=p_nombre_usuario || '@unal.edu.co';

    -- Verificar si el correo ya existe
    WHILE EXISTS (SELECT 1 FROM usuario WHERE correo_institucional = v_correo_final) LOOP
            -- Si existe, generar un nuevo correo con un número incremental
            v_correo_final := p_nombre_usuario || LPAD(v_contador::TEXT, 2, '0') || '@unal.edu.co';
            v_contador := v_contador + 1;
        END LOOP;

    -- Insertar el usuario con el correo generado
    INSERT INTO usuario (
        nombre_usuario, correo_institucional, contrasena, nombres, apellidos, telefono, tipo_identificacion, numero_identificacion
    ) VALUES (
                 p_nombre_usuario, v_correo_final, p_contrasena, p_nombres, p_apellidos, p_telefono, p_tipo_identificacion, p_numero_identificacion
             );
END;
$$ LANGUAGE plpgsql;

-- TRIGGER para insertar nuevo usuario en estudiante

CREATE OR REPLACE FUNCTION asignar_estudiante_y_tutor()
    RETURNS TRIGGER AS $$
DECLARE
    v_id_usuario INT;
    v_id_profesor INT;
BEGIN
    -- Obtener el ID del usuario recién insertado
    SELECT id_usuario INTO v_id_usuario
    FROM usuario
    WHERE correo_institucional = NEW.correo_institucional;

    -- Seleccionar un profesor aleatorio
    SELECT id_usuario INTO v_id_profesor
    FROM profesor
    ORDER BY RANDOM()
    LIMIT 1;

    -- Insertar en la tabla estudiante
    INSERT INTO estudiante (id_usuario, tutor, id_programa) VALUES (v_id_usuario, v_id_profesor ,NEW.id_programa);


    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_asignar_estudiante_y_tutor
    AFTER INSERT ON usuario
    FOR EACH ROW
EXECUTE FUNCTION asignar_estudiante_y_tutor();

-- Historial Academico
CREATE OR REPLACE FUNCTION hstl_academico(id_usuario_ha NUMERIC)
    RETURNS TABLE ( pa NUMERIC, papa NUMERIC, creditos_restantes NUMERIC, creditos_vistos NUMERIC, creditos_perdidos NUMERIC) AS $$
BEGIN
    RETURN QUERY
        SELECT  pa, papa, creditos_restantes, creditos_vistos, creditos_perdidos
        FROM historia_academica
        WHERE historia_academica.id_usuario = id_usuario_ha;
END;
$$ LANGUAGE plpgsql;
-- Asignaturas cursadas en un periodo
CREATE OR REPLACE FUNCTION hstl_academico_asignaturas(id_usuario_ha NUMERIC)
    RETURNS TABLE ( nombre VARCHAR, nota NUMERIC, periodo VARCHAR) AS $$
BEGIN
    RETURN QUERY
        SELECT a.nombre, haa.nota, haa.periodo
        FROM usuario u
                 JOIN estudiante e ON u.id_usuario = e.id_usuario
                 JOIN historia_academica ha ON e.id_usuario = ha.id_usuario
                 JOIN historia_asignatura haa ON ha.id_usuario = haa.id_usuario
                 JOIN asignatura a ON haa.id_asignatura = a.id_asignatura
        ORDER BY nombres;
END;
$$ LANGUAGE plpgsql;