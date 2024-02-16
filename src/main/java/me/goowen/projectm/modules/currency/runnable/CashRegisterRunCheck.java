package me.goowen.projectm.modules.currency.runnable;

import me.goowen.projectm.ProjectM;

public class CashRegisterRunCheck implements Runnable {

    /**
     * Checks if a currency request has expired.
     */
    @Override
    public void run() {
        if (ProjectM.getCurrencyModule().getPaymentRequestList().isEmpty()) return;
        ProjectM.getCurrencyModule().getPaymentRequestList().removeIf(paymentRequest -> paymentRequest.getCurrentTimeMillis() > (System.currentTimeMillis() + 30 * 1000));
    }
}
