package ca.siva.ch08_threads_and_concurrency;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/*
NOTE:
1) ReentrantReadWriteLock: This is the best solution for concurrent reads and a single writer.
The ReadWriteLock allows multiple threads to acquire a read lock simultaneously (i.e., concurrent reads), while ensuring that the write lock is exclusive.
This way, readers don’t block each other, and the writer gets exclusive access when modifying the collection.
2) Writer Priority Over Readers: In general, once a writer thread requests the writeLock,
it is often given priority over new readers to prevent writer starvation (i.e., the writer waiting indefinitely while readers continuously hold the readLock).
This is implementation-dependent but common in many ReadWriteLock implementations.
3) Multiple readers can read simultaneously if no writer is active.
4) A writer blocks all readers and other writers until it finishes.
5) Once the write operation is complete, the read lock can be acquired again by other threads.
6) Lock interface's lock() method returns void, while its tryLock() returns boolean.
7) The first tryLock() returns true because the lock is free, and the thread acquires the lock.
8) The second tryLock() also returns true because the same thread is allowed to reenter the lock without blocking.
9) We need to call unlock() to fully release the lock, once for each tryLock() call.
 */
public class ReadWriteLockExample {
    private static final ReadWriteLock lock = new ReentrantReadWriteLock();
    private static int sharedResource = 0;

    public static void main(String[] args) {
        // Reader thread
        Thread reader1 = new Thread(ReadWriteLockExample::read, "Reader-1");
        Thread reader2 = new Thread(ReadWriteLockExample::read, "Reader-2");

        // Writer thread
        Thread writer = new Thread(ReadWriteLockExample::write, "Writer");

        reader1.start();
        reader2.start();
        writer.start();
    }

    public static void read() {
        lock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + " reading: " + sharedResource);
            Thread.sleep(1000); // Simulate some reading work
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.readLock().unlock();
        }
    }

    public static void write() {
        lock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + " writing...");
            sharedResource++;
            Thread.sleep(2000); // Simulate some writing work
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.writeLock().unlock();
        }
    }
}
