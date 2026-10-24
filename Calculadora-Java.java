import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Calculadora de enteros (v1.0 + CR-001 potencia + CR-002 movimiento lineal del camión).
 */
public class Calculadora {

    // ---------- Operaciones aritméticas (una por método) ----------
    public static long suma(int a, int b)           { return (long) a + b; }
    public static long resta(int a, int b)          { return (long) a - b; }
    public static long multiplicacion(int a, int b) { return (long) a * b; }

    /** Devuelve el cociente decimal. Lanza ArithmeticException si b == 0. */
    public static double division(int a, int b) {
        if (b == 0) {
            throw new ArithmeticException("Error: no se puede dividir por cero.");
        }
        return (double) a / b;
    }

    /** CR-001: a^b con ciclo for. Solo exponentes enteros >= 0. */
    public static long potencia(int a, int b) {
        if (b < 0) {
            throw new IllegalArgumentException("Error: el exponente debe ser un entero mayor o igual a 0.");
        }
        long resultado = 1;
        for (int i = 0; i < b; i++) {
            try {
                resultado = Math.multiplyExact(resultado, (long) a);
            } catch (ArithmeticException e) {
                throw new IllegalArgumentException("Error: el resultado de la potencia excede el rango permitido.");
            }
        }
        return resultado;
    }

    // ---------- CR-002: movimiento lineal del camión ----------
    public static double desplazamientoTotal(double[] x) {
        return x[x.length - 1] - x[0];
    }

    public static double distanciaTotal(double[] x) {
        double d = 0;
        for (int i = 1; i < x.length; i++) d += Math.abs(x[i] - x[i - 1]);
        return d;
    }

    public static double velocidadMedia(double x1, double x2, double t1, double t2) {
        if (t2 == t1) throw new ArithmeticException("Error: intervalo de tiempo igual a cero.");
        return (x2 - x1) / (t2 - t1);
    }

    // ---------- Entrada segura ----------
    private static int leerEntero(Scanner sc, String msg) {
        while (true) {
            System.out.print(msg);
            try {
                return sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Error: solo se aceptan números enteros.");
                sc.next(); // descarta la entrada inválida
            }
        }
    }

    private static double leerNumero(Scanner sc, String msg) {
        while (true) {
            System.out.print(msg);
            try {
                return sc.nextDouble();
            } catch (InputMismatchException e) {
                System.out.println("Error: ingrese un número válido.");
                sc.next();
            }
        }
    }

    private static void calculadora(Scanner sc) {
        int a = leerEntero(sc, "Ingrese el primer entero (a): ");
        int b = leerEntero(sc, "Ingrese el segundo entero (b): ");
        System.out.println("a = " + a + ", b = " + b);
        System.out.println(a + " + " + b + " = " + suma(a, b));
        System.out.println(a + " - " + b + " = " + resta(a, b));
        System.out.println(a + " * " + b + " = " + multiplicacion(a, b));
        try {
            System.out.println(a + " / " + b + " = " + division(a, b));
        } catch (ArithmeticException e) {
            System.out.println(e.getMessage());
        }
        try {
            System.out.println(a + " ^ " + b + " = " + potencia(a, b));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void camion(Scanner sc) {
        int n = leerEntero(sc, "Número de registros (mínimo 2): ");
        if (n < 2) { System.out.println("Error: se requieren al menos 2 registros."); return; }
        double[] t = new double[n], x = new double[n];
        for (int i = 0; i < n; i++) {
            t[i] = leerNumero(sc, "t[" + i + "] (s): ");
            x[i] = leerNumero(sc, "x[" + i + "] (m): ");
            if (i > 0 && t[i] <= t[i - 1]) {
                System.out.println("Error: el tiempo debe ser creciente. Repita el registro.");
                i--;
            }
        }
        System.out.println("a) Desplazamiento total: " + desplazamientoTotal(x) + " m");
        System.out.println("b) Distancia total: " + distanciaTotal(x) + " m");
        System.out.println("c) Velocidad media global: "
                + velocidadMedia(x[0], x[n - 1], t[0], t[n - 1]) + " m/s");
        System.out.println("d) Velocidad media por intervalo:");
        for (int i = 1; i < n; i++) {
            System.out.println("   [" + t[i - 1] + " s - " + t[i] + " s]: "
                    + velocidadMedia(x[i - 1], x[i], t[i - 1], t[i]) + " m/s");
        }
    }

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            int op;
            do {
                System.out.println("\n=== MENÚ ===\n1. Calculadora (5 operaciones)\n2. Movimiento lineal del camión\n0. Salir");
                op = leerEntero(sc, "Opción: ");
                if (op == 1) calculadora(sc);
                else if (op == 2) camion(sc);
                else if (op != 0) System.out.println("Opción no válida.");
            } while (op != 0);
        }
    }
}
