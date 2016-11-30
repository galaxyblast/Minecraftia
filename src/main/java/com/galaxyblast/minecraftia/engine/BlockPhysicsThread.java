package com.galaxyblast.minecraftia.engine;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.galaxyblast.minecraftia.Minecraftia;
import com.galaxyblast.minecraftia.engine.tasks.Task;

public class BlockPhysicsThread extends Thread
{
	private boolean isRunning = false;
	private Queue<Task> taskList = new ConcurrentLinkedQueue();
	
	public synchronized void queueTask(Task t)
	{
		this.taskList.offer(t);
		this.notifyAll();
	}
	
	public synchronized void clearQueue()
	{
		this.taskList.clear();
	}
	
	public synchronized void setRunning(boolean b)
	{
		this.isRunning = b;
	}
	
	public synchronized boolean getRunning()
	{
		return this.isRunning;
	}
	
	@SuppressWarnings("unused")
	@Override
	public synchronized void run()
	{
		Task curTask;
		
		while(true)
		{
			if(!this.taskList.isEmpty())
			{
				curTask = this.taskList.poll();
				curTask.execute();
			}
			else
			{
				if(Minecraftia.debugMode && Minecraftia.debugLevel > 1)
					System.out.println("Pausing physics thread.");
				
				try
				{
					synchronized(this)
					{
						this.wait();
					}
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
}
