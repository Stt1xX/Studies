    CREATE TABLE IF NOT EXISTS Users(
        name text primary key,
        password text
    );

    CREATE TABLE IF NOT EXISTS Movies(
        id serial primary key,
        name text,
        X numeric,
        Y numeric,
        creationDate date default now(),
        oscarsCount integer,
        genre text,
        mpaaRating text,
        owner text references Users (name)
    );

    CREATE TABLE IF NOT EXISTS Operators(
        operatorid serial primary key,
        operatorname text,
        height numeric,
        nationality text,
        eyeColor text,
        locName text,
        Xloc numeric,
        Yloc numeric,
        Zloc numeric
    );

    CREATE TABLE IF NOT EXISTS Movies_Operators(    
        id_Movie int references Movies (id),
        id_Operator int references Operators (operatorid),
        UNIQUE(id_Movie, id_Operator)
    );

    INSERT INTO Users VALUES ('test', 'd8578edf8458ce06fbc5bb76a58c5ca4');
    INSERT INTO Movies VALUES (1, 'Terminator', 320, 180, '12.10.2023', 3, 'ACTION', 'R', 'test');
    INSERT INTO Movies VALUES (2, 'Pirates of the caribbean', 31, 111, '11.10.2023', 5, 'DRAMA', 'R', 'test');
    INSERT INTO Operators VALUES (1, 'Eminem', 176.567565, 'FRANCE', 'RED', 'Russia', 1, 2, 3);
    INSERT INTO Operators VALUES (2, 'Bob Dylon', 13421.31423, 'JAPAN', 'BLACK', 'Russia', 15, 13, 12);
    INSERT INTO Movies_Operators VALUES (1, 2);
    INSERT INTO Movies_Operators VALUES (2, 1);

