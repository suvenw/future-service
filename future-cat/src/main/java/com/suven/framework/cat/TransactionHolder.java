package com.suven.framework.cat;

import com.dianping.cat.message.Transaction;

/**
 * Created by Max on 2016/9/19.
 */
public class TransactionHolder {
    private final static ThreadLocal<Transaction> transaction = new ThreadLocal<>();

    static void set(Transaction $transaction) {
        TransactionHolder.transaction.set($transaction);
    }

    public static Transaction get() {
        return TransactionHolder.transaction.get();
    }

    static void remove() {
        TransactionHolder.transaction.remove();
    }
}
