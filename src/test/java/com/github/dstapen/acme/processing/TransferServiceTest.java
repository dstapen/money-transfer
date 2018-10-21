package com.github.dstapen.acme.processing;

import io.micronaut.context.ApplicationContext;
import io.micronaut.runtime.server.EmbeddedServer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import com.github.dstapen.acme.processing.api.AccountDTO;
import com.github.dstapen.acme.processing.api.OrderDTO;

import java.math.BigDecimal;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static com.github.dstapen.acme.processing.api.OrderDTO.newOrderDTO;


public class TransferServiceTest {

    static EmbeddedServer server;

    @BeforeClass
    public static void startServer() throws Exception {
        server = ApplicationContext.run(EmbeddedServer.class);
    }

    @AfterClass
    public static void stopServer() throws Exception {
        server.stop();
    }

    @Test
    public void testPositive() throws Exception {
        transfer("100000", "50000");
    }

    @Test(expected = AssertionError.class)
    public void testNegative() throws Exception {
        transfer("200", "500");
    }


    private void transfer(String amountBefore, String amountAfter) throws Exception {
        Api client = server.getApplicationContext().getBean(Api.class);

        // region create credit account with initial balance
        AccountDTO credit = client.createNewAccount(AccountDTO.newAccountDTO(null,
                "credit",
                "someone",
                amountBefore,
                null,
                "USD")).blockingGet();
        // endregion

        // region create debit account
        AccountDTO debit = client.createNewAccount(AccountDTO.newAccountDTO(
                null,
                "debit",
                "someone",
                null,
                null,
                "USD")).blockingGet();
        // endregion

        SECONDS.sleep(2);
        AccountDTO result = client.getAccount(credit.id()).blockingGet();
        assertThat(result.balance().toString(), equalTo(amountBefore));
        OrderDTO order = client.placeTransferOrder(newOrderDTO(
                null,
                null,
                credit.id(),
                debit.id(),
                new BigDecimal(amountAfter),
                null,
                null,
                null
        )).blockingGet();
        SECONDS.sleep(2);
        AccountDTO actualCredit = client.getAccount(credit.id()).blockingGet();
        AccountDTO actualDebit = client.getAccount(debit.id()).blockingGet();
        assertThat(actualCredit.balance().toString(), equalTo(amountAfter));
        assertThat(actualDebit.balance().toString(), equalTo(amountAfter));
    }

}
