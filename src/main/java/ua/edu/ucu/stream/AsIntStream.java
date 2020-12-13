package ua.edu.ucu.stream;
import ua.edu.ucu.function.*;
import java.util.ArrayList;

public class AsIntStream implements IntStream {
    private ArrayList<Integer> data;

    private AsIntStream(ArrayList<Integer> values) {
        this.data = values;
    }

    public static IntStream of(int... values) {
        ArrayList<Integer> data = new ArrayList<>();
        for (int value : values) {
            data.add(value);
        }
        return new AsIntStream(data);
    }

    private void isEmpty() {
        if (this.data.size() == 0) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public Double average() {
        isEmpty();
        return ((double) sum() / (double) this.data.size());
    }

    private Integer max_min(boolean flag) {
        isEmpty();
        int result_value = data.get(0);
        for (int value : data) {
            if ((value < result_value) ^ flag) {
                result_value = value;
            }
        }
        return result_value;
    }

    @Override
    public Integer max() {
        return max_min(true);
    }

    @Override
    public Integer min() {
        return max_min(false);
    }

    @Override
    public long count() {
        return this.data.size();
    }

    @Override
    public Integer sum() {
        isEmpty();
        int sum = 0;
        for (int value : data) {
            sum += value;
        }
        return sum;
    }

    @Override
    public IntStream filter(IntPredicate predicate) {
        ArrayList<Integer> newData = new ArrayList<>();
        for (int value : this.data) {
            if (predicate.test(value)) {
                newData.add(value);
            }
        }
        return new AsIntStream(newData);
    }

    @Override
    public void forEach(IntConsumer action) {
        for (int value : this.data) {
            action.accept(value);
        }
    }

    @Override
    public IntStream map(IntUnaryOperator mapper) {
        ArrayList<Integer> newData = new ArrayList<>();
        for (int value : this.data) {
            newData.add(mapper.apply(value));
        }
        return new AsIntStream(newData);
    }

    public ArrayList<Integer> getData() {
        return (ArrayList<Integer>) this.data.clone();
    }

    @Override
    public IntStream flatMap(IntToIntStreamFunction func) {
        ArrayList<Integer> newData = new ArrayList<>();
        for (int value : this.data) {
            newData.addAll(func.applyAsIntStream(value).getData());
        }
        return new AsIntStream(newData);
    }

    @Override
    public int reduce(int identity, IntBinaryOperator op) {
        int result = 0;
        for (int value : this.data) {
            result += op.apply(identity, value);
        }
        return result;
    }

    @Override
    public int[] toArray() {
        int[] result = new int[this.data.size()];
        int counter = 0;
        for (int value : this.data) {
            result[counter] = value;
            counter++;
        }
        return result;
    }

}
