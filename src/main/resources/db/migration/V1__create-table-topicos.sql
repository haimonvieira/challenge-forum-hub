create table topicos(
    id serial not null,
    titulo varchar(100) not null,
    data timestamp not null,
    estado_topico boolean default true not null,
    autor varchar(255) not null,
    curso varchar(255) not null,
    primary key(id)
);