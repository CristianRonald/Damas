CREATE TABLE usuario(
    id INT PRIMARY KEY,
    correo VARCHAR(100),
    contraseña VARCHAR(17),
    apodo VARCHAR(17)
);

CREATE TABLE estadisticas(
    id INT PRIMARY KEY,
    elo INT,
    ganadas INT,
    perdidas INT,
    empatadas INT
);

insert into
    usuario
values
    (
        1,
        'porta.cristianlo@gmail.com',
        '12345678',
        "xDearx"
    );

insert into
    estadisticas
values
    (
        1,
        700,
        2,
        1,
        1
    );