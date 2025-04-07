CREATE TABLE representation_reservation (
    id INT(11) NOT NULL AUTO_INCREMENT,
    representation_id INT(11) NOT NULL,
    reservation_id INT(11) NOT NULL,
    price_id INT(11) NOT NULL,
    quantity TINYINT(4) NOT NULL DEFAULT 1,
    PRIMARY KEY (id),
    KEY representation_id (representation_id),
    KEY reservation_id (reservation_id),
    KEY price_id (price_id),
    CONSTRAINT representation_reservation_representation_id_foreign FOREIGN KEY (representation_id) REFERENCES representations (id) ON DELETE CASCADE,
    CONSTRAINT representation_reservation_reservation_id_foreign FOREIGN KEY (reservation_id) REFERENCES reservations (id) ON DELETE CASCADE,
    CONSTRAINT representation_reservation_price_id_foreign FOREIGN KEY (price_id) REFERENCES prices (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;