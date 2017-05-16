package second;

import java.util.Arrays;

public class Net {

    private Function function; // Функция из варианта методы
    private double[] weights; // Веса
    private double[] functionValues; // Значения функции

    private int count; // Общее число значений функции
    private int windowWidth; // Ширина окна
    private double learningNorm; // Норма обучения

    /**
     * Конструктор
     * Инициализируем сеть, создавая объект функции (из варианта методы) и веса (равные 0 на 1 эпохе)
     * @param count - число значений функции
     * @param windowWidth - ширина окна
     * @param start - стартовая точка отрезка
     * @param end - конечная точка отрезка
     * @param learningNorm - норма обучения
     */
    public Net(int count, int windowWidth, int start, int end, double learningNorm) {
        this.count = count;
        this.learningNorm = learningNorm;
        this.function = new Function(start, end, count);
        this.windowWidth = windowWidth;
        this.weights = new double[windowWidth + 1];
        this.functionValues = new double[count];
        initialize();
    }

    /**
     * Функция инициализации
     * Берем истинные значения из объекта типа Function
     * Их количество зависит от ширины окна
     * Т.е если у нас ширина окна 4 - берем только первые 4 истинных значения,
     * исходя из которых, мы будем определять остальные 16
     */
    private void initialize() {
        for (int i = 0; i < windowWidth; i++) {
            functionValues[i] = function.getValue(i);
        }
    }

    /**
     * Функция, предсказывающее следующее значение функции на отрезке
     * @param index - порядковый номер этого значения в массиве
     */
    private void predictNext(int index) {
        functionValues[index] = weights[0];
        for (int i = 1; i <= windowWidth; i++) {
            functionValues[index] += weights[i] * function.getValue(index - windowWidth + i - 1);
        }
        correctWeights(index);
    }

    /**
     * Корректировка весов по правилу Видроу-Хоффа (как в 1 лабе)
     * @param index - порядковый номер этого значения в массиве
     */
    private void correctWeights(int index) {
        double error = function.getValue(index) - functionValues[index];
        weights[0] += error * learningNorm;
        for (int i = 1; i <= windowWidth; i++) {
            weights[i] += error * learningNorm * functionValues[index - windowWidth + i - 1];
        }
    }

    /**
     * Эпоха
     * Тупо проходим от конца окна до конца самого массива значений функции
     * и пытаемся предсказать эти значения
     */
    private void epoch() {
        for (int i = windowWidth; i < count; i++) {
            predictNext(i);
        }
    }

    /**
     * Определение среднеквадратической ошибки
     * (Формула в методе)
     */
    private double getError() {
        double error = 0.0;
        for (int i = 0; i < count; i++) {
            error += Math.pow(function.getValue(i) - functionValues[i], 2);
        }
        return Math.sqrt(error);
    }

    /**
     * Начало обучения сети
     * Просто запускаем 4000 эпох, примерно после такого количества (зависит от варика, конечно)
     * сеть неплохо предсказывает значения
     */
    public boolean start() {
        for (int i = 0; i < 4000; i++) {
            epoch();
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder
                .append("Веса: ")
                .append(Arrays.toString(weights))
                .append("\n")
                .append("Истинные значения: ")
                .append(function.getValues())
                .append("\n")
                .append("Спрогнозированные: ")
                .append(Arrays.toString(functionValues))
                .append("\n")
                .append("Суммарная ошибка: ")
                .append(getError());
        return builder.toString();
    }

}
