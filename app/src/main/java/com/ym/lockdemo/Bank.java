package com.ym.lockdemo;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @className: Bank
 * @classDescription: 银行业务类
 * @author: leibing
 * @createTime: 2016/8/15
 */
public class Bank {
    // 账户数组
    private double[] accounts;
    // 同步锁
    private Lock bankLock;
    // 同步锁条件
    private Condition condition;

    /**
     * 构造函数
     * @author leibing
     * @createTime 2016/8/15
     * @lastModify 2016/8/15
     * @param n 账户数组大小
     * @param initialBalance 账户初始数据
     * @return
     */
    public Bank(int n, double initialBalance){
        accounts = new double[n];
        bankLock = new ReentrantLock();
        // 得到条件对象
        condition = bankLock.newCondition();
        for (int i=0;i<accounts.length;i++){
            accounts[i] = initialBalance;
        }
    }

    /**
     * 转账
     * @author leibing
     * @createTime 2016/8/15
     * @lastModify 2016/8/15
     * @param from 转账方
     * @param to 接受方
     * @param amount 转账金额
     * @return
     */
    public void transfer(int from ,int to,int amount) throws InterruptedException {
        bankLock.lock();
        try {
            while (accounts[from] == 0 || accounts[from] < amount){
                System.out.println("dddddd 转账方钱不足");
                System.out.println("dddddd threadName = " + Thread.currentThread().getName() + "  阻塞并放弃锁");
                // 阻塞当前线程并放弃锁
                condition.await();
            }
            // 转账操作
            accounts[from]-=amount;
            accounts[to]+=amount;
            System.out.println("dddddd 转账方余额 =" + accounts[from]);
            System.out.println("dddddd 接受方余额 =" + accounts[to]);
            condition.signalAll();
        }finally {
            bankLock.unlock();
        }
    }

    /**
     *  存钱
     * @author leibing
     * @createTime 2016/8/15
     * @lastModify 2016/8/15
     * @param to 存储方
     * @param amount 存储金额
     * @return
     */
    public void save(int to, int amount){
        bankLock.lock();
        try {
            accounts[to]+=amount;
        }finally {
            bankLock.unlock();
        }
    }
}
