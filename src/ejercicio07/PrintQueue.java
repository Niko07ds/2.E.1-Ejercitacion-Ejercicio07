package ejercicio07;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Singleton que centraliza todos los trabajos de impresión de la oficina.
 * Holder idiom (inicialización bajo demanda): la instancia se crea de forma perezosa
 * y segura entre hilos, sin sincronizar getInstance(). El acceso concurrente a la cola
 * se protege con métodos synchronized.
 */

public class PrintQueue {

    // Cola única que centraliza los trabajos de impresión
    private final Queue<PrintJob> jobs = new ArrayDeque<>();

    // Constructor privado para impedir otras instancias
    private PrintQueue() {
    }

    // Holder para crear la instancia de forma perezosa y segura
    private static class Holder {
        private static final PrintQueue INSTANCE = new PrintQueue();
    }

    // Devuelve siempre la instancia única
    public static PrintQueue getInstance() {
        return Holder.INSTANCE;
    }

    // Agrega un documento al final de la cola
    public synchronized void enqueue(PrintJob job) {
        if (job == null) {
            throw new IllegalArgumentException(
                    "El trabajo de impresión no puede ser nulo."
            );
        }

        jobs.offer(job);

        System.out.printf(
                "  [ENCOLADO] %s (autor: %s)%n",
                job.fileName(),
                job.author()
        );
    }

    // Procesa el documento más antiguo de la cola
    public synchronized boolean processNext() {
        PrintJob job = jobs.poll();

        if (job == null) {
            return false;
        }

        System.out.printf(
                "  [IMPRESO] %s (autor: %s)%n",
                job.fileName(),
                job.author()
        );

        return true;
    }

    // Devuelve la cantidad de trabajos pendientes
    public synchronized int size() {
        return jobs.size();
    }

    // Comprueba si la cola está vacía
    public synchronized boolean isEmpty() {
        return jobs.isEmpty();
    }
}