ALTER TABLE compra
DROP COLUMN id_estoque;

ALTER TABLE item_compra
ADD COLUMN id_estoque BIGINT,
ADD CONSTRAINT fk_estoque_item_compra
    FOREIGN KEY (id_estoque)
    REFERENCES estoque(id);