package com.galaxyblast.minecraftia.engine;

import java.util.LinkedList;
import java.util.Queue;

import com.galaxyblast.minecraftia.engine.tasks.Task;

public class BlockPhysics extends Thread
{
	private boolean isRunning = false;
	private Queue<Task> taskList = new LinkedList();
	
	public void queueTask(Task t)
	{
		this.taskList.add(t);
		if(!this.isRunning)
			this.run();
	}
	
	@Override
	public void run()
	{
		Task curTask;
		
		this.isRunning = true;
		while(!this.taskList.isEmpty())
		{
			curTask = this.taskList.remove();
			curTask.execute();
		}
		
		this.isRunning = false;
	}
}
