CREATE TABLE rt_account(
    id uuid,
    owner_id varchar(36),
    initial_balance bigint,
    currency varchar(3),
    description varchar(400)
);

CREATE TABLE rt_order(
    id uuid,
    kind varchar(30),
    version int,
    from_account_id varchar(36),
    to_account_id varchar(36),
    amount bigint,
    state varchar(13),
    description varchar(400),
    note varchar(400),
    created timestamp
);

CREATE TABLE rt_tx(
    id uuid,
    order_id uuid,
    account_id varchar(36),
    from_account_id varchar(36),
    to_account_id varchar(36),
    amount bigint,
    description varchar(400),
    note varchar(60)
);

-- orders' view
CREATE VIEW v_orders AS
WITH T AS (
    SELECT id, max(version) version
    FROM rt_order
    GROUP BY id
)
SELECT
    rt_order.*

FROM
    rt_order INNER JOIN T on rt_order.id = T.id AND rt_order.version = T.version;

-- balances' view
CREATE VIEW v_balance as
SELECT
    account_id,
    sum(amount) balance
FROM rt_tx
GROUP BY account_id;
