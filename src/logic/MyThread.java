package logic;

public abstract class MyThread implements Runnable {

	private Thread thread;
	private boolean running;
	private boolean pause;

	public MyThread() {
		thread = new Thread(this);
	}

	public void start() {// iniciar
		thread.start();
		running = true;
		pause = false;
	}

	public synchronized void stop() {// matar
		running = false;
	}

	public synchronized void pause() {// detener
		pause = true;
	}

	public synchronized void resume() {// reanudar
		pause = false;
		notify();
	}

	public synchronized void join() {
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.out.println("Soy el join fallando");
		}
	}

	public void sleep() {
		try {
			Thread.sleep(getSleep());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (running) {
			executeTask();
			sleep();
			synchronized (this) {
				while (pause) {
					try {
						wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				if (!running) {
					break;
				}
			}
		}

	}

	protected abstract void executeTask();

	protected abstract long getSleep();

}

// synchronized () { el programador tiene que recordar poner esto en
// cada objeto que tiene que ser soncronizado
// }
// concurrencia de hilos,
// lambda, scala, kruster
/**
 * metodos de tread metodo
 * 
 * hilos de interfaz timer swing worker
 */