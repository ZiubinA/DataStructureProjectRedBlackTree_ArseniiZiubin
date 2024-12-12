package demo;

import utils.AvlSet;
import utils.BstSet;
import utils.BstSetIterative;
import utils.SortedSet;
import org.openjdk.jmh.infra.BenchmarkParams;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@State(Scope.Benchmark)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(time = 1, timeUnit = TimeUnit.SECONDS)
public class Benchmark {
    @Param({"1000", "2000", "4000", "8000"})
    public int iterationCount;

    Car[] cars;
    SortedSet<Car> carsThousandSet = new BstSet<>();

    @Setup(Level.Iteration)
    public void generateElements() {
        cars = generateElements(1000);
        for (Car car : cars) {
            carsThousandSet.add(car);
        }
    }

    static Car[] generateElements(int count) {
        return new CarsGenerator().generateShuffle(count, 1.0);
    }

    @org.openjdk.jmh.annotations.Benchmark
    public AvlSet<Car> addAvlAllRecursive() {
        AvlSet<Car> carSetMoreThousend = new AvlSet<>(Car.byPrice);
        for(int i = 0; i < iterationCount; i++)
        {
            carSetMoreThousend.addAll(carsThousandSet);
        }
        return carSetMoreThousend;
    }

    @org.openjdk.jmh.annotations.Benchmark
    public BstSet<Car> addBstAllRecursive() {
        BstSet<Car> carSetMoreThousend = new BstSet<>(Car.byPrice);
        for(int i = 0; i < iterationCount; i++)
        {
            carSetMoreThousend.addAll(carsThousandSet);
        }
        return carSetMoreThousend;
    }


    /*@org.openjdk.jmh.annotations.Benchmark
    public BstSetIterative<Car> addBstIterative() {
        BstSetIterative<Car> carSet = new BstSetIterative<>(Car.byPrice);
        addElements(cars, carSet);
        return carSet;
    }
@org.openjdk.jmh.annotations.Benchmark
    public BstSet<Car> addBstRecursive() {
        BstSet<Car> carSet = new BstSet<>(Car.byPrice);
        addElements(car, carSet);
        return carSet;
    }
    @org.openjdk.jmh.annotations.Benchmark
    public AvlSet<Car> addAvlRecursive() {
        AvlSet<Car> carSet = new AvlSet<>(Car.byPrice);
        addElements(cars, carSet);
        return carSet;
    }

    @org.openjdk.jmh.annotations.Benchmark
    public void removeBst(FullSet carSet) {
        for (Car car : carSet.cars) {
            carSet.carSet.remove(car);
        }
    }

    @org.openjdk.jmh.annotations.Benchmark
    public void sizeBst(FullSet carSet) {
        for (Car car : carSet.cars) {
            carSet.carSet.size();
        }
    }
    public static void addElements(Car[] carArray, SortedSet<Car> carSet) {
        for (Car car : carArray) {
            carSet.add(car);
        }
    }
    */

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(Benchmark.class.getSimpleName())
                .forks(1)
                .build();
        new Runner(opt).run();
    }
}
