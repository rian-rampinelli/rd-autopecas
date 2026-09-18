CREATE TABLE "tb_reservas" (
  "id" BIGSERIAL ,
  "id_venda" BIGINT NOT NULL,
  "id_estoque_item" BIGINT NOT NULL,
  "quantidade" DECIMAL(10,2) NOT NULL,
   CONSTRAINT "pk_reserva" PRIMARY KEY(id),
   CONSTRAINT "fk_reserva_venda" FOREIGN KEY (id_venda) REFERENCES venda(id),
   CONSTRAINT "fk_reserva_item_estoque" FOREIGN KEY (id_estoque_item) REFERENCES estoque_item(id)
);