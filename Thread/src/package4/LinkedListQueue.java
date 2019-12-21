package package4;

//链表实现队列
public class LinkedListQueue {
    // 尾插 + 头删
    private static class Node {
        private int val;
        private Node next = null;

        public Node(int val) {
            this.val = val;
        }
    }

    private Node head = null;
    private Node last = null;
    //入队列(尾插)
    public void put(int val) {
        if (head == null) {
            head = new Node(val);
            last = head;
        } else {
            last.next = new Node(val);
            last = last.next;
        }
    }
    //出队列(头删)
    public int take() {
        if (head == null) {
            throw new RuntimeException("队列是空的");
        }

        int val = head.val;
        head = head.next;
        //当删到头为空时，尾也为空
        if (head == null) {
            last = null;
        }
        return val;
    }
}
