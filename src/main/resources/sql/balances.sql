SELECT
    rt_account.*,
    v_balance.balance

FROM rt_account LEFT JOIN v_balance ON rt_account.id = v_balance.account_id