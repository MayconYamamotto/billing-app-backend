CREATE TABLE "billing_import_history"
(
    id                 UUID         NOT NULL,
    import_date        TIMESTAMP    NOT NULL,
    file_name          VARCHAR(255) NOT NULL,
    blob               TEXT,
    status             VARCHAR(7)   NOT NULL,
    sysuser            UUID         NOT NULL,
    created_by         VARCHAR(255),
    created_date       TIMESTAMP,
    last_modified_by   VARCHAR(255),
    last_modified_date TIMESTAMP
);

CREATE TABLE "bill"
(
    id                 UUID         NOT NULL,
    import_history     UUID,
    expiration_date    DATE         NOT NULL,
    payment_date       DATE,
    description        VARCHAR(255) NOT NULL,
    amount             DECIMAL      NOT NULL,
    sysuser            UUID         NOT NULL,
    status             VARCHAR(16)  NOT NULL,
    created_by         VARCHAR(255),
    created_date       TIMESTAMP,
    last_modified_by   VARCHAR(255),
    last_modified_date TIMESTAMP
);

CREATE TABLE "sysuser"
(
    id                 UUID         NOT NULL,
    first_name         VARCHAR(255) NOT NULL,
    last_name          VARCHAR(255) NOT NULL,
    email              VARCHAR(255) NOT NULL,
    "password"         VARCHAR(255) NOT NULL,
    active             BOOLEAN      NOT NULL,
    created_by         VARCHAR(255),
    created_date       TIMESTAMP,
    last_modified_by   VARCHAR(255),
    last_modified_date TIMESTAMP
);

/* PRIMARY KEYS */
ALTER TABLE "billing_import_history"
    ADD CONSTRAINT pk_billing_import_history_id PRIMARY KEY (id);
ALTER TABLE "bill"
    ADD CONSTRAINT pk_bill_id PRIMARY KEY (id);
ALTER TABLE "sysuser"
    ADD CONSTRAINT pk_user_id PRIMARY KEY (id);

/* FOREIGN KEYS */
ALTER TABLE "billing_import_history"
    ADD CONSTRAINT fk_billing_import_history_user FOREIGN KEY (sysuser) REFERENCES "sysuser" (id);
ALTER TABLE "bill"
    ADD CONSTRAINT fk_bill_import_history FOREIGN KEY ("import_history") REFERENCES "billing_import_history" (id);
ALTER TABLE "bill"
    ADD CONSTRAINT fk_bill_user FOREIGN KEY (sysuser) REFERENCES "sysuser" (id);

/* INDEXES */
CREATE INDEX IF NOT EXISTS "billing_import_history_user_idx" ON "billing_import_history" (sysuser);
CREATE INDEX IF NOT EXISTS "bill_import_history_idx" ON "bill" (import_history);

CREATE INDEX IF NOT EXISTS "bill_user_idx" ON "bill" (sysuser);
