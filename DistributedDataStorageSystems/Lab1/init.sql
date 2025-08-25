CREATE TABLE Н_ЛЮДИ (
    ЧЛВК_ИД NUMERIC(9),
    CONSTRAINT "Н_ЛЮДИ_PK" PRIMARY KEY (ЧЛВК_ИД)
);

CREATE TABLE Н_ВЕДОМОСТ (
    ЧЛВК_ИД NUMERIC(9),
    CONSTRAINT "ВЕД_ЧЛВК_FK" FOREIGN KEY (ЧЛВК_ИД) REFERENCES Н_ЛЮДИ (ЧЛВК_ИД)
);  

COMMENT ON COLUMN Н_ВЕДОМОСТ.ЧЛВК_ИД IS 'Внешний ключ к таблице Н_ЛЮДИ';

CREATE TABLE Н_ИЗМ_ЛЮДИ (
    ЧЛВК_ИД NUMERIC(9),
    CONSTRAINT "ИЗМЛ_PK_CONS" PRIMARY KEY (ЧЛВК_ИД),
    CONSTRAINT "ИЗМЛ_ЧЛВК_FK" FOREIGN KEY (ЧЛВК_ИД) REFERENCES Н_ЛЮДИ (ЧЛВК_ИД)
);

COMMENT ON COLUMN Н_ИЗМ_ЛЮДИ.ЧЛВК_ИД IS 'Внешний ключ к таблице Н_ЛЮДИ';

CREATE TABLE Н_ОБУЧЕНИЯ (
    ЧЛВК_ИД NUMERIC(9),
    CONSTRAINT "ОБУЧ_PK_CONS" PRIMARY KEY (ЧЛВК_ИД),
    CONSTRAINT "ОБУЧ_ЧЛВК_FK" FOREIGN KEY (ЧЛВК_ИД) REFERENCES Н_ЛЮДИ (ЧЛВК_ИД)
);

COMMENT ON COLUMN Н_ОБУЧЕНИЯ.ЧЛВК_ИД IS 'Внешний ключ к таблице Н_ЛЮДИ';

CREATE TABLE Н_СЕССИЯ (
    ЧЛВК_ИД NUMERIC(9),
    CONSTRAINT "SYS_C0014470" FOREIGN KEY (ЧЛВК_ИД) REFERENCES Н_ЛЮДИ (ЧЛВК_ИД)
);

COMMENT ON COLUMN Н_СЕССИЯ.ЧЛВК_ИД IS 'Внешний ключ к таблице Н_ЛЮДИ';

CREATE TABLE Н_УЧЕНИКИ (
    ЧЛВК_ИД NUMERIC(9),
    CONSTRAINT "УЧЕН_ОБУЧ_FK" FOREIGN KEY (ЧЛВК_ИД) REFERENCES Н_ОБУЧЕНИЯ (ЧЛВК_ИД)
);

COMMENT ON COLUMN Н_УЧЕНИКИ.ЧЛВК_ИД IS 'Внешний ключ к таблице Н_ОБУЧЕНИЯ';

CREATE TABLE Н_ЭКЗ_ЛИСТ (
    ЧЛВК_ИД NUMERIC(9),
    CONSTRAINT "ЭЛН_ЧЛВК_FK" FOREIGN KEY (ЧЛВК_ИД) REFERENCES Н_ЛЮДИ (ЧЛВК_ИД)
);

COMMENT ON COLUMN Н_ЭКЗ_ЛИСТ.ЧЛВК_ИД IS 'Внешний ключ к таблице Н_ЛЮДИ';


CREATE INDEX "ВЕД_УВЕД_UK" ON "Н_ВЕДОМОСТ" ("ЧЛВК_ИД");

CREATE INDEX "ИЗЧЕЛ_ЧЛВК_FK_I" ON "Н_ИЗМ_ЛЮДИ" ("ЧЛВК_ИД");
CREATE INDEX "ИЗМЛ_PK" ON "Н_ИЗМ_ЛЮДИ" ("ЧЛВК_ИД");

CREATE INDEX "ОБУЧ_ЧЛВК_FK_I" ON "Н_ОБУЧЕНИЯ" ("ЧЛВК_ИД");
CREATE INDEX "ОБУЧ_PK" ON "Н_ОБУЧЕНИЯ" ("ЧЛВК_ИД");

CREATE INDEX "СЕС_ЧЛВК_FK" ON "Н_СЕССИЯ" ("ЧЛВК_ИД");

CREATE INDEX "УЧЕН_ОБУЧ_FK_I" ON "Н_УЧЕНИКИ" ("ЧЛВК_ИД");

CREATE INDEX "ЭЛН_ЧЛВК_FK" ON "Н_ЭКЗ_ЛИСТ" ("ЧЛВК_ИД");



--- создание процедуры

CREATE OR REPLACE PROCEDURE show_columns_by_name(name VARCHAR)
LANGUAGE plpgsql AS $$
DECLARE
    column_record RECORD;
    result TEXT := '';
    constraint_definition TEXT;
BEGIN
    result := result || E'\n' || 'No  Имя столбца  Имя таблицы  Атрибуты' || E'\n';
    result := result || '--  -----------  -----------  --------' || E'\n';
    FOR column_record IN
        SELECT row_number() OVER (ORDER BY a.attname) AS num,
               a.attname AS column_name, 
               c.relname AS table_name,
               pg_catalog.format_type(a.atttypid, a.atttypmod) AS data_type,
               COALESCE(pgd.description, '') AS column_comment,
               string_agg(DISTINCT quote_ident(i.indexname), E'\n                                  ') AS ind,
               string_agg(DISTINCT quote_ident(con.conname) || ' ' || pg_catalog.pg_get_constraintdef(con.oid), E'\n                                   ') AS constraint_defs
        FROM pg_attribute a
        JOIN pg_class c ON a.attrelid = c.oid
        LEFT JOIN pg_constraint con
            ON con.conrelid = c.oid
        LEFT JOIN pg_catalog.pg_description pgd 
            ON pgd.objoid = c.oid
        LEFT JOIN pg_indexes i
            ON i.tablename = c.relname
        WHERE a.attname = name AND
        c.relkind = 'r'
        GROUP BY a.attname, c.relname, data_type, column_comment
    LOOP
        result := result || 
                column_record.num || '  ' || 
                column_record.column_name || '  ' || 
                column_record.table_name || E'\n' ||
                '                           Type: ' || column_record.data_type || E'\n' ||
                '                           Constr: CONSTRAINT' || E'\n                                   ' || COALESCE(column_record.constraint_defs, '""') || E'\n'
                '                           Commen: ' || COALESCE(quote_ident(column_record.column_comment), '""') || E'\n' ||
                '                           Index: ' || COALESCE(column_record.ind, '""') || E'\n' ||
                E'\n';
    END LOOP;
    
    RAISE NOTICE '%', result;
END;
$$;

