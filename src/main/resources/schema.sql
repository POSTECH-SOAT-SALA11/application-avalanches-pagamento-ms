CREATE TABLE public.pagamento
(
    id_pedido int4 NOT NULL,
    status varchar NOT NULL,
    CONSTRAINT pagamento_pk PRIMARY KEY (id_pedido)
);
