package freedom.login;

import cn.dev33.satoken.SaManager;
import lombok.Synchronized;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.*;
import java.util.concurrent.locks.ReentrantLock;


@SpringBootApplication
public class LoginStart {
    private final static Logger logger = LoggerFactory.getLogger(LoginStart.class);
    public static void main(String[] args) {
//        SpringApplication.run(LoginStart.class, args);
//
//        ReentrantLock reentrantLock = new ReentrantLock();
//        reentrantLock.newCondition();

//        logger.info("-----------------LoginStart success-----------------");
//        logger.info("sa-taken config: {}", SaManager.getConfig());

//        ListNode node1 = new ListNode(1);
//        ListNode node2 = new ListNode(2);
//        ListNode node3 = new ListNode(3);
//        ListNode node4 = new ListNode(4);
//        ListNode node5 = new ListNode(5);
//        node1.next = node2;
//        node2.next = node3;
//        node3.next = node4;
//        node4.next = node5;
//        removeNthFromEnd(node1, 4);
        String str = "";

//        str.indexOf()
//        countAndSay(5);
//        List<List<Integer>> res = combinationSum(new int[]{2, 3, 5}, 8);
//        System.out.println(res.size());
//
//        int[] ss = new int[]{2, 3, 5};
//        List<Integer> list = new ArrayList<>(Arrays.asList(2,3,5));
//        HashSet<Integer> set = new HashSet<>(list);
//
//        set.toArray(new int[][]{ss});
//        System.out.println(getPermutation(3,3));

        TreeNode root = new TreeNode(6);
        TreeNode left = new TreeNode(5);
        TreeNode right = new TreeNode(7);

        TreeNode right2 = new TreeNode(8);

        root.left = left;
        root.right = right;
        right.right = right2;
//        System.out.println(convertBST(root).val);
//        convertBST(root);


        String ss = "";
        char[] c = ss.toCharArray();
//        c.length;

//        Arrays.

//        new HashSet();
//        System.out.println(findAnagrams("aba", "ab").size());
//        System.out.println("abs".substring(0,3));

//        System.out.println(numSquares(4));
//        System.out.println(maxSubArray(new int[]{-2,1,-3,4,-1,2,1,-5,4}));

//        System.out.println(numTrees(3));

        System.out.println(findContentChildren(new int[]{10, 9, 8, 7}, new int[]{10, 9, 8, 7}));

    }

    public static int findContentChildren(int[] g, int[] s) {

        int result = 0;
        Arrays.sort(g);
        Arrays.sort(s);

        int startS = 0;
        for(int i = 0 ; i < g.length ; i++){
            for(int j = startS ; j < s.length; ){
                if(g[i] <= s[j]){
                    startS = ++j;
                    result++;
                    continue;
                }
            }
        }
        return result;
    }

    public static int maxSubArray(int[] nums) {
        int[] sum = new int[nums.length];
        sum[0] = nums[0];

        for(int i = 1 ; i < nums.length; i++){
            if(nums[i] < 0){
                sum[i] = Math.max(sum[i-1], nums[i]);
            }else{
                if(sum[i-1] < 0){
                    sum[i] = nums[i];
                }else{
                    sum[i] = sum[i-1] + nums[i];
                }

            }
        }

        return sum[nums.length - 1];
    }

    public static int numTrees(int n) {
        // g(i) = dp[i] ( 1 <= i <=n )
        //  dp[i] = dp[j] * dp[i-j] (1 < j < n)

        int[] dp = new int[n+1];
//        dp[1] = 1;
        int sum = dp[1];
        for(int i = 1 ; i <= n ; i++){
            int curSum = 0;
            for(int j = 1; j < i;j++ ){
                dp[i] = dp[j] * dp[i-j];
                sum += dp[i];
            }
        }

        return sum;
    }
    public static int numSquares(int n) {
        int[] f = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            int minn = Integer.MAX_VALUE;
            for (int j = 1; j * j <= i; j++) {
                minn = Math.min(minn, f[i - j * j]);
            }
            f[i] = minn + 1;
        }
        return f[n];
    }

    public static List<Integer> findAnagrams(String s, String p) {

        List<Integer> resList = new ArrayList<>();
        char[] pChars = p.toCharArray();
        Arrays.sort(pChars);
        int pLen = pChars.length;
        boolean isEqualsFlag = false;

        char[] sChars = s.toCharArray();
        int sLen = sChars.length;
        for(int i = 0 ; i <= sLen - pLen; i++){
            if(isEqualsFlag){
                if(s.charAt(i+pLen-1) == s.charAt(i-1)){
                    resList.add(i);
                    continue;
                }else{
                    isEqualsFlag = false;
                }
            }else{
                char[] subStrChars = s.substring(i, i+pLen).toCharArray();
                Arrays.sort(subStrChars);
                isEqualsFlag = Arrays.equals(pChars, subStrChars);
                if(isEqualsFlag){
                    resList.add(i);
                }
            }
        }

        return resList;

    }

    static int deepRecord;

    public static int maxDepth(TreeNode root) {
        LinkedList<TreeNode> deque = new LinkedList<>();
        if(null != root){
            deque.push(root);
//            deque.offer();
//
//            deque.pop()
//
//            deque.isEmpty()
        }

        while(deque.size() > 0){
            deepRecord++;
            while(deque.size() > 0) {
                TreeNode tempNode = deque.pop();
                if (null != tempNode.right) {
                    deque.push(tempNode.right);
                }

                if (null != tempNode.left) {
                    deque.push(tempNode.left);
                }
            }
        }

        return deepRecord;
    }
    static Integer curSum = 0;

    static TreeNode convertBST(TreeNode root) {
        listNode(root, curSum);
        return root;
    }

    static void listNode(TreeNode root, Integer curSum){
        if(null != root){
            listNode(root.right, curSum);
            curSum+= root.val;
            root.val = curSum;
            listNode(root.left, curSum);
        }
    }

    static int  sum = 0;




    public static boolean isValidBST(TreeNode root) {
//        Integer.MAX_VALUE
        return listNode(root);
    }

    static boolean listNode(TreeNode node){
        // all nodes are null
        if(null == node.left && null == node.right){
            return true;
        }else{
            if(null != node.left && null == node.right){
                return node.left.val < node.val && listNode(node.left);
            }else if(null == node.left && null != node.right){
                return node.right.val >  node.val && listNode(node.right);
            }else{
                return node.left.val < node.val && node.right.val >  node.val && listNode(node.right) && listNode(node.left);
            }
        }
    }

    public static String getPermutation(int n, int k) {
        StringBuilder sb = new StringBuilder();
        return dfs(n, k, 1, 0, sb);
    }

    static String dfs(int n, int k, int index, int count, StringBuilder sb){
        if(sb.length() == n){
            count++;
            if(count == k)
                return sb.toString();
            else
                return null;
        }

        for(int i = index ; i <= n ; i++){
            sb.append(i);
            String str = dfs(n, k, i+1 , count, sb);
            if(str != null && str.length() == n){
                return str;
            }
            sb.deleteCharAt(sb.length() - 1);
        }
        return null;
    }

    public static List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> resList = new ArrayList<>();
        dfs(candidates, target, new LinkedList<Integer>(), 0, resList);
        return resList;
    }

    static void dfs(int[] candidates, int target, LinkedList<Integer> curResList, int index, List<List<Integer>> resList){
        if(target == 0){
            resList.add(new ArrayList<>(curResList));
            return;
        }
        if(target < 0){
            return;
        }

        for(int i = index; i < candidates.length ; i++){
            curResList.addLast(candidates[i]);
            dfs(candidates, target - candidates[i], curResList, i, resList);
            curResList.removeLast();
        }
    }

    public static String countAndSay(int n) {

        int start = 3;
        StringBuilder resSb = new StringBuilder();
        if(n == 1)
            resSb.append(1);
        resSb.append(11);

        String tempStr = resSb.toString();
        while(start <= n){
            tempStr = getStr(tempStr);
            start++;
        }
        return tempStr;
    }

    static String  getStr(String str){
        StringBuilder resSb = new StringBuilder();
        char[] charArray = str.toCharArray();
        int cal = 0, flag = -1;
        for(int i = 0; i < charArray.length ; i++){
            flag = charArray[i] - '0';
            while(i+1 < charArray.length && charArray[i] == charArray[i+1]){
                cal++;
                i++;
            }
            cal++;
            resSb.append(cal + "" + flag);
            if(i == charArray.length - 1){
                break;
            }
            cal = 0; flag = -1;
        }
        return resSb.toString();
    }

    public static ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode tempNode = head;
        int len = 0;
        while(null != tempNode){
            len++;
            tempNode = tempNode.next;
        }

        ListNode head0 = new ListNode(0, head);

        tempNode = head0.next;
        for(int i = 1 ; i < len; i++){
            if(len == n){
                return head0.next.next;
            }
            if(i == len - n){
                tempNode.next = tempNode.next.next;
                break;
            }else{
                tempNode = tempNode.next;
            }
        }

        return head0.next;

    }
}