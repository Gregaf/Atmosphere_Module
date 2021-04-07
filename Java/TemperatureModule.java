package Java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ConcurrentSkipListSet;

public class TemperatureModule 
{
    private int reportCount = 2;
    private ArrayList<TemperatureSensor> sensors;
    private int sensorCount;
    public int[][] storage;

    TemperatureModule(int sensorCount)
    {
        sensors = new ArrayList<TemperatureSensor>(sensorCount);

        storage = new int[reportCount][sensorCount * 60];

        this.sensorCount = sensorCount;
    }

    // Starts running the worker threads and joins them to calling thread.
    public void BeginLogging()
    {
        int currentReport = 0;
        
        while(currentReport < reportCount)
        {
            sensors.clear();

            for(int i = 0; i < sensorCount; i++)
            {
                TemperatureSensor newSensor = new TemperatureSensor("Sensor " + i, i, sensorCount, storage[currentReport]);
                
                sensors.add(newSensor);
            }

            for (TemperatureSensor sensor : sensors) {
                sensor.begin();
            }

            for (TemperatureSensor sensor : sensors) {
                
                try
                {
                    sensor.t.join();
                }
                catch(InterruptedException e)
                {

                }
            }

            for (Integer num : storage[currentReport]) {
                System.out.print(num + " ");
            }
            // Start ReportGenerate Task
            System.out.println("\n");

            currentReport++;
        }
        // Create a report Generator that copies the storage
        // Generating the report on a seperate thread that creates a text file.

    }
    
    private class ReportGenerator {

        ReportGenerator() 
        {

        }
    }

}
