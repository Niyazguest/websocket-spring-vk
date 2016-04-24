CREATE TABLE authors
(
  id bigserial NOT NULL,
  vk_user_id integer NOT NULL,
  first_name character varying(30),
  last_name character varying(50),
  avatar_url character varying(100),
  page_url character varying(50),
  CONSTRAINT authors_pkey PRIMARY KEY (id)
);

CREATE TABLE messages
(
  id bigserial NOT NULL,
  message character varying(1000),
  author_id bigint,
  date timestamp with time zone,
  CONSTRAINT messages_pkey PRIMARY KEY (id),
  CONSTRAINT fk_messages_1 FOREIGN KEY (author_id)
      REFERENCES public.authors (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);


