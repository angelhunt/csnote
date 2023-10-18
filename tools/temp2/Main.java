public class Main{

    public static long calculate(int n) {
        
        if (n == 0) return 1;

        long result = 0;
        
        for (int i = 0; i < n; i++)
            result += calculate(i) * calculate(n - i - 1);
        
        return result;
    }

    private static long[] cache = new long[100];
    public static long calculateMem(int n) {
        //write your code here
        if(n==0)return 1;
        if(cache[n] != 0)
            return cache[n];
        long result = 0;
        for (int i = 0; i < n; i++)
            result += calculateMem(i) * calculateMem(n - i - 1);
        cache[n] = result;
        return result;
    }

    public static void main(String[] args){
        for(int n = 18; n <=20; n++){
            long startTime = System.currentTimeMillis();
            System.out.print("\nOutput for n =" + n +": " + calculate(n));
            System.out.println(" Execution time (normal): " + (System.currentTimeMillis() - startTime) + " ms");

            startTime = System.currentTimeMillis();
            System.out.print("Output for n =" + n +": " + calculateMem(n));
            System.out.println(" Execution time (memoized): " + (System.currentTimeMillis() - startTime) + " ms");
        }
    }
}