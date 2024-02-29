package org.example;

//https://leetcode.com/problems/best-time-to-buy-and-sell-stock-with-cooldown/description/
public class LeetCode309 {
    public int maxProfit(int[] prices) {
        var idle = new int[prices.length];
        var buy = new int[prices.length];
        var sell = new int[prices.length];

        idle[0] = 0;
        buy[0] = -prices[0];
        sell[0] = Integer.MIN_VALUE;

        for(int i = 1; i < prices.length; i++) {
            idle[i] = Math.max(idle[i - 1], sell[i-1]);
            buy[i] = Math.max(buy[i-1], idle[i-1] - prices[i]);
            sell[i] = buy[i - 1] + prices[i];
        }

        return Math.max(idle[prices.length - 1], sell[prices.length - 1]);
    }
}