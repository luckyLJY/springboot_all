package com.kk.async.threadpool.core;

import java.util.AbstractQueue;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 自定义阻塞队列
 *
 * @author kkmystery
 * @version 1.0 2021/8/30
 * @since 1.0.0
 */
public class AdaptBlockingQueue<E> extends AbstractQueue<E> implements BlockingQueue<E> {
    private static class Node<E> {
        E item;
        Node<E> next;

        Node(E item, Node<E> next) {
            this.item = item;
            this.next = next;
        }

        Node(E item) {
            this(item, null);
        }
    }

    private volatile int capacity;
    private final AtomicInteger count;
    private final ReentrantLock takeLock;
    private final ReentrantLock putLock;
    private final Condition notEmpty;
    private final Condition notFull;
    private Node<E> head;
    private Node<E> tail;

    public AdaptBlockingQueue() {
        this(Integer.MAX_VALUE);
    }

    public AdaptBlockingQueue(int queueCapacity) {
        count = new AtomicInteger();
        takeLock = new ReentrantLock();
        putLock = new ReentrantLock();
        notEmpty = takeLock.newCondition();
        notFull = putLock.newCondition();
        if (queueCapacity < 0) {
            throw new IllegalArgumentException("队列容量不得小于0!");
        } else {
            capacity = queueCapacity;
            head = tail = new Node<E>(null);
        }
    }

    private void fullyLock() {
        putLock.lock();
        takeLock.lock();
    }

    private void fullyUnlock() {
        takeLock.unlock();
        putLock.unlock();
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("队列容量不得小于0!");
        }
        this.capacity = capacity;
    }

    private void signalNotFull() {
        ReentrantLock putLock = this.putLock;
        putLock.lock();
        try {
            notFull.signal();
        } finally {
            putLock.unlock();
        }
    }

    private void signalNotEmpty() {
        ReentrantLock takeLock = this.takeLock;
        takeLock.lock();
        try {
            notEmpty.signal();
        } finally {
            takeLock.unlock();
        }
    }

    private void enqueue(Node<E> node) {
        tail.next = node;
        tail = tail.next;
    }

    private E dequeue() {
        Node<E> h = head;
        Node<E> first = h.next;
        h.next = h;
        head = first;
        E item = first.item;
        first.item = null;
        return item;
    }

    @Override
    public Iterator<E> iterator() {
        return new Itr();
    }

    @Override
    public int size() {
        return count.get();
    }

    @Override
    public void put(E e) throws InterruptedException {
        if (e == null) {
            throw new IllegalArgumentException("参数不能为null!");
        } else {
            Node<E> node = new Node<>(e);
            ReentrantLock putLock = this.putLock;
            AtomicInteger size = this.count;
            putLock.lockInterruptibly();
            int c = -1;
            try {
                while (size.get() >= capacity) {
                    notFull.await();
                }
                enqueue(node);
                c = size.getAndIncrement();
                if (c + 1 < capacity) {
                    notFull.signal();
                }
            } finally {
                putLock.unlock();
            }
            if (c == 0) {
                signalNotEmpty();
            }
        }
    }

    @Override
    public boolean offer(E e, long l, TimeUnit timeUnit) throws InterruptedException {
        if (e == null) {
            throw new IllegalArgumentException("参数不能为null!");
        } else {
            long nanos = timeUnit.toNanos(l);
            AtomicInteger size = this.count;
            ReentrantLock putLock = this.putLock;
            putLock.lockInterruptibly();
            int c;
            try {
                while (size.get() >= capacity) {
                    if (nanos <= 0L) {
                        return false;
                    }
                    notFull.awaitNanos(nanos);
                }
                enqueue(new Node<E>(e));
                c = size.getAndIncrement();
                if (c + 1 < this.capacity) {
                    notFull.signal();
                }
            } finally {
                putLock.unlock();
            }

            if (c == 0) {
                this.signalNotEmpty();
            }
            return true;
        }
    }

    @Override
    public E take() throws InterruptedException {
        ReentrantLock takeLock = this.takeLock;
        AtomicInteger size = this.count;
        takeLock.lockInterruptibly();
        E e;
        int c;
        try {
            while (size.get() == 0) {
                notEmpty.await();
            }
            e = dequeue();
            c = size.getAndDecrement();
            if (c > 1) {
                notEmpty.signal();
            }
        } finally {
            takeLock.unlock();
        }
        if (c == capacity) {
            signalNotFull();
        }
        return e;
    }

    @Override
    public E poll(long l, TimeUnit timeUnit) throws InterruptedException {
        long nanos = timeUnit.toNanos(l);
        AtomicInteger size = this.count;
        ReentrantLock takeLock = this.takeLock;
        takeLock.lockInterruptibly();
        E e;
        int c;
        try {
            while (true) {
                if (size.get() != 0) {
                    e = dequeue();
                    c = size.getAndDecrement();
                    if (c > 1) {
                        notEmpty.signal();
                    }
                    break;
                }
                if (nanos <= 0L) {
                    return null;
                }
                nanos = notEmpty.awaitNanos(l);
            }
        } finally {
            takeLock.unlock();
        }
        if (c == capacity) {
            signalNotFull();
        }
        return null;
    }

    @Override
    public int remainingCapacity() {
        return Math.max(capacity - count.get(), 0);
    }

    @Override
    public int drainTo(Collection<? super E> collection) {
        return this.drainTo(collection, 2147483647);
    }

    @Override
    public int drainTo(Collection<? super E> collection, int maxElements) {
        Objects.requireNonNull(collection);
        if (collection == this) {
            throw new IllegalArgumentException();
        } else if (maxElements <= 0) {
            return 0;
        } else {
            boolean signalNotFull = false;
            ReentrantLock takeLock = this.takeLock;
            takeLock.lock();

            try {
                int n = Math.min(maxElements, count.get());
                Node<E> h = this.head;
                int i = 0;

                try {
                    while (i < n) {
                        Node<E> p = h.next;
                        collection.add(p.item);
                        p.item = null;
                        h.next = h;
                        h = p;
                        ++i;
                    }

                    return n;
                } finally {
                    if (i > 0) {
                        this.head = h;
                        signalNotFull = count.getAndAdd(-i) == this.capacity;
                    }

                }
            } finally {
                takeLock.unlock();
                if (signalNotFull) {
                    this.signalNotFull();
                }

            }
        }
    }

    @Override
    public boolean offer(E e) {
        if (e == null) {
            throw new IllegalArgumentException("参数不能为null!");
        }
        AtomicInteger size = this.count;
        if (size.get() >= capacity) {
            return false;
        } else {
            ReentrantLock putLock = this.putLock;
            Node<E> node = new Node<>(e);
            putLock.lock();
            int c;
            try {
                if (size.get() >= capacity) {
                    return false;
                }
                enqueue(node);
                c = size.getAndIncrement();
                if (c + 1 < capacity) {
                    notFull.signal();
                }
            } finally {
                putLock.unlock();
            }
            if (c == 0) {
                signalNotEmpty();
            }
            return true;
        }
    }

    @Override
    public boolean remove(Object o) {
        if (o == null) {
            return false;
        } else {
            this.fullyLock();

            boolean var8;
            try {
                Node<E> pred = this.head;

                for (Node<E> p = pred.next; p != null; p = p.next) {
                    if (o.equals(p.item)) {
                        this.unlink(p, pred);
                        return true;
                    }

                    pred = p;
                }

                var8 = false;
            } finally {
                this.fullyUnlock();
            }

            return var8;
        }
    }

    void unlink(Node<E> p, Node<E> pred) {
        p.item = null;
        pred.next = p.next;
        if (this.tail == p) {
            this.tail = pred;
        }
    }

    @Override
    public E poll() {
        AtomicInteger size = this.count;
        if (size.get() == 0) {
            return null;
        } else {
            ReentrantLock takeLock = this.takeLock;
            E e;
            int c;
            try {
                takeLock.lock();
                if (size.get() == 0) {
                    return null;
                }
                e = dequeue();
                c = size.getAndDecrement();
                if (c > 1) {
                    notEmpty.signal();
                }
            } finally {
                takeLock.unlock();
            }
            if (c == capacity) {
                signalNotFull();
            }
            return e;
        }
    }

    @Override
    public E peek() {
        AtomicInteger size = this.count;
        if (size.get() == 0) {
            return null;
        } else {
            ReentrantLock takeLock = this.takeLock;
            takeLock.lock();
            E e;
            try {
                if (size.get() == 0) {
                    return null;
                } else {
                    return head.next.item;
                }
            } finally {
                takeLock.unlock();
            }
        }
    }

    private class Itr implements Iterator<E> {
        private Node<E> next;
        private E nextItem;
        private Node<E> lastRet;
        private Node<E> ancestor;

        Itr() {
            fullyLock();
            try {
                if ((this.next = head.next) != null) {
                    this.nextItem = this.next.item;
                }
            } finally {
                fullyUnlock();
            }

        }

        @Override
        public boolean hasNext() {
            return this.next != null;
        }

        @Override
        public E next() {
            Node<E> p;
            if ((p = this.next) == null) {
                throw new NoSuchElementException();
            } else {
                this.lastRet = p;
                E x = this.nextItem;
                fullyLock();
                try {
                    E e = null;
                    for (p = p.next; p != null && (e = p.item) == null; p = succ(p)) {
                    }
                    this.next = p;
                    this.nextItem = e;
                    return x;
                } finally {
                    fullyUnlock();
                }
            }
        }
    }

    Node<E> succ(Node<E> p) {
        if (p == (p = p.next)) {
            p = this.head.next;
        }
        return p;
    }
}
