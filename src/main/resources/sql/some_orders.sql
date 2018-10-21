WITH T AS (
    SELECT id, max(version) version
    FROM rt_order
    GROUP BY id
)
SELECT
    rt_order.*

FROM
    rt_order INNER JOIN T on rt_order.id = T.id AND rt_order.version = T.version

WHERE
    rt_order.state = 'SUBMITTED'
ORDER BY
    rt_order.created
LIMIT 1

