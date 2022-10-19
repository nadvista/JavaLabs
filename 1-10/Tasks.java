public class Tasks
{
    static int remainder(int a, int b)
    {
        return a % b;
    }
    static double triArea(double a, double  h)
    {
        return .5 * a * h;
    }
    static int animals(int chickens, int cows, int pigs)
    {
        return 2*chickens + 4*cows + 4*pigs;
    }
    static boolean profitableGamble(int prob, int prize, int pay)
    {
        return prob*prize > pay;
    }
    static String operation(int a, int b, int n)
    {
        if(a + b == n)
            return "added";
        else if(a - b == n)
            return "subtracted";
        else if(a * b == n)
            return "multiplied";
        else if(a / b == n)
            return  "divided";
        else return "none";
    }
    static int ctoa(char smb)
    {
        return (int)smb;
    }
    static int addUpTo(int n)
    {
        var result = 0;
        for(var i = 1; i <= n; i++)
            result += i;
        return result;
    }
    static double nextEdge(int a, int b)
    {
        return Math.sqrt(a*a + b*b -2*a*b) - 1;
    }
    static int sumOfCubes(int[] array)
    {
        var result = 0;
        for(var i : array)
            result += i*i*i;
        return result;
    } 
    static boolean abcmath(int a, int b, int c)
    {
        for(var i = 0; i < b; i++)
            a += a;
        return a % c == 0;
    }
}