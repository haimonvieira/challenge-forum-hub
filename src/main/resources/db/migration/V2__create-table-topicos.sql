alter table topicos
add column mensagem varchar(255) not null unique;
alter table topicos
add constraint unique_titulo unique (titulo);

