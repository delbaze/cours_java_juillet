@FunctionalInterface
public interface Evaluateur<T> {
    double evaluer(T t);

    default String formater(T objet) {
        return String.format("%.2f €", evaluer(objet));
    }
}
