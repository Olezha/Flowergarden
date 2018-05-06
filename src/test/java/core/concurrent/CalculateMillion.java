package core.concurrent;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/*
 * There is next method that has to calculate long i between 0 … 1 000 000 by increment.
 * Stop when long i will be 1 000 000. Share i value and time.
 *
 * Compare this code with:
 * 1. single code;
 * 2. synchronized classic code with 4 threads and extends Thread;
 * 3. with 4 threads and implement Runnable;
 * 4. by FixedThreadPool(4) with Future with ReentrantLock;
 * 5. by FixedThreadPool(4) with Future with ReadWriteLock;
 * 6. by FixedThreadPool(4) with Future with AtomicLong.
 */
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@BenchmarkMode(Mode.AverageTime)
@Fork(value = 1, warmups = 0)
@Warmup(iterations = 0)
@Measurement(iterations = 1, time = 1)
public class CalculateMillion {

    static final long MILLION = 1_000_000;

    public static void main(String[] args) throws Exception {
        org.openjdk.jmh.Main.main(args);
    }

    @State(Scope.Benchmark)
    public static class SingleThreadState {
        long i = 0;

        @TearDown(Level.Trial)
        public void doTearDown() {
            System.out.print("i=" + i + "; t=");
        }
    }

    @Benchmark
    public void singleThread(SingleThreadState state) {
        while (state.i < MILLION) {
            state.i++;
        }
    }

    @State(Scope.Benchmark)
    public static class LongResourceState {
        LongResource resource = new LongResource();

        @TearDown(Level.Trial)
        public void doTearDown() {
            System.out.print("i=" + resource.i + "; t=");
        }
    }

    @Benchmark
    public void classicSynchronizedWith4ThreadsAndExtendsThread(LongResourceState state) {
        for (int i = 0; i < 4; i++) {
            ExtendsThreadProcess extendsThreadProcess = new ExtendsThreadProcess(state.resource);
            extendsThreadProcess.start();
        }
    }

    @Benchmark
    public void through4ThreadsAndImplementsRunnable(LongResourceState state) {
        for (int i = 0; i < 4; i++) {
            Thread thread = new Thread(new ImplementsRunnableProcess(state.resource));
            thread.start();
        }
    }

    @Benchmark
    public void byFixedThreadPool4WithFutureWithReentrantLock(LongResourceState state, Blackhole blackhole) throws ExecutionException, InterruptedException {
        ReentrantLock reentrantLock = new ReentrantLock();
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        for (int i = 0; i < 4; i++) {
            Future<LongResource> future = executorService.submit(new ImplementsCallableProcess(state.resource, reentrantLock));
            blackhole.consume(future.get().i);
        }
        executorService.shutdown();
    }

    @Benchmark
    public void byFixedThreadPool4WithFutureWithReadWriteLock(LongResourceState state, Blackhole blackhole) throws ExecutionException, InterruptedException {
        ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        for (int i = 0; i < 4; i++) {
            Future<LongResource> future = executorService.submit(new ImplementsCallableProcess(state.resource, readWriteLock.writeLock()));
            blackhole.consume(future.get().i);
        }
        executorService.shutdown();
    }

    @State(Scope.Benchmark)
    public static class AtomicLongResourceState {
        AtomicLong resource = new AtomicLong();

        @TearDown(Level.Trial)
        public void doTearDown() {
            System.out.print("i=" + resource.get() + "; t=");
        }
    }

    @Benchmark
    public void byFixedThreadPool4WithFutureWithAtomicLong(AtomicLongResourceState state, Blackhole blackhole) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        for (int i = 0; i < 4; i++) {
            Future<AtomicLong> future = executorService.submit(new AtomicLongProcess(state.resource));
            blackhole.consume(future.get());
        }
        executorService.shutdown();
    }
}

class ExtendsThreadProcess extends Thread {

    private final LongResource i;

    ExtendsThreadProcess(LongResource i) {
        this.i = i;
    }

    public void run() {
        while (i.i < CalculateMillion.MILLION) {
            synchronized (i) {
                if (i.i < CalculateMillion.MILLION)
                    i.i++;
            }
        }
    }
}

class ImplementsRunnableProcess implements Runnable {

    private final LongResource i;

    ImplementsRunnableProcess(LongResource i) {
        this.i = i;
    }

    @Override
    public void run() {
        while (i.i < CalculateMillion.MILLION) {
            synchronized (i) {
                if (i.i < CalculateMillion.MILLION)
                    i.i++;
            }
        }
    }
}

class ImplementsCallableProcess implements Callable<LongResource> {

    private final LongResource i;
    private final Lock lock;

    ImplementsCallableProcess(LongResource i, Lock lock) {
        this.i = i;
        this.lock = lock;
    }

    @Override
    public LongResource call() throws Exception {
        while (i.i < CalculateMillion.MILLION) {
            lock.lock();
            try {
                if (i.i < CalculateMillion.MILLION)
                    i.i++;
            } finally {
                lock.unlock();
            }
        }
        return i;
    }
}

class AtomicLongProcess implements Callable<AtomicLong> {

    private final AtomicLong i;

    AtomicLongProcess(AtomicLong i) {
        this.i = i;
    }

    @Override
    public AtomicLong call() throws Exception {
        while (i.get() < CalculateMillion.MILLION) {
            i.incrementAndGet();
        }
        return i;
    }
}

class LongResource {
    long i;
}
