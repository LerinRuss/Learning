package org.example;

//https://leetcode.com/problems/best-time-to-buy-and-sell-stock-with-cooldown/description/
public class LeetCode309Optimized {
    public int maxProfit(int[] prices) {
        if (prices.length == 1) {
            return 0;
        }

        int prevIdle = 0;
        int currIdle = prevIdle, currBuy = -prices[0], currSell = Integer.MIN_VALUE;

        for(int i = 1; i < prices.length; i++) {
            currIdle = Math.max(prevIdle, currSell);
            currSell = currBuy + prices[i];
            currBuy = Math.max(currBuy, prevIdle - prices[i]);

            prevIdle = currIdle;
        }

        return Math.max(currIdle, currSell);
    }
}
