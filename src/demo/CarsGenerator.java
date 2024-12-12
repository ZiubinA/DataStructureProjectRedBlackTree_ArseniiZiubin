package demo;



import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class CarsGenerator {

    private boolean isStart = true;

    private Deque<Car> cars;

    public Car[] generateShuffle(int setSize, double shuffleCoef) {
        return generateShuffle(setSize, setSize, shuffleCoef);
    }

    /**
     * @param setSize
     * @param setTake
     * @param shuffleCoef
     * @return Car array of setSize length

     */
    public Car[] generateShuffle(int setSize,
                                 int setTake,
                                 double shuffleCoef) {

        Car[] cars = IntStream.range(0, setSize)
                .mapToObj(i -> new Car.Builder().buildRandom())
                .toArray(Car[]::new);
        return shuffle(cars, setTake, shuffleCoef);
    }

    public Car takeCar() {
        if (cars == null || cars.isEmpty()) {
            throw new IllegalStateException("The car set is empty");
        }
        // Once the Car is taken from the beginning of the array, the next time from the end
        isStart = !isStart;
        return isStart ? cars.pollFirst() : cars.pollLast();
    }

    private Car[] shuffle(Car[] cars, int amountToReturn, double shuffleCoef) {
        if (cars == null) {
            throw new IllegalArgumentException("No cars (null)");
        }
        if (amountToReturn < 0) {
            throw new IllegalStateException(String.valueOf(amountToReturn));
        }
        if (cars.length < amountToReturn) {
            throw new IllegalStateException(cars.length + " >= " + amountToReturn);
        }
        if (shuffleCoef < 0 || shuffleCoef > 1) {
            throw new IllegalStateException(String.valueOf(shuffleCoef));
        }

        int amountToLeave = cars.length - amountToReturn;
        int startIndex = (int) (amountToLeave * shuffleCoef / 2);

        Car[] takeToReturn = Arrays.copyOfRange(cars, startIndex, startIndex + amountToReturn);
        Car[] takeToLeave = Stream
                .concat(Arrays.stream(Arrays.copyOfRange(cars, 0, startIndex)),
                        Arrays.stream(Arrays.copyOfRange(cars, startIndex + amountToReturn, cars.length)))
                .toArray(Car[]::new);

        Collections.shuffle(Arrays.asList(takeToReturn)
                .subList(0, (int) (takeToReturn.length * shuffleCoef)));
        Collections.shuffle(Arrays.asList(takeToLeave)
                .subList(0, (int) (takeToLeave.length * shuffleCoef)));

        this.cars = Arrays.stream(takeToLeave).collect(Collectors.toCollection(ArrayDeque::new));
        return takeToReturn;
    }
}
