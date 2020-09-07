/*
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

/*
 *
 *
 *
 *
 *
 * Written by Doug Lea with assistance from members of JCP JSR-166
 * Expert Group and released to the public domain, as explained at
 * http://creativecommons.org/publicdomain/zero/1.0/
 */

package java.util.concurrent.locks;

/**
 * A {@code ReadWriteLock} maintains a pair of associated {@link
 * Lock locks}, one for read-only operations and one for writing.
 * The {@link #readLock read lock} may be held simultaneously by
 * multiple reader threads, so long as there are no writers.  The
 * {@link #writeLock write lock} is exclusive.
 * <p>
 * ReadWriteLock维护一对关联的Lock，一个用于只读操作，一个用于写入操作。
 * 只要没有写线程，读锁可以同时有多个读线程。这个写锁是独占的。
 *
 * <p>All {@code ReadWriteLock} implementations must guarantee that
 * the memory synchronization effects of {@code writeLock} operations
 * (as specified in the {@link Lock} interface) also hold with respect
 * to the associated {@code readLock}. That is, a thread successfully
 * acquiring the read lock will see all updates made upon previous
 * release of the write lock.
 * <p>
 * 所有的读写锁的实现必须保证写锁操作的内存同步效果，也要考虑读锁的关联代码。也就是说，
 * 一个读锁线程成功首先依赖于它看到所有的写锁的释放的前提。
 *
 * <p>A read-write lock allows for a greater level of concurrency in
 * accessing shared data than that permitted by a mutual exclusion lock.
 * It exploits the fact that while only a single thread at a time (a
 * <em>writer</em> thread) can modify the shared data, in many cases any
 * number of threads can concurrently read the data (hence <em>reader</em>
 * threads).
 * In theory, the increase in concurrency permitted by the use of a read-write
 * lock will lead to performance improvements over the use of a mutual
 * exclusion lock. In practice this increase in concurrency will only be fully
 * realized on a multi-processor, and then only if the access patterns for
 * the shared data are suitable.
 * <p>
 * 一个读写锁允许在访问非互斥锁允许的共享数据。它利用了这样一个事实，虽然只有一个线程可以修改共享数据，
 * 但在许多情况下，任何数量的线程都可以并发地读取数据。
 * 理论上，使用读写锁所允许的并发性的增加将比互斥锁的使用带来性能改进。实际上，只有在多处理器上，并且只有在共享
 * 数据的访问模式合适的情况下，才能完全实现并发性的增加。
 *
 * <p>Whether or not a read-write lock will improve performance over the use
 * of a mutual exclusion lock depends on the frequency that the data is
 * read compared to being modified, the duration of the read and write
 * operations, and the contention for the data - that is, the number of
 * threads that will try to read or write the data at the same time.
 * For example, a collection that is initially populated with data and
 * thereafter infrequently modified, while being frequently searched
 * (such as a directory of some kind) is an ideal candidate for the use of
 * a read-write lock. However, if updates become frequent then the data
 * spends most of its time being exclusively locked and there is little, if any
 * increase in concurrency. Further, if the read operations are too short
 * the overhead of the read-write lock implementation (which is inherently
 * more complex than a mutual exclusion lock) can dominate the execution
 * cost, particularly as many read-write lock implementations still serialize
 * all threads through a small section of code. Ultimately, only profiling
 * and measurement will establish whether the use of a read-write lock is
 * suitable for your application.
 * <p>
 * 在使用互斥锁的情况下，读写锁是否能提高性能,取决于读取数据与修改数据的频率、读写操作的持续时间
 * 以及对数据的争用—即，尝试同时读取或写入数据的线程数时间。例如，一个最初用数据填充，
 * 后来很少修改的集合，而经常被搜索（例如某种目录）是使用读写锁的理想候选。但是，如果更新变得频繁，
 * 那么数据的大部分时间都被独占锁定，并且并发性几乎没有增加。此外，如果读操作太短，读写锁实现的开销
 * （本质上比互斥锁更复杂）可以控制执行成本，特别是许多读写锁实现仍然通过一小段代码序列化所有线程。
 * 最终，只有分析和测量才能确定使用读写锁是否适合您的应用程序。
 *
 * <p>Although the basic operation of a read-write lock is straight-forward,
 * there are many policy decisions that an implementation must make, which
 * may affect the effectiveness of the read-write lock in a given application.
 * Examples of these policies include:
 *
 * 虽然读写锁的基本操作是直接的，但是实现必须做出许多策略决策，
 * 这可能会影响给定应用程序中读写锁的有效性。这些政策的例子包括：
 *
 * <ul>
 * <li>Determining whether to grant the read lock or the write lock, when
 * both readers and writers are waiting, at the time that a writer releases
 * the write lock. Writer preference is common, as writes are expected to be
 * short and infrequent. Reader preference is less common as it can lead to
 * lengthy delays for a write if the readers are frequent and long-lived as
 * expected. Fair, or &quot;in-order&quot; implementations are also possible.
 *
 * 当读卡器和写入器都在等待时，在写入器释放写锁时，确定是授予读锁还是写锁。
 * 编写器首选项是常见的，因为写操作被认为是短而不频繁的。读者偏好不太常见，
 * 因为如果读卡器如预期的那样频繁且寿命长，那么它会导致写操作的长时间延迟。也可以采用“公平”或“按顺序”实现。
 *
 * <li>Determining whether readers that request the read lock while a
 * reader is active and a writer is waiting, are granted the read lock.
 * Preference to the reader can delay the writer indefinitely, while
 * preference to the writer can reduce the potential for concurrency.
 *
 * <li>Determining whether the locks are reentrant: can a thread with the
 * write lock reacquire it? Can it acquire a read lock while holding the
 * write lock? Is the read lock itself reentrant?
 *
 * 确定在读卡器处于活动状态且写入器正在等待时请求读锁的读卡器是否被授予读锁。
 * 对读卡器的偏好可能会无限期地延迟编写器，而对编写器的偏好则会降低并发的可能性。
 *
 * <li>Can the write lock be downgraded to a read lock without allowing
 * an intervening writer? Can a read lock be upgraded to a write lock,
 * in preference to other waiting readers or writers?
 *
 * 是否可以将写锁降级为读锁而不允许中间写入程序？是否可以将读锁升级为写锁，而不是其他等待的读卡器或写入器？
 *
 * </ul>
 * You should consider all of these things when evaluating the suitability
 * of a given implementation for your application.
 *
 * 在评估给定实现对应用程序的适用性时，应该考虑所有这些因素。
 *
 * @author Doug Lea
 * @see ReentrantReadWriteLock
 * @see Lock
 * @see ReentrantLock
 * @since 1.5
 */
public interface ReadWriteLock {
    /**
     * Returns the lock used for reading.
     *
     * @return the lock used for reading
     */
    Lock readLock();

    /**
     * Returns the lock used for writing.
     *
     * @return the lock used for writing
     */
    Lock writeLock();
}
