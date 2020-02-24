create table users (
                     id integer primary key generated always as identity ,
                     name varchar(60) not null,
                     FIO varchar not null,
                     tel varchar not null,
                     e_mail varchar(100),
                     password varchar(50)
);

create table messages (
                        id integer primary key generated always as identity ,
                        date timestamp,
                        text varchar(1000),
                        user_id integer,
                        foreign key (user_id) references users(id)
);

insert into users (name, FIO, tel, e_mail, password) values ('Max', 'Макс О', '8 999 000 00 00', 'max@ya', '222222');
insert into users (name, FIO, tel, e_mail, password) values ('Ivan', 'Иван Петров', '8 999 111 11 11', 'ivan@mail', '111111');
insert into users (name, FIO, tel, e_mail, password) values ('Olya', 'Оля Ля', '8 999 123 45 67', 'olya@ya', '000000');