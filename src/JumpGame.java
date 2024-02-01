public class JumpGame {
    public int jump(int[] nums) {
        int n = nums.length;
        if(n == 1) return 0;
        int windowStart = 0;
        int windowEnd = 0;
        int steps = 0;
        while(windowEnd < n){
            int maxEnd = 0;
            for(;windowStart <= windowEnd ; ++windowStart) {
                maxEnd = Math.max(maxEnd, nums[windowStart] + windowStart);
            }
            windowEnd = maxEnd;
            steps++;
            if(windowEnd == n-1) break;
            if(windowStart > windowEnd) return -1;

        }
        return steps;
    }
}
