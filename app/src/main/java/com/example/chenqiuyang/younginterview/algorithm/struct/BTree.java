package com.example.chenqiuyang.younginterview.algorithm.struct;


import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 二叉树方法
 */
public class BTree {
    /**
     * 构造二叉树
     */
    public static TreeNode createBTree(int[] arr) {
        return createBTree(arr, 0, arr.length);
    }

    //构造二叉树
    private static TreeNode createBTree(int[] arr, int i, int length) {
        if (i >= length) {
            return null;
        }
        TreeNode cur = new TreeNode(arr[i]);
        cur.left = createBTree(arr, 2 * i + 1, length);
        cur.right = createBTree(arr, 2 * i + 2, length);

        return cur;
    }

    /**
     * 236. 二叉树的最近公共祖先
     */
    public static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (hasTreeNode(root.left, p.val) && hasTreeNode(root.left, q.val)) { //都在左子树
            return lowestCommonAncestor(root.left, p, q);
        }
        if (hasTreeNode(root.right, p.val) && hasTreeNode(root.right, q.val)) { //都在右子树
            return lowestCommonAncestor(root.right, p, q);
        }
        return root;


//        if (root == null || root == A || root == B) {
//            return root;
//        }
//        TreeNode left = lowestCommonAncestor(root.left, A, B);
//        TreeNode right = lowestCommonAncestor(root.right, A, B);
//
//        return left == null ? right : right == null ? left : root;
    }

    //判断节点是否在二叉树内
    public static boolean hasTreeNode(TreeNode root, int target) {
        if (root == null) {
            return false;
        }

        if (root.val == target) {
            return true;
        }

        return hasTreeNode(root.left, target) || hasTreeNode(root.right, target);
    }


    /**
     * 104. 二叉树的最大深度
     * 递归方法
     */
    public static int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int max;
        int maxLeft = maxDepth(root.left);
        int maxRight = maxDepth(root.right);
        max = maxLeft > maxRight ? maxLeft : maxRight;
        return max + 1;
    }


    /**
     * 先序遍历 144. 二叉树的前序遍历
     * 迭代算法
     * 用栈实现，先进后出
     */
    public List<Integer> preorderTraversal(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }
        Stack<TreeNode> stack = new Stack<>();
        List<Integer> list = new ArrayList<>();
        stack.push(root);
        while (!stack.empty()) {
            TreeNode treeNode = stack.pop();
            list.add(treeNode.val);
            if (null != treeNode.right) {
                stack.push(treeNode.right);
            }
            if (null != treeNode.left) {
                stack.push(treeNode.left);
            }
        }
        return list;
    }

    /**
     * 中序遍历 94. 二叉树的中序遍历
     * 迭代算法
     * 用栈实现，先进后出
     */
    public static List<Integer> inorderTraversal(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }
        TreeNode cur = root;
        Stack<TreeNode> stack = new Stack<>();
        List<Integer> list = new ArrayList<>();
        stack.push(root);
        while (!stack.empty()) {
            TreeNode treeNode = stack.pop();
            list.add(treeNode.val);
            if (null != treeNode.right) {
                stack.push(treeNode.right);
            }
            if (null != treeNode.left) {
                stack.push(treeNode.left);
            }
        }
        return list;
    }

    /**
     * 输出二叉树
     * type:0 先序
     * type:1 中序
     * type:2 后序
     */
    public static void printBTree(TreeNode root, int type) {
        switch (type) {
            case 0:
                printBTreeI(root);
                break;
            case 1:
                printBTreeII(root);
                break;
            case 2:
                printBTreeIII(root);
                break;
            default:
                break;
        }
    }

    //输出二叉树 先序输出 递归算法
    private static void printBTreeI(TreeNode root) {
        System.out.print(" ");
        if (root == null) {
            System.out.print("null");
            return;
        }
        System.out.print(root.val);
        printBTreeI(root.left);
        printBTreeI(root.right);
    }

    //输出二叉树 中序输出 递归算法
    private static void printBTreeII(TreeNode root) {
        System.out.print(" ");
        if (root == null) {
            System.out.print("null");
            return;
        }
        printBTreeIII(root.left);
        System.out.print(root.val);
        printBTreeIII(root.right);
    }

    //输出二叉树 后序输出 递归算法
    private static void printBTreeIII(TreeNode root) {
        System.out.print(" ");
        if (root == null) {
            System.out.print("null");
            return;
        }
        printBTreeIII(root.left);
        printBTreeIII(root.right);
        System.out.print(root.val);
    }


}
