CREATE TABLE healthy_eating_programs
(
    id          bigserial PRIMARY KEY,
    description TEXT
);

CREATE TABLE users
(
    id   bigserial PRIMARY KEY,
    active    boolean NOT NULL,
    password  varchar(255) NOT NULL,
    username  varchar(255) NOT NULL,
    full_name varchar(255) NOT NULL
);

CREATE TABLE memberships
(
    id          bigserial PRIMARY KEY,
    description varchar(1000)     NOT NULL,
    duration    integer          NOT NULL,
    name        varchar(255)     NOT NULL,
    price       double precision NOT NULL
);



CREATE TABLE reviews
(
    id          bigserial   PRIMARY KEY,
    review_text varchar(1000) NOT NULL,
    user_id     bigint REFERENCES users
);

CREATE TABLE roles
(
    name varchar(255) NOT NULL PRIMARY KEY
);

CREATE TABLE training_programs
(
    id          bigserial PRIMARY KEY,
    description varchar(1000)
);

CREATE TABLE user_roles
(
    user_id   bigint NOT NULL REFERENCES users,
    role_name varchar(255) NOT NULL REFERENCES roles,
    CONSTRAINT fkhfh9dx7w3ubf1co1vdev94g3f FOREIGN KEY (user_id) REFERENCES users,
    CONSTRAINT fkdcdh0gl1mdce42vy0klyio6fi FOREIGN KEY (role_name) REFERENCES roles
);



CREATE TABLE users_memberships
(
    id            bigserial PRIMARY KEY,
    active        boolean NOT NULL,
    membership_id bigint REFERENCES memberships,
    user_id       bigint REFERENCES users,
    CONSTRAINT fk4xyjb2m3e0vo35fn6np481bm3 FOREIGN KEY (membership_id) REFERENCES memberships,
    CONSTRAINT fk49shuqtu4nn5vpkjjd21beeho FOREIGN KEY (user_id) REFERENCES users
);

CREATE TABLE personal_trainings
(
    id                        bigserial PRIMARY KEY,
    active                    boolean,
    customer_id               bigint REFERENCES users,
    healthy_eating_program_id bigint REFERENCES healthy_eating_programs,
    trainer_id                bigint REFERENCES users,
    training_program_id       bigint REFERENCES training_programs
);




