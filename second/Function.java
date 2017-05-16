package second;

import java.util.LinkedList;
import java.util.List;

public class Function {

    private int start, end, count; // стартовая точка, конечная, число точек между ними
    private List<Double> values; // значения фукнции на count точках

    /**
     * Конструктор
     * Инициализируем функцию, задавая ей отрезок и число точек между ними
     * @param a - начало отрезка
     * @param b - конец отрезка
     * @param count - число точек между ними
     */
    public Function(int a, int b, int count) {
        this.start = a;
        this.end = b;
        this.count = count;
        this.values = new LinkedList<>();
        initValues();
    }

    /**
     * Получаем значение функции из отрезка по индексу
     * @param index - индекс (порядок) значения функции в списке
     */
    public double getValue(int index) {
        return values.get(index);
    }

    /**
     * Получаем список всех значений функции на отрезке
     */
    public List<Double> getValues() {
        return values;
    }

    /**
     * Инициализируем функцию, т.е рассчитываем ее значения от a до b
     */
    private void initValues() {
        double xStep = (double) (end - start) / (count - 1); // шаг между двумя точками отрезка аргументов
        for (double i = start; i <= end * 2; i += xStep) {
            values.add(calculate(i));
        }
    }

    /**
     * Возвращаем значение функции по аргументу (функция из варианта в методе)
     * @param t - аргумент
     */
    private double calculate(double t) {
        return 0.5 * Math.exp(0.5 * Math.cos(0.5 * t)) + Math.sin(0.5 * t);
    }
}
