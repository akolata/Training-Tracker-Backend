INSERT INTO tt_role(id, name, version, created_at, updated_at, uuid)
VALUES (nextval('tt_role_seq'), 'ROLE_USER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP,
        '123e4567-e89b-12d3-a456-556642440000'),
       (nextval('tt_role_seq'), 'ROLE_ADMIN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP,
        '7dc53df5-703e-49b3-8670-b1c468f47f1f');