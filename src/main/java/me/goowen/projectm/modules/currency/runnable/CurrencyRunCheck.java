package me.goowen.projectm.modules.currency.runnable;

import me.goowen.projectm.ProjectM;

public class CurrencyRunCheck implements Runnable {

    /**
     * Checks if a currency request has expired.
     */
    @Override
    public void run() {
        if (ProjectM.getCurrencyModule().getExchangeRequestList().isEmpty()) return;
        ProjectM.getCurrencyModule().getExchangeRequestList().removeIf(paymentRequest -> paymentRequest.getCurrentTimeMillis() > (System.currentTimeMillis() + 30 * 1000));
    }
}
