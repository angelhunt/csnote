Convert a non-negative integer num to its English words representation.

# 分治
我们将这个问题分解成一系列子问题。例如，对于数字 1234567890，我们将它从低位开始每三个分成一组，得到 1,234,567,890，它的英文表示为 1 Billion 234 Million 567 Thousand 890。这样我们就将原问题分解成若干个三位整数转换为英文表示的问题了



这样我们继续将原问题分解成一位整数和两位整数的英文表示。其中一位整数的表示是很容易的，而两位整数中除了 10 到 19 以外，其余整数的的表示可以分解成两个一位整数的表示



# 数字转换中文
需要注意

1. 如果数字中出现连续的零，需要把它替换为单一的零。

2. 在亿、万、元的前面一个汉字不可以为零（人民币读取方式决定）。
3. 复杂一点还需要考虑小数部分，几毛，几分.小数部分较简单，整数部分需要根据这个数字所在的位数匹配上对应的单位。
4. 需要考虑十的特殊情况，比如十二，前面是不会加数字的，但是对于一百二十这种前面就会添加数字。
   

将数字按照亿万千分段，每一段按位处理，0要做特殊处理，仅当当前数字不是0，前一数字是0的情况下，添加0

``` python
def convert(digit, start, end):
    units = [' ', '十', '百', '千']
    nums = ['零', '一', '二', '三', '四', '五', '六', '七', '八', '九']
    #string digit
    len = end - start
    pre = -1
    res = ""
    for i in range(start, end):
        number = int(digit[i])
        unit = units[len - 1 - (i-start)]
        if number != 0:
            if pre == 0:
                res = res + nums[0]
            res = res + nums[number] + unit
        pre = number
    return res
```



# 中文转数字

https://leetcode-cn.com/circle/discuss/EY0b4W/

1. 遇到单位值的时候，记录下当前单位，刚开始默认是1.

2. 从后往前遍历字符串，遇到数字的时候，如果是零直接跳过，如果遇到十，百，千，看前一位的数字，然后直接乘以单位，并累加到sumvalue中。

此外如果需要考虑XX万亿这种场景，可以参考下面

http://haiboyu.me/zh-Hans/2017/09/25/%E4%B8%AD%E6%96%87%E6%95%B0%E5%AD%97%E5%92%8C%E9%98%BF%E6%8B%89%E4%BC%AF%E6%95%B0%E5%AD%97%E7%9A%84%E8%BD%AC%E6%8D%A2/
