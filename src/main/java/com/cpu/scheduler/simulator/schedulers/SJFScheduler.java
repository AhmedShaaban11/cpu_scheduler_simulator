package com.cpu.scheduler.simulator.schedulers;

import java.util.*;

class SProcess {
    private String name;
    private int arrivalTime, burstTime, responseTime, finishTime, waitingTime, turnAroundTime;
    public boolean isSProcessDone;

    public SProcess(String name, int arrivalTime, int burstTime) {
        this.isSProcessDone = false;
        this.name = name;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
    }

    public void setResponseTime(int time) {
        responseTime = time;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void doCalc() {
        finishTime = responseTime + burstTime;
        waitingTime = responseTime - arrivalTime;
        turnAroundTime = finishTime - arrivalTime;
    }

    public void print() {
        System.out.println("name : " + name + " , " +
                "arrival : " + arrivalTime + " , " +
                "burst : " + burstTime + " , " +
                "finish : " + finishTime + " , " +
                "waiting : " + waitingTime + " , " +
                "turn around : " + turnAroundTime);
    }
}

class SProcessComparator implements Comparator<SProcess> {
    @Override
    public int compare(SProcess below, SProcess above) {
        if (below.getBurstTime() > above.getBurstTime()) {
            return 1;
        } else if (below.getBurstTime() == above.getBurstTime() && below.getArrivalTime() > above.getArrivalTime()) {
            return 1;
        }
        return -1;
    }
}

public class SJFScheduler {

    public static void SJF(List<SProcess> processes) {
        PriorityQueue<SProcess> processesQueue = new PriorityQueue<>(new SProcessComparator());
        Queue<SProcess> processesFinalOrder = new LinkedList<>();
        PriorityQueue<Integer> arrivalTimeQueue = new PriorityQueue<>(Comparator.naturalOrder()); // to handle the gaps between arrival times
        
        int currentTime = processes.get(0).getArrivalTime();

        for (SProcess process : processes) {     //get the first arrived process
            arrivalTimeQueue.add(process.getArrivalTime());
            currentTime = Math.min(currentTime, process.getArrivalTime());
        }

        for (SProcess process : processes) {    //add the first arrived process to the queue
            if (process.getArrivalTime() == currentTime) {
                process.isSProcessDone = true;
                processesQueue.add(process);
                arrivalTimeQueue.poll();
            }
        }

        while (!processesQueue.isEmpty()) {
            SProcess currentSProcess = processesQueue.poll();
            processesFinalOrder.add(currentSProcess);
            
            //update the served process data
            currentSProcess.setResponseTime(currentTime);
            currentSProcess.doCalc();
            currentTime += currentSProcess.getBurstTime();
            
            //take the second continuouse arrival time if there is a gap (processor is idle) 
            if (!arrivalTimeQueue.isEmpty() && currentTime < arrivalTimeQueue.peek()) {
                currentTime = arrivalTimeQueue.peek();
                arrivalTimeQueue.poll();
            }

            for (SProcess process : processes) {
                if (!process.isSProcessDone && process.getArrivalTime() <= currentTime) {
                    process.isSProcessDone = true;
                    processesQueue.add(process);
                    arrivalTimeQueue.poll();
                }
            }
        }

        while (!processesFinalOrder.isEmpty()) {
            SProcess currentSProcess = processesFinalOrder.poll();
            currentSProcess.print();
        }
    }

    public static void main(String[] args) {
        SProcess p1 = new SProcess("p1", 2, 4);
        SProcess p2 = new SProcess("p2", 6, 3);
        SProcess p3 = new SProcess("p3", 10, 17);
        SProcess p4 = new SProcess("p4", 10, 1);
        
        List<SProcess> processes = Arrays.asList(p1, p2, p3, p4);

        SJF(processes);
    }
}