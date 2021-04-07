package Java;

import java.util.Random;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.ReentrantLock;

// A worker thread.
public class TemperatureSensor implements Runnable
{
    public Thread t;
    private String name;
    private int id;
    private int step;
    private int interval;
    private int[] storage;

    TemperatureSensor(String name, int id, int step, int[] storage)
    {
        this.name = name;
        this.id = id;
        this.step = step;
        this.storage = storage;
        interval = 0;
    }

    @Override
    public void run() {
        // Will wait for 10 seconds to take a temp -> which will be like waiting one minute.
        
        while(interval < 60)
        {
            try{
                Thread.sleep(1000);
                synchronized (System.out)
                {
                    Integer tempTaken = takeTemperature(-100, 70);

                    int index = id + (interval * step);
                    
                    storage[index] = tempTaken;

                    interval++;
                }
            }
            catch(InterruptedException e)
            {
                System.out.println("Failure!");
            }
        }

    }
    
    private Integer takeTemperature(int min, int max)
    {
        return ThreadLocalRandom.current().ints(min, max).findFirst().getAsInt();
    }

    public void begin() {
        //System.out.println("Starting " + name);

        if (t == null) {
            t = new Thread(this, name);
            t.start();
        }
    }

}
