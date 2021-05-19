create table todouser(id identity primary key, username varchar(50), email varchar(50), password varchar(255) );

create table task(id identity primary key, description varchar(255),complete boolean, userid bigint);
ALTER TABLE task ADD CONSTRAINT userfk FOREIGN KEY (userid) REFERENCES todouser(id);