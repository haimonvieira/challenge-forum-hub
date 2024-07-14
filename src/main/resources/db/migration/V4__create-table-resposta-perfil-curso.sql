create table cursos(
    id serial not null,
    nome varchar (100) not null,
    categoria varchar(100) not null,
    primary key(id)
);

create table perfis(
    id serial not null,
    nome varchar(100) not null,
    primary key(id)
);

create table respostas(
    id serial not null,
    mensagem varchar(255) not null,
    primary key(id)
);