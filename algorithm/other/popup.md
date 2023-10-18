https://vopaaz.github.io/2019/10/29/interview-hotstar/

# popup

任务具有优先级，0-n-1,  目前处于乱序，只支持popup,将某一个任务放到队列头部，让整个任务，有序排列最少多少次操作?

我们每次将最小的popup到头部，可以发现如果原始队列里k - n, 因此我们只需要找到，结尾是n-1的，严格连续递增序列

to_find = len(arr) - 1
for i in arr[::-1]:
    if i == to_find:
        to_find -= 1

print(to_find + 1)



现在除了 totop() 之外，还提供另一种操作 tobottom(), 是将一个数字放到数组的结尾。 

此时我们只要找到最长的严格递增序列, i,i+1,i+2...j, 那么对于小于i的数字我们从大到小Popup, 对于大于j的数字，我们从小到大tobottom，就可以解决.

最暴力的方式我们可以o(n^2)解决，但是我们可以注意到i前一个数字肯定是i-1, 因此我们用dp[i]表示i结尾严格递增的数组长度，默认为0

for i in arr：
    # dp[i-1]为0的时候，说明i-1还没出现，此时直接就是1
    dp[i] = dp[i-1]+1
    maxV = max(maxV, dp[i])